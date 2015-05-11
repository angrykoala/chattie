
import java.rmi.NoSuchObjectException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import gui.ClientGUI;

/* NAME: Chat
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Client chat
 */
public class Chat extends ClientGUI implements ClientInterface {
    private String name=null;
    private ServerInterface server=null;
    private boolean logged=false;
    private ImageIcon icon;

    public Chat(String clientName,ServerInterface server,ImageIcon icon)  {
        super(clientName,icon);
        this.name=clientName;
        this.server=server;
        this.icon=icon;
        try {
			login();
		} catch (RemoteException e) {
			System.out.println("Error login in");
			returnLogin();
			
		}
    }
    private void login() throws RemoteException {
        ClientInterface stub=(ClientInterface) UnicastRemoteObject.exportObject((ClientInterface) this,0);
        if(server.login(name,stub)) logged=true;
        else {
            logged=false;
            unexportStub();
            returnLogin();
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
            unexportStub();
            logged=false;
        }
    }
    private void returnLogin() {
        this.dispose();
        new Login(server,icon);
    }
    @Override
    public void getMessage(ChatMessage message) throws RemoteException {
        addText(message.getMessage());
    }
    @Override
    public void kick() throws RemoteException {
       if(logged){
    	logged=false;
        unexportStub();
        addText("You have been kicked from server");
       }
    }
    @Override
	public void updateUsers(ArrayList<String> users) throws RemoteException {
		setUsers(users,this.name);		
	}
    private void unexportStub() {
        try {
            UnicastRemoteObject.unexportObject(this,true);
        }
        catch(NoSuchObjectException e) {
            System.out.println("error unexporting");
        }
    }
    @Override
    protected void exitGUI() {
        try {
            disconnect();
        }
        catch(RemoteException | NotBoundException e) {
            
        }
        this.dispose();
    }
    @Override
    protected void returnGUI(){
    	try {
			disconnect();
		} catch (RemoteException | NotBoundException e) {
			
		}
    	returnLogin();
    	
    }
    @Override
    protected void sendGUI(String string) {
        try {
            sendMessage(string);
        }
        catch(RemoteException e) {
            addText("Message couldn't be sent");
        }
    }

}
