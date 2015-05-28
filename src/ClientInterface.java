import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/* NAME: ClientInterface
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Remote Interface of Chattie client
 */
public interface ClientInterface extends Remote {
    public void getMessage(ChatMessage message) throws RemoteException;
    public void updateUsers(ArrayList<String> users) throws RemoteException;
    public void kick() throws RemoteException;
    public void ping() throws RemoteException;
}
