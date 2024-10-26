package com;

import java.awt.Color;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class RankingTable extends JFrame {

	public RankingTable() throws HeadlessException {
		super();
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(new Color(54, 66, 66));
		this.setSize(Commons.RANK_WIDTH, Commons.RANK_HEIGHT);
		this.setResizable(true);
		this.setLocation(Commons.SCREEN_WIDTH / 2, Commons.SCREEN_HEIGHT / 4);
		this.setForeground(Color.darkGray);
	}

}
