package com;

import javax.swing.JFrame;

public class Main extends JFrame{

    public Main() {
        Board board = new Board();
        this.setResizable(true);
        this.setSize(Commons.TILE_SIZE * Commons.SCREEN_COL, Commons.TILE_SIZE * Commons.SCREEN_ROW);
        this.setTitle("Break out ball");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(board);
//        this.add(new RankingTable());
    }
    
    public static void main(String[] args) {
        Main breakout = new Main();
        breakout.setVisible(true);
    }
}