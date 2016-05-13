package event;

import message.ChatContent;

import java.util.List;

/**
 * Created by tanjingru on 3/26/16.
 */
public class LoginSuccessEvent {

    private List<ChatContent> chatContents;
    private int groupId;
    public LoginSuccessEvent() {
    }

    public LoginSuccessEvent(List<ChatContent> chatContents,int groupId) {
        this.chatContents = chatContents;
        this.groupId = groupId;
    }

    public List<ChatContent> getChatContents() {
        return chatContents;
    }

    public void setChatContents(List<ChatContent> chatContents) {
        this.chatContents = chatContents;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
