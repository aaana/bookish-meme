package message;

import java.io.Serializable;

/**
 * Created by huanganna on 16/6/3.
 */
public class GroupContent implements Serializable{

    private static final long serialVersionUID = 1L;
    private String account;
    private int groupId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public GroupContent(String account, int groupId) {
        this.account = account;
        this.groupId = groupId;
    }
}
