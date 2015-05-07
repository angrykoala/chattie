import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatServer implements ServerInterface {
    private ArrayList<String> users=new ArrayList<String>();
    //private HashMap<String,ClientInterface> users=new ArrayList<String>();
    private Registry registry;
    private final String serverName="Chattie Server";
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
        else if(username==serverName) return false;
        else {
            users.add(username);
            registry.rebind(username, client);
            client.getMessage(serverMessage("Login Success"));
            //	ClientInterface stub=(ClientInterface) UnicastRemoteObject.exportObject((ClientInterface) clientStub,0);
            //	registry.rebind(username, stub);
            return true;
        }
    }

    @Override
    public void sendMessage(ChatMessage message) throws RemoteException {
        if(users.contains(message.getAuthor())) {
            for(String user : users) {
                try {
                    ClientInterface client = (ClientInterface) registry.lookup(user);
                    if(client!=null) client.getMessage(message);
                }
                catch(RemoteException | NotBoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void disconnect(String username) throws RemoteException, NotBoundException {
        ClientInterface client = (ClientInterface) registry.lookup(username);
        client.getMessage(serverMessage("You have been logged out"));
        registry.unbind(username);
        users.remove(username);
        System.out.println(username+" log out");
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

    @Override
    public ArrayList<String> getUsers() throws RemoteException {
        return users;
    }

    @Override
    public boolean changeUsername(String oldUser, String newUser)
    throws RemoteException {
        return false;
    }
}
