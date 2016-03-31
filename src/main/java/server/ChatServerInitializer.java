package server;

import com.google.common.util.concurrent.RateLimiter;
import filter.MessageFilter;
import handler.RateLimitHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import limit.limiter.RtLimiter;
import limit.limiter.SumCountRtLimiter;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;

/**
 * Created by tanjingru on 3/17/16.
 */
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringEncoder())
                .addLast("json_to_ob",new JsonToObjectHandler())
                .addLast("authority",new AuthorityHandler())
                .addLast("channelManager", new ChannelManagerHandler())
                .addLast("Limiter", new LimiterHandler())
                .addLast("log", new LoggerHandler())
                .addLast("response", new Responser());
    }


    private RateLimitHandler<Message> createRateLimitHandler(){

        RtLimiter rateLimiter = new SumCountRtLimiter(5);

        MessageFilter<Message> messageFilter = new MessageFilter<Message>() {
            @Override
            public Boolean shouldFilter(Message msg) {
                if(msg.getType() == MessageType.CHATTING) return true;
                else return false;
            }
        };

        return new RateLimitHandler<Message>(messageFilter, rateLimiter) {
            @Override
            public void messageAgree(Message msg) {
                ;
            }

            @Override
            public void messageDisagree(Message msg) {
                msg.setMessageStatus(MessageStatus.OVERRANGE);
            }
        };

    }

}
