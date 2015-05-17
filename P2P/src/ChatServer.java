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
        System.out.println(username+" connected");
        if(!validUser(username)) return false;
        else if(username==serverName) return false;
        else {
        //	sendMessage(serverMessage(username+" connected"));
            users.put(username,client);
            registry.rebind(username, client);
            client.receiveBroadcast(serverMessage("Login Success"));
            updateUserList();
            return true;
        }
    }

/* @Override
    public boolean sendMessage(ChatMessage message) {
        if(isUser(message.getAuthor()) && message.isValid()) {
            for(String username : users.keySet()) {
                if(username!=null){
					try {
						users.get(username).getMessage(message);
					} catch (RemoteException e) {
						kick(username);
					}
            }
            }
            return true;
        }
        else{
        	if(isUser(message.getAuthor())) return true;
        	else return false;
        }
    }*/
    private void updateUserList() {
    	ArrayList<String> usersList=getUsers();
        for(String username : users.keySet()) {
            if(username!=null){
				try {
					users.get(username).updateUsers(users);
				} catch (RemoteException e) {
					kick(username);
					break;
				}
        }
        }
    	
    }
    private ClientInterface getUser(String username) {
        return users.get(username);
    }
    public boolean validUser(String username) throws RemoteException {
    	updateUserList();
    	if(username==null || username.length()<3 || username.length()>15) return false;
    	else if (username.contains(" ") || username.contains(System.getProperty("line.separator"))) return false;
    	else if(username.toLowerCase().contains("server") || username.toLowerCase().equals(serverName.toLowerCase())) return false;
        else return !isUser(username);
    }
    public boolean isUser(String username){
    	if(username.toLowerCase().equals(serverName)) return true;
    	else return users.containsKey(username);
    }
    
    @Override
    public void disconnect(String username) throws RemoteException, NotBoundException {
        if(isUser(username)) {
            ClientInterface client=getUser(username);
            client.receiveMessage(serverMessage("You have been logged out"));
            removeUser(username);
            System.out.println(username+" disconnected");
        //    sendMessage(serverMessage(username+" disconnected"));
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
          //  sendMessage(serverMessage(oldUser+" change name to" + newUser));
            users.put(newUser,user);
            updateUserList();
            
            return true;
        }
        else return false;
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
             removeUser(username);
             System.out.println(username+" kicked out");
          //   sendMessage(serverMessage("user "+username+" was kicked"));
         }
    	 updateUserList();
    	
    }
    private void shutdown() throws RemoteException, NotBoundException{
    //	sendMessage(serverMessage("server si shutting down"));
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
    private void removeUser(String username){
        try {
			registry.unbind(username);
		} catch (RemoteException | NotBoundException e) {
			System.out.println("Error unbinding username");
		}
        users.remove(username);
		updateUserList();
    }
    public static void main(String[] args) {
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        new ChatServer("Chattie_Server");
    }

	
}
