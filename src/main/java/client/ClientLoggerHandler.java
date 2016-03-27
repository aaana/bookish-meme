package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.ACK;
import message.Message;
import protocol.ACKType;

/**
 * Created by 马二爷 on 2016/3/26.
 */
public class ClientLoggerHandler extends ChannelInboundMessageHandlerAdapter<ACK> {
    public static int loginSuccess=0;
    public static int loginFail=0;
    public static int sendMsgNumber=0;
    public static int receiveMsgNumber=0;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, ACK ack) throws Exception {
        ACKType type=ack.getType();

        if(type == ACKType.LOGINSUCCESS)
            loginSuccess++;
        if(type == ACKType.LOGINFAIL)
            loginFail++;
        if(type == ACKType.OTHERSMESSAGE)
            receiveMsgNumber++;

        ctx.nextInboundMessageBuffer().add(ack);
        ctx.fireInboundBufferUpdated();

    }
}
