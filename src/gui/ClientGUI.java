/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static java.awt.event.KeyEvent.VK_ENTER;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public abstract class ClientGUI extends javax.swing.JFrame {

    String username; //username to be used on GUI
    public ClientGUI(String username,ImageIcon icon) {
        initComponents();
        setUsername(username);
        if(icon!=null) this.setIconImage(icon.getImage());
    }
    //Override for exit action
    protected abstract void exitGUI();
    
    //Override if want to return instead of simply exit
    protected void returnGUI(){
        exitGUI();
    }
    protected abstract void sendGUI(String string);
    protected void setUsers(ArrayList<String> users){
    	usersList.setText(null);
        for(String user:users){
            if(user==username) usersList.append(">");
            usersList.append(user+"\n");
        }
    }
    protected void addText(String string) {
        if(string!=null && !string.isEmpty())
            chatArea.append(string+"\n");
    }
    private void setUsername(String username) {
        this.username=username;
        title.setText(username);
        setTitle("Chattie - "+username);
    }
    private void sendAction() {
        String message=userInput.getText();
        userInput.setText(null);
        sendGUI(message);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        userInput = new javax.swing.JTextArea();
        exitButton = new javax.swing.JButton();
        sendButton = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        usersList = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        userInput.setColumns(20);
        userInput.setLineWrap(true);
        userInput.setRows(3);
        userInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userInputKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(userInput);

        exitButton.setText("Disconnect");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        title.setText(" ");

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setLineWrap(true);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        usersList.setEditable(false);
        usersList.setColumns(9);
        usersList.setRows(5);
        jScrollPane2.setViewportView(usersList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title)
                    .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        returnGUI();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        sendAction();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       exitGUI();
    }//GEN-LAST:event_formWindowClosing

    private void userInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userInputKeyPressed
        if(evt.getKeyCode() == VK_ENTER) {
            evt.consume();
            sendAction();
        }
    }//GEN-LAST:event_userInputKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton exitButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel title;
    private javax.swing.JTextArea userInput;
    private javax.swing.JTextArea usersList;
    // End of variables declaration//GEN-END:variables
}
