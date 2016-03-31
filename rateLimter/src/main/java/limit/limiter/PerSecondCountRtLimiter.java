package limit.limiter;

/**
 * Created by tanjingru on 3/31/16.
 */
import com.google.common.util.concurrent.RateLimiter;

public class PerSecondCountRtLimiter implements RtLimiter {

    private int maxCountPerSec;

    private final RateLimiter rateLimiter;

    public PerSecondCountRtLimiter(int maxCountPerSec) {

        this.maxCountPerSec = maxCountPerSec;

        rateLimiter=RateLimiter.create(maxCountPerSec);
    }

    @Override
    public void reset() {
        ;
    }

    @Override
    public Boolean tryAcquire() {
        if (rateLimiter.tryAcquire()) return true;
        else return false;
    }
}
