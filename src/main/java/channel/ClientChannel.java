package channel;

import io.netty.channel.Channel;

/**
 * Created by huanganna on 16/4/27.
 */
public class ClientChannel {

    private Channel channel;
    private String account;

    public ClientChannel(Channel channel, String account) {
        this.channel = channel;
        this.account = account;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
