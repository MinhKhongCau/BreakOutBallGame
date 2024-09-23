package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.*;

public class Board extends JFrame implements Runnable{
    private Thread clock;
    private Paddle paddle;
    
    public Board() {
        initComponent();
    }
    
    private void initComponent() {
        paddle = new Paddle();
        
        this.setResizable(true);
        this.setSize(Commons.SCREEN_COL,Commons.SCREEN_ROW);
        this.setTitle("Break out ball");
        this.setLocationRelativeTo(null);

        JPanel panelBoard = new JPanel();
        int frameWidth = Commons.SCREEN_ROW, frameHeight = Commons.SCREEN_COL;
        // init screen with scale 16/9
        panelBoard.setSize(frameHeight,frameHeight);
        panelBoard.setBackground(new Color(54, 66, 66));
        this.add(panelBoard);
    }
    
    public void startedGame() {
        clock = new Thread(this);
        clock.start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        paddle.draw(g);
    }

    public void run() {
        while(clock != null) {

            // update position paddle
            update();
            // repaint component
            repaint();
        }
    }

    private void update() {
        // get mouse position
        Point point = MouseInfo.getPointerInfo().getLocation();
        // set position paddle
        paddle.move(point);
        
        System.out.println("point x = "+paddle.getX()+" y = "+paddle.getY());
    }
}
