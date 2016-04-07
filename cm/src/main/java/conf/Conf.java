package conf;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.FileNotExistException;
import exception.KeyNotExistException;
import exception.NoConfigurationFileException;
import exception.TypeConvertionException;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Created by huanganna on 16/4/7.
 */
public class Conf {
    JsonObject jsonObject;

    public void readFile(String confAddr) throws Exception{
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        FileInputStream configIn;
        try {
            configIn = new FileInputStream(confAddr);
            jsonObject = parser.parse(IOUtils.toString(configIn)).getAsJsonObject();
        } catch (FileNotFoundException e) {
            throw new FileNotExistException();
        }
    }

    private Conf(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Conf() {

    }

    private boolean readFileOrNot(){
        if(jsonObject==null)
            return false;
        return true;
    }

    public int getInt(String key)throws Exception{
        if(!readFileOrNot()){
            throw new NoConfigurationFileException();
        }
        if(jsonObject.get(key)==null){
            throw new KeyNotExistException();
        }
        try{
            int result = jsonObject.get(key).getAsInt();
            return result;
        }catch (NumberFormatException e){
            throw new TypeConvertionException();
        }

    }

    public String getString(String key)throws Exception{
        if(!readFileOrNot()){
            throw new NoConfigurationFileException();
        }
        if(jsonObject.get(key)==null){
            throw new KeyNotExistException();
        }
        try{
            String result = jsonObject.get(key).getAsString();
            return result;
        }catch (NumberFormatException e){
            throw new TypeConvertionException();
        }

    }

    public Conf getConf(String key) throws Exception{
        if(!readFileOrNot()){
            throw new NoConfigurationFileException();
        }
        JsonObject jsonObjectNew = jsonObject.getAsJsonObject(key);
        return new Conf(jsonObjectNew);
    }

    public <T> T toObj(Class<T> t) throws Exception{
        if(!readFileOrNot()){
            throw new NoConfigurationFileException();
        }
        Gson gson = new Gson();
        T result = gson.fromJson(jsonObject.toString(), t);
        return result;

    }
}
