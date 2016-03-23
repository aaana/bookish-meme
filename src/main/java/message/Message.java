package message;

import protocol.ACKType;
import protocol.MessageType;

/**
 * Created by tanjingru on 3/20/16.
 */



public class Message {

    private ACKType ackType;
    // 0 => login 1 => chatting

    private MessageType type;
    private Object content;

    public Message(ACKType ackType, Object content, MessageType type) {
        this.ackType = ackType;
        this.content = content;
        this.type = type;
    }

    public ACKType getAckType() {
        return ackType;
    }

    public void setAckType(ACKType ackType) {
        this.ackType = ackType;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
