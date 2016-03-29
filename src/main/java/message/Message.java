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

    private LoginContent loginContent;

    private ChatContent chatContent;

    public Message(MessageType type, MessageStatus messageStatus) {
        this.type = type;
        this.messageStatus = messageStatus;
    }

    public Message(LoginContent loginContent, MessageStatus messageStatus, MessageType type) {
        this.loginContent = loginContent;
        this.messageStatus = messageStatus;
        this.type = type;
    }

    public Message(ChatContent chatContent, MessageStatus messageStatus, MessageType type) {
        this.chatContent = chatContent;
        this.messageStatus = messageStatus;
        this.type = type;
    }

    public ChatContent getChatContent() {
        return chatContent;
    }

    public void setChatContent(ChatContent chatContent) {
        this.chatContent = chatContent;
    }

    public LoginContent getLoginContent() {
        return loginContent;
    }

    public void setLoginContent(LoginContent loginContent) {
        this.loginContent = loginContent;
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
