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

    List<ChatContent> getALLMessageByGid(String gid) throws Exception;
    int delete(String gid,int number) throws Exception;
    int insert(ChatContent chatContent) throws Exception;
    Map<String,List<String>> getGidAndUid() throws Exception;
    List<String> getGidByAcc(String account) throws Exception;
    int addGroup(String name, String groupId) throws Exception;
    public int groupNumIncreaseByOne(String groupId) throws Exception;
    public List<String> getAllGroupIds() throws Exception;
    int createGroup(String groupId) throws Exception;
    public boolean isInTheGroup(String account, String groupId) throws Exception;

    public static void main(String[] args) throws Exception{
        DBServerImpl db = new DBServerImpl();
        DBServer dbi = (DBServer)(UnicastRemoteObject.exportObject(db, 0));
        Registry reg = LocateRegistry.createRegistry(2017);
        reg.rebind("DBServer", dbi);
        System.out.println("DB server in service");
    }

}
