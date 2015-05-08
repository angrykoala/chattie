import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;



public class ChatServer implements ServerInterface {
    //private ArrayList<String> users=new ArrayList<String>();
    private HashMap<String,ClientInterface> users=new HashMap<String,ClientInterface>();
    private Registry registry;
    private final String serverName="Chattie Server";
    
    public ChatServer(String serverName) {
        super();
        try {
            //this.registry=LocateRegistry.createRegistry(1099); //to create registry
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
        if(isUser(username)) return false;
        else if(username==serverName) return false;
        else {
            users.put(username,client);
            registry.rebind(username, client);
            client.getMessage(serverMessage("Login Success"));
            return true;
        }
    }

    @Override
    public void sendMessage(ChatMessage message) throws RemoteException {
        if(isUser(message.getAuthor())) {
            for(ClientInterface user : users.values()) {
                    if(user!=null) user.getMessage(message);
            }
        }
    }
    private ClientInterface getUser(String username){
    		return users.get(username);
    }
    public boolean isUser(String username) throws RemoteException{
    	if(username.toLowerCase()=="server" && username.toLowerCase()==serverName.toLowerCase()) return false;
    	else return users.containsKey(username);
    }
    @Override
    public void disconnect(String username) throws RemoteException, NotBoundException {
       // ClientInterface client = (ClientInterface) registry.lookup(username);
    	if(isUser(username)){
    	ClientInterface client=getUser(username);
        client.getMessage(serverMessage("You have been logged out"));
        registry.unbind(username);
        users.remove(username);
        System.out.println(username+" log out");
    	}
    }

    private void bindServer(String serverName) throws RemoteException {
        ServerInterface stub=(ServerInterface) UnicastRemoteObject.exportObject((ServerInterface) this,0);
        registry.rebind(serverName, stub);
    }
    private ChatMessage serverMessage(String message) {
        return new ChatMessage(this.serverName,message);
    }


    public static void main(String[] args) {
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        new ChatServer("ChatServer");
    }

    @SuppressWarnings("unchecked")
	@Override
    public ArrayList<String> getUsers() throws RemoteException {
        return (ArrayList<String>) users.keySet();
    }

    @Override
    public boolean changeUsername(String oldUser, String newUser) throws RemoteException {
    	if(isUser(oldUser) && !isUser(newUser)){
    		ClientInterface user=getUser(oldUser);
    		users.remove(oldUser);
    		users.put(newUser,user);
    		return true;
    	}
    	else return false;
    }
}
