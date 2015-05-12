import java.rmi.RemoteException;
import javax.swing.ImageIcon;

import gui.LoginGUI;

/* NAME: Login
 * PROJECT: Chattie - https://github.com/demiurgosoft/chattie
 * AUTHOR: demiurgosoft
 * DESCRIPTION: Chattie client main login
 */
public class Login extends LoginGUI {
    private ServerInterface server;
    private ImageIcon icon;
    public Login(ServerInterface server,ImageIcon icon) {
        super(icon);
        this.icon=icon;
        this.server=server;
    }

    @Override
    protected boolean login(String username) {
    	username=username.trim(); //"trim" username, removing initial and final spaces
    	username=username.replaceAll("\\s", "_"); //changes spaces to dash
        boolean validUser=false;
        try {
            validUser=server.validUser(username);
        }
        catch(RemoteException e) {
            setErrorLabel("Connection Problem");
            disableLogin();
            return false;
        }
        if(validUser==true) {
                    new Chat(username,server,icon);
                   
           
            return true;
        }
        else{
        	setErrorLabel("Login Failed");
        	return false;
        }
    }


}
