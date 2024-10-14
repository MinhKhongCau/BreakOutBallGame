package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import DatabaseConfig.ConnectionConfig;
import java.sql.SQLException;
import java.sql.Statement;

public class Board extends JFrame implements Runnable{
    private Thread clock;
    private Paddle paddle;
    private Ball ball;
    private Player player;
    
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
    
    public void startedGame(Player player) {
        this.player = player;
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
        try {
            while(clock != null) {
            double deltaTime = (double) Commons.DELTA_TIME * 1000;
            Thread.sleep((long) deltaTime);
            // update coponent
            update();
            // repaint component
            repaint();   
//            if (player.getLife() == 0) {
//                savePerformance();
//            }
            }    
        } catch (InterruptedException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void update() {
        // get mouse position
        Point point = MouseInfo.getPointerInfo().getLocation();
        // set position paddle
        paddle.move(point);
        
        ball.move();
    }
    
    public void savePerformance() {
        try {
            //1. get connection to database
            Connection con = ConnectionConfig.getConnection();
            //2. create query insert data to database
            String query = "INSERT INTO Player (nick_name) VALUES ('%s')";
            query = String.format(query, player.getName());
            //3. create statement for execute query
            Statement st = con.createStatement();
            //4. execute query
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
