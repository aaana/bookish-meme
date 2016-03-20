package channel;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

/**
 * Created by tanjingru on 3/20/16.
 */

public class Manager {
    public static final ChannelGroup channels = new DefaultChannelGroup();
}
