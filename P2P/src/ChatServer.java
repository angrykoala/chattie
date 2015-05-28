import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/* NAME: ChatServer
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Main Chattie server program
 */
public class ChatServer implements ServerInterface {
    private HashMap<String,ClientInterface> users=new HashMap<String,ClientInterface>();
    private Registry registry;
    private String serverName;;
    public ChatServer(String serverName) {
        super();
        this.serverName=serverName;
        try {
            this.registry=LocateRegistry.getRegistry();
            bindServer();
        }
        catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login(String username,ClientInterface client) throws RemoteException {
        if(!validUser(username)) return false;
        else if(username==serverName) return false;
        else {
            addUser(username,client);
            client.receiveBroadcast(serverMessage("Login Success"));
            System.out.println(username+" connected");
           // updateUserList();
            return true;
        }
    }
    @Override
    public void disconnect(String username) throws RemoteException, NotBoundException {
        if(isUser(username)) {
            ClientInterface client=getUser(username);
            client.receiveBroadcast(serverMessage("You have been logged out"));
            deleteUser(username);
            System.out.println(username+" disconnected");
        }
    }
    
    @Override
    public boolean validUser(String username) throws RemoteException {
    	updateUserList();
    	if(username==null || username.length()<3 || username.length()>15) return false;
    	else if (username.contains(" ") || username.contains(System.getProperty("line.separator"))) return false;
    	else if(username.toLowerCase().contains("server") || username.toLowerCase().equals(serverName.toLowerCase())) return false;
        else return !isUser(username);
    }
    private void addUser(String username,ClientInterface client) throws AccessException, RemoteException{
        registry.rebind(username, client);
    	users.put(username,client);
        client.updateUsers(users);
        for(String user : users.keySet()) {
				try {
					users.get(user).addUser(username, client);
				} catch (RemoteException e) {
					kick(user);
				}
        }
    }
            
    private void deleteUser(String username){
    	try {
			registry.unbind(username);
		} catch (RemoteException | NotBoundException e) {
			System.out.println("Error unbinding username");
		}
    	if(users.remove(username)!=null){
    		for(String user : users.keySet()) {
                if(user!=null)
    				try {
    					users.get(user).deleteUser(username);
    				} catch (RemoteException e) {
    					kick(user); //don't break
    				}	
    		} 	
    	}
    }
    private void updateUserList() {
        for(String username : users.keySet()) {
            if(username!=null){
				try {
					users.get(username).updateUsers(users);
				} catch (RemoteException e) {
					kick(username);
					updateUserList(); //restart update user list
					break;
				}
        }
        }
    	
    }
    private ClientInterface getUser(String username) {
        return users.get(username);
    }

    public boolean isUser(String username){
    	if(username.toLowerCase().equals(serverName)) return true;
    	else return users.containsKey(username);
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


    private void kick(String username) {
    	 if(isUser(username)) {
             ClientInterface client=getUser(username);
             try {
				client.receiveMessage(serverMessage("You have been Kicked out"));
				client.kick();
			} catch (RemoteException e) {
				//System.out.println();
			}
             deleteUser(username);
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

	@Override
	public void ping() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	
}
