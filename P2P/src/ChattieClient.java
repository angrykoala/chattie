import gui.ClientGUI;

import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/* NAME: ChattieClient
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Main Chattie client program
 */
public class ChattieClient extends ClientGUI implements ClientInterface {
	//Static variables
	private final static String iconName="chattie.png";
	private final static String serverName="Chattie_Server";
	private static String serverHost=null;
	private static ImageIcon icon=null;
	public static ChattieClient client=null;
	
	private String name=null;
	private ServerInterface server=null;
	
	private HashMap<String,ClientInterface> users=new HashMap<String,ClientInterface>();
	private ArrayList<Chat> activeChats=new ArrayList<Chat>(); //Use hashmap??
	private boolean logged=false;
	public ChattieClient(String name,ServerInterface server,ImageIcon icon){
		super(name,icon);
		this.name=name;
		this.server=server;
		
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
        else{
            unexportStub();
            returnLogin();
            logged=false;
        }
    }
    private void returnLogin() {
        this.dispose();
        new Login(server,icon);
    }
    private void unexportStub(){
    	 try {
			UnicastRemoteObject.unexportObject(this,true);
		} catch (NoSuchObjectException e) {
			System.out.println("Error unexporting client");
		}
    }
    private void updateUsersList(){
    	 setUsers(users.keySet().toArray(new String[users.size()]));
    }
	public void sendMessage(String to,ChatMessage message){
		//TODO
		
	}
	//reconnects to server
	public void reconnect(){
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
	//reconnects to given partner (if possible)
	public void reconnect(String partner){
		//TODO
		
	}
	public void changeUsername(String newUser){
		//TODO
		
	}
	public void sendBroadcast(String message){
		ChatMessage msg=new ChatMessage(this.name,message);
		//send to everybody!!
	}
	public static ServerInterface connectToServer() throws RemoteException, NotBoundException{
		Registry registry;
		registry = LocateRegistry.getRegistry(serverHost);
        ServerInterface server;
        server = (ServerInterface) registry.lookup(serverName);
        return server;
	}
	
    public static void main(String args[]) {
        ChattieClient.serverHost=args[0];
        icon = new ImageIcon(iconName,"Chattie client icon");
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            ServerInterface server=connectToServer();
            new Login(server,icon);
        }
        catch(RemoteException | NotBoundException e1) {
            System.out.println("Couldn't connect to server");
        }
    }
    //RMI METHODS
	@Override
	public void receiveMessage(ChatMessage message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateUsers(HashMap<String,ClientInterface> users) throws RemoteException {
		this.users=users;
		updateUsersList();
		//check active chats?
	}
	@Override
	public void addUser(String username,ClientInterface stub) throws RemoteException {
		this.users.put(username,stub);
		updateUsersList();		
	}
	@Override
	public void deleteUser(String username) throws RemoteException {
		this.users.remove(username);
		updateUsersList();
		//check chats?
		
	}
	@Override
	public void kick() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void receiveBroadcast(ChatMessage message) throws RemoteException {
		addText(message.getMessage());
		
	}
	//GUI METHODS
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
	private void disconnect() throws RemoteException, NotBoundException {
        if(logged) {
            server.disconnect(name);
            unexportStub();
            logged=false;
        }
		
	}
	@Override
	protected void reconnectGUI() {
		reconnect();		
	}
	@Override
	protected void changeUsernameGUI() {
		String newName = JOptionPane.showInputDialog("Please insert your new name");
		newName=newName.trim(); //"trim" username, removing initial and final spaces
    	newName=newName.replaceAll("\\s", "_"); //changes spaces to dash
		changeUsername(newName);
		
	}
	@Override
	protected void sendGUI(String string) {
		sendBroadcast(string);
		
	}
	@Override
	protected void showHelpGUI() {
		JOptionPane.showMessageDialog(null, "Chattie v0.2\nby demiurgosoft\nhttps://github.com/demiurgosoft/chattie");
		
	}
	@Override
	protected void startChat(String username) {
		// TODO Auto-generated method stub
		
	}

}

/* old chat
 * 
 *     private String name=null;
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
    public void sendMessage(String message,String toUser) throws RemoteException {
    	 boolean b=false;
       // if(logged) b=server.sendMessage(new ChatMessage(this.name,message));
        if(b==false){
        	addText("server rejected message, trying reconnecting");
        	reconnectGUI();	
        }
    }
    public void broadcastMessage(String message){
    	//send message to everyone :D
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
    public void receiveMessage(ChatMessage message) throws RemoteException {
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
		//if(server.changeUsername(String oldUser,String newUser) throws RemoteException;
		//TODO
	}
	@Override
	public void addUser(String username) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteUser(String username) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	*/
