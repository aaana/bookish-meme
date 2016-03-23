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

public class Responser extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

        System.out.println("from responser, type" + message.getType() + "status:" + message.getMessageStatus() );

        Channel incomingChannel  = channelHandlerContext.channel();

        MessageType messageType = message.getType();
        MessageStatus messageStatus = message.getMessageStatus();

        //fail to login
        if (messageType == MessageType.AUTHORITY && messageStatus == MessageStatus.LOGINFAIL ){
            incomingChannel.write("0"+ "\n");
            return;
        }

        // success to login
        if (messageType == MessageType.AUTHORITY && messageStatus == MessageStatus.NEEDHANDLED){
            incomingChannel.write("1" + "\n");
            return;
        }


        // need to forward the message to everyone connected
        if (messageType == MessageType.CHATTING && messageStatus == MessageStatus.NEEDHANDLED )
        {
            for (Channel channel : Manager.channels){
                if ( channel != incomingChannel){
                    channel.write("[" + incomingChannel.remoteAddress() + "]" + message.getContent().toString()
                            + "\n");
                }
            }

            incomingChannel.write("2" + "\n");
            return;
        }


        if(messageType == MessageType.CHATTING && messageStatus == MessageStatus.OVERRANGE){
            incomingChannel.write("3" + "\n");
            return;
        }

        if(messageType == MessageType.CHATTING && messageStatus == MessageStatus.TOOFREQUENT){
            incomingChannel.write("4" + "\n");
            return;
        }
    }
}
