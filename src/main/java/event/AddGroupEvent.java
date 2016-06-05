package event;

import java.util.List;

/**
 * Created by huanganna on 16/6/3.
 */
public class AddGroupEvent {
    private List<String> onlineAccounts;
    private String groupId;

    public AddGroupEvent(List<String> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }

    public List<String> getOnlineAccounts() {
        return onlineAccounts;
    }

    public void setOnlineAccounts(List<String> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public AddGroupEvent(List<String> onlineAccounts, String groupId) {
        this.onlineAccounts = onlineAccounts;
        this.groupId = groupId;
    }
}
