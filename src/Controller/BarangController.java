/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

// Import kelas yang dibutuhkan
import Model.BarangHelper; // Kelas model untuk berinteraksi dengan data barang
import View.MenuBarang;  // Kelas view untuk mengelola tampilan barang

import java.util.List; // Import List untuk manipulasi koleksi data

/**
 *
 * @author rhnfa
 */


public class BarangController {
    private BarangHelper barangHelper; // Objek untuk berinteraksi dengan model (data barang)
    private MenuBarang menuBarangView; // Objek untuk berinteraksi dengan view (tampilan)

     // Konstruktor untuk inisialisasi objek controller
    public BarangController(MenuBarang menuBarangView) {
        this.menuBarangView = menuBarangView; // Inisialisasi view
        this.barangHelper = new BarangHelper(); // Inisialisasi helper model
    }

    // Mengambil semua barang dan mengupdate tampilan
    public void loadBarangData() {
        List<String[]> daftarBarang = barangHelper.tampilData(); // Mengambil data barang dari model
        menuBarangView.updateBarangTable(daftarBarang); // Mengirim data ke view untuk ditampilkan
    }

    // Menambahkan barang baru
    public void tambahBarang(String namaBarang, String jenisBarang) {
        barangHelper.tambahBarang(namaBarang, jenisBarang);  // Menambahkan barang melalui model
        loadBarangData(); // Memperbarui data tampilan setelah penambahan
        
    }

    // Mengupdate barang yang ada
    public void updateBarang(int idBarang, String namaBarangBaru, String jenisBarangBaru) {
        barangHelper.updateBarang(idBarang, namaBarangBaru, jenisBarangBaru); // Mengupdate data barang di model
        loadBarangData();  // Memperbarui data tampilan setelah pengubahan
    }

    // Menghapus barang
    public void hapusBarang(int idBarang) {
        barangHelper.hapusBarang(idBarang); // Menghapus barang melalui model
        loadBarangData(); // Memperbarui data tampilan setelah penghapusan
    }
}