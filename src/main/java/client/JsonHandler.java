package client;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.ACK;
import message.ChatContent;
import protocol.ACKType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Created by 马二爷 on 2016/3/24.
 */
public class JsonHandler extends ChannelInboundMessageHandlerAdapter<String> {

    public ACK readContentACK(JsonReader reader) throws IOException
    {
        String type=null;
        ACKType t=null;
        ChatContent content=null;

        reader.beginObject();
        while(reader.hasNext())
        {
            String name=reader.nextName();
            if(name.equals("type")){
                type=reader.nextString();
            }else if(name.equals("Content")){
                content=readContent(reader);
            }
            else{
                reader.skipValue();
            }

        }
        reader.endObject();
        if(type!=null) {
            if (type.equals("LOGINFAIL")) t = ACKType.LOGINFAIL;
            else if (type.equals("LOGINSUCCESS")) t = ACKType.LOGINSUCCESS;
            else if (type.equals("REDOLOGIN")) t = ACKType.REDOLOGIN;
            else if (type.equals("TOOFRENQUENT")) t = ACKType.TOOFRENQUENT;
            else if (type.equals("SENDSUCCESS")) t = ACKType.SENDSUCCESS;
            else if (type.equals("OTHERSMESSAGE")) t = ACKType.OTHERSMESSAGE;
        }
        return new ACK(t,content);
    }
    public ChatContent readContent(JsonReader reader) throws IOException
    {
        String message=null;
        reader.beginObject();
        while (reader.hasNext())
        {
            String name=reader.nextName();
            if(name.equals("message")){
                message=reader.nextString();
            }
            else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ChatContent(message);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        Gson gson=new Gson();
        System.out.println("from client json:"+msg);
        ACK ack=gson.fromJson(msg,ACK.class);

        ACKType type=ack.getType();
        JsonReader reader=new JsonReader(new StringReader(msg));

        if(type==ACKType.OTHERSMESSAGE)
        {
            ack=readContentACK(reader);
        }
        ctx.nextInboundMessageBuffer().add(ack);
        ctx.fireInboundBufferUpdated();

    }
}
