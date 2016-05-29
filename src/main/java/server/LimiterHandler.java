package server;

import Util.Conf;
import Util.ConfigReader;
//import conf.Config;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import message.Message;

import com.google.common.util.concurrent.RateLimiter;
import message.MessageStatus;
import octoteam.tahiti.config.ConfigManager;
import octoteam.tahiti.config.loader.JsonAdapter;
import protocol.MessageType;

import reuse.license.FrequencyRestriction;
import reuse.license.MaxNumOfMessage;

/**
 * Created by 马二爷 on 2016/3/20.
 */
public class LimiterHandler extends ChannelInboundMessageHandlerAdapter<Message> {

    private int maxMsgNumber;// configurable
    private int maxMsgNumberPerSec;//configurable
    private Conf conf;
    private FrequencyRestriction frequencyRestriction;
    private MaxNumOfMessage maxMessageLimter;

    private ConfigManager configManager;
    public LimiterHandler() throws Exception {
        configManager = new ConfigManager(new JsonAdapter(),"./config/config.json");
        conf = configManager.loadToBean(Conf.class);
        maxMsgNumberPerSec = conf.getMaxMsgNumberPerSec();
        maxMsgNumber = conf.getMaxMsgNumber();
        frequencyRestriction=new FrequencyRestriction(maxMsgNumberPerSec);
        maxMessageLimter=new MaxNumOfMessage(maxMsgNumber);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {


        if(msg.getType() == MessageType.CHATTING){
            if(frequencyRestriction.Check())
            {
                if(maxMessageLimter.Check()) {
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
