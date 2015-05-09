import java.rmi.RemoteException;
import javax.swing.ImageIcon;

import gui.LoginGUI;

public class Login extends LoginGUI {
    private ServerInterface server;
    private Chat mainChat;
    public Login(ServerInterface server,ImageIcon icon) {
        super(icon);
        this.server=server;
        this.setVisible(true);
    }

    @Override
    protected boolean login(String username) {
        boolean isUser=false;
        try {
            isUser=server.isUser(username);
        }
        catch(RemoteException e) {
            e.printStackTrace();
        }
        if(isUser==false) {
            //this.dispose();
            try {
                mainChat=new Chat(username,server);
                mainChat.setVisible(true);
            }
            catch(RemoteException e) {
                e.printStackTrace();
            }
            return true;
        }
        else return false;
    }


}
