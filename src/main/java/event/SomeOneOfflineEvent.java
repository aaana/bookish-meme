package event;

/**
 * Created by 马二爷 on 2016/5/14.
 */
public class SomeOneOfflineEvent {
    String account;

    public SomeOneOfflineEvent(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
