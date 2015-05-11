import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.ImageIcon;


/* NAME: ChattieClient
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Main Chattie client program
 */
public class ChattieClient {
	private final static String iconName="chattie.png";
	private final static String serverName="Chattie_Server";
	private static String serverHost=null;
	
	
	public static ServerInterface connectToServer() throws RemoteException, NotBoundException{
		Registry registry;
		registry = LocateRegistry.getRegistry(serverHost);
        ServerInterface server;
        server = (ServerInterface) registry.lookup(serverName);
        return server;
	}
	
    public static void main(String args[]) {
        ChattieClient.serverHost=args[0];
        ImageIcon icon = new ImageIcon(iconName,"Chattie client icon");
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            ServerInterface server=connectToServer();
            new Login(server,icon);
        }
        catch(RemoteException | NotBoundException e1) {
            System.out.println("Couldn't connect to server");
        }
    }
}
