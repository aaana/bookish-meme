package limit.limiter;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Vector;

import static org.junit.Assert.*;

public class RtLimiterSumCountTest {

    private RtLimiter rtLimiter;

    @Before
    public void setUp() throws Exception {
        rtLimiter = new SumCountRtLimiter(5);
    }

    @Test
    public void testTryAcquire() throws Exception {
        int acquireTime = 5;
        Boolean[] expectResult = {true, true, true, true, false};
        for(int i = 0 ; i < acquireTime ; i ++) {
            assertEquals(rtLimiter.tryAcquire(), expectResult[i]);
        }
    }

    @Test
    public void testReset() throws Exception {
        int acquireTime = 10;
        for(int i = 0; i < acquireTime ; i++) rtLimiter.tryAcquire();
        assertEquals(rtLimiter.tryAcquire(), false);
        rtLimiter.reset();
        assertEquals(rtLimiter.tryAcquire(), true);
    }
}