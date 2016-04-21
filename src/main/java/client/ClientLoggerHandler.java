package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import log.Log;
import message.ACK;
import message.Message;
import octoteam.tahiti.performance.recorder.CountingRecorder;
import protocol.ACKType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 马二爷 on 2016/3/26.
 */
public class ClientLoggerHandler extends ChannelInboundMessageHandlerAdapter<ACK> {
//    public static int loginSuccess=0;
//    public static int loginFail=0;
//    public static int sendMsgNumber=0;
//    public static int receiveMsgNumber=0;
    static CountingRecorder loginSuccess = new CountingRecorder("loginSuccess");
    static CountingRecorder loginFail = new CountingRecorder("loginFail");
    static CountingRecorder sendMsgNumber = new CountingRecorder("sendMsgNumber");
    static CountingRecorder receiveMsgNumber = new CountingRecorder("receiveMsgNumber");

    @Override
    public void messageReceived(ChannelHandlerContext ctx, ACK ack) throws Exception {
        ACKType type=ack.getType();

        if(type == ACKType.LOGINSUCCESS)
            loginSuccess.record();
        if(type == ACKType.LOGINFAIL)
            loginFail.record();
        if(type == ACKType.OTHERSMESSAGE){
            receiveMsgNumber.record();
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
            String snow = dateFormat.format(now);
            Log.writeFile("./messageRecords/client.txt", snow + ": " + ack.getChatContent().toString());
        }


        ctx.nextInboundMessageBuffer().add(ack);
        ctx.fireInboundBufferUpdated();

    }
}
