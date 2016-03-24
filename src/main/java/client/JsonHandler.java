package client;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.ACK;
import message.ChatContent;
import protocol.ACKType;

/**
 * Created by 马二爷 on 2016/3/24.
 */
public class JsonHandler extends ChannelInboundMessageHandlerAdapter<String> {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        Gson gson=new Gson();
        ACK ack=gson.fromJson(msg,ACK.class);
        ACKType type=ack.getType();
        if(type==ACKType.OTHERSMESSAGE)
        {
            ChatContent chatContent=gson.fromJson(ack.getContent().toString(),ChatContent.class);
            ack.setContent(chatContent);
        }
        ctx.nextInboundMessageBuffer().add(ack);
        ctx.fireInboundBufferUpdated();

    }
}
