package Util;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/3/27.
 */
public class ConfigReaderTest {
    private ConfigReader reader;
    private String host;
    private int port;
    private int maxMsgNumber;
    private int maxMsgNumberPerSec;

    @Before
    public void setUp() throws Exception {
        reader = new ConfigReader();

    }

    //正确读入配置文件
    @Test
    public void testReadConf() throws Exception {
        Conf conf = reader.readConf("config/conf.json");
        host = conf.getHost();
        port = conf.getPort();
        maxMsgNumber = conf.getMaxMsgNumber();
        maxMsgNumberPerSec = conf.getMaxMsgNumberPerSec();


        assertEquals(host,"localhost");
        assertEquals(port,8080);
        assertEquals(maxMsgNumber,100);
        assertEquals(maxMsgNumberPerSec,5);

    }


}