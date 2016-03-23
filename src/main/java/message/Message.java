package message;

/**
 * Created by tanjingru on 3/20/16.
 */



public class Message {

    private int needsToHandle;
    // 0 => login 1 => chatting

    private int type;
    private Object content;

    public Message(int type, Object content, int needsToHandle) {
        this.type = type;
        this.content = content;
        this.needsToHandle = needsToHandle;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getNeedsToHandle() {
        return needsToHandle;
    }

    public void setNeedsToHandle(int needsToHandle) {
        this.needsToHandle = needsToHandle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
