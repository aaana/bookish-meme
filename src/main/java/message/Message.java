package message;

/**
 * Created by tanjingru on 3/20/16.
 */
public class Message {
    private int needsToHandle;
    private int type;
    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Message(Object content, int type) {

        this.content = content;
        this.type = type;
    }
}
