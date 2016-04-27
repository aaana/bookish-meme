package channel;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;

/**
 * Created by tanjingru on 3/20/16.
 */

public class Manager {
//    public static final ChannelGroup channels = new DefaultChannelGroup();
    public static final ArrayList<ClientChannel> clientChannels = new ArrayList<ClientChannel>();
}

