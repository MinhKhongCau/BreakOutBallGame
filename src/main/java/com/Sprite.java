package com;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Sprite {
    public int x;
    public int y;
    public int imageWidth;
    public int imageHeight;
    public BufferedImage image;

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

    Rectangle getRect() {

        return new Rectangle(x, y,
                image.getWidth(null), image.getHeight(null));
    }

    void getImageDimensions() {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
}
