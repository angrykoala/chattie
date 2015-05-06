import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatServer implements ServerInterface {
    ArrayList<String> users=new ArrayList<String>();
    Registry registry;
    public ChatServer(String serverName) {
        super();
        try {
            LocateRegistry.createRegistry(1099);
            this.registry=LocateRegistry.getRegistry();
            bindServer(serverName);
        }
        catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login(String username) throws RemoteException {
        System.out.println("Registrar "+username);
        if(users.contains(username)) return false;
        else {
            users.add(username);
            //	ClientInterface stub=(ClientInterface) UnicastRemoteObject.exportObject((ClientInterface) clientStub,0);
            //	registry.rebind(username, stub);
            return true;
        }
    }

    @Override
    public void sendMessage(String username, String mensaje) throws RemoteException {
    	 if(users.contains(username)) System.out.println("Mensaje de "+username+": "+mensaje);
        /*	for(String user : users){
        				try{
        					System.out.println(user);
        				ClientInterface client = (ClientInterface) registry.lookup(user);
        				client.getMessage(username,mensaje);
        				} catch ( RemoteException | NotBoundException e) {
        				e.printStackTrace();
        			}
        		}*/
    }

    @Override
    public void disconnect(String username) throws RemoteException {
        users.remove(username);
        System.out.println(username+" se desconecto");
    }

    private void bindServer(String serverName) throws RemoteException {
        ServerInterface stub=(ServerInterface) UnicastRemoteObject.exportObject((ServerInterface) this,0);
        registry.rebind(serverName, stub);
    }



    public static void main(String[] args) {
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        new ChatServer("ChatServer");
        /*try {
        	serverInterface.bindServer(serverInterface,"ChatServer");
        } catch (RemoteException e) {
        	e.printStackTrace();
        }*/
    }
}
