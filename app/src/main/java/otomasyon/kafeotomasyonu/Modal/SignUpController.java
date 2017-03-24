package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by Bilal on 24.03.2017.
 */

public class SignUpController {
    protected String eMail;
    protected String adSoyad;
    public SignUpController(String eMail, String adSoyad)
    {
        this.eMail = eMail;
        this.adSoyad = adSoyad;
    }
    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
