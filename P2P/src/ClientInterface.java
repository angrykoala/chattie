import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/* NAME: ClientInterface
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Remote Interface of Chattie client
 */
public interface ClientInterface extends Remote {
    public void receiveMessage(ChatMessage message) throws RemoteException;
    public void receiveBroadcast(ChatMessage message) throws RemoteException;
    public void updateUsers(HashMap<String,ClientInterface> users) throws RemoteException;
    public void addUser(String username,ClientInterface stub) throws RemoteException;
    public void deleteUser(String username) throws RemoteException;
    public void kick() throws RemoteException;
    public void ping() throws RemoteException;
}
