package server;

import channel.Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import message.Message;

import java.util.function.IntConsumer;

/**
 * Created by tanjingru on 3/17/16.
 */
public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming  = ctx.channel();
        for ( Channel channel : Manager.channels){
            channel.write("[SERVER] - " + incoming.remoteAddress() + "has joined\n");
        }
        Manager.channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for ( Channel channel : Manager.channels){
            channel.write("[SERVER] - " + incoming.remoteAddress() + "has left\n");
        }
        Manager.channels.remove(ctx.channel());
    }

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        Channel incoming  = channelHandlerContext.channel();
        for (Channel channel : Manager.channels){
            if ( channel != incoming){
                channel.write("[" + incoming.remoteAddress() + "]" + message.getContent().toString() + "\n");
            }
        }
    }
}
