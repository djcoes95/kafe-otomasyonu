package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by bilal on 07.05.2017.
 */

public class BildirimController {
    private String bildirimId;
    private String masaId;
    public BildirimController(String bildirimId, String masaId)
    {
        this.bildirimId = bildirimId;
        this.masaId = masaId;
    }
    public String getBildirimId() {
        return bildirimId;
    }

    public void setBildirimId(String bildirimId) {
        this.bildirimId = bildirimId;
    }

    public String getMasaId() {
        return masaId;
    }

    public void setMasaId(String masaId) {
        this.masaId = masaId;
    }
}