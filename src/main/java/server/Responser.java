package server;

import channel.ClientChannel;
import channel.Manager;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.ACK;
import message.ChatContent;
import message.Message;
import message.MessageStatus;
import protocol.ACKType;
import protocol.MessageType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tanjingru on 3/21/16.
 */

public class Responser extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

        System.out.println("from responser, type" + message.getType() + "status:" + message.getMessageStatus() );

        Channel incomingChannel  = channelHandlerContext.channel();

        MessageType messageType = message.getType();
        MessageStatus messageStatus = message.getMessageStatus();

        Gson gson = new Gson();
        ACK ack = new ACK();

        //fail to login
        if (messageType == MessageType.AUTHORITY && messageStatus == MessageStatus.LOGINFAIL ){
            ack.setType(ACKType.LOGINFAIL);
        }

        // success to login
        if (messageType == MessageType.AUTHORITY && messageStatus == MessageStatus.NEEDHANDLED){
            ack.setType(ACKType.LOGINSUCCESS);
            ack.setLoginContent(message.getLoginContent());

            int groupId = message.getLoginContent().getGroupId();
            String account = message.getLoginContent().getAccount();
            int missingNum = Manager.groupClientsMissingNum.get(groupId).get(account);
            List<ChatContent> messages = new ArrayList<ChatContent>();
            //从数据库中取
            while (missingNum>0){

                messages.add(new ChatContent("aaa"));
                missingNum--;
                if(missingNum==0){

                    Manager.groupClientsMissingNum.get(groupId).remove(account);
                    Manager.groupClientsMissingNum.get(groupId).put(account, 0);
                }
            }


            System.out.println(Manager.groupClientsMissingNum);
            ack.setMissingChatContents(messages);


        }


        // need to forward the message to everyone connected
        if (messageType == MessageType.CHATTING && messageStatus == MessageStatus.NEEDHANDLED )
        {

            int groupId=-1;
            for (ClientChannel clientChannel : Manager.clientChannels){
                if(clientChannel.getChannel()==incomingChannel){
                    groupId = clientChannel.getGroupId();
                    break;
                }
            }

            Set<String> onlineClientsInSameGroup = new HashSet<String>();
            for (ClientChannel clientChannel : Manager.clientChannels){
                if(clientChannel.getGroupId()==groupId && clientChannel.getChannel()!=incomingChannel){
                    onlineClientsInSameGroup.add(clientChannel.getAccount());
                    ACK toOthersACK = new ACK();
                    toOthersACK.setType(ACKType.OTHERSMESSAGE);
                    toOthersACK.setChatContent(message.getChatContent());
                    String otherACKJson = gson.toJson(toOthersACK);
                    clientChannel.getChannel().write(otherACKJson + "\n");
                }
            }

            onlineClientsInSameGroup.add(message.getChatContent().getAccount());

            Set<String> clientKeysSet = (Manager.groupClientsMissingNum.get(groupId)).keySet();

            List<String> clientKeys = new ArrayList<String>();
            clientKeys.addAll(clientKeysSet);
            for(String clientKey : clientKeys){
                System.out.println(clientKey);
                if(!onlineClientsInSameGroup.contains(clientKey)){
                    int num = Manager.groupClientsMissingNum.get(groupId).get(clientKey);
                    Manager.groupClientsMissingNum.get(groupId).remove(clientKey);
                    Manager.groupClientsMissingNum.get(groupId).put(clientKey, num + 1);
                    System.out.println(Manager.groupClientsMissingNum);
                }
            }


            System.out.println(Manager.groupClientsMissingNum);

            ack.setType(ACKType.SENDSUCCESS);
        }


        if(messageType == MessageType.CHATTING && messageStatus == MessageStatus.OVERRANGE){
            for (ClientChannel clientChannel : Manager.clientChannels){

                if(clientChannel.getChannel()==incomingChannel){
                    Manager.clientChannels.remove(clientChannel);
                }
            }
            ack.setType(ACKType.REDOLOGIN);

        }

        if(messageType == MessageType.CHATTING && messageStatus == MessageStatus.TOOFREQUENT){
            ack.setType(ACKType.TOOFRENQUENT);
        }

        String ackJson = gson.toJson(ack);
        incomingChannel.write(ackJson + "\n");

    }
}
