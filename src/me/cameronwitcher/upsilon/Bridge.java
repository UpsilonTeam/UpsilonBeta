package me.cameronwitcher.upsilon;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import me.cameronwitcher.upsilon.boards.Game;
import me.cameronwitcher.upsilon.boards.GameBoard;
import me.cameronwitcher.upsilon.boards.LevelDebugBoard;
import me.cameronwitcher.upsilon.utils.Utils;

public class Bridge {
	

	private static Game game;
	private static Game game_temp;
	
	 public static void main(String[] args) {
		 game = new Game(1);
		 game.init();
		 File file = new File("C://Upsilon/");
		 if(!file.exists()){
			 Utils.runInstall();
		 }
		 Utils.init();
	 
		 
	 }
	 
	 public static Game getGame(){
		 return game;
	 }

	 //Cameron
	 //Ends the game from Runtime.
	public static void quit() {
		System.exit(0);
	}
	
	public static void openLevelDebug(int level){
		JFrame frame = new JFrame("Debug: " + level);
		frame.add(new LevelDebugBoard(level));
		frame.setVisible(true);
		frame.setSize(640, 640);
	}
	 
	
	 
	 
}
