package Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;


/**
 * Created by huanganna on 16/3/20.
 */
public class TestNew {
    public static void main(String[] args) throws Exception{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Conf.class, new ConfDeserializer());
        Gson gson = gsonBuilder.create();

        FileInputStream configIn = new FileInputStream("conf.json");
        Conf conf = gson.fromJson(IOUtils.toString(configIn), Conf.class);

        System.out.println(conf.getHost()+" "+conf.getPort());
    }

}
