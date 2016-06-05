package server;

import channel.ClientChannel;
import channel.Manager;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.ACK;
import message.Message;
import message.MessageStatus;
import protocol.ACKType;
import protocol.MessageType;
import provider.ServiceProvider;
import sun.java2d.pipe.AAShapePipe;

import java.util.*;

/**
 * Created by tanjingru on 3/20/16.
 */
public class ChannelManagerHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if( message.getType() == MessageType.ENTERGROUP && message.getMessageStatus() == MessageStatus.NEEDHANDLED){

            Channel channel = channelHandlerContext.channel();
//            String groupId = message.getLoginContent().getGroupId();
//            String account = message.getLoginContent().getAccount();
            String account = message.getGroupContent().getAccount();
            String currentGroupId = message.getGroupContent().getGroupId();
            ClientChannel clientChannel = new ClientChannel(channel,account,currentGroupId);
            Manager.clientChannels.add(clientChannel);


        }
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
//        List<String> groupids=new ArrayList<String>();
        String currentGroupId=null;
        String account=null;

        for(Iterator<ClientChannel> it=Manager.clientChannels.iterator();it.hasNext();)
        {
            ClientChannel clientChannel=it.next();
            if(clientChannel.getChannel()==channel)
            {
                it.remove();
//                groupids=clientChannel.getGroupId();
                currentGroupId = clientChannel.getCurrentGroupId();
                account=clientChannel.getAccount();
            }

        }
//        for (ClientChannel clientChannel : Manager.clientChannels){
//
//                groupid=clientChannel.getGroupId();
//                account=clientChannel.getAccount();
//                System.out.println("Now!"+account);
//
//        }

        for (ClientChannel clientChannel : Manager.clientChannels){

            if(clientChannel.getCurrentGroupId().equals(currentGroupId)){
                ACK ack=new ACK();
                ack.setType(ACKType.SOMEONEOFFLINE);
                ArrayList<String> accounts=new ArrayList<String>();
                accounts.add(account);
                ack.setAccounts(accounts);
                String json=new Gson().toJson(ack);
                clientChannel.getChannel().write(json+"\n");
            }
        }
        System.out.println(currentGroupId+": "+account + "下线！");
    }

}
