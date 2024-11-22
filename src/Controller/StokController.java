/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.StokHelper;
import View.MenuStok;
import java.util.List;


public class StokController {
    private MenuStok menuStok;
    private StokHelper stokHelper;
    
    public StokController(MenuStok menuStok) {
        this.menuStok = menuStok;
        this.stokHelper = new StokHelper();
    }

    public void loadStokData() {
        List<Object[]> daftarStok = stokHelper.tampilData();
        menuStok.updateStokTable(daftarStok);
    }
}
