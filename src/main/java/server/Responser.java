package server;

import channel.Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;

/**
 * Created by tanjingru on 3/21/16.
 */

public class Responser extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

        System.out.println("from responser, type" + message.getType() + "status:" + message.getNeedsToHandle());

        Channel incomingChannel  = channelHandlerContext.channel();

        int messageType = message.getType();
        int isMessageNeedsToHandle = message.getNeedsToHandle();

        //fail to login
        if (messageType == 0 && isMessageNeedsToHandle != 0 ){
            incomingChannel.write("0"+ "\n");
            return;
        }

        // success to login
        if (messageType == 0 && isMessageNeedsToHandle == 0){
            incomingChannel.write("1" + "\n");
            return;
        }


        // need to forward the message to everyone connected
        if (messageType == 1 && isMessageNeedsToHandle == 0 )
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


        if(messageType == 1 && isMessageNeedsToHandle == 1){
            incomingChannel.write("3" + "\n");
            return;
        }

        if(messageType == 1 && isMessageNeedsToHandle == 2){
            incomingChannel.write("4" + "\n");
            return;
        }
    }
}
