package Util;

/**
 * Created by huanganna on 16/3/20.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        ConfReader conf = ConfReader.getInstance();
        String host = conf.getHost();
        int port = conf.getPort();
        System.out.println(host+" "+port);

    }
}
