package event;

import message.ChatContent;

import java.util.List;

/**
 * Created by huanganna on 16/6/4.
 */
public class EnterGroupEvent {
    private List<ChatContent> chatContents;
    private String groupId;
    private List<String> onlineAccounts;

    public EnterGroupEvent(List<ChatContent> chatContents, String groupId, List<String> onlineAccounts) {
        this.chatContents = chatContents;
        this.groupId = groupId;
        this.onlineAccounts = onlineAccounts;
    }

    public List<ChatContent> getChatContents() {
        return chatContents;
    }

    public void setChatContents(List<ChatContent> chatContents) {
        this.chatContents = chatContents;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getOnlineAccounts() {
        return onlineAccounts;
    }

    public void setOnlineAccounts(List<String> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }
}
