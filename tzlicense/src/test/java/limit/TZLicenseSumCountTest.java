package limit;

import license.SumCountLicense;
import license.TZLicense;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TZLicenseSumCountTest {

    private TZLicense tzLicense;

    @Before
    public void setUp() throws Exception {
        tzLicense = new SumCountLicense(5);
    }

    @Test
    public void testTryAcquire() throws Exception {
        int acquireTime = 5;
        Boolean[] expectResult = {true, true, true, true, false};
        for(int i = 0 ; i < acquireTime ; i ++) {
            assertEquals(tzLicense.tryAcquire(), expectResult[i]);
        }
    }

    @Test
    public void testReset() throws Exception {
        int acquireTime = 10;
        for(int i = 0; i < acquireTime ; i++) tzLicense.tryAcquire();
        assertEquals(tzLicense.tryAcquire(), false);
        tzLicense.reset();
        assertEquals(tzLicense.tryAcquire(), true);
    }

}