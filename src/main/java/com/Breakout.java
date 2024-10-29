package com;

import javax.swing.JFrame;

public class Breakout extends JFrame{
    private Player player;

    public Breakout() {
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
        Breakout breakout = new Breakout();
        breakout.setVisible(true);
    }
}