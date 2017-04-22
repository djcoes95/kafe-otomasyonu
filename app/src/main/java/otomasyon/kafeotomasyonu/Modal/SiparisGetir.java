package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by Bilal on 4.04.2017.
 */

public class SiparisGetir {

    private String urunadi;
    private int urunid;
    private int urunsayisi;

    public SiparisGetir(String urunadi, int urunid,int urunsayisi){
        this.urunadi = urunadi;
        this.urunid = urunid;
        this.urunsayisi = urunsayisi;
    }

    public int getUrunsayisi() {
        return urunsayisi;
    }

    public void setUrunsayisi(int urunsayisi) {
        this.urunsayisi = urunsayisi;
    }

    public int getUrunid() {
        return urunid;
    }

    public void setUrunid(int urunid) {
        this.urunid = urunid;
    }

    public String getUrunadi() {
        return urunadi;
    }

    public void setUrunadi(String urunadi) {
        this.urunadi = urunadi;
    }


}
