package message;


import com.google.gson.internal.StringMap;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class ChatContent {

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
