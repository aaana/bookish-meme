package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

/**
 * Created by tanjingru on 3/20/16.
 */
public class TestHandler extends ChannelInboundMessageHandlerAdapter<String> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        message += "test";
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}