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
import me.cameronwitcher.upsilon.utils.Utils;
import res.Texture;

public class Game extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int DEBUG_LEVEL = 1;
	private String version = "0.0.1 BETA";
	public JPanel board;

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
		Bridge.getGame().board.loadLevel();
	}

	public void openLevelDebug(int i) {
		game.clear();
		level = new LevelDebugBoard(i);
		game.setPreferredSize(new Dimension(level.L_WIDTH, level.L_HEIGHT));
		game.pack();
		game.setVisible(true);

		game.add(level);
		game.setLocationRelativeTo(null);
	}

	public void loadLevel(int i) {
		game.clear();
		Utils.broadcastMessage("LOAD LEVEL", "Game.class (167)");
		game.pack();
		board = new GameBoard();
		game.setPreferredSize(new Dimension(board.B_WIDTH, board.B_HEIGHT));
		game.setResizable(false);
		game.startLevel(board);
	}

	public void openPlayerModels() {
		game.clear();

		Utils.broadcastMessage("LOAD MODELS", "Game.class (178)");
		game.pack();
		game.setVisible(true);
		models = new PlayerModelBoard(game);
		game.setPreferredSize(new Dimension(models.P_WIDTH, models.P_HEIGHT));
		game.add(models);
		game.setLocationRelativeTo(null);
	}

	public void openMenu() {
		game.clear();
		menu = new MenuBoard();
		game.setPreferredSize(new Dimension(menu.M_WIDTH, menu.M_HEIGHT));
		game.pack();
		game.setVisible(true);
		
		game.add(menu);
		game.setLocationRelativeTo(null);
	}

	public void clear() {
		removeAll();
		dispose();
	}

	public void openInventory(List<Tool> inv) {

		board.inv = true;

	}

	public void closeInventory() {
		board.inv = false;
	}

	public void setBoard(JPanel board) {
		this.board = board;
		add(board);
	}

}
