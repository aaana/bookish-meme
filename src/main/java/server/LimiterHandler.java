package server;

import Util.Conf;
import Util.ConfigReader;
import conf.Config;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;

import com.google.common.util.concurrent.RateLimiter;
import message.MessageStatus;
import protocol.MessageType;

/**
 * Created by 马二爷 on 2016/3/20.
 */
public class LimiterHandler extends ChannelInboundMessageHandlerAdapter<Message> {


    private int receivedNumber=0;
    private int maxMsgNumber;// configurable
    private int maxMsgNumberPerSec;//configurable
    private final RateLimiter rateLimiter;

    private Config config;
    public LimiterHandler() throws Exception {
        config = new Config();
        config.readFile("config/conf.json");
        maxMsgNumberPerSec=config.getConf("server").getInt("maxMsgNumberPerSec");
        rateLimiter=RateLimiter.create(maxMsgNumberPerSec);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
        config.readFile("config/conf.json");
        maxMsgNumber=config.getConf("server").getInt("maxMsgNumber");
        maxMsgNumberPerSec = config.getConf("server").getInt("maxMsgNumberPerSec");
        if(rateLimiter.getRate()!=maxMsgNumberPerSec)
             rateLimiter.setRate(maxMsgNumberPerSec);

        if(msg.getType() == MessageType.CHATTING){
            if(rateLimiter.tryAcquire())
            {
                if(receivedNumber+1<maxMsgNumber) {
                    receivedNumber++;
                    msg.setMessageStatus(MessageStatus.NEEDHANDLED);
                }
                else{
                    //too many
                    msg.setMessageStatus(MessageStatus.OVERRANGE);
                }
            }
            else
            {
                //too frequently
                msg.setMessageStatus(MessageStatus.TOOFREQUENT);
            }
        }
        ctx.nextInboundMessageBuffer().add(msg);
        ctx.fireInboundBufferUpdated();

    }
}
