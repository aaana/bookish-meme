package auth_server;

import message.LoginContent;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;

/**
 * Created by tanjingru on 5/29/16.
 */
public class AuthorityServerImpl implements AuthorityServer {
    @Override
    public Message handler(Message message) throws Exception {
        MessageType messageType = message.getType();
        //login message
        if(messageType == MessageType.AUTHORITY){
            LoginContent loginContent = message.getLoginContent();
            String account = loginContent.getAccount();
            String password = loginContent.getPassword();
            LoginServer loginServer = new LoginServer();
            int success = loginServer.login(account,password);
//            login fails
            if(success==-1) {
                message.setMessageStatus(MessageStatus.LOGINFAIL);
            }
            // login success
            else{
                loginContent.setGroupId(success);
                message.setLoginContent(loginContent);
            }
        }
        return message;
    }
}
