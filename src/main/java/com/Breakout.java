package com;

import com.Player;
import javax.swing.JFrame;

public class Breakout {
    private Player player;

    public static void main(String[] args) {
//        LoginForm login = new LoginForm();
//        login.setVisible(true);
        Board board = new Board();
        JFrame frameBoard = new JFrame();
        frameBoard.setResizable(false);
        frameBoard.setSize(Commons.TILE_SIZE * Commons.SCREEN_COL, Commons.TILE_SIZE * Commons.SCREEN_ROW);
        frameBoard.setTitle("Break out ball");
        frameBoard.setLocationRelativeTo(null);
        frameBoard.add(board);
        frameBoard.setVisible(true);
        // new RankingTable().setVisible(true);;
    }
}
