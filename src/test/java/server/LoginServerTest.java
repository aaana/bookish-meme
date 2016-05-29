package server;

import auth_server.LoginServer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/3/27.
 */
public class LoginServerTest {
    private LoginServer loginServer;
    private  String[] userName={"100", "101'", "","wefjj';knehgiur43gbjkddafefs","200"};
    private String[] password={ "123456", "123456",  "123456", "","123456"};
    private int[] expectedResult={1,-1,-1,-1,2};
    @Before
    public void setUp() throws Exception {
        loginServer = new LoginServer();
    }

    @Test
    public void testLogin() throws Exception {
        for(int i=0;i<5;i++)
            assertEquals(expectedResult[i],loginServer.login(userName[i],password[i]));
    }
}