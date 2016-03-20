package Util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by huanganna on 16/3/19.
 */
public class ConfReader {


    private String host;
    private int port;

    private static ConfReader _instance;

    static {
        Gson gson = new Gson();
        FileInputStream configIn = null;
        try {
            configIn = new FileInputStream("conf.json");
            _instance = gson.fromJson(IOUtils.toString(configIn), ConfReader.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(configIn);
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static ConfReader getInstance() {
        return _instance;
    }
}
