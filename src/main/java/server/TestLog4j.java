package server;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by tanjingru on 3/24/16.
 */


public class TestLog4j {
    public static void main(String[] args) {
        PropertyConfigurator.configure("config/log4j.property");
        Logger logger = Logger.getLogger(TestLog4j.class);
        logger.debug("debug");
        logger.error("error");
    }
}
