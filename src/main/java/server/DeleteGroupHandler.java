package server;

import channel.Manager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;
import provider.ServiceProvider;

/**
 * Created by huanganna on 16/6/5.
 */
public class DeleteGroupHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if(message.getType()== MessageType.DELETEGROUP){
            String accountId = message.getGroupContent().getAccount();
            String groupId = message.getGroupContent().getGroupId();
            int result = ServiceProvider.getDbServer().deleteGroup(accountId,groupId);
            Manager.groupClientsMissingNum.get(message.getGroupContent().getGroupId()).remove(accountId);
            System.out.println("!!!!!!!!!!!!,,,,,,!!!!" + Manager.groupClientsMissingNum);
        }
        //其他情况下不作处理直接流到下一个channel
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}
