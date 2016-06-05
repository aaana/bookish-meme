package event;

/**
 * Created by huanganna on 16/6/3.
 */
public class SomeOneAddGroupEvent {
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public SomeOneAddGroupEvent(String account) {
        this.account = account;
    }
}
