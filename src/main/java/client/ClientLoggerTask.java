package client;

import java.util.TimerTask;
import org.apache.log4j.Logger;

/**
 * Created by 马二爷 on 2016/3/26.
 */
public class ClientLoggerTask extends TimerTask {
    @Override
    public void run() {
        Logger.getLogger(ChatClient.class).info("Login failed number is " + ClientLoggerHandler.loginFail);
        Logger.getLogger(ChatClient.class).info("Login successful number is " + ClientLoggerHandler.loginSuccess);
        Logger.getLogger(ChatClient.class).info("received message number is " + ClientLoggerHandler.receiveMsgNumber);
        Logger.getLogger(ChatClient.class).info("sent message number is " + ClientLoggerHandler.sendMsgNumber);
    }
}
