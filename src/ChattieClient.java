import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public class ChattieClient {
    public static void main(String args[]) {
        String serverName = "ChatServer";
        Registry registry;
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            registry = LocateRegistry.getRegistry(args[0]);
            ServerInterface server;
            server = (ServerInterface) registry.lookup(serverName);
            new Login(server,null);
        }
        catch(RemoteException | NotBoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
