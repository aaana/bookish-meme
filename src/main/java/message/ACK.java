package message;

import protocol.ACKType;

import java.util.List;
import java.util.Objects;

/**
 * Created by tanjingru on 3/23/16.
 */
public class ACK {

    ACKType type;
    ChatContent chatContent;
    LoginContent loginContent;
    GroupContent groupContent;
    List<ChatContent> missingChatContents;
    List<String> accounts;
    String groupId;

    public ACK(ACKType type) {
        this.type = type;
    }

    public ACK(ChatContent chatContent, ACKType type) {
        this.chatContent = chatContent;
        this.type = type;
    }

    public ACK() {
    }

    public GroupContent getGroupContent() {
        return groupContent;
    }

    public void setGroupContent(GroupContent groupContent) {
        this.groupContent = groupContent;
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

    public ACKType getType() {
        return type;
    }

    public void setType(ACKType type) {
        this.type = type;
    }

    public List<ChatContent> getMissingChatContents() {
        return missingChatContents;
    }

    public void setMissingChatContents(List<ChatContent> missingChatContents) {
        this.missingChatContents = missingChatContents;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> sameGroupOnlineAccounts) {
        this.accounts = sameGroupOnlineAccounts;
    }

}
