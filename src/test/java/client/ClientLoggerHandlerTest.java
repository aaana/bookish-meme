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
            {1,1,},
            {1,1,3,2}};
    private ACK[] testACK = new ACK[6];
    @Before
    public void setUp() throws Exception {
        channel = new EmbeddedMessageChannel(new ClientLoggerHandler());

        //测试用例，依次发送
        testACK[0] = new ACK(ACKType.LOGINFAIL,new LoginContent("100","123"));
        testACK[1] = new ACK(ACKType.LOGINSUCCESS,new LoginContent("100","123456"));
        testACK[2] = new ACK(ACKType.OTHERSMESSAGE,new ChatContent("hello"));
        testACK[3] = new ACK(ACKType.SENDSUCCESS,new ChatContent("hello"));
        testACK[4] = new ACK(ACKType.TOOFRENQUENT,new ChatContent("hello"));
        testACK[5] = new ACK(ACKType.REDOLOGIN,new ChatContent("hello"));


    }

    @Test
    public void testMessageReceived() throws Exception {
        for(int i=0;i<6;i++) {
            channel.writeInbound(testACK[i]);
            int[] actualResult = {ClientLoggerHandler.loginFail,ClientLoggerHandler.loginSuccess,
                    ClientLoggerHandler.receiveMsgNumber,ClientLoggerHandler.sendMsgNumber};

            Assert.assertArrayEquals(expectedResult[i], actualResult);
        }
    }
}