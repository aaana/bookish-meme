package server;

import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedMessageChannel;
import io.netty.handler.codec.string.StringDecoder;
import message.ChatContent;
import message.Message;
import message.MessageStatus;
import org.junit.Before;
import org.junit.Test;
import protocol.MessageType;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class JsonToObjectChannelTest {

    private EmbeddedMessageChannel channel;

    @Before
    public void setUp() throws Exception {
        channel = new EmbeddedMessageChannel(new JsonToObjectHandler());
    }

    @Test
    public void chattingMessageTest() {

        Message testMessage = new Message(new ChatContent("hello"), MessageStatus.NEEDHANDLED, MessageType.CHATTING);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(testMessage);
        channel.writeInbound(jsonStr + "\n");

        Message myMessage = (Message)channel.readInbound();
        // Perform checks on your object
        assertEquals("hello", ((ChatContent)myMessage.getContent()).getMessage());
    }
}