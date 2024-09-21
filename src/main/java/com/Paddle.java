package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Paddle extends Sprite{  
    private int width;
    private int height;

    public Paddle() {
        this.x = Commons.INIT_PADDLE_X;
        this.y = Commons.INIT_PADDLE_Y;
        this.width = Commons.TILE_SIZE*Commons.PADDLE_WIDTH;
        this.height = Commons.TILE_SIZE*Commons.PADDLE_HEIGHT;
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
            this.x = (int) point.getX();
            this.y = (int) point.getY();
            
            System.out.println(point.getX()+"-"+point.getY());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    } 
    
    public void draw(Graphics g) {
        int tile = Commons.TILE_SIZE;
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int frameWidth = Commons.SCREEN_COL*Commons.TILE_SIZE;
        int positionX = this.x - (int) (screenWidth-frameWidth)/2;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(positionX, Commons.INIT_PADDLE_Y, Commons.PADDLE_WIDTH*tile, Commons.PADDLE_HEIGHT*tile);
        g2.dispose();
    }
}
