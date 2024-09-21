package com;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.border.EmptyBorder;

public interface Commons {
    int ORIGINAL_SIZE = 16;
    int SCALE = 2;
    int TILE_SIZE = ORIGINAL_SIZE*SCALE;
    int BOTTOM_EDGE = 390;
    int N_OF_BRICKS = 30;
    // Commons in paddle dimention
    int INIT_PADDLE_X = 200;
    int INIT_PADDLE_Y = TILE_SIZE*13;
    int PADDLE_WIDTH = 4;
    int PADDLE_HEIGHT = 1;
    // Commons in ball dimention
    int INIT_BALL_X = 230;
    int INIT_BALL_Y = 355;
    int PERIOD = 10;
    // Commons in login form dimention
    int LOGINFORM_WIDTH = 600;
    int LOGINFORM_HEIGHT = 300;
    // Common in spacing in component
    EmptyBorder TITLE_BORDER = new EmptyBorder(50, 50, 50, 50);
    EmptyBorder ITEM_BORDER = new EmptyBorder(8, 8, 8, 8);
    EmptyBorder PANEL_BORDER = new EmptyBorder(10,20,10,20);
    int INPUT_WIDTH = 100;
    // Common in screen scale size
    int SCREEN_COL = 32;
    int SCREEN_ROW = 18;
    // Commons of Windows toolkit
    Toolkit toolkit = Toolkit.getDefaultToolkit();
}
