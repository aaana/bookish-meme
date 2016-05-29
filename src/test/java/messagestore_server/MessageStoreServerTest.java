package messagestore_server;

import message.ChatContent;
import message.LoginContent;
import message.Message;
import message.MessageStatus;
import org.junit.Test;
import protocol.MessageType;
import provider.ServiceProvider;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/5/29.
 */
public class MessageStoreServerTest {

    @Test
    public void testMessageStore() throws Exception {
        Message chattingMessage = new Message(new ChatContent("100","hahaha"), MessageStatus.NEEDHANDLED, MessageType.CHATTING);
        Message loginMessage = new Message(new LoginContent("100","123456"),MessageStatus.NEEDHANDLED,MessageType.AUTHORITY);
        ServiceProvider.getMessageStoreServer().store(chattingMessage);
        ServiceProvider.getMessageStoreServer().store(loginMessage);
    }
}