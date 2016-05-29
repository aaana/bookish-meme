package auth_server;

import message.LoginContent;
import message.Message;
import message.MessageStatus;
import protocol.MessageType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by tanjingru on 5/29/16.
 */
public interface AuthorityServer extends Remote {

    public Message handler(Message message) throws Exception;

    public static void main(String[] args) throws RemoteException {
        AuthorityServerImpl c = new AuthorityServerImpl();
        AuthorityServer ci = (AuthorityServer)(UnicastRemoteObject.exportObject(c, 0));
        Registry reg = LocateRegistry.createRegistry(2015);
        reg.rebind("authorityServer", ci);
        System.out.println("authority server in service");
    }
}
