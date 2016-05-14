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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tanjingru on 3/20/16.
 */
public class ChannelManagerHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if( message.getType() == MessageType.AUTHORITY && message.getMessageStatus() == MessageStatus.NEEDHANDLED){

            Channel channel = channelHandlerContext.channel();
            int groupId = message.getLoginContent().getGroupId();
            String account = message.getLoginContent().getAccount();
            ClientChannel clientChannel = new ClientChannel(channel,groupId);
            clientChannel.setAccount(account);
            Manager.clientChannels.add(clientChannel);


        }
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        int groupid=-1;
        String account=null;

        for(Iterator<ClientChannel> it=Manager.clientChannels.iterator();it.hasNext();)
        {
            ClientChannel clientChannel=it.next();
            if(clientChannel.getChannel()==channel)
            {
                it.remove();
                groupid=clientChannel.getGroupId();
                account=clientChannel.getAccount();
            }

        }
       /* for (ClientChannel clientChannel : Manager.clientChannels){

            if(clientChannel.getChannel()==channel){
                Manager.clientChannels.remove(clientChannel);
                groupid=clientChannel.getGroupId();
                account=clientChannel.getAccount();
            }
        }*/

        for (ClientChannel clientChannel : Manager.clientChannels){

            if(clientChannel.getGroupId()==groupid){
                ACK ack=new ACK();
                ack.setType(ACKType.SOMEONEOFFLINE);
                ArrayList<String> accounts=new ArrayList<String>();
                accounts.add(account);
                ack.setAccounts(accounts);
                String json=new Gson().toJson(ack);
                clientChannel.getChannel().write(json+"\n");
            }
        }
        System.out.println(account + "下线！");
    }

}
