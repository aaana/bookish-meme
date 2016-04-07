package conf;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;


/**
 * Created by huanganna on 16/4/7.
 */
public class Conf {
    JsonObject jsonObject;

    public void readFile(String confAddr) throws Exception{
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        FileInputStream configIn = new FileInputStream(confAddr);
        jsonObject = parser.parse(IOUtils.toString(configIn)).getAsJsonObject();
    }

    public Conf(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Conf() {

    }

    public int getInt(String key){
        return jsonObject.get(key).getAsInt();
    }

    public String getString(String key){
        return jsonObject.get(key).getAsString();
    }

    public Conf getConf(String key){
        JsonObject jsonObjectNew = jsonObject.getAsJsonObject(key);
        return new Conf(jsonObjectNew);
    }

    public <T> T toObj(Class<T> t){
        Gson gson = new Gson();
        T result = gson.fromJson(jsonObject.toString(), t);
        return result;

    }
}
