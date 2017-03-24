package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by bilal on 3/19/17.
 */

public class Kullanici {
    private String adsoyad;
    private int yas;
    private String telefon;
    private String dTarihi;
    private String mail;

    public String getAdSoyad() {
        return adsoyad;
    }

    public void setAdSoyad(String adsoyad) {
        this.adsoyad = adsoyad;
    }


    public int getYas() {
        return yas;
    }

    public void setYas(int yas) {
        this.yas = yas;
    }

    public String getdTarihi() {
        return dTarihi;
    }

    public void setdTarihi(String dTarihi) {
        this.dTarihi = dTarihi;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
