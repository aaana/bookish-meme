package limit.limiter;

/**
 * Created by tanjingru on 3/31/16.
 */
import com.google.common.util.concurrent.RateLimiter;

public class SumCountRtLimiter implements RtLimiter {

    private int maxSumCount;

    private int receiveNumber;

    public SumCountRtLimiter(int maxSumCount) {
        this.maxSumCount = maxSumCount;
        this.receiveNumber = 0;
    }

    @Override
    public Boolean tryAcquire() {

        if(receiveNumber >= maxSumCount - 1 ) return false;
        else {
            receiveNumber += 1;
            return true;
        }
    }

    public int getReceiveNumber() {
        return receiveNumber;
    }

    @Override
    public void reset() {
        receiveNumber = 0;
    }
}
