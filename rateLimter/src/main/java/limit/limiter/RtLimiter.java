package limit.limiter;

/**
 * Created by tanjingru on 3/31/16.
 */


public abstract interface RtLimiter {

    public abstract Boolean tryAcquire();

    public abstract void reset();

}
