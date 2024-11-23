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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rhnfa
 */


public class StokHelper {
    
    // Mengambil semua data stok dengan nama barang
    public List<Object[]> tampilData() {
        List<Object[]> dataStok = new ArrayList<>(); // List untuk menyimpan data stok.
        
        // Membuat instance dari DatabaseConnection untuk mengatur koneksi.
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();  // Membuka koneksi

        // Query untuk mengambil data stok dan nama barang
        String query = "SELECT b.nama_barang, s.stok FROM stok s JOIN barang b ON s.id_barang = b.id_barang"; 
        try (PreparedStatement statement = connection.prepareStatement(query);  // Menggunakan PreparedStatement untuk menyiapkan dan mengeksekusi query.
            ResultSet resultSet = statement.executeQuery()) {  // Menyimpan hasil eksekusi query ke dalam ResultSet.
            // Iterasi melalui setiap baris dalam hasil query.
                while (resultSet.next()) {
                    // Mengambil data dari kolom "nama_barang" dan "stok".
                    String namaBarang = resultSet.getString("nama_barang");
                    int stok = resultSet.getInt("stok");
                    dataStok.add(new Object[]{namaBarang, stok}); // Menambahkan data ke dalam list sebagai array objek.
                }
        } catch (SQLException e) {
            e.printStackTrace(); // Menangkap dan mencetak error jika terjadi masalah dengan SQL.
        } finally {
            dbConnection.closeConnection(); // Memastikan koneksi ke database ditutup untuk mencegah kebocoran sumber daya.
        }
        return dataStok; // Mengembalikan list yang berisi data stok.
    }
}