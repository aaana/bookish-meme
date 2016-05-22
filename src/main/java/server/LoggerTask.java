package server;

import org.apache.log4j.Logger;

import java.util.TimerTask;

/**
 * Created by tanjingru on 3/26/16.
 */
public class LoggerTask extends TimerTask {
    @Override
    public void run() {
        Logger.getLogger(ChatServer.class).info("validLoginNumber is " + LoggerHandler.validLoginNumber);
        Logger.getLogger(ChatServer.class).info("invalidLoginNumber is " + LoggerHandler.invalidLoginNumber);
        Logger.getLogger(ChatServer.class).info("ignoredMessageNumber is " + LoggerHandler.ignoredMessageNumber);
        Logger.getLogger(ChatServer.class).info("receivedMessageNumber is " + LoggerHandler.receivedMessageNumber);
        Logger.getLogger(ChatServer.class).info("forwardMessageNumber is " + LoggerHandler.forwardMessageNumber);

    }
}
