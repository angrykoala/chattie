import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

//Interfaz de Chat
public interface ServerInterface extends Remote {
    public boolean login(String username) throws RemoteException;
    public void sendMessage(String username,String mensaje) throws RemoteException;
    public void disconnect(String username) throws RemoteException, NotBoundException;
}
