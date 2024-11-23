/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package Model;

// Mengimpor kelas-kelas yang diperlukan untuk koneksi database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author rhnfa
 */


public class DatabaseConnection { 
    private static final String URL = "jdbc:sqlite:inventoriDB";  // URL database SQLite. Lokasi file database bernama "inventoriDB".
    
    // Variabel untuk menyimpan koneksi ke database.
    private Connection connection;
    
    
    // Method untuk membuka koneksi
    public Connection getConnection() {
        Connection connection = null; // Variabel lokal untuk menyimpan koneksi sementara
        try {
            connection = DriverManager.getConnection(URL);   // Membuka koneksi menggunakan DriverManager dan URL yang ditentukan.
        } catch (SQLException e) {
            e.printStackTrace();   // Menangkap dan mencetak error jika koneksi gagal.
        }

        return connection; // Mengembalikan objek koneksi (null jika gagal).

    }
    
    // Method untuk menutup koneksi
    public void closeConnection() {
        if (connection != null) {    // Memeriksa apakah koneksi tidak null sebelum mencoba menutup.
            try {
                connection.close(); // Menutup koneksi ke database.
                System.out.println("Koneksi ke database ditutup.");
            } catch (SQLException e) {
                System.err.println("Gagal menutup koneksi.");   // Menangkap error jika terjadi kegagalan saat menutup koneksi.
                e.printStackTrace();
            }
        }
    }
}