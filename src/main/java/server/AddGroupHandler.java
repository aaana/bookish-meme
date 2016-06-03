package server;

import auth_server.LoginServer;
import channel.Manager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;
import protocol.MessageType;
import provider.ServiceProvider;

/**
 * Created by huanganna on 16/6/3.
 */
public class AddGroupHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if(message.getType()== MessageType.ADDINGGROUP){
            LoginServer loginServer = new LoginServer();
            String accountId = message.getGroupContent().getAccount();
            int groupId = message.getGroupContent().getGroupId();
            loginServer.addGroup(accountId,groupId);
            Manager.groupClientsMissingNum.get(0).remove(accountId);
            Manager.groupClientsMissingNum.get(message.getGroupContent().getGroupId()).put(accountId,0);
        }
        //其他情况下不作处理直接流到下一个channel
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}
