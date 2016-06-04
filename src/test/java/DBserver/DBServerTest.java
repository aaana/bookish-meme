package DBserver;

import conf.Config;
import org.junit.Before;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/5/30.
 */
public class DBServerTest {

    private Config config = new Config();
    private DBServer dbServer;

    @Before

    public void setUp() throws Exception{
        config.readFile("config/conf.json");
        Registry reg = LocateRegistry.getRegistry("localhost", 2017);
        dbServer = (DBServer)(reg.lookup("DBServer"));
    }

    @Test
    public void testGetGidByAcc() throws Exception {

        System.out.println(dbServer.getGidByAcc("101"));
    }

    @Test
    public void testGetGidAndAcc() throws Exception{
        Map<String,List<String>> uidAndGids = dbServer.getGidAndUid();
        for(String accountId : uidAndGids.keySet()){
            for(String groupId: uidAndGids.get(accountId))
                System.out.println("账号："+accountId+"\t"+"组号："+groupId);
        }
    }

    @Test
    public void testgetALLMessageByGid() throws Exception{
        dbServer.getALLMessageByGid("1");
    }

    @Test
    public void testAddGroup() throws Exception{
        System.out.println(dbServer.addGroup("100", "public"));
    }

    @Test
    public void testIsintheGroup() throws Exception{
        System.out.println(dbServer.isInTheGroup("100", "public"));
    }
}