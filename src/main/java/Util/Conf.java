package Util;

/**
 * Created by huanganna on 16/3/20.
 */
public class Conf {
    private String host;
    private int port;
    private int maxMsgNumber;
    private int maxMsgNumberPerSec;

//    public Conf(String host, int port, int maxMsgNumber, int maxMsgNumberPerSec) {
//        this.host = host;
//        this.port = port;
//        this.maxMsgNumber = maxMsgNumber;
//        this.maxMsgNumberPerSec = maxMsgNumberPerSec;
//    }

    public Conf() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getMaxMsgNumber() {
        return maxMsgNumber;
    }

    public void setMaxMsgNumber(int maxMsgNumber) {
        this.maxMsgNumber = maxMsgNumber;
    }

    public int getMaxMsgNumberPerSec() {
        return maxMsgNumberPerSec;
    }

    public void setMaxMsgNumberPerSec(int maxMsgNumberPerSec) {
        this.maxMsgNumberPerSec = maxMsgNumberPerSec;
    }
}
