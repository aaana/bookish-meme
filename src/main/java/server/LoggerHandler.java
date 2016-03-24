package server;

import channel.Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;

/**
 * Created by tanjingru on 3/21/16.
 */

public class LoggerHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    static public int validLoginNumber = 0;
    static public int invalidLoginNumber = 0;
    static public int receivedMessageNumber = 0;
    static public int ignoredMessageNumber = 0;
    static public int forwardMessageNumber = 0;

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        Channel incomingChannel  = channelHandlerContext.channel();

        MessageType messageType = message.getType();
        MessageStatus messageStatus = message.getMessageStatus();

        //fail to login
        if (messageType == MessageType.AUTHORITY && messageStatus == MessageStatus.LOGINFAIL){
            invalidLoginNumber += 1;
        }

        // success to login
        if (messageType == MessageType.AUTHORITY && messageStatus == MessageStatus.NEEDHANDLED){
            validLoginNumber += 1;
        }


        if( messageType == MessageType.CHATTING){
            receivedMessageNumber += 1;

            if (messageStatus == MessageStatus.NEEDHANDLED) forwardMessageNumber += 1;
            else ignoredMessageNumber += 1;

        }

        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}
