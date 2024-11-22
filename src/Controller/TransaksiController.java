/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BarangHelper;
import Model.TransaksiHelper;
import View.MenuTransaksi;
import java.util.List;

/**
 *
 * @author rhnfa
 */
public class TransaksiController {
    private TransaksiHelper transaksiHelper;
    private MenuTransaksi transaksiView;
    private BarangHelper barangHelper;
    
    public TransaksiController(MenuTransaksi transaksiView){
        this.transaksiView = transaksiView;
        this.transaksiHelper = new TransaksiHelper();
        barangHelper = new BarangHelper();
    }
    
    public void loadTransaksiData(){
        List<String[]> daftarTransaksi = transaksiHelper.tampilData();
        transaksiView.updateTransaksiTabel(daftarTransaksi);
    }
    
    public void tambahTransaksi(int id_stok, String jenis_transaksi, int jumlah_barang) {
       transaksiHelper.tambahTransaksi(id_stok, jenis_transaksi, jumlah_barang);
        loadTransaksiData(); // Memuat ulang data setelah menambah barang
        
    }

    public void updateTransaksi(int id_transaksi, int id_stok, String jenis_transaksi, int jumlah_barang) {
       transaksiHelper.updateTransaksi(id_transaksi, id_stok, jenis_transaksi, jumlah_barang);
        loadTransaksiData(); // Memuat ulang data setelah menambah barang
    }
    
    public void hapusTransaksi(int id_transaksi){
        transaksiHelper.hapusTransaksi(id_transaksi);
        loadTransaksiData();
    }
    
    public List<String[]> getNamaDanId() {
        return barangHelper.getNamaDanId();
    }
}
