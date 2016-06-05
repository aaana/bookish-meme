package reg_server;

import message.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by huanganna on 16/6/5.
 */
public interface RegisterServer  extends Remote {
    int register(Message message) throws Exception;

    static void main(String[] args) throws RemoteException {
        RegisterServerImpl rsi = new RegisterServerImpl();
        RegisterServer rs = (RegisterServer)(UnicastRemoteObject.exportObject(rsi, 0));
        Registry reg = LocateRegistry.createRegistry(2014);
        reg.rebind("registerServer", rs);
        System.out.println("register server in service");
    }
}
