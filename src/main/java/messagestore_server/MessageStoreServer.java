package messagestore_server;

import auth_server.AuthorityServerImpl;
import compressor.CompressTask;
import compressor.TZCompressor;
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
        CompressTask messageTask = new CompressTask("archive/archive-all", "messageRecords/");
        messageTask.setInterval(1000 * 60 * 60 * 24);
        messageTask.setDelay(1000 * 60 * 2);
        TZCompressor tzCompressor = new TZCompressor();
        tzCompressor.addTask(messageTask, "MSG");
        tzCompressor.startAllTask();
        System.out.println("message store server in service");
    }
}
