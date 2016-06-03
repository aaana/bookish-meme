package event;

import java.util.List;

/**
 * Created by huanganna on 16/6/3.
 */
public class AddGroupEvent {
    private List<String> onlineAccounts;
    private int groupId;

    public AddGroupEvent(List<String> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }

    public List<String> getOnlineAccounts() {
        return onlineAccounts;
    }

    public void setOnlineAccounts(List<String> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public AddGroupEvent(List<String> onlineAccounts, int groupId) {
        this.onlineAccounts = onlineAccounts;
        this.groupId = groupId;
    }
}
