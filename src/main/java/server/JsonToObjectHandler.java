package server;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.util.CharsetUtil;
import message.ChatContent;
import message.LoginContent;
import message.Message;
import protocol.MessageType;

/**
 * Created by 马二爷 on 2016/3/20.
 */
public class JsonToObjectHandler extends ChannelInboundMessageHandlerAdapter<String> {


    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {

        Gson gson=new Gson();
        Message message = gson.fromJson(msg,Message.class);
        System.out.print("from json:");
        System.out.println(message.getContent());
        MessageType type=message.getType();

        if(type==MessageType.AUTHORITY)
        {
            LoginContent lg=gson.fromJson(message.getContent().toString(),LoginContent.class);
            message.setContent(lg);
        }
        else if (type==MessageType.CHATTING){
            ChatContent ct=gson.fromJson(message.getContent().toString(),ChatContent.class);
            message.setContent(ct);
        }

        ctx.nextInboundMessageBuffer().add(message);
        ctx.fireInboundBufferUpdated();

    }
}
