/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

// Import kelas model dan view yang dibutuhkan
import Model.BarangHelper; // Kelas model untuk berinteraksi dengan data barang
import Model.TransaksiHelper;// Kelas model untuk berinteraksi dengan data transaksi
import View.MenuTransaksi; // Kelas view untuk menampilkan menu transaksi
import java.util.List; // Import List untuk manipulasi koleksi data

/**
 *
 * @author rhnfa
 */
public class TransaksiController {
    private TransaksiHelper transaksiHelper;  // Objek model untuk mengelola data transaksi
    private MenuTransaksi transaksiView; // Objek view untuk berinteraksi dengan tampilan transaksi
    private BarangHelper barangHelper; // Objek model untuk mengambil data barang
    
    public TransaksiController(MenuTransaksi transaksiView){
        this.transaksiView = transaksiView; // Inisialisasi view transaksi
        this.transaksiHelper = new TransaksiHelper(); // Inisialisasi model transaksi
        barangHelper = new BarangHelper();  // Inisialisasi model barang
    }
    
    // Menampilkan data Transaksi
    public void loadTransaksiData(){
        List<String[]> daftarTransaksi = transaksiHelper.tampilData(); // Mengambil data transaksi dari model
        transaksiView.updateTransaksiTabel(daftarTransaksi); // Mengupdate tampilan tabel transaksi di view
    }
    
    //Menambahkan data Transaksi
    public void tambahTransaksi(int id_stok, String jenis_transaksi, int jumlah_barang) {
       transaksiHelper.tambahTransaksi(id_stok, jenis_transaksi, jumlah_barang); // Menambahkan data transaksi melalui model
        loadTransaksiData(); // Memuat ulang data transaksi untuk memperbarui tampilan
        
    }
    
    //Mengubah data Transaksi
    public void updateTransaksi(int id_transaksi, int id_stok, String jenis_transaksi, int jumlah_barang) {
       transaksiHelper.updateTransaksi(id_transaksi, id_stok, jenis_transaksi, jumlah_barang); // Mengubah data transaksi melalui model
        loadTransaksiData(); // Memuat ulang data transaksi untuk memperbarui tampilan
    }
    
    //Menghapus data Transaksi
    public void hapusTransaksi(int id_transaksi){
        transaksiHelper.hapusTransaksi(id_transaksi); // Menghapus data transaksi melalui model
        loadTransaksiData(); // Memuat ulang data transaksi untuk memperbarui tampilan
    }
    
    //Mengambil nama dan id_stok barang
    public List<String[]> getNamaDanId() {
        return barangHelper.getNamaDanId(); // Mengambil data nama dan id_stok dari model barang
    }
}
