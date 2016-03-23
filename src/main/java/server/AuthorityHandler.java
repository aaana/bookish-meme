package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.LoginContent;
import message.Message;
import protocol.ACKType;
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
            LoginContent loginContent = (LoginContent)(message.getContent());
            String account = loginContent.getAccount();
            String password = loginContent.getPassword();
            LoginServer loginServer = new LoginServer();
            boolean success = loginServer.login(account,password);
//            login fails
            if(!success) {
                message.setAckType(ACKType.LOGINFAIL);
            }
            System.out.println("from auth: success is " + success);
        }
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}
