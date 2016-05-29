package messagestore_server;

import log.Log;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;
import provider.ServiceProvider;
import server.DBOperate;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huanganna on 16/5/29.
 */
public class MessageStoreServerImpl implements MessageStoreServer {

    public void store(Message message) throws Exception {
        MessageStatus messageStatus = message.getMessageStatus();
        MessageType messageType = message.getType();
        if( messageType == MessageType.CHATTING) {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
            String snow = dateFormat.format(now);
            Log.writeFile("./messageRecords/server.log", "[" + snow + "]" + " " + message.getChatContent().getSender() + ": " + message.getChatContent().toString());
            if (messageStatus == MessageStatus.NEEDHANDLED) {
                //将消息记录到数据库中

//                DBOperate dbOperate = new DBOperate();
//                dbOperate.insert(message.getChatContent());
                ServiceProvider.getDbServer().insert(message.getChatContent());


            }
        }
    }
}
