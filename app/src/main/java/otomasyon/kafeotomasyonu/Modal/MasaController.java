package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by Bilal on 24.03.2017.
 */

public class MasaController {
    private String masaid;
    MasaController(String masaid){
        this.masaid = masaid;
    }
    public String getMasaid() {
        return masaid;
    }

    public void setMasaid(String masaid) {
        this.masaid = masaid;
    }

}
