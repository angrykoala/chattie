import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient implements ClientInterface {
    private String name=null;
    private ServerInterface server=null;
	private boolean logged=false;
	private Registry registry;

	public ChatClient(){
	super();
	}
    public ChatClient(String clientName,ServerInterface server,Registry registry) throws RemoteException {
        super();
        this.name=clientName;
        this.server=server;
        this.registry=registry;
        login();
    }
    private void login() throws RemoteException{
    if(server.login(name)){ logged=true;
    ClientInterface stub=(ClientInterface) UnicastRemoteObject.exportObject((ClientInterface) this,0);
    registry.rebind(this.name, stub);
    }
    else logged=false;
    }
    public void sendMessage(String message) throws RemoteException{
    	if(logged) server.sendMessage(this.name,message);
    }
    public boolean isLogged(){
    	return logged;
    }
    public void disconnect() throws RemoteException, NotBoundException{
    	if(logged){
    		server.disconnect(name);
    	logged=false;
    	}
    }
    @Override
    public void getMessage(String username, String message) throws RemoteException {
        System.out.println(username+":"+message);
    }
    @Override    
    public void kick() throws RemoteException{
        logged=false;
        System.out.println("You have been kicked from server");
        }
    /*	private static void bindClient(ClientInterface clientInterface,String clientName) throws RemoteException {
    		ServerInterface stub=(ServerInterface) UnicastRemoteObject.exportObject(clientInterface,0);
    		Registry registry = LocateRegistry.getRegistry();
    		registry.rebind(clientName, stub);
    	}*/

    public static void main(String args[]) {
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        String serverName = "ChatServer";
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(args[0]);
            ServerInterface server = (ServerInterface) registry.lookup(serverName);
            ChatClient client=new ChatClient("Ford",server,registry);

            if(client.isLogged()) System.out.println("Login Success");
            else System.out.println("Login Fail");
            client.sendMessage("Bring your towel!!");
            //client.disconnect();
        }
        catch(RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        //Registry registry=LocateRegistry.getRegistry();
        //ClientInterface stub=(ClientInterface) UnicastRemoteObject.exportObject((ClientInterface) client,0);
        //registry.rebind(client.name, stub);
        //ClientInterface clientstub=(ClientInterface)registry.lookup(client.name);
    }



}
