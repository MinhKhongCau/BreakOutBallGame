package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Board extends JFrame implements Runnable{
    private Thread clock;
    private Paddle paddle;
    private Ball ball;
    
    public Board() {
        initComponent();
    }
    
    private void initComponent() {
        paddle = new Paddle();
        ball = new Ball();
        
        this.setResizable(false);
        this.setSize(Commons.TILE_SIZE*Commons.SCREEN_COL,Commons.TILE_SIZE*Commons.SCREEN_ROW);
        this.setTitle("Break out ball");
        this.setLocationRelativeTo(null);

        JPanel panelBoard = new JPanel();
        int frameWidth = Commons.SCREEN_WIDTH, frameHeight = Commons.SCREEN_HEIGHT;
        // init screen with scale 16/9
        panelBoard.setSize(frameWidth,frameHeight);
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
        ball.draw(g);
    }

    /**
     * This is run method override Runnable interface
     */
    @Override
    public void run() {
        while(clock != null) {
            //                Thread.sleep(1000);
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
        
//        ball.move(point);
    }
}
