package server;

import channel.Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;

/**
 * Created by tanjingru on 3/21/16.
 */

public class Logger extends ChannelInboundMessageHandlerAdapter<Message> {

    static public int validLoginNumber = 0;
    static public int invalidLoginNumber = 0;
    static public int receivedMessageNumber = 0;
    static public int ignoredMessageNumber = 0;
    static public int forwardMessageNumber = 0;

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        Channel incomingChannel  = channelHandlerContext.channel();

        int messageType = message.getType();
        int isMessageNeedsToHandle = message.getNeedsToHandle();

        //fail to login
        if (messageType == 0 && isMessageNeedsToHandle != 0 ){
            invalidLoginNumber += 1;
        }

        // success to login
        if (messageType == 0 && isMessageNeedsToHandle == 0){
            validLoginNumber += 1;
        }


        // need to forward the message to everyone connected
        if (messageType == 1 && isMessageNeedsToHandle == 0 )
        {
            forwardMessageNumber += 1;
        }

        if( messageType == 1){
            receivedMessageNumber += 1;

            if (isMessageNeedsToHandle == 0) forwardMessageNumber += 1;
            else ignoredMessageNumber += 1;

        }

        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}
