package channel;

import io.netty.channel.Channel;

/**
 * Created by huanganna on 16/4/27.
 */
public class ClientChannel {

    private Channel channel;
    private int groupId;

    public ClientChannel(Channel channel, int groupId) {
        this.channel = channel;
        this.groupId = groupId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
