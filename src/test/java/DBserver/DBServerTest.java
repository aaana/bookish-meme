package DBserver;

import conf.Config;
import org.junit.Before;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/5/30.
 */
public class DBServerTest {

    Config config = new Config();

    @Test
    public void testGetGidByAcc() throws Exception {

        config.readFile("config/conf.json");
        Registry reg = LocateRegistry.getRegistry("localhost", 2017);
        DBServer dbServer = (DBServer)(reg.lookup("DBServer"));

        assertEquals(dbServer.getGidByAcc(100),1);
    }
}