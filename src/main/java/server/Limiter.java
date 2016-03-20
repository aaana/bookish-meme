package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;

import java.util.HashMap;

/**
 * Created by 马二爷 on 2016/3/20.
 */
public class Limiter extends ChannelInboundMessageHandlerAdapter<Message> {
    private HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
    private static final Integer maxMsgNumber=100;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
        int id=ctx.channel().id();
        if(map.containsKey(id))
        {
            if(map.get(id)<maxMsgNumber) {
                map.put(id, map.get(id) + 1);
                msg.setNeedsToHandle(0);
            }
            else{
                //relogin
                msg.setNeedsToHandle(1);
            }
        }
        else
        {
            map.put(id,1);
            msg.setNeedsToHandle(0);
        }

    }
}
