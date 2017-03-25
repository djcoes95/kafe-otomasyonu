package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by Bilal on 24.03.2017.
 */

public class Urunler {
    private String urunadi;
    private String urunfiyati;
    public Urunler(String urunadi , String urunfiyati){
        this.urunadi = urunadi;
        this.urunfiyati = urunfiyati;
    }
    public String getUrunadi() {
        return urunadi;
    }

    public void setUrunadi(String urunadi) {
        this.urunadi = urunadi;
    }

    public String getUrunfiyati() {
        return urunfiyati;
    }

    public void setUrunfiyati(String urunfiyati) {
        this.urunfiyati = urunfiyati;
    }
}
