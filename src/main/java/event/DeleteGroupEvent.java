package event;

/**
 * Created by huanganna on 16/6/6.
 */
public class DeleteGroupEvent {
    private String account;
    private String groupId;

    public DeleteGroupEvent(String account, String groupId) {
        this.account = account;
        this.groupId = groupId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
