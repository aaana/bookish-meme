package client;

import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedMessageChannel;
import message.ACK;
import message.ChatContent;
import message.LoginContent;
import org.junit.Before;
import org.junit.Test;
import protocol.ACKType;

import javax.swing.text.AbstractDocument;
import java.util.AbstractCollection;

import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/3/29.
 */
public class JsonHandlerTest {
    private EmbeddedMessageChannel channel;

    @Before
    public void setUp() throws Exception {

        channel = new EmbeddedMessageChannel(new JsonHandler());

    }

    //发送成功的确认ACK测试
    @Test
    public void sendSuccessACKTest(){
        ACK sendSuccessACK = new ACK();
        sendSuccessACK.setType(ACKType.SENDSUCCESS);
        Gson gson = new Gson();
        String ackJson = gson.toJson(sendSuccessACK);
        channel.writeInbound(ackJson+"\n");

        ACK ack = (ACK)channel.readInbound();
        assertEquals(ACKType.SENDSUCCESS,ack.getType());



    }

    //收到其他人消息的确认ACK测试
    @Test
    public void othersMessageACKTest(){
        ACK othersMessageACK = new ACK();
        ChatContent loginContent = new ChatContent("hello");
        othersMessageACK.setType(ACKType.OTHERSMESSAGE);
        othersMessageACK.setContent(loginContent);
        Gson gson = new Gson();
        String ackJson = gson.toJson(othersMessageACK);
        channel.writeInbound(ackJson + "\n");

        ACK ack = (ACK)channel.readInbound();
        assertEquals(ACKType.OTHERSMESSAGE,ack.getType());
        assertEquals("hello",((ChatContent)(ack.getContent())).getMessage());

    }

    //发送太频繁ACK测试
    @Test
    public void tooFrequentACKTest(){
        ACK tooFrequentACK = new ACK();
        tooFrequentACK.setType(ACKType.TOOFRENQUENT);
        Gson gson = new Gson();
        String ackJson = gson.toJson(tooFrequentACK);
        channel.writeInbound(ackJson+"\n");

        ACK ack = (ACK)channel.readInbound();
        assertEquals(ACKType.TOOFRENQUENT,ack.getType());
    }


    //重新登录ACK测试
    @Test
    public void reDoLoginACKTest(){
        ACK reDoLoginACK = new ACK();
        reDoLoginACK.setType(ACKType.REDOLOGIN);
        Gson gson = new Gson();
        String ackJson = gson.toJson(reDoLoginACK);
        channel.writeInbound(ackJson + "\n");

        ACK ack = (ACK)channel.readInbound();
        assertEquals(ACKType.REDOLOGIN,ack.getType());

    }

    //登陆成功的ACK测试
    @Test
    public void loginSuccessACKTest(){
        ACK loginSuccessACK = new ACK();
        loginSuccessACK.setType(ACKType.LOGINSUCCESS);
        Gson gson = new Gson();
        String ackJson = gson.toJson(loginSuccessACK);
        channel.writeInbound(ackJson+"\n");

        ACK ack = (ACK)channel.readInbound();
        assertEquals(ACKType.LOGINSUCCESS,ack.getType());
    }

    //登陆失败的ACK测试
    @Test
    public void loginFailACKTest(){
        ACK loginFailACK = new ACK();
        loginFailACK.setType(ACKType.LOGINFAIL);
        Gson gson = new Gson();
        String ackJson = gson.toJson(loginFailACK);
        channel.writeInbound(ackJson+"\n");

        ACK ack = (ACK)channel.readInbound();
        assertEquals(ACKType.LOGINFAIL,ack.getType());
    }





}