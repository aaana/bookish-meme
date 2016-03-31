package limit.limiter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PerSecondCountRtLimiterTest {

    private RtLimiter rtLimiter;

    @Before
    public void setUp() throws Exception {
        rtLimiter = new PerSecondCountRtLimiter(5);
    }

    @Test
    public void testTryAcquire() throws Exception {
        int acquireTime = 5;
        Boolean[] expectResult = {true, false, false, false, false};
        for(int i = 0 ; i < acquireTime ; i ++) {
            assertEquals(rtLimiter.tryAcquire(), expectResult[i]);
        }
        Thread.sleep(1000);
        assertEquals(rtLimiter.tryAcquire(), true);
    }

    @Test
    public void testReset() throws Exception {

    }
}