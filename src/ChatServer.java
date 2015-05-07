import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatServer implements ServerInterface {
    ArrayList<String> users=new ArrayList<String>();
    //HashMap<String,ClientInterface> users=new ArrayList<String>();
    Registry registry;
    public ChatServer(String serverName) {
        super();
        try {
        	//this.registry=LocateRegistry.createRegistry(1099);
            this.registry=LocateRegistry.getRegistry();
            bindServer(serverName);
        }
        catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login(String username,ClientInterface client) throws RemoteException {
        System.out.println("Registrar "+username);
        if(users.contains(username)) return false;
        else {
            users.add(username);
            registry.rebind(username, client);
            client.getMessage("server", "sending stub works!!!");
            //	ClientInterface stub=(ClientInterface) UnicastRemoteObject.exportObject((ClientInterface) clientStub,0);
            //	registry.rebind(username, stub);
            return true;
        }
    }

    @Override
    public void sendMessage(String username, String mensaje) throws RemoteException {
    	 if(users.contains(username)) System.out.println("Mensaje de "+username+": "+mensaje);
        	for(String user : users){
        				try{
        				ClientInterface client = (ClientInterface) registry.lookup(user);
        				if(client!=null) client.getMessage(username,mensaje);
        				} catch ( RemoteException | NotBoundException e) {
        				e.printStackTrace();
        			}
        		}
    }

    @Override
    public void disconnect(String username) throws RemoteException, NotBoundException {
    	ClientInterface client = (ClientInterface) registry.lookup(username);
    	client.getMessage("server","you have been disconnected");
    	registry.unbind(username);
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

	@Override
	public ArrayList<String> getUsers() throws RemoteException {
		return users;
	}

	@Override
	public boolean changeUsername(String oldUser, String newUser)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
}
