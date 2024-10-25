package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball extends Sprite {
    private int width;
    private int height;
    private int dir = 3;
    private int speed;

    public Ball() {
        this.x = Commons.INIT_BALL_X;
        this.y = Commons.INIT_BALL_Y;
        this.width = Commons.BALL_SIZE;
        this.height = Commons.BALL_SIZE;
        this.speed = Commons.BALL_SPEED;
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Move function
     */
    public void move() {
        try {
            // dir = 1: up + right
            // dir = 2: down + right
            // dir = 3: dowm + left
            // dir = 4: up + left
            if (dir == 1) {
                this.x += (int) speed * Commons.DELTA_TIME;
                this.y -= (int) speed * Commons.DELTA_TIME;
            } else if (dir == 2) {
                this.x += (int) speed * Commons.DELTA_TIME;
                this.y += (int) speed * Commons.DELTA_TIME;
            } else if (dir == 3) {
                this.x -= (int) speed * Commons.DELTA_TIME;
                this.y += (int) speed * Commons.DELTA_TIME;
            } else if (dir == 4) {
                this.x -= (int) speed * Commons.DELTA_TIME;
                this.y -= (int) speed * Commons.DELTA_TIME;
            }
            // this.x += (int) dirX * speed * Commons.DELTA_TIME;
            // this.y += (int) dirY * speed * Commons.DELTA_TIME;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void reverseX() {
        // 1 -> 4 : 2 -> 3 : 3 -> 2 : 4 -> 1
        this.dir = 5 - this.dir;
    }

    public void reverseY() {
        // 1 -> 2 : 2 -> 1 : 3 -> 4 : 4 -> 3
        if (dir % 2 == 0) dir -= 1;
        else dir += 1;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillOval(this.x, this.y, this.width, this.height);
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

}
