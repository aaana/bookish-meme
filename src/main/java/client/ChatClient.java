package client;

/**
 * Created by tanjingru on 3/17/16.
 */
import Util.Conf;
import Util.ConfigReader;
import com.google.gson.Gson;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import message.ChatContent;
import message.LoginContent;
import message.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {

    public static void main(String[] args) throws Exception{
        ConfigReader configReader = new ConfigReader("conf.json");
        Conf conf = configReader.readConf();
        String host = conf.getHost();
        int port = conf.getPort();
        ChatClient chatClient = new ChatClient(host, port);
        chatClient.Login("101", "123456");
    }

    private final String host;
    private final int port;
    private Channel connectedChannel;
    private EventLoopGroup eventGroup = null;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Channel connectServer() throws InterruptedException{
        Channel connectChannel;
        eventGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(eventGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChatClientInitializer());

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();
            connectChannel = f.channel();
        }catch (Exception e){
            connectChannel = null;
            System.out.println("链接服务器失败");
        }
        return connectChannel;
    }

    private void closeConnection(){
        eventGroup.shutdownGracefully();
    }

    public void Login(String account, String password) throws InterruptedException{
        connectedChannel = connectServer();

        LoginContent loginContent = new LoginContent(account, password);
        Message loginMessage = new Message(0, loginContent, 0);
        Gson gson=new Gson();
        String jsonPayload= gson.toJson(loginMessage);
        System.out.println(jsonPayload);

        connectedChannel.write(jsonPayload + "\n\r");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true){
            try {
                String msg=in.readLine();
                ChatContent content=new ChatContent(msg);
                Message message=new Message(1,content,0);
                jsonPayload=gson.toJson(message);
                //channel.write(message);
                connectedChannel.write(jsonPayload+"\n\r");
                //channel.write(in.readLine() + "\n\r");
            } catch (Exception e){
                ;
            }
        }
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
            Gson gson=new Gson();
            while (true){
                String msg=in.readLine();
                ChatContent content=new ChatContent(msg);
                Message message=new Message(1,content,0);
                String jsonPayload=gson.toJson(message);
                //channel.write(message);
                 channel.write(jsonPayload+"\n\r");
                //channel.write(in.readLine() + "\n\r");
            }
            // Wait until the connection is closed.
        }finally {
            group.shutdownGracefully();
        }
    }
}
