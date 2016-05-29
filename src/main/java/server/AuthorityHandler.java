package server;

import auth_server.AuthorityServer;
import auth_server.LoginServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.LoginContent;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;
import provider.ServiceProvider;

import javax.xml.ws.Service;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by huanganna on 16/3/21.
 */
public class AuthorityHandler extends ChannelInboundMessageHandlerAdapter<Message> {
    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        Message handledMessage =  ServiceProvider.getAuthorityServer().handler(message);
        //其他情况下不作处理直接流到下一个channel
        channelHandlerContext.nextInboundMessageBuffer().add(handledMessage);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}
