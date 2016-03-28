package server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/3/27.
 */
public class LoginServerTest {
    private LoginServer loginServer;
    private  String[] userName={"100",    "101'",  "",       "wefjniknvienvdj';knehgiur43gbjkddafefs"};
    private String[] password={ "123456", "123456",  "123456", ""};
    private boolean[] expectedResult={true,false,false,false};
    @Before
    public void setUp() throws Exception {
        loginServer = new LoginServer();
    }

    @Test
    public void testLogin() throws Exception {
        for(int i=0;i<4;i++)
            assertEquals(expectedResult[i],loginServer.login(userName[i],password[i]));
    }
}