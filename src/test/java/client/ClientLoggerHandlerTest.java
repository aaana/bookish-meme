package client;

import io.netty.channel.embedded.EmbeddedMessageChannel;
import message.ACK;
import message.ChatContent;
import message.LoginContent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import protocol.ACKType;
import protocol.MessageType;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/3/29.
 */
public class ClientLoggerHandlerTest {

    private EmbeddedMessageChannel channel;

    private int[][] expectedResult={{1,0,0,0},
            {1,1,0,0},
            {1,1,1,0},
            {1,1,1,0},
            {1,1,1,0},
            {1,1,1,0}};
    private ACK[] testACK = new ACK[6];
    @Before
    public void setUp() throws Exception {
        channel = new EmbeddedMessageChannel(new ClientLoggerHandler());

        //测试用例，依次发送
        testACK[0] = new ACK(new ChatContent("hello"),ACKType.LOGINFAIL);
        testACK[1] = new ACK(new ChatContent("hello"),ACKType.LOGINSUCCESS);
        testACK[2] = new ACK(new ChatContent("hello"),ACKType.OTHERSMESSAGE);
        testACK[3] = new ACK(new ChatContent("hello"),ACKType.SENDSUCCESS);
        testACK[4] = new ACK(new ChatContent("hello"),ACKType.TOOFRENQUENT);
        testACK[5] = new ACK(new ChatContent("hello"),ACKType.REDOLOGIN);
    }

    @Test
    public void testMessageReceived() throws Exception {
        for(int i=0;i<6;i++) {
            channel.writeInbound(testACK[i]);
//            int[] actualResult = {ClientLoggerHandler.loginFail,ClientLoggerHandler.loginSuccess,
//                    ClientLoggerHandler.receiveMsgNumber,ClientLoggerHandler.sendMsgNumber};

//            Assert.assertArrayEquals(expectedResult[i], actualResult);
        }
    }
}