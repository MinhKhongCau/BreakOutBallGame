package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.Connection;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import DatabaseConfig.ConnectionConfig;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.sql.SQLException;
import java.sql.Statement;

public class Board extends JPanel implements Runnable{
    private Thread clock;
    private Paddle paddle;
    private Ball ball;
    private Brick[] brick;
    private int amount_brick=0;
    private Player player;
    private Item item;
    private JLabel labelName;
    private JLabel labelFPS;
    private JLabel labelLife;
    private JLabel labelScore;
    
    public Board() {
        initComponent();
    }
    
    private void initComponent() {
        paddle = new Paddle();
        ball = new Ball();  
        item = new Item(100, 150, 998);
        brick = new Brick[Commons.BRICK_ROW*Commons.BRICK_COL];
        createBrick(brick);
        
        int frameWidth = Commons.SCREEN_WIDTH, frameHeight = Commons.SCREEN_HEIGHT;
        // init screen with scale 16/9
        this.setSize(frameWidth,frameHeight);
        this.setBackground(new Color(54, 66, 66));
        this.setLayout(new BorderLayout());
        
        Font font = new java.awt.Font("Segoe UI", Font.BOLD, 15);
        
        labelName = new JLabel("Name: Unknown");
        labelName.setForeground(new java.awt.Color(250, 242, 233));
        labelName.setFont(font);
        
        labelFPS = new JLabel(String.format("FPS: %d", Commons.FPS));
        labelFPS.setForeground(new java.awt.Color(250, 242, 233));
        labelFPS.setFont(font);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(54, 66, 66));
        topPanel.setBorder(Commons.PANEL_BORDER);
        topPanel.add(labelName, BorderLayout.WEST);
        topPanel.add(labelFPS, BorderLayout.EAST);
        
        this.add(topPanel, BorderLayout.NORTH);
        
        labelScore = new JLabel(String.format("Score: %d", 0));
        labelScore.setForeground(new java.awt.Color(250, 242, 233));
        labelScore.setFont(font);
        
