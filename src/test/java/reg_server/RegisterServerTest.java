package reg_server;

import message.Message;
import message.MessageStatus;
import message.RegisterContent;
import org.junit.Test;
import protocol.MessageType;
import provider.ServiceProvider;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/6/5.
 */
public class RegisterServerTest {

    @Test
    public void testRegister() throws Exception {
//        Registry reg = LocateRegistry.getRegistry("localhost", 2014);
//        RegisterServer registerServer = (RegisterServer)(reg.lookup("registerServer"));

        Message registerMessage = new Message(MessageType.REGISTER, MessageStatus.NEEDHANDLED,new RegisterContent("300","123456"));
        assertEquals(-1, ServiceProvider.getRegisterServer().register(registerMessage));
    }
}