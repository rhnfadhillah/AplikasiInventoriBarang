package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StokHelper {
    
    // Mengambil semua data stok dengan nama barang
    public List<Object[]> tampilData() {
        List<Object[]> dataStok = new ArrayList<>();
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        // Query untuk mengambil data stok dan nama barang
        String query = "SELECT b.nama_barang, s.stok FROM stok s JOIN barang b ON s.id_barang = b.id_barang"; 
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String namaBarang = resultSet.getString("nama_barang");
                int stok = resultSet.getInt("stok");
                dataStok.add(new Object[]{namaBarang, stok}); // Menambahkan data ke list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection(); // Pastikan koneksi ditutup
        }
        return dataStok; // Mengembalikan list stok
    }
}