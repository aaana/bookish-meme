package license;

/**
 * Created by tanjingru on 3/31/16.
 */


public abstract interface TZLicense {

    public abstract Boolean tryAcquire();

    public abstract void reset();

}
