import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ClientInterface extends Remote {
    public void getMessage(ChatMessage message) throws RemoteException;
    public void updateUsers(ArrayList<String> users) throws RemoteException;
    public void kick() throws RemoteException;
}
