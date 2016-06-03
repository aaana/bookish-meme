package message;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by huanganna on 16/6/3.
 */
public class RegisterContent implements Serializable{

    private static final long serialVersionUID = 1L;

    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterContent(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
