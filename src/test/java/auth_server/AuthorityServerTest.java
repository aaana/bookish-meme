package auth_server;

import message.ChatContent;
import message.LoginContent;
import message.Message;
import message.MessageStatus;
import org.junit.Test;
import protocol.MessageType;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Locale;

import static org.junit.Assert.*;

public class AuthorityServerTest {
    @Test
    public void testLogin() throws Exception {
        Registry reg = LocateRegistry.getRegistry("localhost", 2015);
        AuthorityServer authorityServer = (AuthorityServer)(reg.lookup("authorityServer"));

        Message validMessage = new Message(new LoginContent("100","123456"),MessageStatus.NEEDHANDLED, MessageType.AUTHORITY);
        validMessage =  authorityServer.handler(validMessage);
        assertEquals(validMessage.getMessageStatus(), MessageStatus.NEEDHANDLED);

        Message invalidMessage = new Message(new LoginContent("100","43423"),MessageStatus.NEEDHANDLED, MessageType.AUTHORITY);
        invalidMessage =  authorityServer.handler(invalidMessage);
        assertEquals(invalidMessage.getMessageStatus(), MessageStatus.LOGINFAIL);

        Message chattingMessage = new Message(new ChatContent("haha"),MessageStatus.NEEDHANDLED, MessageType.CHATTING);
        chattingMessage = authorityServer.handler(chattingMessage);
        assertEquals(chattingMessage.getMessageStatus(),MessageStatus.NEEDHANDLED);
        assertEquals(chattingMessage.getChatContent().getMessage(),"haha");
        assertEquals(chattingMessage.getType(),MessageType.CHATTING);
    }
}