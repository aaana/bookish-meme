package messagestore_server;

import auth_server.AuthorityServerImpl;
import message.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by huanganna on 16/5/29.
 */
public interface MessageStoreServer extends Remote{

    public void store(Message message) throws Exception;

    public static void main(String[] args) throws RemoteException {
        MessageStoreServerImpl ms = new MessageStoreServerImpl();
        MessageStoreServer msi = (MessageStoreServer)(UnicastRemoteObject.exportObject(ms, 0));
        Registry reg = LocateRegistry.createRegistry(2016);
        reg.rebind("messageStoreServer", msi);
        System.out.println("message store server in service");
    }
}
