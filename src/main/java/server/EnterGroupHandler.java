package server;

import channel.ClientChannel;
import channel.Manager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;
import provider.ServiceProvider;

/**
 * Created by huanganna on 16/6/4.
 */
public class EnterGroupHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

        if(message.getType()== MessageType.ENTERGROUP){
            String account = message.getGroupContent().getAccount();
            String groupId = message.getGroupContent().getGroupId();
            for (ClientChannel clientChannel:Manager.clientChannels){
                if(clientChannel.getAccount().equals(account)&&clientChannel.getChannel()!=channelHandlerContext.channel()){
                    if(clientChannel.getCurrentGroupId().equals(groupId))
                        message.setMessageStatus(MessageStatus.ENTERGROUPFAIL);
                }
            }
        }
        //其他情况下不作处理直接流到下一个channel
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}