package message;


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
