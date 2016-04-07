package config;

import com.google.gson.JsonElement;

/**
 * Created by huanganna on 16/4/2.
 */
public class ConfigReader {

    private String url;

    public ConfigReader(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //public JsonElement getValue(String key){

   // }

}