        labelLife = new JLabel(String.format("Life: %d", 0));
        labelLife.setForeground(new java.awt.Color(250, 242, 233));
        labelLife.setFont(font);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(54, 66, 66));
        bottomPanel.setBorder(Commons.PANEL_BORDER);
        bottomPanel.add(labelLife,BorderLayout.WEST);
        bottomPanel.add(labelScore,BorderLayout.EAST);
        
        this.add(bottomPanel,BorderLayout.SOUTH);
    }
    
    private void createBrick(Brick[] brick) {
    	for(int i=0; i<Commons.BRICK_ROW; i++) {
    		for(int j=0; j<Commons.BRICK_COL; j++) {
    			brick[amount_brick] = new Brick();
    			brick[amount_brick].x = i*Commons.BRICK_WIDTH+155;
    			brick[amount_brick].y = j*Commons.BRICK_HEIGHT+80;
    			amount_brick+=1;
    		}
    	}
    }
    
    public void startedGame(Player player) {
        this.player = player;
        clock = new Thread(this);
        clock.start();
    }
    
    public void drawBrick(Graphics g) {
        for(int i=0;i<amount_brick;i++) {
            if (brick[i].getStatus() == 1) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(185, 211, 238));
                g2.fillRect(brick[i].getX(), brick[i].getHeight(), Commons.BRICK_WIDTH-2, Commons.BRICK_HEIGHT-2);
                g2.setColor(new Color(54,66,66));
                g2.drawRect(brick[i].getX(), brick[i].getHeight(), Commons.BRICK_WIDTH, Commons.BRICK_HEIGHT);
            }
        }
    }
    
    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Clears the screen

        // Draw the paddle, ball, item, and bricks
        paddle.draw(g);
        ball.draw(g);
        item.draw(g);

        // Draw the active bricks
        for (int i = 0; i < amount_brick; i++) {
            if (brick[i].getStatus() == 1) {  // Only draw bricks that are not destroyed
                brick[i].draw(g);
            }
        }
    }


    /**
     * This is run method override Runnable interface
     */
    @Override
    public void run() {
        try {
            double msPerFrame = 1000 / Commons.FPS;
            long lastTime = System.currentTimeMillis();  // Time of the previous frame
            double delta = 0;  // Tracks how much time has passed
            long timer = System.currentTimeMillis();
            int frames = 0;
            
            while(clock != null) {

                long now = System.currentTimeMillis();  // Current time in miliseconds
                delta = (now - lastTime);  // Accumulate the elapsed time
                lastTime = now;
                // If enough time has passed (1 frame's worth), update and render
                while (delta >= msPerFrame) {
                    // update coponent
                    update();
                    // repaint component
                    repaint(ball.getX()-ball.getWidth(),ball.getY()-ball.getHeight(),ball.getWidth()*2,ball.getHeight()*2);
                    repaint(0,paddle.getY(),Commons.SCREEN_WIDTH,paddle.getHeight()*2);   
                    repaint(item.getX()-item.getWidth(),item.getY()-item.getHeight(),item.getWidth()*2,item.getHeight()*2);

                    delta-=msPerFrame;    // Reduce delta since we've processed one frame
                }
                clock.sleep((long) msPerFrame);
                
                frames++;  // Count frames per second

                // Optional: Print FPS every second for debugging
                if (System.currentTimeMillis() - timer > 1000) {
                    labelFPS.setText(String.format("FPS: %d", frames));
                    frames = 0;
                    timer += 1000;
                }
                
                
                // if (player.getLife() == 0) {
                //     savePerformance();
                // }
            }  
        } catch (InterruptedException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void update() {
        labelName.setText(String.format("Name: %s", player.getName()));
        labelLife.setText(String.format("Life: %d", player.getLife()));
        labelScore.setText(String.format("Score: %d", player.getScore()));
        // get mouse position
        Point point = MouseInfo.getPointerInfo().getLocation();
        // set position paddle
        paddle.move(point);
        ball.move();
        if(ball.getY()==200) {
            dropItem(155,80);
        }
        if(item.getY()>paddle.getY() && (item.getX()>=paddle.getX() && item.getX()<=paddle.getX()+paddle.getWidth())) {
        	touchItem(item);
        	item = new Item(100, 150, 998);
        }
        	
        if(item.getY()>Commons.SCREEN_HEIGHT) {
        	item = new Item(100, 150, 998);
        }
        
        if(item.getNum()!=998) {
        	item.move();
        }
        if(ball.getY()>200) {
            dropItem(155,80);
        }


        if(item.getY()>paddle.getY() && (item.getX()>=paddle.getX() && item.getX()<=paddle.getX()+paddle.getWidth())) {
            touchItem(item);
            item = new Item(100, 150, 998);
        }

        if(item.getY()>Commons.SCREEN_HEIGHT) {
            item = new Item(100, 150, 998);
        }
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
    
    public void dropItem(int x, int y) {
    	if(item.getNum()==998) {
//    		Random generator = new Random();
//        	int value = generator.nextInt(10)+1;
//        	
//        	if(value%3==0) {
    		item = new Item(100, 100, 9);
//        		item.setX(x);
//        		item.setY(y);
//        		item.setNum(3);
//        	}
        	
    	}
    }
    
    public void touchItem(Item i) {   	
    	 switch(i.getNum()){
    	 	case 3: paddle.setWidth(Commons.PADDLE_WIDTH+200);
    	 			setPaddleDefault();
    	 			break;
    	 	case 6: paddle.setWidth(Commons.PADDLE_WIDTH-60);
    	 			setPaddleDefault();
    	 			break;
    	 	case 9: ball.setWidth(Commons.BALL_SIZE+5);
    	 			ball.setHeight(Commons.BALL_SIZE+5);
    	 			ball.setSpeed(Commons.BALL_SPEED-100);
    	 			setBallDefault();
    	 			break;
    	 	case 12: ball.setWidth(Commons.BALL_SIZE-3);
		 			ball.setHeight(Commons.BALL_SIZE-3);
		 			ball.setSpeed(Commons.BALL_SPEED+100);
		 			setBallDefault();
		 			break;
    	 	case 15: if(ball.getY()==paddle.getY()) {    	 				
		        		ball.setDir(-1);
		 			 }
    	 			 setDefaultShield();
    	 			 break;
    	 	default:
    	 		break;
    	 }    	 
    }
    
    public void setPaddleDefault() {
    	Timer t = new Timer();
    	TimerTask task = new TimerTask() {
            public void run() {
                paddle.setWidth(Commons.PADDLE_WIDTH); 
            }
        };
        t.schedule(task, 2000);
    }
    
    public void setBallDefault() {
    	Timer t = new Timer();
    	TimerTask task = new TimerTask() {
            public void run() {
                ball.setWidth(Commons.BALL_SIZE);
                ball.setHeight(Commons.BALL_SIZE);
                ball.setSpeed(500);
            }
        };
        t.schedule(task, 2000);
    }
    
    public void setDefaultShield() {
    	Timer t = new Timer();
    	TimerTask task = new TimerTask() {
            public void run() {
            	if(ball.getY()==paddle.getY()) {
            		ball.setDir(1);
	 			}
            }
        };
        t.schedule(task, 10000);
    }
}