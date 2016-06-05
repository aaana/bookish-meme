package server;

import channel.Manager;
import compressor.CompressTask;
import compressor.TZCompressor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import octoteam.tahiti.performance.PerformanceMonitor;
import octoteam.tahiti.performance.reporter.LogReporter;
import octoteam.tahiti.performance.reporter.RollingFileReporter;
import org.apache.log4j.PropertyConfigurator;
import provider.ServiceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Created by tanjingru on 3/17/16.
 */

public class ChatServer{

    public static void main(String[] args) throws InterruptedException{

        PropertyConfigurator.configure("config/log4j-server.property");
        new ChatServer(8080).run();

    }

    private final int port;
    public ChatServer(int port){
        this.port = port;
        LogReporter realtimeReporter = new RollingFileReporter("pmlog/server-log-%d{yyyy-MM-dd_HH-mm}.log");

        PerformanceMonitor monitor = new PerformanceMonitor(realtimeReporter);
        monitor
                .addRecorder(LoggerHandler.forwardMessageNumber)
                .addRecorder(LoggerHandler.ignoredMessageNumber)
                .addRecorder(LoggerHandler.invalidLoginNumber)
                .addRecorder(LoggerHandler.receivedMessageNumber)
                .addRecorder(LoggerHandler.validLoginNumber)
                .start(1, TimeUnit.MINUTES);

        TZCompressor tzCompressor = new TZCompressor();
        CompressTask PMTask = new CompressTask("archive/archive-all","pm-log/");
        PMTask.setInterval(1000 * 60 * 60 * 24);
        PMTask.setDelay(1000*60*2);
        CompressTask allTask = new CompressTask("archive/archive", "archive/archive-all");
        allTask.setInterval(1000*60*60*24*7);
        allTask.setDelay(1000* 60 * 2 );
        tzCompressor.addTask(PMTask,"PM")
                .addTask(allTask,"all");
        tzCompressor.startAllTask();
//
//        if(!Manager.groupClientsMissingNum.containsKey(groupId)){
//
//            Map<String,Integer> missingIndex = new HashMap<String, Integer>(){
//                {
//                    put(accountId,-1);
//                }
//
//            };
//            Manager.groupClientsMissingIndex.put(groupId,missingIndex);
//        }else{
//            Manager.groupClientsMissingIndex.get(groupId).put(accountId,-1);
//        }

        //从数据库中得到client的信息！！！unfinished！！！
//        DBOperate dbOperate = new DBOperate();
        try {
//            dbOperate.getGidAndUid();
            Map<String,List<String>> uidAndGids = ServiceProvider.getDbServer().getGidAndUid();
            for (String accountId:uidAndGids.keySet()){
                List<String> groupIds = uidAndGids.get(accountId);
                for(String groupId:groupIds){
                    if(!Manager.groupClientsMissingNum.containsKey(groupId)){

                        Map<String,Integer> missingIndex = new HashMap<String, Integer>(){
                            {
                                put(accountId,0);
                            }

                        };
                        Manager.groupClientsMissingNum.put(groupId,missingIndex);
                    }else{
                        Manager.groupClientsMissingNum.get(groupId).put(accountId,0);
                    }
                }

            }
            System.out.println(Manager.groupClientsMissingNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
