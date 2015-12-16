package me.cameronwitcher.upsilon.boards;

import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.spriteutils.PlayerModel;
import me.cameronwitcher.upsilon.spriteutils.tools.Tool;
import me.cameronwitcher.upsilon.utils.Board;
import me.cameronwitcher.upsilon.utils.Utils;
import res.Texture;

public class Game extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int DEBUG_LEVEL = 1;
	private String version = "0.0.1 BETA";
	public Board board;

	public Game() {
		Utils.broadcastMessage("TEEST", "Game.class (32)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public String getVersion(){
		return version;
	}

	public void init() {
		
		add(new MenuBoard());

		setResizable(false);
		pack();

		setTitle("Upsilon");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setIconImage(Texture.loadTexture("logo.png"));
	}



	

	public void start() {
		clear();
		setTitle("Upsilon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setIconImage(Texture.loadTexture("logo.png"));
		Utils.broadcastMessage("START", "Game.class (105)");
		
		setPreferredSize(new Dimension(640, 640));
		pack();
		
		add(new GameBoard());

		setResizable(false);
		
		if (Utils.player_level == 1) {
			((GameBoard) board).loadHelp();
		}
	}
	
	public void restart(){
		Utils.player_level = 1;
		((GameBoard)Bridge.getGame().getBoard()).loadLevel();
	}

	public void openLevelDebug(int i) {
		clear();
		setPreferredSize(new Dimension(640, 640));
		pack();
		setVisible(true);

		setBoard(new LevelDebugBoard(i));
		setLocationRelativeTo(null);
	}

	public void loadLevel(int i) {
		clear();
		Utils.broadcastMessage("LOAD LEVEL", "Game.class (167)");
		pack();
		setBoard(new GameBoard());
		setPreferredSize(new Dimension(640, 640));
		setResizable(false);
		start();
	}

	

	public void openMenu() {
		clear();
		setPreferredSize(new Dimension(960, 640));
		pack();
		setVisible(true);
		
		
		setLocationRelativeTo(null);
	}

	public void clear() {
		removeAll();
		dispose();
	}

	public void openInventory(List<Tool> inv) {

		((GameBoard)Bridge.getGame().getBoard()).inv = true;

	}

	public void closeInventory() {
		((GameBoard)Bridge.getGame().getBoard()).inv = false;
	}

	public void setBoard(Board board) {
		this.board = board;
		add(board);
	}

	public Board getBoard() {
		return board;
	}

}
