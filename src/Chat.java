
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import gui.ChattieGUI;


public class Chat extends ChattieGUI implements ClientInterface {
    private String name=null;
    private ServerInterface server=null;
    private boolean logged=false;

    public Chat(String clientName,ServerInterface server) throws RemoteException {
        super(clientName,null);
        this.name=clientName;
        this.server=server;
        login();
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
            returnLogin();
        }
    }
    private void returnLogin() {
        this.dispose();
        new Login(server,null);
    }
    @Override
    public void getMessage(ChatMessage message) throws RemoteException {
        addText(message.getMessage());
    }
    @Override
    public void kick() throws RemoteException {
        logged=false;
        unexportStub();
        addText("You have been kicked from server");
    }
    private void unexportStub() {
        try {
            UnicastRemoteObject.unexportObject(this,true);
        }
        catch(NoSuchObjectException e) {
            System.out.println("error unexporting");
            //	e.printStackTrace();
        }
    }
    @Override
    protected void exitGUI() {
        try {
            disconnect();
        }
        catch(RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        this.dispose();
    }
    @Override
    protected void sendGUI(String string) {
        try {
            sendMessage(string);
        }
        catch(RemoteException e) {
            e.printStackTrace();
        }
    }






}
