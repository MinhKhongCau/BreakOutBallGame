/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author minhn
 */
public class Login extends JPanel{
    private JLabel labelTitle;
    private JLabel labelNickName;
    private JTextField textNickName;
    private JButton buttonStart;
    private StartGameListener start;

    
    public Login() {
        
        setSize(400,300);
        setBackground(new java.awt.Color(54, 66, 66));
        setForeground(java.awt.Color.darkGray);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        
        // constraint title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        labelTitle = new JLabel();
        labelTitle.setBackground(new java.awt.Color(153, 153, 153));
        labelTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(250, 242, 233));
        labelTitle.setText("BREAK OUT BALL GAME");
        add(labelTitle,gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        
        // constraint nickname and label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        labelNickName = new JLabel("Nick name: ");
        labelNickName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelNickName.setForeground(new java.awt.Color(250, 242, 233));
        add(labelNickName,gbc);
        
        textNickName = new JTextField();
        textNickName.setBackground(new java.awt.Color(54, 66, 66));
        textNickName.setForeground(new java.awt.Color(250, 242, 233));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(textNickName,gbc);
        
        buttonStart = new JButton();
        buttonStart.setText("Get started");
        buttonStart.setBackground(new java.awt.Color(0, 102, 255));
        buttonStart.setForeground(new java.awt.Color(250, 242, 233));
        buttonStart.setBorderPainted(false);
        buttonStart.addActionListener((ActionEvent evt) -> {
            buttonStartActionPerformed(evt);
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(buttonStart,gbc);
                
        }

        public void buttonStartActionPerformed(ActionEvent e) {
        String name = textNickName.getText();
        if (start != null && !name.isEmpty()){
            start.startGame(name);
        } else {
            JOptionPane.showMessageDialog(Login.this, "Please enter a player name.");
        }
    }
        
    public void prepareGame(StartGameListener start) {
        this.start = start;
    }
    
    public interface StartGameListener {
        void startGame(String playerName);
    }
        
}