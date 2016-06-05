package reg_server;

import auth_server.LoginServer;
import message.Message;
import protocol.MessageType;

/**
 * Created by huanganna on 16/6/5.
 */
public class RegisterServerImpl implements RegisterServer {
    @Override
    public int register(Message message) throws Exception {
        int result = -2;
        if(message.getType()== MessageType.REGISTER) {
            String account = message.getRegisterContent().getAccount();
            String password = message.getRegisterContent().getPassword();
            LoginServer loginServer = new LoginServer();
            result = loginServer.register(account, password);
        }
        return result;
    }
}
