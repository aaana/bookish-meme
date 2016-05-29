package DBserver;

import message.ChatContent;
import messagestore_server.MessageStoreServerImpl;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * Created by huanganna on 16/5/29.
 */
public interface DBServer extends Remote{

    List<ChatContent> getALLMessageByGid(int gid) throws Exception;
    int delete(int gid,int number) throws Exception;
    int insert(ChatContent chatContent) throws Exception;
    Map<String,Integer> getGidAndUid() throws Exception;
    int getGidByAcc(int account) throws Exception;

    public static void main(String[] args) throws Exception{
        DBServerImpl db = new DBServerImpl();
        DBServer dbi = (DBServer)(UnicastRemoteObject.exportObject(db, 0));
        Registry reg = LocateRegistry.createRegistry(2017);
        reg.rebind("DBServer", dbi);
        System.out.println("DB server in service");
    }

}
