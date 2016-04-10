package license;

/**
 * Created by tanjingru on 3/31/16.
 */

public class SumCountLicense implements TZLicense {

    private int maxSumCount;

    private int receiveNumber;

    public SumCountLicense(int maxSumCount) {
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
