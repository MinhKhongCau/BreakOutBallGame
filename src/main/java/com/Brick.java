package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Brick extends Sprite{
	private int width;
    private int height;
    private int status;
    
    public Brick() {
		this.width = Commons.BRICK_WIDTH;
		this.height = Commons.BRICK_HEIGHT;
		status = 1;
	}
    
    public Brick(int width, int height) {
    	this.width = width;
        this.height = height;
    }
    
    public boolean brick_break() {
    	if(status==1) {
    		this.status=0;
    		return true;
    	}
    	return false;
    }
    
    public int getStatus() {
    	return status;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        if(status==1) {
        	g2.setColor(new Color(185, 211, 238));
            g2.fillRect(this.x, this.y, Commons.BRICK_WIDTH-2, Commons.BRICK_HEIGHT-2);
            g2.setColor(new Color(54,66,66));
            g2.drawRect(this.x, this.y, Commons.BRICK_WIDTH, Commons.BRICK_HEIGHT);
        }else {
        	g2.setColor(new Color(54,66,66));
            g2.fillRect(this.x, this.y, Commons.BRICK_WIDTH, Commons.BRICK_HEIGHT);
            g2.setColor(new Color(54,66,66));
            g2.drawRect(this.x, this.y, Commons.BRICK_WIDTH, Commons.BRICK_HEIGHT);
        }
    }
}
