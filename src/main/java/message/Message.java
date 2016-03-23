package message;

import protocol.ACKType;
import protocol.MessageType;

/**
 * Created by tanjingru on 3/20/16.
 */



public class Message {

    private MessageStatus messageStatus;
    // 0 => login 1 => chatting

    private MessageType type;
    private Object content;

    public Message(Object content, MessageStatus messageStatus, MessageType type) {
        this.content = content;
        this.messageStatus = messageStatus;
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
