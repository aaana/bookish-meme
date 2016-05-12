package message;


import com.google.gson.internal.StringMap;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class ChatContent {

    private int groupId;

    private String sender;

    private String sendDate;



    public ChatContent(String sender, String message) {

        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public ChatContent(String message) {

        this.message = message;
    }

    public String toString()
    {return message;}

}
