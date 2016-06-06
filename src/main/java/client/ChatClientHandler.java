package client;

import event.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.ACK;
import protocol.ACKType;

/**
 * Created by tanjingru on 3/17/16.
 */

public class ChatClientHandler extends ChannelInboundMessageHandlerAdapter<ACK> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, ACK s) throws Exception {

        System.out.println(s.getType());

        ACKType ackType = s.getType();

        if (ackType == ACKType.SENDSUCCESS)
        PublicEvent.eventBus.post(new SendSuccessEvent());

        if (ackType == ACKType.LOGINFAIL)
            PublicEvent.eventBus.post(new LoginFailEvent());

        if (ackType == ACKType.LOGINSUCCESS){
            //            PublicEvent.eventBus.post(new LoginSuccessEvent(s.getMissingChatContents(),s.getGroupId(),s.getAccounts()));
            PublicEvent.eventBus.post(new LoginSuccessEvent());
        }

        if(ackType == ACKType.ENTERGROUP)
            PublicEvent.eventBus.post(new EnterGroupEvent(s.getMissingChatContents(),s.getGroupId(),s.getAccounts()));

        if (ackType == ACKType.OTHERSMESSAGE)
            PublicEvent.eventBus.post(new ReceiveMessageEvent(s.getChatContent()));

        if (ackType == ACKType.REDOLOGIN)
            PublicEvent.eventBus.post(new ReLoginEvent());

        if (ackType == ACKType.TOOFRENQUENT)
            PublicEvent.eventBus.post(new TooFrequentEvent());

        if(ackType==ACKType.SOMEONEONLINE)
            PublicEvent.eventBus.post(new SomeOneOnlineEvent(s.getAccounts().get(0)));

        if(ackType==ACKType.SOMEONEOFFLINE)
            PublicEvent.eventBus.post(new SomeOneOfflineEvent(s.getAccounts().get(0)));

        if(ackType==ACKType.ADDSUCCESS)
            PublicEvent.eventBus.post(new AddGroupEvent(s.getAccounts(),s.getGroupId()));

        if(ackType==ACKType.SOMEONEADDGROUP)
            PublicEvent.eventBus.post(new SomeOneAddGroupEvent(s.getAccounts().get(0)));
        if(ackType==ACKType.REGISTERSUCCESS)
            PublicEvent.eventBus.post(new RegisterSuccessEvent());
        if(ackType==ACKType.ACCOUNTEXIST)
            PublicEvent.eventBus.post(new AccountExistEvent());
        if(ackType==ACKType.REGISTERFAIL)
            PublicEvent.eventBus.post(new RegisterFailEvent());
        if(ackType==ACKType.ADDFAIL)
            PublicEvent.eventBus.post(new AddGroupFailEvent());
        if(ackType==ACKType.CREATEGROUPSUCCESS)
            PublicEvent.eventBus.post(new CreateGroupSuccessEvent());
        if(ackType==ACKType.GROUPALREADYEXIST)
            PublicEvent.eventBus.post(new GroupAlreadyExist());
        if(ackType==ACKType.ENTERGROUPFAIL)
            PublicEvent.eventBus.post(new EnterGroupFailEvent());
        if(ackType == ACKType.DELETEGROUPSUCCESS)
            PublicEvent.eventBus.post(new DeleteGroupEvent(s.getGroupContent().getAccount(),s.getGroupContent().getGroupId()));
        if(ackType==ACKType.SOMEONEESCAPEGROUP)
            PublicEvent.eventBus.post(new SomeOneEscapeGroupEvent(s.getAccounts().get(0)));


    }


}
