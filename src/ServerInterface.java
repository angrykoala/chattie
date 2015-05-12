import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/* NAME: ServerInterface
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Remote interface of chattie Server
 */
public interface ServerInterface extends Remote {
    public boolean login(String username,ClientInterface stub) throws RemoteException;
    public boolean validUser(String username) throws RemoteException;
    public boolean changeUsername(String oldUser,String newUser) throws RemoteException;
    public boolean sendMessage(ChatMessage message) throws RemoteException;
    public void disconnect(String username) throws RemoteException, NotBoundException;
  //  public ArrayList<String> getUsers() throws RemoteException;
}
