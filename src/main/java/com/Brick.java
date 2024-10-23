package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Brick extends Sprite{
    private int width;
    private int height;
    private boolean broke;
    
    public Brick() {
//		this.x = Commons.INIT_BRICK_X;
//		this.y = Commons.INIT_BRICK_Y;
                this.width = Commons.BRICK_WIDTH;
                this.height = Commons.BRICK_HEIGHT;
                this.broke = false;
	}
    
    public Brick(int width, int height) {
    	this.width = width;
        this.height = height;
        this.broke = false;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public boolean isBroke() {
        return broke;
    }
    
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(185, 211, 238));
        g2.fillRect(this.x, this.y, Commons.BRICK_WIDTH-2, Commons.BRICK_HEIGHT-2);
        g2.setColor(new Color(54,66,66));
        g2.drawRect(this.x, this.y, Commons.BRICK_WIDTH, Commons.BRICK_HEIGHT);
//        g2.dispose();
    }
}
