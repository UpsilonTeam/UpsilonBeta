package me.cameronwitcher.upsilon;

import java.awt.Dimension;

import java.io.File;
import java.io.StringReader;

import javax.swing.JFrame;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;

import me.cameronwitcher.upsilon.boards.Game;
import me.cameronwitcher.upsilon.boards.GameBoard;
import me.cameronwitcher.upsilon.boards.LevelDebugBoard;
import me.cameronwitcher.upsilon.sprites.Player;
import me.cameronwitcher.upsilon.utils.Utils;
import res.Texture;

@WebServiceProvider
@ServiceMode(value = Service.Mode.PAYLOAD)
public class Bridge implements Provider<Source> {

	private static Game game;
	public static Player player;
	
	public Source invoke(Source request) {
        return  new StreamSource(new StringReader("<p>Hello There!</p>"));
    }

	public static void main(String[] args) {
		game = new Game();
		game.init();
		File file = new File("C://Upsilon/");
		if (!file.exists()) {
			Utils.runInstall();
		}
		Utils.init();

	}
	
	public static void setPlayer(Player p){
		player = p;
	}

	public static Game getGame() {
		return game;
	}

	// Cameron
	// Ends the game from Runtime.
	public static void quit() {
		System.exit(0);
	}

	public static void openLevelDebug(int level) {
		JFrame frame = new JFrame("Debug: " + level);
		frame.add(new LevelDebugBoard(level));
		frame.setVisible(true);
		frame.setSize(640, 640);
	}

	public static void start() {
		game.clear();
		game = new Game();
		game.setTitle("Upsilon");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		game.setIconImage(Texture.loadTexture("logo.png"));
		Utils.broadcastMessage("START", "Bridge.class (53)");
		
		game.setPreferredSize(new Dimension(640, 640));
		game.pack();
		
		game.setBoard(new GameBoard());
		
		((GameBoard) game.board).init();
		

		game.setResizable(false);
		
		if (Utils.player_level == 1) {
			((GameBoard) game.board).loadHelp();
		}
	
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayerLocation(int x, int y) {
		player.x = x;
		player.y = y;
	}

}
