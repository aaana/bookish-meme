package server;

import client.JsonHandler;
import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import com.google.gson.stream.JsonReader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.util.CharsetUtil;
import message.ChatContent;
import message.LoginContent;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by 马二爷 on 2016/3/20.
 */
public class JsonToObjectHandler extends ChannelInboundMessageHandlerAdapter<String> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.print("from server json:"+msg);

        Gson gson=new Gson();

        Message message = gson.fromJson(msg,Message.class);

        MessageType type=message.getType();

        ctx.nextInboundMessageBuffer().add(message);
        ctx.fireInboundBufferUpdated();

    }
}
