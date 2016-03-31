package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.PropertyConfigurator;

import java.util.Timer;

/**
 * Created by tanjingru on 3/17/16.
 */

public class ChatServer{

    public static void main(String[] args) throws InterruptedException{

        PropertyConfigurator.configure("config/log4j-server.property");

        // 开始定时任务
        Timer timer = new Timer();
        timer.schedule(new LoggerTask(), 60 * 1000,  60 * 1000);

        //打开服务器
        new ChatServer(8080).run();

    }

    private final int port;
    public ChatServer(int port){
        this.port = port;
    }

    public void run() throws InterruptedException{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitializer());

            Channel channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();

        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
