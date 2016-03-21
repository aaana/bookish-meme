package message;

/**
 * Created by 马二爷 on 2016/3/21.
 */
public class ChatContent {

    private String message;

    public ChatContent(String message)
    {
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String toString()
    {return message;}
}
