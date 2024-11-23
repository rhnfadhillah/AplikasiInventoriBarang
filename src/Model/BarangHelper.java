/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

// Import library untuk koneksi ke database
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rhnfa
 */

public class BarangHelper {
    private DatabaseConnection conn; // Objek koneksi ke database
    
    // Inisialisasi koneksi
    public BarangHelper() {
        conn = new DatabaseConnection();
    }
    
    // Method untuk menambah data barang
    public void tambahBarang(String namaBarang, String jenisBarang) {
        String query = "INSERT INTO barang(nama_barang, jenis_barang) VALUES(?, ?)";
        try (Connection connection = conn.getConnection();  // Membuka koneksi database
            PreparedStatement pstmt = connection.prepareStatement(query)) { // Menyiapkan query
                pstmt.setString(1, namaBarang); // Mengisi parameter pertama dengan nama barang
                pstmt.setString(2, jenisBarang); // Mengisi parameter kedua dengan jenis barang
                pstmt.executeUpdate(); // Menjalankan query
                System.out.println("Barang berhasil ditambahkan."); // Notifikasi sukses
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan barang: " + e.getMessage()); // Penanganan error
        }
    }

    // Method untuk menampilkan data barang
    public List<String[]> tampilData() {
        List<String[]> daftarBarang = new ArrayList<>(); // List untuk menyimpan hasil
        String query = "SELECT * FROM barang"; // Query untuk mengambil semua data
        try (Connection connection = conn.getConnection(); // Membuka koneksi database
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) { // Eksekusi query
                while (resultSet.next()) {
                    int idBarang = resultSet.getInt("id_barang"); // Mengambil ID barang
                    String namaBarang = resultSet.getString("nama_barang"); // Mengambil nama
                    String jenisBarang = resultSet.getString("jenis_barang"); // Mengambil jenis barang
                    daftarBarang.add(new String[]{String.valueOf(idBarang), namaBarang, jenisBarang}); // Menambah ke daftar
                }
        } catch (SQLException e) {
            System.err.println("Error saat menampilkan data: " + e.getMessage()); // Penanganan error
        }
        return daftarBarang; // Mengembalikan daftar barang
    }
    
    //Method untuk mengupdate data barang
    public void updateBarang(int idBarang, String namaBarangBaru, String jenisBarangBaru) {
        String query = "UPDATE barang SET nama_barang = ?, jenis_barang = ? WHERE id_barang = ?";
        try (Connection connection = conn.getConnection(); // Perbaikan di sini
            PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, namaBarangBaru); // Mengisi parameter pertama dengan namaBarangBaru
                statement.setString(2, jenisBarangBaru); // Mengisi parameter kedua dengan jenisBarangBaru
                statement.setInt(3, idBarang); // Mengisi parameter ketiga dengan idBarang
                statement.executeUpdate(); // Menjalankan query
                System.out.println("Barang berhasil diperbarui."); // Menampilkan pesan berhasil
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui barang: " + e.getMessage()); //Penanganan Error
        }
    }
    
    // Method untuk menghapus barang
    public void hapusBarang(int idBarang) {
        String query = "DELETE FROM barang WHERE id_barang = ?";
        try (Connection connection = conn.getConnection(); // Membuka koneksi ke database
            PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idBarang); // Mengisi parameter pertama dengan idbarang
                statement.executeUpdate(); // Menjalankan query
                System.out.println("Barang berhasil dihapus."); // Menampilkan pesan berhasil
        } catch (SQLException e) {
            System.err.println("Error saat menghapus barang: " + e.getMessage()); // Penanganan Error
        }
    }
    
    //Melakukan join antara stok dan barang untuk mengambil nama barang dan id_stok dari barang yang dipilih
     public List<String[]> getNamaDanId() {
        List<String[]> daftarBarang = new ArrayList<>(); // List untuk menyimpan hasil
        String query = "SELECT s.id_stok, b.nama_barang FROM stok s JOIN barang b ON s.id_barang = b.id_barang"; // Query join

         try (Connection connection = conn.getConnection(); // Membuka koneksi database
              PreparedStatement pstmt = connection.prepareStatement(query);
              ResultSet rs = pstmt.executeQuery()) { // Eksekusi query
                while (rs.next()) {
                    String[] barang = new String[2];
                    barang[0] = String.valueOf(rs.getInt("id_stok")); // Mengambil id_stok
                    barang[1] = rs.getString("nama_barang"); // Mengambil nama barang
                    daftarBarang.add(barang); // Menambah ke daftar
                }
         } catch (SQLException e) {
             System.err.println("Error saat mengambil daftar barang: " + e.getMessage()); // Penanganan Error
         }
         return daftarBarang; // Mengembalikan daftar barang
     }
    
     public boolean barangExists(String namaBarang) {
        String sql = "SELECT COUNT(*) FROM barang WHERE nama_barang = ?";
        try (Connection connection = conn.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, namaBarang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Pastikan ada hasil
                return rs.getInt(1) > 0; // Kembalikan true jika barang ada
            }   
        } catch (SQLException e) {
            System.err.println("Error saat melakukan cek barang: " + e.getMessage());
        }
         return false;
    }
     
     public void cekDanTambahBarang(String namaBarang, int stok) {
        String jenisBarang = "Barang Baru";
        if (!barangExists(namaBarang)) { // Cek apakah barang sudah ada
            // Tambahkan barang terlebih dahulu
            String insertBarangQuery = "INSERT INTO barang(nama_barang, jenis_barang) VALUES(?, ?)";
            try (Connection connection = conn.getConnection(); 
                PreparedStatement pstmt = connection.prepareStatement(insertBarangQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, namaBarang);
                pstmt.setString(2, jenisBarang);
                pstmt.executeUpdate(); // Menjalankan query untuk menambah barang
                // Ambil ID barang yang baru ditambahkan
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idBarangBaru = generatedKeys.getInt(1); // Ambil id_barang yang baru ditambahkan
                    // Perbarui stok menggunakan id_barang
                    String updateStokQuery = "UPDATE stok SET stok = stok + ? WHERE id_barang = ?";
                    try (PreparedStatement pstmtUpdate = connection.prepareStatement(updateStokQuery)) {
                        pstmtUpdate.setInt(1, stok); // Tambahkan jumlah stok
                        pstmtUpdate.setInt(2, idBarangBaru); // ID barang yang baru
                        int rowsAffected = pstmtUpdate.executeUpdate(); // Menjalankan query untuk memperbarui stok               
                        if (rowsAffected > 0) {
                            System.out.println("Jumlah stok berhasil diperbarui untuk barang dengan ID: " + idBarangBaru);
                        } else {
                            System.out.println("Tidak ada stok yang diperbarui. Pastikan ID barang ada di tabel stok.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error saat menambahkan barang dan memperbarui stok: " + e.getMessage());
            }
        } else {
            System.out.println("Barang sudah ada: " + namaBarang); // Notifikasi jika barang sudah ada
        }
    }
}