import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientInterface extends Remote {
    public void getMessage(ChatMessage message) throws RemoteException;
    public void kick() throws RemoteException;
}
