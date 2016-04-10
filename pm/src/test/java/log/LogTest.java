package log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LogTest {

    private Log log;
    @Before
    public void setUp()
    {
        log=new Log();

    }
    @Test
    public void testRun() throws Exception
    {
        log.setDelay(1000);
        log.setInterval(10000);
        log.run();
        for(int i=0;i<100;i++)
        {
            log.setParam("hello",i);
            log.setParam("thankyou",i+1);
            Thread.sleep(1000);
        }
        log.stop();
    }
}