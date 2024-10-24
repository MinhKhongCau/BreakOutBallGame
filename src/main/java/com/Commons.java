package com;

import java.awt.Toolkit;
import javax.swing.border.EmptyBorder;

public interface Commons {
    int ORIGINAL_SIZE = 16;
    double SCALE = 1.2;
    int TILE_SIZE = (int) (ORIGINAL_SIZE*SCALE);
    int BOTTOM_EDGE = 390;
    int N_OF_BRICKS = 30;
    // Common in screen scale size
    int SCREEN_COL = 64;
    int SCREEN_ROW = 36;
    int SCREEN_WIDTH = TILE_SIZE*SCREEN_COL;
    int SCREEN_HEIGHT = TILE_SIZE*SCREEN_ROW;
    // Commons in paddle dimention
    int INIT_PADDLE_X = 200;
    int INIT_PADDLE_Y = SCREEN_HEIGHT/10*8;
    int PADDLE_WIDTH = TILE_SIZE*8;
    int PADDLE_HEIGHT = TILE_SIZE*1;
    // Commons in brick dimention
    int BRICK_WIDTH = TILE_SIZE*6;
    int BRICK_HEIGHT = TILE_SIZE*2;
    int BRICK_ROW = (SCREEN_WIDTH*3/4)/BRICK_WIDTH;
    int BRICK_COL = (SCREEN_HEIGHT/3)/BRICK_HEIGHT;
    // Commons in item dimention
    int ITEM_SIZE = TILE_SIZE-5;
    // Commons in ball dimention
    int BALL_SPEED =500;
    int INIT_BALL_X = 100;
    int INIT_BALL_Y = 100;
    int BALL_SIZE = TILE_SIZE;
    int PERIOD = 10;
    // Commons in login form dimention
    int LOGINFORM_WIDTH = 600;
    int LOGINFORM_HEIGHT = 300;
    // Common in spacing in component
    EmptyBorder TITLE_BORDER = new EmptyBorder(50, 50, 50, 50);
    EmptyBorder ITEM_BORDER = new EmptyBorder(8, 8, 8, 8);
    EmptyBorder PANEL_BORDER = new EmptyBorder(10,20,10,20);
    int INPUT_WIDTH = 100;
    // Commons of Windows toolkit
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    // Delta time
    int FPS = 60;
    double DELTA_TIME = (double) 1/FPS;
    // Commons of Ranking
    int RANK_WIDTH = TILE_SIZE*18;
    int RANK_HEIGHT = TILE_SIZE*36;
}
