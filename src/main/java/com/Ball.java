package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Ball extends Sprite{
    private int width;
    private int height;
    private int dir=1;
    private int speed;
    
    public Ball () {
        this.x = Commons.INIT_BALL_X;
        this.y = Commons.INIT_BALL_Y;
        this.width = Commons.BALL_SIZE;
        this.height = Commons.BALL_SIZE;
        this.speed = Commons.BALL_SPEED-400;
    }

    public Ball(int width, int height) {
        this.x = Commons.INIT_BALL_X;
        this.y = Commons.INIT_BALL_Y;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
   
    public void setDir(int dir) {
    	this.dir=dir;
    }
    
    public void setSpeed(int speed) {
    	this.speed = speed;
    }
    
    public void move () {
        try {
            this.x += (int) dir*speed*Commons.DELTA_TIME;
            this.y += (int) dir*speed*Commons.DELTA_TIME;
            
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    public void draw (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillOval(this.x, this.y, this.width, this.height);
    }
}
