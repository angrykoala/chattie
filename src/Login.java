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
    private Chat mainChat;
    private ImageIcon icon;
    public Login(ServerInterface server,ImageIcon icon) {
        super(icon);
        this.icon=icon;
        this.server=server;
        
    }

    @Override
    protected boolean login(String username) {
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
                    mainChat=new Chat(username,server,icon);
                   
           
            return true;
        }
        else{
        	setErrorLabel("Login Failed");
        	return false;
        }
    }


}
