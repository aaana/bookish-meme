package Util;


import conf.Config;

/**
 * Created by huanganna on 16/3/20.
 */
public class TestNew {
    public static void main(String[] args) throws Exception{
//        ConfigReader reader = new ConfigReader();
//        Config conf = reader.readConf("config/conf.json");
//        if(conf!=null){
//            System.out.println(conf.getHost()+" "+conf.getPort()+" "+conf.getMaxMsgNumber()+" "+conf.getMaxMsgNumberPerSec());
//        }

        Config config = new Config();
        config.readFile("config/conf.json");
        System.out.println(config.getConf("server").getString("host"));



    }

}