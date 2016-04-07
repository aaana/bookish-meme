package config;

/**
 * Created by huanganna on 16/4/7.
 */
public abstract class ConfigReader {

    public abstract <T>T readToObj(String confAddr,Class<T> type);


}
