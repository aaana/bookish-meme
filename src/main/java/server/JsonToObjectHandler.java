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

    public Message readChatMessage(JsonReader reader) throws IOException
    {
        String status=null;
        MessageStatus st=null;
        String type=null;
        MessageType t=null;
        ChatContent content=null;

        reader.beginObject();
        while (reader.hasNext())
        {
            String name=reader.nextName();
            if(name.equals("messageStatus")){
                status=reader.nextString();
            }
            else if (name.equals("type")){
                type=reader.nextString();
            }
            else if(name.equals("content")){
                content=chatContentReader(reader);
            }else{
                reader.skipValue();
            }
        }


        reader.endObject();
        if(status!=null) {
            if (status.equals("NEEDHANDLED")) st = MessageStatus.NEEDHANDLED;
            else if (status.equals("LOGINFAIL")) st = MessageStatus.LOGINFAIL;
            else if (status.equals("OVERRANGE")) st = MessageStatus.OVERRANGE;
            else if (status.equals("TOOFREQUENT")) st = MessageStatus.TOOFREQUENT;
        }
        if(type!=null) {
            if (type.equals("AUTHORITY")) t = MessageType.AUTHORITY;
            else if (type.equals("CHATTING")) t = MessageType.CHATTING;
        }
        return new Message(content,st,t);

    }
    public Message readLoginMessage(JsonReader reader) throws IOException
    {
        String status=null;
        MessageStatus st=null;
        String type=null;
        MessageType t=null;
        LoginContent content=null;

        reader.beginObject();
        while (reader.hasNext())
        {
            String name=reader.nextName();
            if(name.equals("messageStatus")){
                status=reader.nextString();
            }
            else if (name.equals("type")){
                type=reader.nextString();
            }
            else if(name.equals("content")){
                content=loginContentReader(reader);
            }else{
                reader.skipValue();
            }
        }

        reader.endObject();
        if(status!=null) {
            if (status.equals("NEEDHANDLED")) st = MessageStatus.NEEDHANDLED;
            else if (status.equals("LOGINFAIL")) st = MessageStatus.LOGINFAIL;
            else if (status.equals("OVERRANGE")) st = MessageStatus.OVERRANGE;
            else if (status.equals("TOOFREQUENT")) st = MessageStatus.TOOFREQUENT;
        }
        if(type!=null) {
            if (type.equals("AUTHORITY")) t = MessageType.AUTHORITY;
            else if (type.equals("CHATTING")) t = MessageType.CHATTING;
        }
        return new Message(content,st,t);

    }
    public ChatContent chatContentReader(JsonReader reader) throws IOException {
        String message=null;
        reader.beginObject();
        while (reader.hasNext())
        {
            String name=reader.nextName();
            if(name.equals("message"))
            {
                message=reader.nextString();
            }
            else
            {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ChatContent(message);

    }
    public LoginContent loginContentReader(JsonReader reader) throws IOException
    {
        String account=null;
        String password=null;
        reader.beginObject();
        while(reader.hasNext())
        {
            String name=reader.nextName();
            if(name.equals("account")){
                account=reader.nextString();
            }else if(name.equals("password")) {
                password=reader.nextString();
            }else {reader.skipValue();}
        }
        reader.endObject();
        return new LoginContent(account,password);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.print("from server json:"+msg);
        Gson gson=new Gson();
        JsonReader reader =new JsonReader(new StringReader(msg));

        Message message = gson.fromJson(msg,Message.class);

        MessageType type=message.getType();

        if(type==MessageType.AUTHORITY)
        {
            message=readLoginMessage(reader);
        }
        else if (type==MessageType.CHATTING){

            message=readChatMessage(reader);
        }

        ctx.nextInboundMessageBuffer().add(message);
        ctx.fireInboundBufferUpdated();

    }
}
