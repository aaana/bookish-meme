package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.LoginContent;
import message.Message;

/**
 * Created by huanganna on 16/3/21.
 */
public class AuthorityHandler extends ChannelInboundMessageHandlerAdapter<Message> {
    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        int messageType = message.getType();
        //login message
        if(messageType == 0){
            LoginContent loginContent = (LoginContent)message.getContent();
            String account = loginContent.getAccount();
            String password = loginContent.getPassword();
            LoginServer loginServer = new LoginServer();
            boolean success = loginServer.login(account,password);
//            login fails
            if(!success) {
                message.setNeedsToHandle(1);
            }

        }
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}
