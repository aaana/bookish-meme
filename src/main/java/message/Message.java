package message;

import protocol.ACKType;
import protocol.MessageType;

import java.io.Serializable;

/**
 * Created by tanjingru on 3/20/16.
 */



public class Message implements Serializable{

    private static final long serialVersionUID = 1L;

    private MessageStatus messageStatus;
    // 0 => login 1 => chatting

    private MessageType type;

    private LoginContent loginContent;

    private ChatContent chatContent;

    private GroupContent groupContent;

    private RegisterContent registerContent;

    public RegisterContent getRegisterContent() {
        return registerContent;
    }

    public Message(MessageType type, MessageStatus messageStatus, RegisterContent registerContent) {
        this.type = type;
        this.messageStatus = messageStatus;
        this.registerContent = registerContent;
    }

    public void setRegisterContent(RegisterContent registerContent) {
        this.registerContent = registerContent;
    }

    public GroupContent getGroupContent() {
        return groupContent;
    }

    public void setGroupContent(GroupContent groupContent) {
        this.groupContent = groupContent;
    }

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

    public Message(MessageType type, MessageStatus messageStatus, GroupContent groupContent) {
        this.type = type;
        this.messageStatus = messageStatus;
        this.groupContent = groupContent;
    }

    public Message(GroupContent groupContent, MessageStatus messageStatus, MessageType type) {
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
