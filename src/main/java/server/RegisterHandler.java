package server;

import auth_server.LoginServer;
import channel.Manager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;
import provider.ServiceProvider;

/**
 * Created by huanganna on 16/6/3.
 */
public class RegisterHandler extends ChannelInboundMessageHandlerAdapter<Message> {
    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if(message.getType()== MessageType.REGISTER){
            String account = message.getRegisterContent().getAccount();
            String password = message.getRegisterContent().getPassword();
            LoginServer loginServer = new LoginServer();
            int result = loginServer.register(account,password);
            if(result==-1){
                message.setMessageStatus(MessageStatus.ACCOUNTEXSIT);
            }else if(result==0){
                message.setMessageStatus(MessageStatus.REGISTERFAIL);
            }else{
                ServiceProvider.getDbServer().addGroup(account,"public");
                Manager.groupClientsMissingNum.get("public").put(account,0);
            }
        }
        //其他情况下不作处理直接流到下一个channel
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}

