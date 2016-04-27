package server;

import channel.Manager;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedMessageChannel;
import message.ChatContent;
import message.LoginContent;
import message.Message;
import message.MessageStatus;
import org.junit.Before;
import org.junit.Test;
import protocol.MessageType;

import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/3/27.
 */
public class ChannelManagerHandlerTest {

    private EmbeddedMessageChannel channel;

    @Before
    public void setUp() throws Exception {

        channel = new EmbeddedMessageChannel(new ChannelManagerHandler());
    }


    //authority成功时channels长度＋1
    @Test
    public void loginSucceed(){

        int channelNum = Manager.clientChannels.size();
        LoginContent loginContent = new LoginContent("100","123456");
        Message loginSucceedMessage = new Message(loginContent, MessageStatus.NEEDHANDLED, MessageType.AUTHORITY);
        channel.writeInbound(loginSucceedMessage);

        assertEquals(channelNum + 1, Manager.clientChannels.size());

        Message message = (Message)channel.readInbound();
        //不会改变登录内容
        assertEquals("100", ( message.getLoginContent()).getAccount());
        assertEquals("123456",(message.getLoginContent()).getPassword());


    }

    //authority失败时channels长度不变
    @Test
    public void loginFail(){

        int channelNum = Manager.clientChannels.size();
        LoginContent loginContent = new LoginContent("100","123");
        Message loginFailMessage = new Message(loginContent, MessageStatus.LOGINFAIL, MessageType.AUTHORITY);
        channel.writeInbound(loginFailMessage);

        assertEquals(channelNum,Manager.clientChannels.size());
    }

    //chatting不进行处理,channels长度不变
    @Test
    public void chattingMethod(){

        int channelNum = Manager.clientChannels.size();
        ChatContent chatContent = new ChatContent("Hello");
        Message chattingMessage = new Message(chatContent,MessageStatus.NEEDHANDLED,MessageType.CHATTING);
        channel.writeInbound(chattingMessage);

        assertEquals(channelNum,Manager.clientChannels.size());
    }


    @Test
    public void clientExit(){

        int channelNum = Manager.clientChannels.size();
        LoginContent loginContent = new LoginContent("100","123456");
        Message loginSucceedMessage = new Message(loginContent, MessageStatus.NEEDHANDLED, MessageType.AUTHORITY);
        channel.writeInbound(loginSucceedMessage);

        channel.close();

        assertEquals(channelNum, Manager.clientChannels.size());
    }


}