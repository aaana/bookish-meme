package server;

import com.google.common.util.concurrent.RateLimiter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
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
                .addLast("register",new RegisterHandler())
                .addLast("authority", new AuthorityHandler())
                .addLast("channelManager", new ChannelManagerHandler())
                .addLast("addGroup",new AddGroupHandler())
                .addLast("Limiter", new LimiterHandler())
                .addLast("log", new LoggerHandler())
                .addLast("response", new Responser());
    }

}
