/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rhnfa
 */
public class TransaksiHelper {
     private DatabaseConnection conn;

    public TransaksiHelper() {
        conn = new DatabaseConnection();
    }
    
   public void tambahTransaksi(int id_stok, String jenis_transaksi, int jumlah_barang) {
        LocalDate today = LocalDate.now();
        String tanggal_transaksi = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String query = "INSERT INTO transaksi(id_stok, jenis_transaksi, jumlah_barang, tanggal_transaksi) VALUES(?, ?, ?, ?)";
        try (Connection connection = conn.getConnection(); 
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id_stok);
            pstmt.setString(2, jenis_transaksi);
            pstmt.setInt(3, jumlah_barang);
            pstmt.setString(4, tanggal_transaksi);
            pstmt.executeUpdate();
            System.out.println("Transaksi berhasil ditambahkan.");
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan transaksi: " + e.getMessage());
        }
    }
   public void updateTransaksi(int id_transaksi, int id_stok, String jenis_transaksi, int jumlah_barang) {
        LocalDate today = LocalDate.now();
        String tanggal_transaksi = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String query = "UPDATE transaksi SET id_stok = ?, jenis_transaksi = ?, jumlah_barang = ?, tanggal_transaksi = ? WHERE id_transaksi = ?";
        try (Connection connection = conn.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id_stok);
            pstmt.setString(2, jenis_transaksi);
            pstmt.setInt(3, jumlah_barang);
            pstmt.setString(4, tanggal_transaksi);
            pstmt.setInt(5, id_transaksi); // Menetapkan ID transaksi yang akan diperbarui
            pstmt.executeUpdate();
            System.out.println("Transaksi berhasil diperbarui.");
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui transaksi: " + e.getMessage()); // Pesan kesalahan yang lebih informatif
        }
    }
   
   public void hapusTransaksi(int id_Transaksi) {
        String query = "DELETE FROM transaksi WHERE id_transaksi = ?";
        try (Connection connection = conn.getConnection(); // Perbaikan di sini
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_Transaksi);
            statement.executeUpdate();
            System.out.println("Transaksi berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Error saat menghapus transaksi: " + e.getMessage()); // Pesan kesalahan yang lebih informatif
        }
    }
   
  public List<String[]> tampilData() {
    List<String[]> daftarTransaksi = new ArrayList<>();
    String query = "SELECT t.id_transaksi, b.nama_barang, t.jenis_transaksi, t.jumlah_barang, t.tanggal_transaksi " +
                   "FROM transaksi t " +
                   "JOIN stok s ON t.id_stok = s.id_stok " + // Menyambungkan dengan tabel stok
                   "JOIN barang b ON s.id_barang = b.id_barang"; // Menyambungkan dengan tabel barang

    try (Connection connection = conn.getConnection();
         Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            String[] transaksi = new String[5]; // Ubah ukuran array menjadi 5
            transaksi[0] = rs.getString("id_transaksi"); // Ambil id_transaksi
            transaksi[1] = rs.getString("nama_barang");
            transaksi[2] = rs.getString("jenis_transaksi");
            transaksi[3] = String.valueOf(rs.getInt("jumlah_barang"));
            transaksi[4] = rs.getString("tanggal_transaksi");
            daftarTransaksi.add(transaksi);
        }
    } catch (SQLException e) {
        System.err.println("Error saat mengambil daftar transaksi: " + e.getMessage());
    }
    return daftarTransaksi;
}
}
