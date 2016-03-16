package client;

/**
 * Created by tanjingru on 3/17/16.
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {
    public static void main(String[] args) throws Exception{
        new ChatClient("localhost", 8080).run();
    }

    private final String host;
    private final int port;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

        public void run() throws Exception{
            EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(group); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChatClientInitializer());

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            Channel channel = f.channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                channel.write(in.readLine() + "\n\r");
            }
            // Wait until the connection is closed.
        }finally {
            group.shutdownGracefully();
        }
    }
}
