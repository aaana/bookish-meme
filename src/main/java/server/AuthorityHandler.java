package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.LoginContent;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;

/**
 * Created by huanganna on 16/3/21.
 */
public class AuthorityHandler extends ChannelInboundMessageHandlerAdapter<Message> {
    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        MessageType messageType = message.getType();
        //login message
        if(messageType == MessageType.AUTHORITY){
            LoginContent loginContent = message.getLoginContent();
            String account = loginContent.getAccount();
            String password = loginContent.getPassword();
            LoginServer loginServer = new LoginServer();
            int success = loginServer.login(account,password);
//            login fails
            if(success==-1) {
                message.setMessageStatus(MessageStatus.LOGINFAIL);
            }
            // login success
            else{
                loginContent.setGroupId(success);
                message.setLoginContent(loginContent);
            }
        }
        //其他情况下不作处理直接流到下一个channel
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}
