/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SubGame;

/**
 *
 * @author minhn
 */

import com.Commons;
import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PrepareGame extends JPanel{
    private JLabel notice;

    public PrepareGame() {
        this.setBackground(Commons.COMPONENT_COLOR);
        this.setSize(Commons.SCREEN_WIDTH,Commons.SCREEN_HEIGHT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        notice = new JLabel();
        notice.setText("Click here to Start game");
        notice.setFont(Commons.LARGE_FONT);
        notice.setForeground(Commons.COMPONENT_COLOR);
        notice.setSize(Commons.SCREEN_WIDTH,Commons.SCREEN_HEIGHT);
        notice.setHorizontalAlignment(SwingConstants.CENTER);
        notice.setVerticalAlignment(SwingConstants.CENTER);
        
        this.add(notice,BorderLayout.CENTER);
        System.out.println("Blur layer was initizalation...");
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
