package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection { 
    private static final String URL = "jdbc:sqlite:inventoriDB"; // Nama file database SQLite

    private Connection connection;

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;

    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Koneksi ke database ditutup.");
            } catch (SQLException e) {
                System.err.println("Gagal menutup koneksi.");
                e.printStackTrace();
            }
        }
    }
}