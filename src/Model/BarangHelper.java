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
import java.util.ArrayList;
import java.util.List;

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
    
}