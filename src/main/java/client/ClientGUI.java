package client;

import com.google.common.eventbus.Subscribe;
import event.LoginSuccessEvent;
import event.ReceiveMessageEvent;
import message.ChatContent;

import javax.swing.plaf.PanelUI;

/**
 * Created by tanjingru on 3/27/16.
 */
public class ClientGUI {

    private ChatClient client;

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
        PublicEvent.eventBus.register(clientGUI);

    }

    public ClientGUI() {
        this.client = new ChatClient("localhost", 8080);
    }

    @Subscribe
    public void LoginSuccess(LoginSuccessEvent loginSuccessEvent){

    }

    @Subscribe
    public void receiveOtherMessage(ReceiveMessageEvent event){
        ChatContent chatContent = event.getChatContent();
    }




}
