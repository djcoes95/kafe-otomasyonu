package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by Bilal on 24.03.2017.
 */

public class Urunler {
    private String urunAdi;
    private String fiyat;
    public Urunler(String urunAdi , String fiyat){
        this.urunAdi = urunAdi;
        this.fiyat = fiyat;
    }
    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }
}
