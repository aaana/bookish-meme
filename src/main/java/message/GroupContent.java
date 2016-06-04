package message;

import java.io.Serializable;

/**
 * Created by huanganna on 16/6/3.
 */
public class GroupContent implements Serializable{

    private static final long serialVersionUID = 1L;
    private String account;
    private String groupId;

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

    public GroupContent(String account, String groupId) {
        this.account = account;
        this.groupId = groupId;
    }
}
