package event;

/**
 * Created by huanganna on 16/6/6.
 */
public class SomeOneEscapeGroupEvent {
    private String account;

    public SomeOneEscapeGroupEvent(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
