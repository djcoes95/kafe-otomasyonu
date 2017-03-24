package otomasyon.kafeotomasyonu.Modal;

/**
 * Created by dilolokko on 3/19/17.
 */

public class LoginController {
    protected String eMail;
    protected String password;
    public LoginController(String eMail, String password)
    {
        this.eMail = eMail;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
