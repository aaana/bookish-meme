package channel;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanjingru on 3/20/16.
 */

public class Manager {
//    public static final ChannelGroup channels = new DefaultChannelGroup();
    public static final ArrayList<ClientChannel> clientChannels = new ArrayList<ClientChannel>();
    /*
        key: groupId
        value: Map(key:AccountId, value: missingIndex)
     */
    public static Map<String,Map<String,Integer>> groupClientsMissingNum = new HashMap<String, Map<String, Integer>>();
}

