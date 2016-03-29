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

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        Gson gson=new Gson();
        System.out.println("from client json:"+msg);
        ACK ack=gson.fromJson(msg,ACK.class);

        ctx.nextInboundMessageBuffer().add(ack);
        ctx.fireInboundBufferUpdated();

    }
}
