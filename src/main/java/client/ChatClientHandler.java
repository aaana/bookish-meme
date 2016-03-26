package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.ACK;

/**
 * Created by tanjingru on 3/17/16.
 */

public class ChatClientHandler extends ChannelInboundMessageHandlerAdapter<ACK> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, ACK s) throws Exception {
        System.out.print(s.getType());
    }

}
