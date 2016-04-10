package conf;

import com.google.gson.*;
import exception.FileNotExistException;
import exception.KeyNotExistException;
import exception.NoConfigurationFileException;
import exception.TypeConvertionException;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by huanganna on 16/4/7.
 */
public class Config {
    JsonObject jsonObject;

    public void readFile(String confAddr) throws FileNotExistException{
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        FileInputStream configIn;
        try {
            configIn = new FileInputStream(confAddr);
            try{
                jsonObject = parser.parse(IOUtils.toString(configIn)).getAsJsonObject();
            }catch (IOException e){
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            throw new FileNotExistException();
        }
    }

    private Config(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Config() {
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

    public float getFloat(String key)throws Exception{
        if(!readFileOrNot()){
            throw new NoConfigurationFileException();
        }
        if(jsonObject.get(key)==null){
            throw new KeyNotExistException();
        }
        try{
            float result = jsonObject.get(key).getAsFloat();
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

    public Config getConf(String key) throws Exception{
        if(!readFileOrNot()){
            throw new NoConfigurationFileException();
        }
        JsonObject jsonObjectNew = jsonObject.getAsJsonObject(key);
        return new Config(jsonObjectNew);
    }

    public <T> T toObj(Class<T> t) throws Exception{
        if(!readFileOrNot()){
            throw new NoConfigurationFileException();
        }
        Gson gson = new Gson();
        T result = gson.fromJson(jsonObject.toString(), t);
        return result;
    }

    public String[] getStringArray(String key) throws Exception{
        if(!readFileOrNot()){
            throw new NoConfigurationFileException();
        }
        if(jsonObject.get(key)==null){
            throw new KeyNotExistException();
        }
        JsonArray jsonArray = jsonObject.get(key).getAsJsonArray();
        final String[] array = new String[jsonArray.size()];
        try{
            for (int i = 0; i < array.length; i++) {
                final JsonElement jsonElement = jsonArray.get(i);
                array[i] = jsonElement.getAsString();
            }
        }catch (NumberFormatException e){
            throw new TypeConvertionException();
        }

        return array;
    }
}
