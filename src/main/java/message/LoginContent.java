package message;

import com.google.gson.internal.StringMap;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tanjingru on 3/20/16.
 */
public class LoginContent {
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

    public LoginContent(String account, String password) {

        this.account = account;
        this.password = password;
    }

}
