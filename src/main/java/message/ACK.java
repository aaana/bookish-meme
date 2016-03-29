package message;

import protocol.ACKType;

import java.util.Objects;

/**
 * Created by tanjingru on 3/23/16.
 */
public class ACK {

    ACKType type;
    ChatContent chatContent;

    public ACK(ACKType type) {
        this.type = type;
    }

    public ACK(ChatContent chatContent, ACKType type) {
        this.chatContent = chatContent;
        this.type = type;
    }

    public ACK() {
    }

    public ChatContent getChatContent() {
        return chatContent;
    }

    public void setChatContent(ChatContent chatContent) {
        this.chatContent = chatContent;
    }

    public ACKType getType() {
        return type;
    }

    public void setType(ACKType type) {
        this.type = type;
    }
}
