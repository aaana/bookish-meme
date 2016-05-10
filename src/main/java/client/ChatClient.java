package client;

/**
 * Created by tanjingru on 3/17/16.
 */
import Util.Conf;
import Util.ConfigReader;
import com.google.gson.Gson;
import compressor.CompressTask;
import compressor.TZCompressor;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import message.ChatContent;
import message.LoginContent;
import message.Message;
import message.MessageStatus;
import octoteam.tahiti.config.ConfigManager;
import octoteam.tahiti.config.loader.JsonAdapter;
import octoteam.tahiti.performance.PerformanceMonitor;
import octoteam.tahiti.performance.reporter.LogReporter;
import octoteam.tahiti.performance.reporter.RollingFileReporter;
import org.apache.log4j.PropertyConfigurator;
import protocol.MessageType;
import server.LoggerHandler;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class ChatClient {

    public static void main(String[] args) throws Exception{
        PropertyConfigurator.configure("config/log4j-client.property");
        Timer timer = new Timer();
        timer.schedule(new ClientLoggerTask(), 60 * 1000,  60 * 1000);
        ChatClient chatClient = new ChatClient();
        chatClient.Login("101", "123456");
    }

    private final String host;
    private final int port;
    private String account;
    private Channel connectedChannel;
    private EventLoopGroup eventGroup = null;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public ChatClient() {
//        Config config = new Config();
        ConfigManager configManager = new ConfigManager(new JsonAdapter(),"./config/config.json");
        String host="localhost";
        int port=8080;
        try{
            Conf conf = configManager.loadToBean(Conf.class);
            host = conf.getHost();
            port = conf.getPort();
        }catch (IOException e){
            e.printStackTrace();
        }

        /*
        try {
            config.readFile("config/conf.json");
            host = config.getConf("server").getString("host");
            port = config.getConf("server").getInt("port");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        this.host = host;
        this.port = port;

        // pm
        LogReporter realtimeReporter = new RollingFileReporter("pmlog/client-log-%d{yyyy-MM-dd_HH-mm}.log");

        PerformanceMonitor monitor = new PerformanceMonitor(realtimeReporter);
        monitor
                .addRecorder(ClientLoggerHandler.loginFail)
                .addRecorder(ClientLoggerHandler.loginSuccess)
                .addRecorder(ClientLoggerHandler.receiveMsgNumber)
                .addRecorder(ClientLoggerHandler.sendMsgNumber)
                .start(1, TimeUnit.MINUTES);

        TZCompressor tzCompressor = new TZCompressor();
        CompressTask PMTask = new CompressTask("archive/archive-all","pm-log/");
        PMTask.setInterval(1000*60*60*24);
        PMTask.setDelay(1000*60);
        CompressTask messageTask = new CompressTask("archive/archive-all", "messageRecords/");
        messageTask.setInterval(1000 * 60 * 60 * 24);
        messageTask.setDelay(1000 * 60);
        CompressTask allTask = new CompressTask("archive/archive", "archive/archive-all");
        allTask.setInterval(1000*60*60*24*7);
        allTask.setDelay(1000*60);
        tzCompressor.addTask(PMTask,"PM")
                .addTask(messageTask, "MSG")
                .addTask(allTask, "all");
        tzCompressor.startAllTask();

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

    public void closeConnection(){
        eventGroup.shutdownGracefully();
    }

    public void Login(String account, String password) throws InterruptedException{

        connectedChannel = connectServer();

        LoginContent loginContent = new LoginContent(account, password);
        Message loginMessage = new Message(MessageType.AUTHORITY,MessageStatus.NEEDHANDLED);
        loginMessage.setLoginContent(loginContent);
        Gson gson=new Gson();
        String jsonPayload= gson.toJson(loginMessage);
        System.out.println(jsonPayload);

        connectedChannel.write(jsonPayload + "\n\r");

//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
////
////        while (true){
////            try {
////                String msg=in.readLine();
////                ChatContent content = new ChatContent(msg);
////                Message chattingMessage = new Message(MessageType.CHATTING,MessageStatus.NEEDHANDLED);
////                chattingMessage.setChatContent(content);
////                jsonPayload=gson.toJson(chattingMessage);
////                //channel.write(message);
////                System.out.println("msg: " + msg + "\n");
////                connectedChannel.write(jsonPayload+"\n\r");
////                //channel.write(in.readLine() + "\n\r");
////            } catch (Exception e){
////                ;
////            }
////        }
    }

    public void sendMessage(ChatContent chatContent) throws InterruptedException{
        Gson gson=new Gson();
        Message chattingMessage = new Message(MessageType.CHATTING,MessageStatus.NEEDHANDLED);
        chattingMessage.setChatContent(chatContent);
        String jsonPayload=gson.toJson(chattingMessage);
        //channel.write(message);
        System.out.println("msg: " + chatContent.getMessage() + "\n");
        connectedChannel.write(jsonPayload+"\n\r");
        ClientLoggerHandler.sendMsgNumber.record();
    }

}
