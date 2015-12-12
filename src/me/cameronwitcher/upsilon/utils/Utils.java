package me.cameronwitcher.upsilon.utils;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.sprites.Player;
import me.cameronwitcher.upsilon.spriteutils.PlayerModel;
import me.cameronwitcher.upsilon.spriteutils.Sprite;
import me.cameronwitcher.upsilon.spriteutils.tools.Tool;
import me.cameronwitcher.upsilon.spriteutils.tools.ToolType;
import res.Audio;

public class Utils {

	private static List<Integer> ids = new ArrayList<>();
	private static File config;
	private static File modelsFile;
	private static File inventoryFile;
	public static int player_level;
	public static int player_score;
	public static String player_model;
	private static String root;
	private static File rootFile;
	private static ArrayList<String> models = new ArrayList<>();
	private static ArrayList<String> player_inv = new ArrayList<>();

	public static void init() {
		root = "C://Upsilon/";
		rootFile = new File(root);
		if (!rootFile.exists())
			runInstall();
		config = new File(root + "/config.txt");
		modelsFile = new File(root + "/models.txt");
		inventoryFile = new File(root + "/inventory.txt");

		int length = (int) config.length();
		byte[] bytes = new byte[length];
		FileInputStream in;
		try {
			in = new FileInputStream(config);
			try {
				in.read(bytes);
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}

		int invl = (int) inventoryFile.length();
		byte[] invb = new byte[invl];
		FileInputStream invin;
		try {
			invin = new FileInputStream(inventoryFile);
			try {
				invin.read(invb);
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				try {
					invin.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}

		int length1 = (int) modelsFile.length();
		byte[] bytes1 = new byte[length1];
		FileInputStream in1;
		try {
			in1 = new FileInputStream(modelsFile);
			try {
				in1.read(bytes1);
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				try {
					in1.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}

		String invs = new String(invb);
		String[] invd = invs.split("-");
		for (String info : invd) {
			if (info.equalsIgnoreCase("empty"))
				break;
			if (info.contains("empty"))
				info.replaceAll("empty", "");
			player_inv.add(info);
		}

		String contents = new String(bytes);
		String[] data = contents.split("-");
		for (String info : data) {
			if (info.contains("level"))
				player_level = Integer.parseInt(info.split(":")[1]);
			if (info.contains("score"))
				player_score = Integer.parseInt(info.split(":")[1]);
			if (info.contains("model"))
				player_model = info.split(":")[1];
		}

		String contents1 = new String(bytes1);
		String[] data1 = contents1.split("-");
		for (String info : data1) {
			models.add(info);
		}

	}

	public static void broadcastMessage(String message, String location) {
		System.out.println(message);
		System.out.println(location);
		System.out.println();
	}

	public static void displayMessage(int id, String message, int x, int y, int time, String color, int size) {
		Bridge.getGame()
				.getBoard().messages.put(
						id,
						message + ":"
								+ ((x - Bridge.getGame().getBoard()
										.getFontMetrics(new Font("Helvetica", Font.BOLD, size)).stringWidth(message)
										/ 2))
								+ ":" + y + ":" + time + ":" + color + ":" + size);
	}

	public static void addPlayerMessage(int id, String message, int xmod, int ymod, int time, String color, int size) {
		Bridge.getGame().getBoard().messages_player.put(id,
				message + ":" + xmod + ":" + ymod + ":" + time + ":" + color + ":" + size);
	}

	public static int getNextID() {
		try {
			int i = ids.size();
			ids.add(i);
			return i;
		} catch (IndexOutOfBoundsException ex) {
			ids.add(0);
			return 0;
		}
	}

	public static void savePlayerInfo(Player player) {
		config.delete();
		if (!config.getParentFile().mkdirs()) {
		}
		inventoryFile.delete();
		if (!inventoryFile.getParentFile().mkdirs()) {
		}
		modelsFile.delete();
		if (!modelsFile.getParentFile().mkdirs()) {
		}
		try {
			player_level = player.level;
			player_score = player.score;
			player_model = player.model.toString();
		} catch (NullPointerException ex) {
		}

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(config, true));
			out.append("level:" + player_level + "-");
			out.append("score:" + player_score + "-");
			out.append("model:" + player_model);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		try {
			out = new BufferedWriter(new FileWriter(inventoryFile, true));
			try {
				for (Tool tool : player.inventory) {
					out.append(tool.getType() + "-");
					Utils.broadcastMessage("SAVE: " + tool.getType().toString(), "Utils.class (222)");
				}
			} catch (NullPointerException ex) {
				Utils.broadcastMessage("BROKE", "Utils.class (225)");
			}
		} catch (IOException ex) {
		} finally {
			try {
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		try {
			out = new BufferedWriter(new FileWriter(modelsFile, true));
			for (String model : models) {
				out.append(model + "-");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public static void initPlayer(Player player) {
		player.level = player_level;
		player.score = player_score;
		player.model = PlayerModel.valueOf(player_model);
		for (String i : player_inv) {
			try {
				player.inventory.add(ToolType.getNewTool(ToolType.valueOf(i)));
			} catch (IllegalArgumentException ex) {
			}
		}

	}

	public static void runInstall() {
		root = "C://Upsilon/";
		rootFile = new File(root);
		if (!rootFile.exists())
			rootFile.mkdirs();
		config = new File(root + "/config.txt");
		modelsFile = new File(root + "/models.txt");
		inventoryFile = new File(root + "/inventory.txt");

		if (!config.getParentFile().mkdirs()) {
			Utils.broadcastMessage("Coundn't make config", "Utils.class (280)");
		}
		if (!modelsFile.getParentFile().mkdirs()) {
			Utils.broadcastMessage("Coundn't make models", "Utils.class (283)");
		}
		if (!inventoryFile.getParentFile().mkdirs()) {
			Utils.broadcastMessage("Coundn't make inv", "Utils.class (286)");
		}

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(config, true));
			out.append("level:" + 1 + "-");
			out.append("score:" + 0 + "-");
			out.append("model:YELLOW");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		BufferedWriter out1 = null;

		try {
			out1 = new BufferedWriter(new FileWriter(modelsFile, true));
			out1.append("YELLOW");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				out1.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		BufferedWriter invout = null;

		try {
			invout = new BufferedWriter(new FileWriter(inventoryFile, true));
			invout.append("empty");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				invout.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public static void checkPlayerInfo(Player player) {
		player.level = player_level;
		player.score = player_score;
		if (!player.model.toString().toLowerCase().equals(player_model.toLowerCase()))
			player.setPlayerModel(PlayerModel.valueOf(player_model));
	}

	public static void setPlayerModel(PlayerModel model) {
		player_model = model.toString();
	}

	public static String getPlayerModel() {
		return "YELLOW";
	}

	public static boolean playerHasModel(PlayerModel model) {
		if (models.contains(model.toString()))
			return true;
		else
			return false;
	}

	public static void buyModel(PlayerModel model) {
		if (Bridge.getGame().getBoard().player.score >= PlayerModel.valueOf(model.toString().toUpperCase()).getPrice()) {
			models.add(model.toString().toUpperCase());
			player_score = player_score - model.getPrice();
			Bridge.getGame().getBoard().player.score = player_score;
			Utils.setPlayerModel(model);
			JOptionPane.showMessageDialog(null, "You have bought the " + model.toString() + " player model!", "Title",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
					"You don't have enough money for that. You need $" + (model.getPrice()) + " more.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static Sprite getSpriteAtLocation(int x, int y) {
		Rectangle point = new Rectangle(x, y, 5, 5);
		Bridge.getGame().getBoard().drawRectangle(point);
		for (Sprite sprite : Bridge.getGame().getBoard().getLevel(player_level)) {
			if (point.intersects(sprite.getPolygon().getBounds())) {
				point = null;
				return sprite;
			}
		}
		point = null;
		return null;
	}

	public static void playSound(Sound sound) {
		Audio.playSound(sound);
		
	}
	
	public static boolean intersects(Shape shape1, Shape shape2){
		Area a1 = new Area(shape1);
		Area a2 = new Area(shape2);
		a1.intersect(a2);
		return !a1.isEmpty();
	}

}
