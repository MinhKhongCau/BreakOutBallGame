/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author minhn
 */
public class LoginForm extends JFrame {
    Board board;
    /**
     * Creates new form LoginForm
     */
    public LoginForm() {
        board = new Board();
        JFrame frameBoard = new JFrame();
        frameBoard.setResizable(false);
        frameBoard.setSize(Commons.TILE_SIZE*Commons.SCREEN_COL,Commons.TILE_SIZE*Commons.SCREEN_ROW);
        frameBoard.setTitle("Break out ball");
        frameBoard.setLocationRelativeTo(null);
        frameBoard.add(board);
        frameBoard.setVisible(true);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel_Title = new javax.swing.JLabel();
        jTextField_NickName = new javax.swing.JTextField();
        jLabel_NickName = new javax.swing.JLabel();
        jButton_Started = new javax.swing.JButton();
        jLabel_Exit = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(54, 66, 66));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.darkGray);
        setLocation(new java.awt.Point(500, 250));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(54, 66, 66));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel_Title.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_Title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_Title.setForeground(new java.awt.Color(250, 242, 233));
        jLabel_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Title.setText("BREAK OUT BALL GAME");

        jTextField_NickName.setBackground(new java.awt.Color(54, 66, 66));
        jTextField_NickName.setForeground(new java.awt.Color(250, 242, 233));
        jTextField_NickName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_NickNameActionPerformed(evt);
            }
        });

        jLabel_NickName.setForeground(new java.awt.Color(250, 242, 233));
        jLabel_NickName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel_NickName.setText("Nickname: ");

        jButton_Started.setBackground(new java.awt.Color(0, 102, 255));
        jButton_Started.setForeground(new java.awt.Color(250, 242, 233));
        jButton_Started.setText("Get started");
        jButton_Started.setBorder(null);
        jButton_Started.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_StartedActionPerformed(evt);
            }
        });

        jLabel_Exit.setBackground(new java.awt.Color(54, 66, 66));
        jLabel_Exit.setForeground(new java.awt.Color(242, 242, 242));
        jLabel_Exit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Exit.setText("X");
        jLabel_Exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_Exit.setOpaque(true);
        jLabel_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_ExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_ExitMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_NickName, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_NickName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addComponent(jLabel_Title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_Started, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_NickName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_NickName))
                .addGap(29, 29, 29)
                .addComponent(jButton_Started, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_NickNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_NickNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_NickNameActionPerformed

    private void jLabel_ExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExitMouseEntered
        // TODO add your handling code here:
        jLabel_Exit.setBackground(Color.RED);
    }//GEN-LAST:event_jLabel_ExitMouseEntered

    private void jLabel_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExitMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jLabel_ExitMouseClicked

    private void jLabel_ExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExitMouseExited
        // TODO add your handling code here:
        jLabel_Exit.setBackground(new java.awt.Color(54,66,66));
    }//GEN-LAST:event_jLabel_ExitMouseExited

    private void jButton_StartedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_StartedActionPerformed
        // TODO add your handling code here:
        String name = jTextField_NickName.getText();
        Player player = new Player(name,0,3);
        board.startedGame(player);
        this.setVisible(false);
    }//GEN-LAST:event_jButton_StartedActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Started;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel_Exit;
    private javax.swing.JLabel jLabel_NickName;
    private javax.swing.JLabel jLabel_Title;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField_NickName;
    // End of variables declaration//GEN-END:variables
}
