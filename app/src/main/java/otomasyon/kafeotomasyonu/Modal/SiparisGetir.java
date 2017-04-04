package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by Bilal on 4.04.2017.
 */

public class SiparisGetir {

    private String urunadi;
    private int urunsayisi;

    public SiparisGetir(String urunadi, int urunsayisi){
        this.urunadi = urunadi;
        this.urunsayisi = urunsayisi;
    }

    public int getUrunsayisi() {
        return urunsayisi;
    }

    public void setUrunsayisi(int urunsayisi) {
        this.urunsayisi = urunsayisi;
    }

    public String getUrunadi() {
        return urunadi;
    }

    public void setUrunadi(String urunadi) {
        this.urunadi = urunadi;
    }


}
