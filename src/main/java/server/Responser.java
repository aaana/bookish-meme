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
        }


        // need to forward the message to everyone connected
        if (messageType == MessageType.CHATTING && messageStatus == MessageStatus.NEEDHANDLED )
        {

//            for (Channel channel : Manager.channels){
//                if ( channel != incomingChannel){
//                    ACK toOthersACK = new ACK();
//                    toOthersACK.setType(ACKType.OTHERSMESSAGE);
//                    toOthersACK.setChatContent(message.getChatContent());
//                    String otherACKJson = gson.toJson(toOthersACK);
//                    channel.write(otherACKJson + "\n");
//                }
//            }

            for (ClientChannel clientChannel : Manager.clientChannels){
                if(clientChannel.getChannel()!=incomingChannel){
                    ACK toOthersACK = new ACK();
                    toOthersACK.setType(ACKType.OTHERSMESSAGE);
                    toOthersACK.setChatContent(message.getChatContent());
                    String otherACKJson = gson.toJson(toOthersACK);
                    clientChannel.getChannel().write(otherACKJson + "\n");
                }
            }

            ack.setType(ACKType.SENDSUCCESS);
        }


        if(messageType == MessageType.CHATTING && messageStatus == MessageStatus.OVERRANGE){
//            Manager.channels.remove(incomingChannel);
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
