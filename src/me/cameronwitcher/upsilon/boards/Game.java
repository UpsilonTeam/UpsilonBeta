package me.cameronwitcher.upsilon.boards;

import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import me.cameronwitcher.upsilon.spriteutils.PlayerModel;
import me.cameronwitcher.upsilon.spriteutils.tools.Tool;
import me.cameronwitcher.upsilon.utils.Utils;
import res.Texture;

public class Game extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Scanner commandScanner;
	GameBoard board;
	private Game game;
	private Game game_temp;
	private LevelDebugBoard level;
	private PlayerModelBoard models;
	private MenuBoard menu;
	public int DEBUG_LEVEL = 1;
	private String version = "0.0.1 BETA";

	public Game(int i) {
		
		game = this;
		Utils.broadcastMessage("TEEST", "Game.class (32)");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String getVersion(){
		return version;
	}

	public void init() {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				commandScanner = new Scanner(System.in);
				String command = commandScanner.next();
				if (command.equalsIgnoreCase("fly"))
					getBoard().toggleGravity();
				if (command.equalsIgnoreCase("yellow")) {
					Utils.broadcastMessage("TESTST", "Game.class (48)");
					getBoard().player.setPlayerModel(PlayerModel.YELLOW);
				}
				if (command.equalsIgnoreCase("green"))
					getBoard().player.setPlayerModel(PlayerModel.GREEN);
				if (command.equalsIgnoreCase("blue"))
					getBoard().player.setPlayerModel(PlayerModel.BLUE);
				if (command.equalsIgnoreCase("purple"))
					getBoard().player.setPlayerModel(PlayerModel.PURPLE);
				if (command.equalsIgnoreCase("pink"))
					getBoard().player.setPlayerModel(PlayerModel.PINK);
			}

		}, 15, 15);

		menu = new MenuBoard(this);
		add(menu);

		// setResizable(false);
		pack();

		setTitle("Upsilon");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setIconImage(Texture.loadTexture("logo.png"));
	}

	public void startLevel(GameBoard board) {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				commandScanner = new Scanner(System.in);
				String command = commandScanner.nextLine();
				if (command.equalsIgnoreCase("fly"))
					getBoard().toggleGravity();
				if (command.equalsIgnoreCase("yellow")) {
					Utils.broadcastMessage("TESTST", "Game.class (88)");
					getBoard().player.setPlayerModel(PlayerModel.YELLOW);
				}
				if (command.equalsIgnoreCase("green"))
					getBoard().player.setPlayerModel(PlayerModel.GREEN);
				if (command.equalsIgnoreCase("blue"))
					getBoard().player.setPlayerModel(PlayerModel.BLUE);
				if (command.equalsIgnoreCase("purple"))
					getBoard().player.setPlayerModel(PlayerModel.PURPLE);
				if (command.equalsIgnoreCase("pink"))
					getBoard().player.setPlayerModel(PlayerModel.PINK);
			}

		}, 15, 15);

		add(board);

		setResizable(false);
		pack();
		setLocationRelativeTo(null);

		setTitle("Upsilon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setIconImage(Texture.loadTexture("/res/logo.png"));

		if (Utils.player_level == 1) {
			board.loadHelp();
		}
	}

	public GameBoard getBoard() {

		return board;
	}

	private void restart() {
		game.clear();
		Utils.broadcastMessage("RESTART", "Game.class (126)");
		menu = new MenuBoard(this);		
		game.setPreferredSize(new Dimension(menu.M_WIDTH, menu.M_HEIGHT));
		game.pack();
		game.setVisible(true);

		game.add(menu);
		game.setLocationRelativeTo(null);
		
	}

	public void start(int i) {
		game.clear();
		Utils.broadcastMessage("START", "Game.class (143)");
		board = new GameBoard(1);
		game.setPreferredSize(new Dimension(board.B_WIDTH, board.B_HEIGHT));
		game.pack();

		game.setResizable(false);
		game.startLevel(board);
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
		board = new GameBoard(i);
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
		game.restart();
	}

	public void clear() {
		game.removeAll();
		game.dispose();
		game = new Game(1);
	}

	public void openInventory(List<Tool> inv) {

		board.inv = true;

	}

	public void closeInventory() {
		board.inv = false;
	}

}
