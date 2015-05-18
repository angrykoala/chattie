import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/* NAME: ServerInterface
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Remote interface of chattie Server
 */
public interface ServerInterface extends Remote {
    public boolean login(String username,ClientInterface stub) throws RemoteException;
    public boolean validUser(String username) throws RemoteException;
    public boolean changeUsername(String oldUser,String newUser) throws RemoteException;
    public void disconnect(String username) throws RemoteException, NotBoundException;
}
