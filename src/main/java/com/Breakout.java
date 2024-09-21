package com;

public class Breakout {
    public static void main(String[] args) {
//        new LoginForm().setVisible(true);
        Board board = new Board();
        board.setVisible(true);
        board.startedGame();
    }
}
