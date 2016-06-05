package server;

import channel.Manager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;
import provider.ServiceProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huanganna on 16/6/4.
 */
public class CreateGroupHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if(message.getType()== MessageType.CREATEGROUP){
            String accountId = message.getGroupContent().getAccount();
            String groupId = message.getGroupContent().getGroupId();
            try {
                        int result = ServiceProvider.getDbServer().createGroup(groupId);
                        if(result<=0){
                            message.setMessageStatus(MessageStatus.GROUPALREADYEXIST);
//                            Alert alert = new Alert(Alert.AlertType.ERROR,"创建失败");
//                            alert.setContentText("该组已存在！");
//                            alert.showAndWait();
                        }else {
                            Map<String,Integer> missingNum = new HashMap<>();
                            missingNum.put(accountId,0);
                            Manager.groupClientsMissingNum.put(groupId,missingNum);
                            ServiceProvider.getDbServer().addGroup(accountId, groupId);

//                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"创建成功！");
//                            alert.showAndWait();
//                            ServiceProvider.getDbServer().addGroup(account, groupId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
        }
        //其他情况下不作处理直接流到下一个channel
        channelHandlerContext.nextInboundMessageBuffer().add(message);
        channelHandlerContext.fireInboundBufferUpdated();
    }
}

