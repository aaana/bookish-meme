package message;


import com.google.gson.internal.StringMap;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class ChatContent {

    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public ChatContent(String account, String message) {

        this.account = account;
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatContent(String message) {

        this.message = message;
    }

    public String toString()
    {return message;}

}
