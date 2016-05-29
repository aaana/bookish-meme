package provider;

import DBserver.DBServer;
import auth_server.AuthorityServer;
import conf.Config;
import exception.FileNotExistException;
import messagestore_server.MessageStoreServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by tanjingru on 5/29/16.
 */
public class ServiceProvider {

    private static AuthorityServer authorityServer = null;
    private static MessageStoreServer messageStoreServer = null;
    private static DBServer dbServer = null;


    private static Config config = new Config();


    public static synchronized AuthorityServer getAuthorityServer() throws Exception {
        if (authorityServer == null){
            config.readFile("config/conf.json");
            Registry reg = LocateRegistry.getRegistry(config.getConf("auth_server").getString("host"), config.getConf("auth_server").getInt("port"));
            authorityServer = (AuthorityServer)(reg.lookup("authorityServer"));
        }
        return authorityServer;
    }

    public static synchronized MessageStoreServer getMessageStoreServer() throws Exception {
        if (messageStoreServer == null){
            config.readFile("config/conf.json");
            Registry reg = LocateRegistry.getRegistry(config.getConf("store_server").getString("host"), config.getConf("store_server").getInt("port"));
            messageStoreServer = (MessageStoreServer)(reg.lookup("messageStoreServer"));
        }
        return messageStoreServer;
    }

    public static synchronized DBServer getDbServer() throws Exception {
        if (dbServer == null){
            config.readFile("config/conf.json");
            Registry reg = LocateRegistry.getRegistry(config.getConf("db_server").getString("host"), config.getConf("db_server").getInt("port"));
            dbServer = (DBServer)(reg.lookup("DBServer"));
        }
        return dbServer;
    }

}
