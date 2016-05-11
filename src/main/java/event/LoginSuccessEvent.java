package event;

import message.ChatContent;

import java.util.List;

/**
 * Created by tanjingru on 3/26/16.
 */
public class LoginSuccessEvent {

    List<ChatContent> chatContents;
    public LoginSuccessEvent() {
    }

    public LoginSuccessEvent(List<ChatContent> chatContents) {
        this.chatContents = chatContents;
    }

    public List<ChatContent> getChatContents() {
        return chatContents;
    }

    public void setChatContents(List<ChatContent> chatContents) {
        this.chatContents = chatContents;
    }
}
