package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Item extends Sprite{
    private int width;
    private int height;
    
    public Item () {
        this.x = Commons.INIT_BALL_X;
        this.y = Commons.INIT_BALL_Y;
        this.width = Commons.BALL_SIZE;
        this.height = Commons.BALL_SIZE;
    }

    public Item(int width, int height) {
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
    
    public void move () {
        try {
            int speed = 200;
            this.y += (int) speed*Commons.DELTA_TIME;
            
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    public void draw (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(255, 160, 122));
        g2.fillOval(this.x, this.y, this.width, this.height);
    }
}
