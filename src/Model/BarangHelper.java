package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarangHelper {
    private DatabaseConnection conn;

    public BarangHelper() {
        conn = new DatabaseConnection();
    }

    public void tambahBarang(String namaBarang, String jenisBarang) {
        String query = "INSERT INTO barang(nama_barang, jenis_barang) VALUES(?, ?)";
        try (Connection connection = conn.getConnection(); // Perbaikan di sini
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, namaBarang);
            pstmt.setString(2, jenisBarang);
            pstmt.executeUpdate();
            System.out.println("Barang berhasil ditambahkan.");
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan barang: " + e.getMessage()); // Pesan kesalahan yang lebih informatif
        }
    }

    public List<String[]> tampilData() {
        List<String[]> daftarBarang = new ArrayList<>();
        String query = "SELECT * FROM barang";
        try (Connection connection = conn.getConnection(); // Perbaikan di sini
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idBarang = resultSet.getInt("id_barang"); // Pastikan nama kolom sesuai
                String namaBarang = resultSet.getString("nama_barang");
                String jenisBarang = resultSet.getString("jenis_barang");
                daftarBarang.add(new String[]{String.valueOf(idBarang), namaBarang, jenisBarang});
            }
        } catch (SQLException e) {
            System.err.println("Error saat menampilkan data: " + e.getMessage()); // Pesan kesalahan yang lebih informatif
        }
        return daftarBarang;
    }

    public void updateBarang(int idBarang, String namaBarangBaru, String jenisBarangBaru) {
        String query = "UPDATE barang SET nama_barang = ?, jenis_barang = ? WHERE id_barang = ?";
        try (Connection connection = conn.getConnection(); // Perbaikan di sini
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, namaBarangBaru);
            statement.setString(2, jenisBarangBaru);
            statement.setInt(3, idBarang);
            statement.executeUpdate();
            System.out.println("Barang berhasil diperbarui.");
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui barang: " + e.getMessage()); // Pesan kesalahan yang lebih informatif
        }
    }

    public void hapusBarang(int idBarang) {
        String query = "DELETE FROM barang WHERE id_barang = ?";
        try (Connection connection = conn.getConnection(); // Perbaikan di sini
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idBarang);
            statement.executeUpdate();
            System.out.println("Barang berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Error saat menghapus barang: " + e.getMessage()); // Pesan kesalahan yang lebih informatif
        }
    }
    
     public List<String[]> getNamaDanId() {
        List<String[]> daftarBarang = new ArrayList<>();
        String query = "SELECT s.id_stok, b.nama_barang FROM stok s JOIN barang b ON s.id_barang = b.id_barang"; // Ganti dengan nama tabel dan kolom yang sesuai

         try (Connection connection = conn.getConnection();
              PreparedStatement pstmt = connection.prepareStatement(query);
              ResultSet rs = pstmt.executeQuery()) {
             while (rs.next()) {
                 String[] barang = new String[2];
                 barang[0] = String.valueOf(rs.getInt("id_stok")); // ID Stok
                 barang[1] = rs.getString("nama_barang"); // Nama Barang
                 daftarBarang.add(barang);
             }
         } catch (SQLException e) {
             System.err.println("Error saat mengambil daftar barang: " + e.getMessage());
         }
         return daftarBarang;
     }
    
}