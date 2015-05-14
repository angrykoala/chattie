
import java.rmi.NoSuchObjectException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
    	boolean b=false;
        if(logged) b=server.sendMessage(new ChatMessage(this.name,message));
        if(b==false){
        	addText("server rejected message, trying reconnecting");
        	reconnectGUI();	
        }
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
            addText("Message couldn't be sent, trying to reconnect");
            reconnectGUI();
            
        }
    }
	@Override
	protected void reconnectGUI() {
		try {
			disconnect();
		} catch (RemoteException | NotBoundException e1) {
		}
		try {
			this.server=ChattieClient.connectToServer();
			login();
		} catch (RemoteException | NotBoundException e) {
			addText("Problem Connecting to server");
		}
	}
	@Override
	protected void changeUsernameGUI() {
		String newName = JOptionPane.showInputDialog("Please insert your new name");
		String oldName=this.name;
		this.name=newName;
		try {
			if(server.changeUsername(oldName,newName)){
				addText("User Changed successfully");
				setGUIUsername(newName);
			}else{
				this.name=oldName;
				addText("Couldn't change name");
			}
		} catch (RemoteException e) {
			this.name=oldName;
			addText("Problem connecting server");
			//e.printStackTrace();
		}
	}
	@Override
	protected void showHelpGUI() {
		JOptionPane.showMessageDialog(null, "Chattie v0.1\nby demiurgosoft\nhttps://github.com/demiurgosoft/chattie");
	}

}
