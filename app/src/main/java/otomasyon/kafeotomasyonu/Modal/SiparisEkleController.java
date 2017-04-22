package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by bilal on 20.04.2017.
 */

public class SiparisEkleController {
    private int masaid;
    private String garsonid;
    private int adet;
    private int urunid;
    private boolean durum;
    private String tarih;

    public SiparisEkleController(int masaid, String garsonid, int adet,int urunid ,boolean durum,String tarih){
        this.masaid = masaid;
        this.garsonid = garsonid;
        this.adet = adet;
        this.urunid = urunid;
        this.durum = durum;
        this.tarih = tarih;
    }

    public int getMasaid() {
        return masaid;
    }

    public void setMasaid(int masaid) {
        this.masaid = masaid;
    }

    public String getGarsonid() {
        return garsonid;
    }

    public void setGarsonid(String garsonid) {
        this.garsonid = garsonid;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public int getUrunid() {
        return urunid;
    }

    public void setUrunid(int urunid) {
        this.urunid = urunid;
    }

    public boolean isDurum() {
        return durum;
    }

    public void setDurum(boolean durum) {
        this.durum = durum;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
