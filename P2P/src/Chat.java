
import java.rmi.NoSuchObjectException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import gui.ChatGUI;
import gui.ClientGUI;

/* NAME: Chat
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Client chat
 */
public class Chat extends ChatGUI{
	String name;
	String partner;
	ChattieClient client;
	
	public Chat(String user,String partner,ChattieClient client){
		super();
		changeUsername(user);
		updatePartner(partner);
		this.client=client;
	}
	public void changeUsername(String name){
		this.name=name;
	}
	public void updatePartner(String partner){
		this.partner=partner;
		setPartnerUsername(partner);
	}
	public void closeChat(){
		this.dispose();
	}
	public String getPartner(){
		return this.partner;
	}
	public void receiveMessage(ChatMessage msg){
		addText(msg.getMessage());
	}
	@Override
	protected void sendGUI(String content) {
		client.sendMessage(partner,new ChatMessage(this.name,content));
	}
	@Override
	protected void exitGUI() {
		closeChat();
		
	}
	@Override
	protected void reconnectGUI() {
		client.reconnect(partner);
		
	}
	@Override
	protected void changeUsernameGUI() {
		String newName = JOptionPane.showInputDialog("Please insert your new name");
		newName=newName.trim(); //"trim" username, removing initial and final spaces
    	newName=newName.replaceAll("\\s", "_"); //changes spaces to dash
		client.changeUsername(newName);
		}
		
	@Override
	protected void showHelpGUI() {
		JOptionPane.showMessageDialog(null, "Chattie v0.2\nby demiurgosoft\nhttps://github.com/demiurgosoft/chattie");
	}

}
