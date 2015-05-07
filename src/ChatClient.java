import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient implements ClientInterface {
    private String name=null;
    private ServerInterface server=null;
    private boolean logged=false;

    public ChatClient() {
        super();
    }
    public ChatClient(String clientName,ServerInterface server) throws RemoteException {
        super();
        this.name=clientName;
        this.server=server;
        login();
    }
    private void login() throws RemoteException {
        ClientInterface stub=(ClientInterface) UnicastRemoteObject.exportObject((ClientInterface) this,0);
        if(server.login(name,stub)) logged=true;
        else {
            logged=false;
            unexportStub();
        }
    }
    public void sendMessage(String message) throws RemoteException {
        if(logged) server.sendMessage(new ChatMessage(this.name,message));
    }
    public boolean isLogged() {
        return logged;
    }
    public void disconnect() throws RemoteException, NotBoundException {
        if(logged) {
            server.disconnect(name);
            UnicastRemoteObject.unexportObject(this,true);
            logged=false;
        }
    }
    @Override
    public void getMessage(ChatMessage message) throws RemoteException {
        System.out.println(message.getMessage());
    }
    @Override
    public void kick() throws RemoteException {
        logged=false;
        unexportStub();
        System.out.println("You have been kicked from server");
    }
    private void unexportStub() {
        try {
            UnicastRemoteObject.unexportObject(this,true);
        }
        catch(NoSuchObjectException e) {
            System.out.println("error unexporting");
            //	e.printStackTrace();
        }
    }


    public static void main(String args[]) {
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        String serverName = "ChatServer";
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(args[0]);
            ServerInterface server = (ServerInterface) registry.lookup(serverName);
            ChatClient client=new ChatClient("Ford",server);
            if(!client.isLogged()) System.out.println("Login Fail");
            client.sendMessage("Bring your towel!!");
            client.disconnect();
        }
        catch(RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }



}
