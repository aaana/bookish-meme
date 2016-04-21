package server;

import channel.Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import log.Log;
import message.Message;
import message.MessageStatus;
import octoteam.tahiti.performance.PerformanceMonitor;
import octoteam.tahiti.performance.recorder.CountingRecorder;
import octoteam.tahiti.performance.reporter.LogReporter;
import octoteam.tahiti.performance.reporter.RollingFileReporter;
import protocol.MessageType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tanjingru on 3/21/16.
 */

public class LoggerHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    static CountingRecorder validLoginNumber = new CountingRecorder("validLoginNumber");
    static CountingRecorder invalidLoginNumber = new CountingRecorder("invalidLoginNumber");
    static CountingRecorder receivedMessageNumber = new CountingRecorder("receivedMessageNumber");
    static CountingRecorder ignoredMessageNumber = new CountingRecorder("ignoredMessageNumber");
    static CountingRecorder forwardMessageNumber = new CountingRecorder("forwardMessageNumber");

//    static public int validLoginNumber = 0;
//    static public int invalidLoginNumber = 0;
//    static public int receivedMessageNumber = 0;
//    static public int ignoredMessageNumber = 0;
//    static public int forwardMessageNumber = 0;

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        Channel incomingChannel  = channelHandlerContext.channel();

        MessageType messageType = message.getType();
        MessageStatus messageStatus = message.getMessageStatus();

        //fail to login
        if (messageType == MessageType.AUTHORITY && messageStatus == MessageStatus.LOGINFAIL){
            invalidLoginNumber.record();
        }

        // success to login
        if (messageType == MessageType.AUTHORITY && messageStatus == MessageStatus.NEEDHANDLED){
            validLoginNumber.record();
        }


        if( messageType == MessageType.CHATTING){
            receivedMessageNumber.record();
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
            String snow = dateFormat.format(now);
            Log.writeFile("./messageRecords/server.txt","["+ snow+ "]" + " "+message.getChatContent().getAccount()+": "+message.getChatContent().toString());

            if (messageStatus == MessageStatus.NEEDHANDLED) forwardMessageNumber.record();
            else ignoredMessageNumber.record();

        }

        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}
