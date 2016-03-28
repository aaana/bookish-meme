package server;

import channel.Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;

/**
 * Created by tanjingru on 3/20/16.
 */
public class ChannelManagerHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if( message.getType() == MessageType.AUTHORITY && message.getMessageStatus() == MessageStatus.NEEDHANDLED){

           /* Channel incoming  = channelHandlerContext.channel();
            for ( Channel channel : Manager.channels){
                channel.write("[SERVER] - " + incoming.remoteAddress() + "has joined\n");
            }*/
            Manager.channels.add(channelHandlerContext.channel());

        }
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        /*Channel incoming = ctx.channel();
        for ( Channel channel : Manager.channels){
            channel.write("[SERVER] - " + incoming.remoteAddress() + "has left\n");
        }*/
        Manager.channels.remove(ctx.channel());
    }

}
