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

    public ChatContent(StringMap sm){
        Iterator it = sm.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pairs = (Map.Entry)it.next();
            Class<?> c = this.getClass();
            try {
                Field value = c.getDeclaredField((String) pairs.getKey());
                value.set(this, pairs.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
