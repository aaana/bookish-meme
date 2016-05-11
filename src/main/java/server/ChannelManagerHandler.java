package server;

import channel.ClientChannel;
import channel.Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanjingru on 3/20/16.
 */
public class ChannelManagerHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if( message.getType() == MessageType.AUTHORITY && message.getMessageStatus() == MessageStatus.NEEDHANDLED){

            Channel channel = channelHandlerContext.channel();
            int groupId = message.getLoginContent().getGroupId();
            String account = message.getLoginContent().getAccount();
            ClientChannel clientChannel = new ClientChannel(channel,groupId);
            clientChannel.setAccount(account);
            Manager.clientChannels.add(clientChannel);


        }
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (ClientChannel clientChannel : Manager.clientChannels){

            if(clientChannel.getChannel()==channel){
                Manager.clientChannels.remove(clientChannel);
            }
        }

    }

}
