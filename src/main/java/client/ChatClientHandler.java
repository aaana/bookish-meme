package client;

import event.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.ACK;
import message.ChatContent;
import protocol.ACKType;

/**
 * Created by tanjingru on 3/17/16.
 */

public class ChatClientHandler extends ChannelInboundMessageHandlerAdapter<ACK> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, ACK s) throws Exception {

        System.out.print(s.getType());

        ACKType ackType = s.getType();

        if (ackType == ACKType.SENDSUCCESS)
        PublicEvent.eventBus.post(new SendSuccessEvent());

        if (ackType == ACKType.LOGINFAIL)
            PublicEvent.eventBus.post(new LoginFailEvent());

        if (ackType == ACKType.LOGINSUCCESS)
            PublicEvent.eventBus.post(new LoginSuccessEvent());

        if (ackType == ACKType.OTHERSMESSAGE)
            PublicEvent.eventBus.post(new ReceiveMessageEvent((ChatContent)s.getContent()));

        if (ackType == ACKType.REDOLOGIN)
            PublicEvent.eventBus.post(new ReLoginEvent());

        if (ackType == ACKType.TOOFRENQUENT)
            PublicEvent.eventBus.post(new TooFrequentEvent());

    }

}
