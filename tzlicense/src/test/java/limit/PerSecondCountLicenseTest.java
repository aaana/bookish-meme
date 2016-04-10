package limit;

import license.PerSecondCountLicense;
import license.TZLicense;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PerSecondCountLicenseTest {

    private TZLicense tzLicense;

    @Before
    public void setUp() throws Exception {
        tzLicense = new PerSecondCountLicense(5);
    }

    @Test
    public void testTryAcquire() throws Exception {
        int acquireTime = 5;
        Boolean[] expectResult = {true, false, false, false, false};
        for(int i = 0 ; i < acquireTime ; i ++) {
            assertEquals(tzLicense.tryAcquire(), expectResult[i]);
        }
        Thread.sleep(1000);
        assertEquals(tzLicense.tryAcquire(), true);
    }

    @Test
    public void testReset() throws Exception {

    }
}