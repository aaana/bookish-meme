package server;

import Util.Conf;
import Util.ConfigReader;
import io.netty.channel.embedded.EmbeddedMessageChannel;
import message.ChatContent;
import message.Message;
import message.MessageStatus;
import org.junit.Before;
import org.junit.Test;
import protocol.MessageType;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/3/28.
 */
public class LimiterHandlerTest {

    private EmbeddedMessageChannel channel;
    private int maxMsgNumber;
    private int maxMsgNumberPerSec;

    @Before
    public void setUp() throws Exception {
        channel = new EmbeddedMessageChannel(new LimiterHandler());
        ConfigReader reader = new ConfigReader();
        Conf conf = reader.readConf("conf.json");
        maxMsgNumber=conf.getMaxMsgNumber();
        maxMsgNumberPerSec=conf.getMaxMsgNumberPerSec();
    }


    @Test
    public void testTOOFREQUENT() throws Exception {
        Message testMessage = new Message(new ChatContent("hello"), MessageStatus.NEEDHANDLED, MessageType.CHATTING);

        for(int i=0;i<maxMsgNumberPerSec-1;i++){
            channel.writeInbound(testMessage);
            Message myMessage = (Message)channel.readInbound();

            assertEquals("hello", ((ChatContent)myMessage.getContent()).getMessage());
            assertEquals(MessageStatus.NEEDHANDLED, myMessage.getMessageStatus());
            assertEquals(MessageType.CHATTING, myMessage.getType());

            Thread.sleep(200);
        }
    }

    @Test
    public void testOVERRANGE() throws Exception {
        Message testMessage = new Message(new ChatContent("hello"), MessageStatus.NEEDHANDLED, MessageType.CHATTING);

        for(int i=0;i<maxMsgNumber-1;i++){
            channel.writeInbound(testMessage);

            Message myMessage = (Message)channel.readInbound();

            assertEquals(MessageStatus.NEEDHANDLED, myMessage.getMessageStatus());

             Thread.sleep(200);
        }
        for(int i=0;i<7;i++) {
            channel.writeInbound(testMessage);
            Message myMessage = (Message) channel.readInbound();
            assertEquals(MessageStatus.OVERRANGE, myMessage.getMessageStatus());
            Thread.sleep(200);
        }
    }
}