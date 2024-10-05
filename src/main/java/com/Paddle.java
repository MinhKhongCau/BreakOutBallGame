package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;

public class Paddle extends Sprite{  
    private int width;
    private int height;

    public Paddle() {
        this.x = Commons.INIT_PADDLE_X;
        this.y = Commons.INIT_PADDLE_Y;
        this.width = Commons.PADDLE_WIDTH;
        this.height = Commons.PADDLE_HEIGHT;
    }

    public Paddle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void move(Point point) {
        try {
            double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int frameWidth = Commons.SCREEN_WIDTH;
            int positionX = (int) point.getX() - (int) (screenWidth-frameWidth)/2 - this.width/2;
            this.x = positionX;
            
        } catch (NumberFormatException e) { 
            e.printStackTrace();
        }
    } 
    
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(this.x, Commons.INIT_PADDLE_Y, Commons.PADDLE_WIDTH, Commons.PADDLE_HEIGHT);
//        g2.dispose();
    }
}
