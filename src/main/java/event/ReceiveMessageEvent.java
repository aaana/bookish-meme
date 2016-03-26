package event;

import message.ChatContent;

/**
 * Created by tanjingru on 3/26/16.
 */
public class ReceiveMessageEvent {

    private ChatContent chatContent;

    public ReceiveMessageEvent(ChatContent chatContent) {
        this.chatContent = chatContent;
    }

    public ChatContent getChatContent() {
        return chatContent;
    }

    public void setChatContent(ChatContent chatContent) {
        this.chatContent = chatContent;
    }
}
