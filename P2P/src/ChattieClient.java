import gui.ClientGUI;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
	private HashMap<String,Chat> activeChats=new HashMap<String,Chat>();
	
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
    	closeAllChats();
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
	public boolean sendMessage(String to,ChatMessage message){
		ClientInterface stub=users.get(to);
		Chat chat=activeChats.get(to);
		if(stub!=null){
			try {
				stub.receiveMessage(message);	
				return true;
			} catch (RemoteException e) {
				System.out.println("Problem sending message");
				return false;
			}
		}
		System.out.println("Problem sending message (stub not found)");	
		return false;
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
	public void sendBroadcast(String message){
		ChatMessage msg=new ChatMessage(this.name,message);
		for(ClientInterface client : users.values()){
			try {
				client.receiveBroadcast(msg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		addText(msg.getMessage());
	}
	public static ServerInterface connectToServer() throws RemoteException, NotBoundException{
		Registry registry;
		registry = LocateRegistry.getRegistry(serverHost);
        ServerInterface server;
        server = (ServerInterface) registry.lookup(serverName);
        return server;
	}
	
  
    //RMI METHODS
	@Override
	public void receiveMessage(ChatMessage message) throws RemoteException {
		String partner=message.getAuthor();
		Chat chat=startChat(partner);
		if(chat!=null) chat.receiveMessage(message);
		else{
			System.out.println("Error message recieving");
		}
		
	}
	@Override
	public void receiveBroadcast(ChatMessage message) throws RemoteException {
		addText(message.getMessage());
	}
	@Override
	public void updateUsers(HashMap<String,ClientInterface> users) throws RemoteException {
		this.users=users;
		users.remove(this.name);
		updateUsersList();
		//check active chats?
	}
	@Override
	public void addUser(String username,ClientInterface stub) throws RemoteException {
		if(!username.equals(name)){
			this.users.put(username,stub);
		updateUsersList();		
		}
	}
	@Override
	public void deleteUser(String username) throws RemoteException {
		this.users.remove(username);
		updateUsersList();
		//check chats?
		
	}
	@Override
	public void kick() throws RemoteException {
		 unexportStub();
        // returnLogin();
         logged=false;
		
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
	protected void sendGUI(String string) {
		sendBroadcast(string);
		
	}
	@Override
	protected void showHelpGUI() {
		JOptionPane.showMessageDialog(null, "Chattie v0.2\nby demiurgosoft\nhttps://github.com/demiurgosoft/chattie");
		
	}
	@Override
	protected void startChatGUI(String username){
		startChat(username);
	}
	
	private Chat startChat(String username) {
		if(users.containsKey(username)){
			Chat chat=getChat(username);
			if(chat==null){
				chat=new Chat(this.name,username,this);
				activeChats.put(username,chat);
				return chat;
			}else return chat;
			}
		else return null;
		}
	private Chat getChat(String partner){
		return activeChats.get(partner);
	}
	public void closeChat(String partner) {
		Chat c=activeChats.remove(partner);	
		if(c!=null)	c.dispose();
	}
	private void closeAllChats(){
		for(String partner:activeChats.keySet()){
			closeChat(partner);			
		}
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
	@Override
	public void ping() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
