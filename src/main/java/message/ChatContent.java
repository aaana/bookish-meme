package message;


import com.google.gson.internal.StringMap;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class ChatContent implements Serializable{

    private static final long serialVersionUID = 1L;

    private String groupId;
    private String sender;
    private String sendDate;
    private String message;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ChatContent(String sender, String message) {

        this.sender = sender;
        this.message = message;
    }
    public ChatContent(String gid,String sender,String sendDate,String message) {
        this.groupId = gid;
        this.sendDate = sendDate;
        this.sender = sender;
        this.message = message;
    }


    public ChatContent(String sender, String sendDate, String message) {
        this.sender = sender;
        this.sendDate = sendDate;
        this.message = message;
    }

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public ChatContent(String message) {

        this.message = message;
    }

    public String toString()
    {return message;}

}
