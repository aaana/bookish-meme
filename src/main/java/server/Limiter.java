package server;

import Util.Conf;
import Util.ConfigReader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;

import com.google.common.util.concurrent.RateLimiter;
/**
 * Created by 马二爷 on 2016/3/20.
 */
public class Limiter extends ChannelInboundMessageHandlerAdapter<Message> {
    private int receivedNumber=0;
    private int maxMsgNumber;// configurable
    private int maxMsgNumberPerSec;//configurable
    private final RateLimiter rateLimiter;
    public Limiter() throws Exception {
        ConfigReader reader = new ConfigReader("conf.json");
        Conf conf = reader.readConf();
        maxMsgNumber=conf.getMaxMsgNumber();
        maxMsgNumberPerSec=conf.getMaxMsgNumberPerSec();
        rateLimiter=RateLimiter.create(maxMsgNumberPerSec);
    }
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println("from limiter, type" + msg.getType() + "status:" + msg.getNeedsToHandle());
        if(msg.getType() == 1){
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
        }
        ctx.nextInboundMessageBuffer().add(msg);
        ctx.fireInboundBufferUpdated();

    }
}
