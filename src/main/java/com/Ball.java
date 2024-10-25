package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball extends Sprite {
    private int width;
    private int height;
    private int dirX = 1;
    private int dirY = 1;
    private int speed;

    public Ball() {
        this.x = Commons.INIT_BALL_X;
        this.y = Commons.INIT_BALL_Y;
        this.width = Commons.BALL_SIZE;
        this.height = Commons.BALL_SIZE;
        this.speed = Commons.BALL_SPEED - 350;
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

    // public void setDir(int dir) {
    // this.dir = dir;
    // }

    public void move() {
        try {

            this.x += (int) dirX * speed * Commons.DELTA_TIME;
            this.y += (int) dirY * speed * Commons.DELTA_TIME;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void reverseX() {
        this.dirX = -this.dirX;
    }

    public void reverseY() {
        this.dirY = -this.dirY;
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
