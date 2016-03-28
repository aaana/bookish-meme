package Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;

/**
 * Created by huanganna on 16/3/20.
 */
public class ConfigReader {
//    private String confAddr;

//    public ConfigReader(String confAddr) {
//        this.confAddr = confAddr;
//    }


    public ConfigReader() {
    }

    public Conf readConf(String confAddr){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Conf.class, new ConfDeserializer());
        Gson gson = gsonBuilder.create();
        Conf conf = null;
        try{
            FileInputStream configIn = new FileInputStream(confAddr);
            conf = gson.fromJson(IOUtils.toString(configIn), Conf.class);
        }
        catch (Exception e){

        }

        return conf;
    }
}
