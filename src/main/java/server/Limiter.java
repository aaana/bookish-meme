package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;

import java.util.HashMap;
import com.google.common.util.concurrent.RateLimiter;
/**
 * Created by 马二爷 on 2016/3/20.
 */
public class Limiter extends ChannelInboundMessageHandlerAdapter<Message> {
    private int receivedNumber=0;
    private static final Integer maxMsgNumber=100;// configurable
    private static final Integer maxMsgNumberPerSec=5;//configurable
    final RateLimiter rateLimiter=RateLimiter.create(maxMsgNumberPerSec);
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
        if(rateLimiter.tryAcquire())
        {
            if(receivedNumber+1<maxMsgNumber) {
                receivedNumber++;
                msg.setNeedsToHandle(0);
            }
            else{
                //too many
                msg.setNeedsToHandle(1);
            }
        }
        else
        {
            //too frequently
            msg.setNeedsToHandle(2);
        }
        ctx.nextInboundMessageBuffer().add(msg);
        ctx.fireInboundBufferUpdated();

    }
}
