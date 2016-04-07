package conf;

/**
 * Created by huanganna on 16/3/20.
 */
public class Configuration {
    private String HOST;
    private int PORT;
    private int maxMsgNumber;
    private int maxMsgNumberPerSec;

//    public Conf(String host, int port, int maxMsgNumber, int maxMsgNumberPerSec) {
//        this.host = host;
//        this.port = port;
//        this.maxMsgNumber = maxMsgNumber;
//        this.maxMsgNumberPerSec = maxMsgNumberPerSec;
//    }

    public Configuration() {
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
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
