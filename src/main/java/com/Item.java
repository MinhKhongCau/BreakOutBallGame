package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Item extends Sprite{
    private int width;
    private int height;
    private int num;
    
    public Item (int x, int y, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
        this.width = Commons.ITEM_SIZE;
        this.height = Commons.ITEM_SIZE;
    }
    
    public int getNum() {
    	return num;
    }
    
    public void setNum(int num) {
    	this.num = num;
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
        switch(num) {
        	case 3:
	        	g2.setColor(Color.GREEN);
	            g2.fillOval(this.x, this.y, this.width, this.height);
	            break;
        	case 6:
	        	g2.setColor(Color.RED);
	            g2.fillOval(this.x, this.y, this.width, this.height);
	            break;
        	case 9:
	        	g2.setColor(Color.ORANGE);
	            g2.fillOval(this.x, this.y, this.width, this.height);
	            break;
        	case 12:
	        	g2.setColor(Color.CYAN);
	            g2.fillOval(this.x, this.y, this.width, this.height);
	            break;
        	case 15:        		
	        	g2.setColor(Color.PINK);
	            g2.fillOval(this.x, this.y, this.width, this.height);
	            break;
        	case 18:
	        	g2.setColor(Color.MAGENTA);
	            g2.fillOval(this.x, this.y, this.width, this.height);
	            break;
	        default:
	            break;
        }
    }
}
