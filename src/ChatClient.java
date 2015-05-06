import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient implements ClientInterface {
    private String name;

    public ChatClient(String clientName) {
        super();
        this.name=clientName;
    }

    public static void main(String args[]) {
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        String serverName = "ChatServer";
        System.out.println("Buscando el objeto remoto");
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(args[0]);
            ServerInterface server = (ServerInterface) registry.lookup(serverName);
            //ChatClient client=new ChatClient("Ford");
            //Registry registry=LocateRegistry.getRegistry();
            //ClientInterface stub=(ClientInterface) UnicastRemoteObject.exportObject((ClientInterface) client,0);
            //registry.rebind(client.name, stub);
            //ClientInterface clientstub=(ClientInterface)registry.lookup(client.name);
            server.sendMessage("test","bien");
            if(server.login("Ford")) System.out.println("Registrado con exito");
            else System.out.println("problema en registro");
            server.sendMessage("Ford","Bring your Towel");
            server.disconnect("Ford");
            /* if(server.login(client.name,clientstub)==false) System.out.println("Login Fail");
             else{
              System.out.println("Login Success");
             server.sendMessage(client.name,"Este es el mensaje");
             server.disconnect(client.name);
             }*/
        }
        catch(RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void getMessage(String username, String message)
    throws RemoteException {
        System.out.println(username+":"+message);
    }
    /*	private static void bindClient(ClientInterface clientInterface,String clientName) throws RemoteException {
    		ServerInterface stub=(ServerInterface) UnicastRemoteObject.exportObject(clientInterface,0);
    		Registry registry = LocateRegistry.getRegistry();
    		registry.rebind(clientName, stub);
    	}*/

}
