/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

// Import kelas yang dibutuhkan
import Model.StokHelper; // Kelas model untuk mengelola data stok
import View.MenuStok;  // Kelas view untuk menampilkan data stok ke pengguna
import java.util.List; // Import List untuk manipulasi koleksi data

/**
 *
 * @author rhnfa
 */

public class StokController {
    private MenuStok menuStok; // Objek view untuk berinteraksi dengan tampilan stok
    private StokHelper stokHelper;  // Objek model untuk berinteraksi dengan data stok
    
    public StokController(MenuStok menuStok) {
        this.menuStok = menuStok;  // Menghubungkan controller dengan view
        this.stokHelper = new StokHelper(); // Membuat instance helper untuk akses data stok
    }
    
    //Menampilkan data stok
    public void loadStokData() {
        List<Object[]> daftarStok = stokHelper.tampilData(); // Mengambil data stok dari model
        menuStok.updateStokTable(daftarStok);   // Mengupdate tampilan tabel stok di view
    }
}
