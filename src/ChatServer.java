import java.rmi.NoSuchObjectException;
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
    private String serverName;;
    public ChatServer(String serverName) {
        super();
        this.serverName=serverName;
        try {
            //this.registry=LocateRegistry.createRegistry(1099); //to create registry
            this.registry=LocateRegistry.getRegistry();
            bindServer();
        }
        catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login(String username,ClientInterface client) throws RemoteException {
        System.out.println("Registrar "+username);
        if(!validUser(username)) return false;
        else if(username==serverName) return false;
        else {
            users.put(username,client);
            registry.rebind(username, client);
            client.getMessage(serverMessage("Login Success"));
            updateUserList();
            return true;
        }
    }

    @Override
    public void sendMessage(ChatMessage message) throws RemoteException {
        if(isUser(message.getAuthor()) && message.isValid()) {
            for(ClientInterface user : users.values()) {
                if(user!=null) user.getMessage(message);
            }
        }
    }
    private void updateUserList() throws RemoteException{
    	ArrayList<String> usersList=getUsers();
        for(ClientInterface user : users.values()) {
            if(user!=null) user.updateUsers(usersList);
        }
    	
    }
    private ClientInterface getUser(String username) {
        return users.get(username);
    }
    public boolean validUser(String username) throws RemoteException {
    	if(username==null || username.length()<3) return false;
    	else if (username.contains(" ") || username.contains(System.getProperty("line.separator"))) return false;
    	else if(username.toLowerCase()=="server" || username.toLowerCase()==serverName.toLowerCase()) return false;
        else return !isUser(username);
    }
    public boolean isUser(String username){
    	if(username==serverName) return true;
    	else return users.containsKey(username);
    }
    
    @Override
    public void disconnect(String username) throws RemoteException, NotBoundException {
        if(isUser(username)) {
            ClientInterface client=getUser(username);
            client.getMessage(serverMessage("You have been logged out"));
            registry.unbind(username);
            users.remove(username);
            updateUserList();
            System.out.println(username+" log out");
        }
    }

    private void bindServer() throws RemoteException {
        ServerInterface stub=(ServerInterface) UnicastRemoteObject.exportObject((ServerInterface) this,0);
        registry.rebind(serverName, stub);
    }
    private ChatMessage serverMessage(String message) {
        return new ChatMessage(this.serverName,message);
    }

    public ArrayList<String> getUsers() {
    	return new ArrayList<String>(users.keySet());
    }

    @Override
    public boolean changeUsername(String oldUser, String newUser) throws RemoteException {
        if(isUser(oldUser) && validUser(newUser)) {
            ClientInterface user=getUser(oldUser);
            users.remove(oldUser);
            users.put(newUser,user);
            return true;
        }
        else return false;
    }
    private void kick(String username) throws RemoteException, NotBoundException{
    	 if(isUser(username)) {
             ClientInterface client=getUser(username);
             client.getMessage(serverMessage("You have been Kicked out"));
             client.kick();
             registry.unbind(username);
             users.remove(username);
             updateUserList();
             System.out.println(username+" kicked out");
         }
    	
    }
    private void shutdown() throws RemoteException, NotBoundException{
    	for(String user : users.keySet()) {
            kick(user);
        }
    	registry.unbind(serverName);
    	try {
            UnicastRemoteObject.unexportObject(this,true);
        }
        catch(NoSuchObjectException e) {
            System.out.println("error unexporting");
        }
    }
    public static void main(String[] args) {
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        new ChatServer("Chattie_Server");
    }

	
}
