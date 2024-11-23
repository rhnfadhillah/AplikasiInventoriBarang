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
     private DatabaseConnection conn; // Objek untuk mengelola koneksi ke database.

    // Konstruktor untuk menginisialisasi objek DatabaseConnection.
    public TransaksiHelper() {
        conn = new DatabaseConnection();
    }
    
    // Method untuk menambah transaksi
   public void tambahTransaksi(int id_stok, String jenis_transaksi, int jumlah_barang) {
        LocalDate today = LocalDate.now(); // Mendapatkan tanggal saat ini.
        String tanggal_transaksi = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Format tanggal.
        String query = "INSERT INTO transaksi(id_stok, jenis_transaksi, jumlah_barang, tanggal_transaksi) VALUES(?, ?, ?, ?)";
        try (Connection connection = conn.getConnection(); 
            PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Mengisi parameter query dengan data.
                pstmt.setInt(1, id_stok);
                pstmt.setString(2, jenis_transaksi);
                pstmt.setInt(3, jumlah_barang);
                pstmt.setString(4, tanggal_transaksi);
                pstmt.executeUpdate(); // Menjalankan query.
                System.out.println("Transaksi berhasil ditambahkan."); // Pesan sukses.
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan transaksi: " + e.getMessage()); // Pesan error.
        }
    }
   
   // Method untuk mengubah data transaksi
   public void updateTransaksi(int id_transaksi, int id_stok, String jenis_transaksi, int jumlah_barang) {
        LocalDate today = LocalDate.now();  // Mendapatkan tanggal saat ini.
        String tanggal_transaksi = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Format tanggal.
        String query = "UPDATE transaksi SET id_stok = ?, jenis_transaksi = ?, jumlah_barang = ?, tanggal_transaksi = ? WHERE id_transaksi = ?";
        try (Connection connection = conn.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query)) {
             // Mengisi parameter query dengan data.
                pstmt.setInt(1, id_stok);
                pstmt.setString(2, jenis_transaksi);
                pstmt.setInt(3, jumlah_barang);
                pstmt.setString(4, tanggal_transaksi);
                pstmt.setInt(5, id_transaksi); // Menetapkan ID transaksi yang akan diperbarui
                pstmt.executeUpdate(); // Menjalankan query.
                System.out.println("Transaksi berhasil diperbarui."); // Pesan sukses.
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui transaksi: " + e.getMessage()); // Pesan error.
        }
    }
   
   // Method untuk menghapus data transaksi
   public void hapusTransaksi(int id_Transaksi) {
        String query = "DELETE FROM transaksi WHERE id_transaksi = ?";
        try (Connection connection = conn.getConnection(); // Perbaikan di sini
            PreparedStatement statement = connection.prepareStatement(query)) {
              // Mengisi parameter query dengan ID transaksi.
                statement.setInt(1, id_Transaksi);
                statement.executeUpdate(); // Menjalankan query.
                System.out.println("Transaksi berhasil dihapus."); // Pesan sukses.
        } catch (SQLException e) {
            System.err.println("Error saat menghapus transaksi: " + e.getMessage()); // Pesan error.
        }
    }
   
   // Method untuk menampilkan data transaksi dan nama barang dengan melakuka join antara tabel stok dan tabel barang
    public List<String[]> tampilData() {
      List<String[]> daftarTransaksi = new ArrayList<>();  // List untuk menyimpan daftar transaksi.

      // Query untuk mengambil data transaksi dan melakukan join dengan tabel stok dan barang.
      String query = "SELECT t.id_transaksi, b.nama_barang, t.jenis_transaksi, t.jumlah_barang, t.tanggal_transaksi " +
                     "FROM transaksi t " +
                     "JOIN stok s ON t.id_stok = s.id_stok " + // Menyambungkan dengan tabel stok
                     "JOIN barang b ON s.id_barang = b.id_barang"; // Menyambungkan dengan tabel barang

      try (Connection connection = conn.getConnection();
          Statement stmt = connection.createStatement();
          ResultSet rs = stmt.executeQuery(query)) {
          // Iterasi melalui setiap baris hasil query.
              while (rs.next()) {
                  // Menyimpan data transaksi ke dalam array String.
                  String[] transaksi = new String[5];
                  transaksi[0] = rs.getString("id_transaksi"); // ID transaksi.
                  transaksi[1] = rs.getString("nama_barang"); // Nama barang.
                  transaksi[2] = rs.getString("jenis_transaksi");  // Jenis transaksi.
                  transaksi[3] = String.valueOf(rs.getInt("jumlah_barang")); // Jumlah barang.
                  transaksi[4] = rs.getString("tanggal_transaksi"); // Tanggal transaksi.
                  daftarTransaksi.add(transaksi); // Menambahkan ke list.
              }
      } catch (SQLException e) {
          System.err.println("Error saat mengambil daftar transaksi: " + e.getMessage());  // Pesan error.
      }
      return daftarTransaksi; // Mengembalikan list transaksi.
  }
}
