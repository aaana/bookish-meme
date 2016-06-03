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
import provider.ServiceProvider;

import java.util.*;

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
            List<String>sameGroupOnlineAccounts=new ArrayList<String>();


            for(ClientChannel clientChannel:Manager.clientChannels)
            {
                if(clientChannel.getGroupId()==groupId&&clientChannel.getChannel()!=incomingChannel)
                {
                    sameGroupOnlineAccounts.add(clientChannel.getAccount());
                    ACK a=new ACK();
                    a.setType(ACKType.SOMEONEONLINE);
                    ArrayList<String> account=new ArrayList<String>();
                    account.add(message.getLoginContent().getAccount());
                    a.setAccounts(account);
                    String json=gson.toJson(a);
                    clientChannel.getChannel().write(json+"\n");
                }
            }

            ack.setAccounts(sameGroupOnlineAccounts);

            String account = message.getLoginContent().getAccount();
            int missingNum = Manager.groupClientsMissingNum.get(groupId).get(account);
            List<ChatContent> messages = new ArrayList<ChatContent>();
            //从数据库中取！！unfinished
//            DBOperate dbOperate = new DBOperate();
            if (missingNum>0){
                List<ChatContent> temp;
//                temp = dbOperate.getALLMessageByGid(groupId);
                temp = ServiceProvider.getDbServer().getALLMessageByGid(groupId);
                System.out.println(temp);
                messages = temp.subList(temp.size()-missingNum,temp.size());
                Manager.groupClientsMissingNum.get(groupId).remove(account);
                Manager.groupClientsMissingNum.get(groupId).put(account, 0);

//                messages.add(new ChatContent("aaa"));
//                missingNum--;
//                if(missingNum==0){
//
//                    Manager.groupClientsMissingNum.get(groupId).remove(account);
//                    Manager.groupClientsMissingNum.get(groupId).put(account, 0);
//                }
            }

            //找该组所有成员未接受消息的最大个数

            Iterator iterator =  Manager.groupClientsMissingNum.get(groupId).entrySet().iterator();
            int maxValue = 0;
            while(iterator.hasNext()){
                Map.Entry<String,Integer> entry = (Map.Entry)(iterator.next());
                int value = entry.getValue();
                if(value > maxValue){
                    maxValue = value;
                }
            }

            //删除数据库中该组前(该组所有消息－maxValue)个消息!!!! unfinished
//            dbOperate.delete(groupId,maxValue);
            ServiceProvider.getDbServer().delete(groupId,maxValue);


            System.out.println(Manager.groupClientsMissingNum);
            ack.setMissingChatContents(messages);
            ack.setGroupId(groupId);


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
            //转发给同组在线的其他成员
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

            //该组所有在线clients的account,包括发送方
            onlineClientsInSameGroup.add(message.getChatContent().getSender());

            Set<String> clientKeysSet = (Manager.groupClientsMissingNum.get(groupId)).keySet();

            //该组所有clients的account
            List<String> clientKeys = new ArrayList<String>();
            clientKeys.addAll(clientKeysSet);
            for(String clientKey : clientKeys){
                System.out.println(clientKey);

                //该组不在线成员遗漏消息数+1
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

        if(messageType==MessageType.ADDINGGROUP){
            ack.setType(ACKType.ADDSUCCESS);
            int groupId = message.getGroupContent().getGroupId();
            List<String>sameGroupOnlineAccounts=new ArrayList<String>();


            for(ClientChannel clientChannel:Manager.clientChannels)
            {
                if(clientChannel.getGroupId()==groupId&&clientChannel.getChannel()!=incomingChannel)
                {
                    sameGroupOnlineAccounts.add(clientChannel.getAccount());
                    ACK a=new ACK();
                    a.setType(ACKType.SOMEONEADDGROUP);
                    ArrayList<String> account=new ArrayList<String>();
                    account.add(message.getGroupContent().getAccount());
                    a.setAccounts(account);
                    String json=gson.toJson(a);
                    clientChannel.getChannel().write(json+"\n");
                }

                if(clientChannel.getChannel() == incomingChannel){
                    clientChannel.setGroupId(groupId);
                }
            }

            ack.setAccounts(sameGroupOnlineAccounts);
            ack.setGroupId(groupId);
        }

        if(messageType==MessageType.REGISTER && messageStatus==MessageStatus.ACCOUNTEXSIT){
            ack.setType(ACKType.ACCOUNTEXIST);
        }

        if(messageType==MessageType.REGISTER&&messageStatus==MessageStatus.REGISTERFAIL){
            ack.setType(ACKType.REGISTERFAIL);
        }

        if(messageType==MessageType.REGISTER&&messageStatus==MessageStatus.NEEDHANDLED){
            String account = message.getRegisterContent().getAccount();
            ack.setType(ACKType.REGISTERSUCCESS);
            if(Manager.groupClientsMissingNum.containsKey(0))
                Manager.groupClientsMissingNum.get(0).put(account,0);
            else
            {
                HashMap<String,Integer> missingNum = new HashMap<String,Integer>();
                missingNum.put(account,0);
                Manager.groupClientsMissingNum.put(0,missingNum);
            }
        }
        String ackJson = gson.toJson(ack);
        incomingChannel.write(ackJson + "\n");

    }
}
