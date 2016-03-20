package server;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.util.CharsetUtil;
import message.Message;

/**
 * Created by 马二爷 on 2016/3/20.
 */
public class JsonToObjectHandler extends ChannelInboundMessageHandlerAdapter<String> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        Gson gson=new Gson();
        Message message=gson.fromJson(msg,Message.class);
        ctx.nextInboundMessageBuffer().add(message);
        ctx.fireInboundBufferUpdated();
    }
}
