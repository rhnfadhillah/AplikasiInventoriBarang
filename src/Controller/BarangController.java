package Controller;

import Model.BarangHelper;
import View.MenuBarang;

import java.util.List;

public class BarangController {
    private BarangHelper barangHelper;
    private MenuBarang menuBarangView;

    public BarangController(MenuBarang menuBarangView) {
        this.menuBarangView = menuBarangView;
        this.barangHelper = new BarangHelper();
    }

    // Mengambil semua barang dan mengupdate tampilan
    public void loadBarangData() {
        List<String[]> daftarBarang = barangHelper.tampilData();
        menuBarangView.updateBarangTable(daftarBarang);
    }

    // Menambahkan barang baru
    public void tambahBarang(String namaBarang, String jenisBarang) {
        barangHelper.tambahBarang(namaBarang, jenisBarang);
        loadBarangData(); // Memuat ulang data setelah menambah barang
        
    }

    // Mengupdate barang yang ada
    public void updateBarang(int idBarang, String namaBarangBaru, String jenisBarangBaru) {
        barangHelper.updateBarang(idBarang, namaBarangBaru, jenisBarangBaru);
        loadBarangData(); // Memuat ulang data setelah mengupdate barang
    }

    // Menghapus barang
    public void hapusBarang(int idBarang) {
        barangHelper.hapusBarang(idBarang);
        loadBarangData(); // Memuat ulang data setelah menghapus barang
    }
}