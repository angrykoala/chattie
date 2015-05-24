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
		super(partner);
		this.partner=partner;
		changeUsername(user);
		this.client=client;
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void changeUsername(String name){
		this.name=name;
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
	protected void showHelpGUI() {
		JOptionPane.showMessageDialog(null, "Chattie v0.2\nby demiurgosoft\nhttps://github.com/demiurgosoft/chattie");
	}

}
