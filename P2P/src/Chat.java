import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.ChatGUI;

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
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void changeUsername(String name){
		this.name=name;
	}
	public void updatePartner(String partner){
		this.partner=partner;
		setPartnerUsername(partner);
	}
	private void closeChat(){
		client.closeChat(this.partner);
	}
	public String getPartner(){
		return this.partner;
	}
	public void receiveMessage(ChatMessage msg){
		addText(msg.getMessage());
	}
	@Override
	protected void sendGUI(String content) {
		ChatMessage msg=new ChatMessage(this.name,content);
		if(client.sendMessage(partner,msg)) addText(msg.getMessage());
		else addText("Problem sending message");
	}
	@Override
	protected void exitGUI() {
		closeChat();
		
	}
	@Override
	protected void reconnectGUI() {
		client.reconnect();
		
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
