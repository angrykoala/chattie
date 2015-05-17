/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static java.awt.event.KeyEvent.VK_ENTER;


public abstract class ChatGUI extends javax.swing.JFrame {
    
    public ChatGUI() {
        initComponents();
      //  setPartnerUsername(partner);
    }
    protected abstract void sendGUI(String string);
    protected abstract void exitGUI();
    protected abstract void reconnectGUI();
    protected abstract void changeUsernameGUI();
    protected abstract void showHelpGUI();
    
    
    protected void addText(String string) {
        if(string!=null && !string.isEmpty())
            chatArea.append(string+"\n");
    }
            
    private void sendAction(){
       String text=userInput.getText();
       userInput.setText(null);
       sendGUI(text);
    }

    protected void setPartnerUsername(String username) {
        titleLabel.setText(username);
        setTitle("Chattie - "+username);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        userInput = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        reconnectMenu = new javax.swing.JMenuItem();
        changeNameMenu = new javax.swing.JMenuItem();
        closeMenu = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        sendMenu = new javax.swing.JMenuItem();
        clearMenu = new javax.swing.JMenuItem();
        helpmenu = new javax.swing.JMenu();
        aboutMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        userInput.setColumns(20);
        userInput.setLineWrap(true);
        userInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userInputKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(userInput);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        titleLabel.setText(" ");

        closeButton.setText("Close Chat");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");

        reconnectMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        reconnectMenu.setText("Reconnect");
        reconnectMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconnectMenuActionPerformed(evt);
            }
        });
        fileMenu.add(reconnectMenu);

        changeNameMenu.setText("Change Name");
        changeNameMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeNameMenuActionPerformed(evt);
            }
        });
        fileMenu.add(changeNameMenu);

        closeMenu.setText("Close");
        closeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuActionPerformed(evt);
            }
        });
        fileMenu.add(closeMenu);

        jMenuBar1.add(fileMenu);

        editMenu.setText("Edit");

        sendMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK));
        sendMenu.setText("Send");
        sendMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMenuActionPerformed(evt);
            }
        });
        editMenu.add(sendMenu);

        clearMenu.setText("Clear");
        clearMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearMenuActionPerformed(evt);
            }
        });
        editMenu.add(clearMenu);

        jMenuBar1.add(editMenu);

        helpmenu.setText("Help");

        aboutMenu.setText("About");
        aboutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuActionPerformed(evt);
            }
        });
        helpmenu.add(aboutMenu);

        jMenuBar1.add(helpmenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(closeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
      this.dispose(); 
    }//GEN-LAST:event_closeButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
   this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void userInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userInputKeyPressed
             if(evt.getKeyCode() == VK_ENTER) {
            evt.consume();
            sendAction();
        }
    }//GEN-LAST:event_userInputKeyPressed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
    sendAction();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void reconnectMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconnectMenuActionPerformed
    reconnectGUI();
    }//GEN-LAST:event_reconnectMenuActionPerformed

    private void aboutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuActionPerformed
    showHelpGUI();
    }//GEN-LAST:event_aboutMenuActionPerformed

    private void clearMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearMenuActionPerformed
    userInput.setText(null);
    }//GEN-LAST:event_clearMenuActionPerformed

    private void sendMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMenuActionPerformed
      sendAction();
    }//GEN-LAST:event_sendMenuActionPerformed

    private void closeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuActionPerformed
     this.dispose();
    }//GEN-LAST:event_closeMenuActionPerformed

    private void changeNameMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeNameMenuActionPerformed
      changeUsernameGUI();
    }//GEN-LAST:event_changeNameMenuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenu;
    private javax.swing.JMenuItem changeNameMenu;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JMenuItem clearMenu;
    private javax.swing.JButton closeButton;
    private javax.swing.JMenuItem closeMenu;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpmenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem reconnectMenu;
    private javax.swing.JButton sendButton;
    private javax.swing.JMenuItem sendMenu;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextArea userInput;
    // End of variables declaration//GEN-END:variables
}
