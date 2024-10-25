package com;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Sprite {
    protected int x;
    protected int y;
    protected int imageWidth;
    protected int imageHeight;
    protected BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Rectangle getRect() {
        if (image == null) {
            return new Rectangle(x, y, imageWidth, imageHeight);
        }
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    void getImageDimensions() {
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
    }
}
