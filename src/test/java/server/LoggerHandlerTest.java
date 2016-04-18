package server;

import io.netty.channel.embedded.EmbeddedMessageChannel;
import message.ChatContent;
import message.Message;
import message.MessageStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import protocol.MessageType;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/3/28.
 */
public class LoggerHandlerTest {

    private EmbeddedMessageChannel channel;

    private Message[] testMessage = new Message[5];
    //预计结果 依次为登录成功，登录失败，接收消息，忽略消息，转发消息数目
    private int[][] expectedResult={{0,1,0,0,0},
                                    {1,1,0,0,0},
                                    {1,1,1,0,1},
                                    {1,1,2,1,1},
                                    {1,1,3,2,1}};

    @Before
    public void setUp() throws Exception {
        channel = new EmbeddedMessageChannel(new LoggerHandler());

        //测试用例，依次发送
        testMessage[0] = new Message(new ChatContent("hello"), MessageStatus.LOGINFAIL, MessageType.AUTHORITY);
        testMessage[1] = new Message(new ChatContent("hello"), MessageStatus.NEEDHANDLED, MessageType.AUTHORITY);
        testMessage[2] = new Message(new ChatContent("hello"), MessageStatus.NEEDHANDLED, MessageType.CHATTING);
        testMessage[3] = new Message(new ChatContent("hello"), MessageStatus.OVERRANGE, MessageType.CHATTING);
        testMessage[4] = new Message(new ChatContent("hello"), MessageStatus.TOOFREQUENT, MessageType.CHATTING);

    }
//
//    @Test
//    public void testMessageReceived() throws Exception {
//        for(int i=0;i<5;i++) {
//            channel.writeInbound(testMessage[i]);
//            int[] actualResult = {LoggerHandler.validLoginNumber,LoggerHandler.invalidLoginNumber,
//                    LoggerHandler.receivedMessageNumber,LoggerHandler.ignoredMessageNumber,LoggerHandler.forwardMessageNumber};
//            //测试数目是否正确
//            Assert.assertArrayEquals(expectedResult[i],actualResult);
//
//            //测试状态类型是否被修改
//            Message myMessage = (Message)channel.readInbound();
//            assertEquals("hello", (myMessage.getChatContent()).getMessage());
//            assertEquals(testMessage[i].getMessageStatus(),myMessage.getMessageStatus());
//            assertEquals(testMessage[i].getType(),myMessage.getType());
//        }
//    }
}