package provider;

import auth_server.AuthorityServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by tanjingru on 5/29/16.
 */
public class ServiceProvider {

    private static AuthorityServer authorityServer = null;


    public static synchronized AuthorityServer getAuthorityServer() throws RemoteException, NotBoundException {
        if (authorityServer == null){
            Registry reg = LocateRegistry.getRegistry("localhost", 2015);
            authorityServer = (AuthorityServer)(reg.lookup("authorityServer"));
        }
        return authorityServer;
    }

}
