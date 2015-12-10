package me.cameronwitcher.upsilon.boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.sprites.FallingFloor;
import me.cameronwitcher.upsilon.sprites.Floor;
import me.cameronwitcher.upsilon.sprites.Gate;
import me.cameronwitcher.upsilon.sprites.Knobber;
import me.cameronwitcher.upsilon.sprites.Ladder;
import me.cameronwitcher.upsilon.sprites.Player;
import me.cameronwitcher.upsilon.sprites.Spike;
import me.cameronwitcher.upsilon.sprites.Wall;
import me.cameronwitcher.upsilon.spriteutils.Clickable;
import me.cameronwitcher.upsilon.spriteutils.Keyable;
import me.cameronwitcher.upsilon.spriteutils.Moveable;
import me.cameronwitcher.upsilon.spriteutils.Sprite;
import me.cameronwitcher.upsilon.spriteutils.SpriteType;
import me.cameronwitcher.upsilon.spriteutils.State;
import me.cameronwitcher.upsilon.spriteutils.tools.Bow;
import me.cameronwitcher.upsilon.spriteutils.tools.NinjaCloak;
import me.cameronwitcher.upsilon.spriteutils.tools.Tool;
import me.cameronwitcher.upsilon.utils.Button;
import me.cameronwitcher.upsilon.utils.ButtonMethod;
import me.cameronwitcher.upsilon.utils.Utils;
import res.Texture;

public class GameBoard extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public Timer timer;
    public Player player;
    public boolean debug;
    public boolean won;
    public boolean loaded = false;
    public boolean ready = false;
    public boolean painting = false;
    public boolean ingame;
    public boolean inv = false;
    public String gameStatus;
    public final int B_WIDTH = 640;
    public final int B_HEIGHT = 640;
    public final int DELAY = 15;
    private boolean hitboxes = false;
    public JButton btn1;
	public JButton btn2;
	public double i = 0.9;
	public int l = 0;
	
	
	
	public boolean paused = false;
	ArrayList<Rectangle> recs= new ArrayList<>();
	public Map<Integer, String> messages = new HashMap<>();
	public Map<Integer, String> messages_player = new HashMap<>();	
	public HashMap<Integer, Sprite> sprites = new HashMap<>();
	public List<Clickable> clickables = new ArrayList<>();
	public List<Player> players = new ArrayList<>();
	public List<Moveable> moveables = new ArrayList<>();
	
	public List<Moveable> moveables_temp = new ArrayList<>();
	public List<Sprite> removedSprites = new ArrayList<>();
	public List<String> strings_temp = new ArrayList<>();
	public List<String> strings_temp_player = new ArrayList<>();
	public List<Sprite> tools = new ArrayList<>();
    ArrayList<Sprite> level1 = new ArrayList<>();
    ArrayList<Sprite> level2 = new ArrayList<>();
    ArrayList<Sprite> level3 = new ArrayList<>();
    ArrayList<Sprite> level4 = new ArrayList<>();
    ArrayList<Sprite> level5 = new ArrayList<>();
    ArrayList<Sprite> level6 = new ArrayList<>();
    private HashMap<Integer, ArrayList<Sprite>> levels = new HashMap<>();

    public GameBoard(int level) {
    	
    	messages.put(0, "1:1:1:1:#333333:1");
    	initBoard(level);
    	Bridge.getGame().board = this;
    }

    private void initBoard(int level) {
    	
    	
    	Utils.savePlayerInfo(player);
    	Utils.player_level = level;
    	
    	ingame = true;
    	
    	gameStatus = "ingame";
    	
    	player = new Player(0,0,0);

        addKeyListener(new TAdapter());
        addMouseMotionListener(new MMListener());
        addMouseListener(new MListener());
        setFocusable(true);
        setBackground(Color.RED);
        
        loadLevels();
        
        
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

       
        

        timer = new Timer(DELAY, this);
        timer.start();
        
    
        won = false;
        
        update();
        loadLevel();
        
        
        
        	
        
    }
    
    public void loadLevels(){
    	levels.clear();
    	level1.add(new Floor(0,608));
    	level1.add(new Floor(32,608));
    	level1.add(new Floor(2*32,608));
    	level1.add(new Floor(3*32,608));
    	level1.add(new Floor(4*32,608));
    	level1.add(new Floor(5*32,608));
    	level1.add(new Floor(6*32,608));
    	level1.add(new Floor(7*32,608));
    	level1.add(new Floor(8*32,608));
    	
    	
    	
    	
    	
    	level1.add(new Floor(9*32,608));
    	level1.add(new Floor(10*32,608));
    	level1.add(new Floor(11*32,608));
    	


    	level1.add(new Spike(6*32,598));
    	level1.add(new Spike(7*32,598));
    	level1.add(new Spike(8*32,598));
    	level1.add(new Spike(9*32,598));
    	level1.add(new Spike(10*32,598));
    	level1.add(new Spike(11*32,598));
    	
    	
    	level1.add(new Floor(12*32,608));
    	level1.add(new Floor(13*32,608));
    	level1.add(new Floor(14*32,608));
    	level1.add(new Floor(15*32,608));
    	level1.add(new Floor(16*32,608));
    	level1.add(new Floor(17*32,608));
    	level1.add(new Ladder(17*32,18*32));
    	level1.add(new Ladder(17*32,17*32));
    	level1.add(new Ladder(17*32,16*32));
    	level1.add(new Ladder(17*32,15*32));
    	level1.add(new Floor(16*32,16*32));
    	level1.add(new Floor(15*32,16*32));
    	level1.add(new Floor(14*32,16*32));
    	level1.add(new Floor(13*32,16*32));
    	level1.add(new Floor(12*32,16*32));
    	level1.add(new Floor(11*32,16*32));
    	level1.add(new Floor(10*32,16*32));
    	level1.add(new Floor(9*32,16*32));
    	level1.add(new Floor(8*32,16*32));
    	level1.add(new Floor(7*32,16*32));
    	level1.add(new Floor(6*32,16*32));
    	level1.add(new Floor(5*32,16*32));
    	level1.add(new Floor(4*32,16*32));
    	level1.add(new Floor(3*32,16*32));
    	level1.add(new Ladder(3*32,15*32));
    	level1.add(new Ladder(3*32,14*32));
    	level1.add(new Ladder(3*32,13*32));
    	level1.add(new Ladder(3*32,12*32));
    	level1.add(new Ladder(3*32,11*32));
    	level1.add(new Ladder(3*32,10*32));
    	level1.add(new Floor(4*32,11*32));
    	level1.add(new Floor(5*32,11*32));
    	level1.add(new Floor(6*32,11*32));
    	level1.add(new Floor(7*32,11*32));
    	level1.add(new Floor(8*32,11*32));
    	level1.add(new Wall(8*32,10*32,State.VERTICAL));
    	level1.add(new Floor(9*32,11*32));
    	level1.add(new Floor(10*32,11*32));
    	level1.add(new Floor(11*32,11*32));
    	level1.add(new Floor(12*32,11*32));
    	level1.add(new Gate(12*32,10*32));
    	level1.add(new Bow(9*32,14*32));
    	level1.add(new NinjaCloak(10*32,14*32));
    	level1.add(player);
    	
    	
    	levels.put(1, level1);
        

    	level2.add(new Floor(0,14*32));
    	level2.add(new Floor(1*32,14*32));
    	level2.add(new Floor(2*32,14*32));
    	level2.add(new Floor(3*32,14*32));
    	level2.add(new Floor(4*32,14*32));
    	level2.add(new Floor(5*32,14*32));
    	level2.add(new Floor(6*32,14*32));
    	level2.add(new Floor(7*32,14*32));
    	level2.add(new Floor(8*32,13*32));
    	level2.add(new Floor(9*32,12*32));
    	level2.add(new Floor(10*32,11*32));
    	level2.add(new Floor(11*32,10*32));
    	level2.add(new Floor(12*32,9*32));
    	level2.add(new Gate(12*32,8*32));
    	level2.add(player);
    	
    	levels.put(2, level2);
    	
    	
        
    	level3.add(new FallingFloor(0,8*32));
    	level3.add(new FallingFloor(1*32,8*32));
    	level3.add(new FallingFloor(2*32,8*32));
    	level3.add(new FallingFloor(3*32,8*32));
    	level3.add(new FallingFloor(4*32,8*32));
    	level3.add(new FallingFloor(5*32,8*32));
    	level3.add(new FallingFloor(6*32,8*32));
    	level3.add(new FallingFloor(7*32,8*32));
    	level3.add(new FallingFloor(8*32,9*32));
    	level3.add(new FallingFloor(9*32,10*32));
    	level3.add(new FallingFloor(10*32,11*32));
    	level3.add(new FallingFloor(11*32,12*32));
    	level3.add(new FallingFloor(12*32,13*32));
    	level3.add(new Gate(12*32,12*32));
    	level3.add(player);
    	
    	levels.put(3, level3);
    	
    	level4.add(new Floor(0*32,608));
    	level4.add(new Floor(1*32,608));
    	level4.add(new Floor(2*32,608));
    	level4.add(new Floor(3*32,608));
    	level4.add(new Floor(4*32,608));
    	level4.add(new Floor(5*32,608));
    	level4.add(new Floor(6*32,608));
    	level4.add(new Floor(7*32,608));
    	level4.add(new Floor(8*32,608));
    	level4.add(new Floor(9*32,608));
    	level4.add(new Floor(10*32,608));
    	level4.add(new Floor(11*32,608));
    	level4.add(new Floor(12*32,608));
    	level4.add(new Floor(13*32,608));
    	level4.add(new Floor(14*32,608));
    	level4.add(new Floor(15*32,608));
    	level4.add(new Floor(16*32,608));
    	level4.add(new Floor(17*32,608));
    	level4.add(new Ladder(17*32,18*32));
    	level4.add(new Ladder(17*32,17*32));
    	level4.add(new Ladder(17*32,16*32));
    	level4.add(new Ladder(17*32,15*32));
    	level4.add(new Floor(16*32,16*32));
    	level4.add(new Floor(15*32,16*32));
    	level4.add(new Floor(14*32,16*32));
    	level4.add(new Floor(13*32,16*32));
    	level4.add(new Floor(12*32,16*32));
    	level4.add(new Floor(11*32,16*32));
    	level4.add(new Floor(10*32,16*32));
    	level4.add(new Floor(9*32,16*32));
    	level4.add(new Floor(8*32,16*32));
    	level4.add(new Floor(7*32,16*32));
    	level4.add(new Floor(6*32,16*32));
    	level4.add(new Floor(5*32,16*32));
    	level4.add(new Floor(4*32,16*32));
    	level4.add(new Floor(3*32,16*32));
    	level4.add(new Wall(2*32,0*32,State.VERTICAL));
    	level4.add(new Wall(2*32,1*32,State.VERTICAL));
    	level4.add(new Wall(2*32,2*32,State.VERTICAL));
    	level4.add(new Wall(2*32,3*32,State.VERTICAL));
    	level4.add(new Wall(2*32,4*32,State.VERTICAL));
    	level4.add(new Wall(2*32,5*32,State.VERTICAL));
    	level4.add(new Wall(2*32,6*32,State.VERTICAL));
    	level4.add(new Wall(2*32,7*32,State.VERTICAL));
    	level4.add(new Wall(2*32,8*32,State.VERTICAL));
    	level4.add(new Wall(2*32,9*32,State.VERTICAL));
    	level4.add(new Wall(2*32,15*32,State.VERTICAL));
    	level4.add(new Wall(2*32,14*32,State.VERTICAL));
    	level4.add(new Wall(2*32,13*32,State.VERTICAL));
    	level4.add(new Wall(2*32,12*32,State.VERTICAL));
    	level4.add(new Wall(2*32,11*32,State.VERTICAL));
    	level4.add(new Wall(2*32,10*32,State.VERTICAL));
    	
    	level4.add(new Ladder(3*32,15*32));
    	level4.add(new Ladder(3*32,14*32));
    	level4.add(new Ladder(3*32,13*32));
    	level4.add(new Ladder(3*32,12*32));
    	level4.add(new Ladder(3*32,11*32));
    	level4.add(new Ladder(3*32,10*32));
    	level4.add(new Floor(4*32,11*32));
    	level4.add(new Floor(5*32,11*32));
    	level4.add(new Floor(6*32,11*32));
    	level4.add(new Floor(7*32,11*32));
    	level4.add(new Floor(8*32,11*32));
    	level4.add(new Floor(9*32,11*32));
    	level4.add(new Floor(10*32,11*32));
    	level4.add(new Floor(11*32,11*32));
    	level4.add(new Floor(12*32,11*32));
    	level4.add(new Gate(12*32,10*32));
    	level4.add(new Knobber(10*32,14*32));
    	level4.add(player);
    	
    	
    	
    	
    	levels.put(4, level4);
    	
    	level5.add(new Floor(0*32,4*32));
    	level5.add(new Floor(1*32,4*32));
    	level5.add(new Floor(2*32,4*32));
    	level5.add(new Floor(3*32,4*32));
    	level5.add(new Floor(4*32,4*32));
    	level5.add(new Floor(5*32,4*32));
    	level5.add(new Floor(6*32,4*32));
    	level5.add(new Floor(7*32,4*32));
    	level5.add(new Floor(8*32,4*32));
    	level5.add(new Floor(9*32,4*32));
    	level5.add(new Floor(10*32,4*32));
    	level5.add(new Floor(11*32,4*32));
    	level5.add(new Floor(12*32,4*32));
    	level5.add(new Floor(13*32,4*32));
    	level5.add(new Floor(14*32,4*32));
    	level5.add(new Floor(15*32,4*32));
    	level5.add(new Floor(16*32,4*32));
    	level5.add(new Floor(17*32,4*32));
    	level5.add(new Floor(18*32,4*32));
    	level5.add(new FallingFloor(19*32,4*32));
    	
    	level5.add(new FallingFloor(0*32,8*32));
    	level5.add(new Floor(1*32,8*32));
    	level5.add(new Floor(2*32,8*32));
    	level5.add(new Floor(3*32,8*32));
    	level5.add(new Floor(4*32,8*32));
    	level5.add(new Floor(5*32,8*32));
    	level5.add(new Floor(6*32,8*32));
    	level5.add(new Floor(7*32,8*32));
    	level5.add(new Floor(8*32,8*32));
    	level5.add(new Floor(9*32,8*32));
    	level5.add(new Floor(10*32,8*32));
    	level5.add(new Floor(11*32,8*32));
    	level5.add(new Floor(12*32,8*32));
    	level5.add(new Floor(13*32,8*32));
    	level5.add(new Floor(14*32,8*32));
    	level5.add(new Floor(15*32,8*32));
    	level5.add(new Floor(16*32,8*32));
    	level5.add(new Floor(17*32,8*32));
    	level5.add(new Floor(18*32,8*32));
    	level5.add(new Floor(19*32,8*32));
    	
    	
    	
    	
    	level5.add(new Floor(0*32,12*32));
    	level5.add(new Floor(1*32,12*32));
    	level5.add(new Floor(2*32,12*32));
    	level5.add(new Floor(3*32,12*32));
    	level5.add(new Floor(4*32,12*32));
    	level5.add(new Floor(5*32,12*32));
    	level5.add(new Floor(6*32,12*32));
    	level5.add(new Floor(7*32,12*32));
    	level5.add(new Floor(8*32,12*32));
    	level5.add(new Floor(9*32,12*32));
    	level5.add(new Floor(10*32,12*32));
    	level5.add(new Floor(11*32,12*32));
    	level5.add(new Floor(12*32,12*32));
    	level5.add(new Floor(13*32,12*32));
    	level5.add(new Floor(14*32,12*32));
    	level5.add(new Floor(15*32,12*32));
    	level5.add(new Floor(16*32,12*32));
    	level5.add(new Floor(17*32,12*32));
    	level5.add(new Floor(18*32,12*32));
    	level5.add(new FallingFloor(19*32,12*32));
    	
    	level5.add(new Floor(0*32,16*32));
    	level5.add(new Floor(1*32,16*32));
    	level5.add(new Floor(2*32,16*32));
    	level5.add(new Floor(3*32,16*32));
    	level5.add(new Floor(4*32,16*32));
    	level5.add(new Floor(5*32,16*32));
    	level5.add(new Floor(6*32,16*32));
    	level5.add(new Floor(7*32,16*32));
    	level5.add(new Floor(8*32,16*32));
    	level5.add(new Floor(9*32,16*32));
    	level5.add(new Floor(10*32,16*32));
    	level5.add(new Floor(11*32,16*32));
    	level5.add(new Floor(12*32,16*32));
    	level5.add(new Floor(13*32,16*32));
    	level5.add(new Floor(14*32,16*32));
    	level5.add(new Floor(15*32,16*32));
    	level5.add(new Floor(16*32,16*32));
    	level5.add(new Floor(17*32,16*32));
    	level5.add(new Floor(18*32,16*32));
    	level5.add(new Floor(19*32,16*32));
    	level5.add(new Gate(1,15*32));
    	level5.add(player);
    	
    	levels.put(5, level5);
    	
    	
    	level6.add(new Wall(0*32, 2*32, State.HORIZONTAL));
    	level6.add(new Wall(1*32, 2*32, State.HORIZONTAL));
    	level6.add(new Wall(2*32, 2*32, State.HORIZONTAL));
    	level6.add(new Wall(3*32, 2*32, State.HORIZONTAL));
    	level6.add(new Wall(4*32, 2*32, State.HORIZONTAL));
    	
    	for(int i=4;i!=17;i++){
    		level6.add(new Wall(i*32, (i*32)/2, State.HORIZONTAL));
    	}
    	level6.add(new Ladder(17*32, 8*32));
    	level6.add(new Ladder(17*32, 9*32));
    	level6.add(new Ladder(17*32, 10*32));
    	
    	for(int i=0;i!=18;i++){
    		level6.add(new Floor(i*32, 11*32));
    	}
    	
    	level6.add(player);
    	
    	
    	levels.put(6, level6);
    	
    	
        
    	
    	
    }
    
    public ArrayList<Sprite> getLevel(int level){
    	
		return levels.get(level);
    }

    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        if(inv){
        	drawInventory(g);
        	return;
        }
            
            if(won){
            	drawWin(g);
            	
            	return;
            }

            if (ingame) {
            	if(ready) drawObjects(g);

            } else {

                drawGameOver(g);
            }

            Toolkit.getDefaultToolkit().sync();
        
    	
    	
    }
    
  
    
    public void loadLevel(){
    	if(Utils.player_level > levels.size()) {
    		won = true;
    		return;
    	}
    	sprites.clear();
    	moveables.clear();
    	players.clear();
    	tools.clear();
    	clickables.clear();
    	try{
    		for(Sprite sprite : getLevel(Utils.player_level)){
        		sprites.put(sprite.getID(), sprite);
        		
        		if(sprite instanceof Moveable){
        			sprites.remove(sprite);
        			if(!(sprite instanceof Player)) moveables.add((Moveable) sprite);
        		}
        		
        		if(sprite instanceof Player){
        			sprites.remove(player);
        			players.add((Player) sprite);
        			
        		}
        		if(sprite instanceof Tool){
        			sprites.remove(sprite);
        			tools.add(sprite);
        		}
        		
        	}
    	} catch(NullPointerException ex){
    		for(Sprite sprite : getLevel(1)){
        		sprites.put(sprite.getID(), sprite);
        		
        		if(sprite instanceof Moveable){
        			sprites.remove(sprite);
        			if(!(sprite instanceof Player)) moveables.add((Moveable) sprite);
        		}
        		
        		if(sprite instanceof Player){
        			sprites.remove(player);
        			players.add((Player) sprite);
        			
        		}
        		if(sprite instanceof Tool){
        			sprites.remove(sprite);
        			tools.add(sprite);
        		}
        		
        	}
    	}
    	
    	ingame = true;

    	loaded = true;
    	
    }
    
    void loadHelp() {
    	
    	paused = true;
    	Utils.displayMessage(1, "Welcome to Upsilon. Here is some help for you.", B_WIDTH/2, 40, -1, "#FFFFFF", 25);
    	Utils.displayMessage(13, "(Press ESC to play)", B_WIDTH/2, 100, -1, "#FFFFFF", 20);
    	
    	Utils.displayMessage(2, "These are coins. Pick them up to gain points!", 272, 578, -1, "#FFFFFF", 15);
    	
    	Utils.displayMessage(3, "This is a bow. If you pick it up, it'll appear in your \"tool\" slot.", 9*32, 14*32, -1, "#FFFFFF", 15);
    	
    	Utils.displayMessage(4, "<--- This is your HUD --->", B_WIDTH/2,15, -1, "#FFFFFF", 10);
    	
    	Utils.displayMessage(5, "Controls", B_WIDTH/2, (B_HEIGHT/2)-30, -1, "#FFFFFF", 10);
    	Utils.displayMessage(6, "Left-> \"A\" or the left arrow", B_WIDTH/2, (B_HEIGHT/2)-20, -1, "#FFFFFF", 10);
    	Utils.displayMessage(7, "Right -> \"D\" or the right arrow", B_WIDTH/2, (B_HEIGHT/2)-10, -1, "#FFFFFF", 10);
    	Utils.displayMessage(8, "Up -> \"W\" or the up arrow", B_WIDTH/2, B_HEIGHT/2, -1, "#FFFFFF", 10);
    	Utils.displayMessage(9, "Down -> \"S\" or the down arrow", B_WIDTH/2, (B_HEIGHT/2)+10, -1, "#FFFFFF", 10);
    	Utils.displayMessage(10, "Jump -> SPACE", B_WIDTH/2, (B_HEIGHT/2)+20, -1, "#FFFFFF", 10);
    	Utils.displayMessage(11, "Use tool -> SHIFT", B_WIDTH/2, (B_HEIGHT/2)+30, -1, "#FFFFFF", 10);
    	Utils.displayMessage(12, "This is your health ->", getFontMetrics(getFont()).stringWidth("This is your health ->"), 70, -1, "#FFFFFF", 10);
    	
    	
    	
    	
    }
    
    private void drawInventory(Graphics g){
    	try{
    		g.drawImage(Texture.loadTexture("/res/background-win.png"), 0, 0, null);
    	
    	
    
    		String inv = "Inventory";

    		String close = "Close";

    		String select = "Select";

    		Font small = new Font("Helvetica", Font.BOLD, 14);

    		Font large = new Font("Helvetica", Font.BOLD, 25);

    		FontMetrics fmlarge = getFontMetrics(large);
        

    		g.setColor(Color.WHITE);

    		g.setFont(large);

    		g.drawString(inv, (B_WIDTH/2) - (fmlarge.stringWidth(inv)/2), 30);
        

    		g.drawImage(player.inventory.get(l).getImage(), B_WIDTH/2, B_HEIGHT/2, this);
        
    
    		clickables.add(new Button(close, B_WIDTH/4, (B_HEIGHT/2 + B_HEIGHT)/2, B_WIDTH/6, 18, Color.GRAY, Color.WHITE, small, ButtonMethod.CLOSE_INVENTORY));

    		clickables.add(new Button(select, (B_WIDTH/2 + B_WIDTH)/2, (B_HEIGHT/2 + B_HEIGHT)/2, B_WIDTH/6, 18, Color.GRAY, Color.WHITE, small, ButtonMethod.SELECT_TOOL));
    		
    		
    		for(Clickable clickable : clickables){
        		g.drawPolygon(clickable.drawPolygon(g));
        		if(clickable instanceof Button){
        			Button button = (Button) clickable;
        			g.setColor((button).getForeground());
        			g.setFont(button.getFont());
        			g.drawString((button).getString(), button.x - (getFontMetrics(button.getFont()).stringWidth(button.getString())/2), button.y + (getFontMetrics(button.getFont()).getHeight()/4));
        		}
        	}
    		
    		
    	}catch(IndexOutOfBoundsException ex){
    		inv = false;
    		Utils.addPlayerMessage(new Random().nextInt(), "Your inventory is empty", 0, 15, 10, "#FFFFFF", 10);
    	}
         
        
    }

	private void drawWin(Graphics g){
    	
    	g.drawImage(Texture.loadTexture("/res/background-win.png"), 0, 0, null);
    	
        String won = "You won!";
        String restart = "Restart?";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        Font large = new Font("Helvetica", Font.BOLD, 25);
        FontMetrics fmsmall = getFontMetrics(small);
        FontMetrics fmlarge = getFontMetrics(large);

        g.setColor(Color.white);
        g.setFont(large);
        g.drawString(won, (B_WIDTH - fmlarge.stringWidth(won)) / 2, B_HEIGHT / 3);
        g.setColor(Color.red);
        g.setFont(small);
        
        g.drawString(restart, (B_WIDTH - fmsmall.stringWidth(restart)) / 2, B_HEIGHT / 2);
        
    
    	
    	
    	
    }

    private void drawObjects(Graphics g) {
    	
    	g.drawImage(Texture.loadTexture("/res/background.png"), 0, 0, this);
    	g.setColor(Color.black);
    	g.setFont(new Font("Helvetica", Font.BOLD, 10));
    	g.drawString("Lives: " + player.lives, B_WIDTH/4, 20);
    	g.drawString("Score: " + player.score, (B_WIDTH/2 + B_WIDTH)/2, 10);
    	g.drawString("Tool:", (B_WIDTH/2 + B_WIDTH)/2, 20);
    	for(Sprite sprite : getLevel(player.level)){
    		
    		if(!(sprite instanceof Player) && !(sprite instanceof Knobber)) g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
    		if(debug && hitboxes)
    			g.drawRect((int)sprite.getPolygon().getBounds().getX(),(int) sprite.getPolygon().getBounds().getY(), (int) sprite.getPolygon().getBounds().width, (int) sprite.getPolygon().getBounds().height);	
    	}
    	
    
    	
		for(Moveable s : moveables){
    		Sprite sprite = (Sprite) s;
    		SpriteType type = (sprite).getType();
    		switch(type){
    		case KNOBBER:
    			Knobber sk = (Knobber) s;
    			g.drawImage(sprite.getImage(), (sprite.getX()), sprite.getY(), 29, 41, this);
    			((Knobber) s).drawHealthBar(g, sk.x-(50/2), sk.y-20, 50, 5);
    			if(!((Knobber) sprite).hasTool()) ((Knobber) sprite).setTool(new Bow(0,0));
    			break;
    		case ARROW:
    			g.drawImage(sprite.getImage(), (sprite.getX()), sprite.getY(), 16, 4, this);
    			break;
    			default:
    				g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
    				break;
    		}
    		
    		if(debug && hitboxes)
    			g.drawRect((int)sprite.getPolygon().getBounds().getX(),(int) sprite.getPolygon().getBounds().getY(), (int) sprite.getPolygon().getBounds().width, (int) sprite.getPolygon().getBounds().height);	
    	}
		
		for(Player s : players){
    		Sprite sprite = (Sprite) s;
    		s.drawHealthBar(g, (B_WIDTH/2)-((B_WIDTH/2)/2), 60, B_WIDTH/2, 20);
    		if(!player.walking){
    			g.drawImage((sprite).getImage(), (sprite.getX()), sprite.getY(), 13, 41, this);	
    		} else {
    			g.drawImage((sprite).getImage(), (sprite.getX()), sprite.getY(), 29, 41, this);
    		}
    		if(((Player) sprite).hasTool()){
    			Tool tool = ((Player) sprite).getTool();
    			g.drawImage(tool.getImage(), ((B_WIDTH/2 + B_WIDTH)/2)+getFontMetrics(new Font("Helvetica", Font.BOLD, 10)).stringWidth("Tool: "), 10, 15, 15, this);
    		}
    		if(debug && hitboxes)
    			g.drawRect((int)sprite.getPolygon().getBounds().getX(),(int) sprite.getPolygon().getBounds().getY(), (int) sprite.getPolygon().getBounds().width, (int) sprite.getPolygon().getBounds().height);
		}
    		
    	
    	for(Sprite sprite : tools){
    		g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
    	}
    	
    	if(debug){
    		g.setColor(Color.black);
    		g.setFont(new Font("Helvetica", Font.BOLD, 10));
    		g.drawString("Gravity: " + player.gravity, 0, 10);
    		g.drawString("Falling: " + player.falling, 0, 20);
        	g.drawString("Onground: " + player.onground, 0, 30);
        	g.drawString("Jumping: " + player.jumping, 0, 40);
        	g.drawString("Speed: " + player.speedboost, 0, 50);
        	g.drawString("Location: " + player.x + ":" + player.y, 0, 60);
        	g.drawString("Left: " + player.left, 0, 70);
        	g.drawString("Right: " + player.right, 0, 80);
        	g.drawString("Up: " + player.up, 0, 90);
        	g.drawString("Climbing: " + player.climbing, 0, 100);
        	g.drawString("Level: " + player.level, 0, 110);	
    	}
    	
    	for(Rectangle rec : recs){
    		g.drawRect(rec.x, rec.y, rec.width, rec.height);
    	}
    	
    	if(paused){
    		g.drawImage(Texture.loadTexture("/res/darken.png"), 0, 0, null);
    	}
    	
    	
    	for(Entry<Integer, String> entry : messages.entrySet()){
    		String[] info = entry.getValue().split(":");
    		int time = Integer.parseInt(info[3]);
    		if(time!=-1){
    			g.setColor(Color.decode(info[4]));
        		g.setFont(new Font("Helvetica", Font.BOLD, Integer.parseInt(info[5])));
        		if(info[0].contains("//n")){
        			String[] m = info[0].split("//n");
        			for(int i=0; i!=m.length;i++){
        				g.drawString(m[i], Integer.parseInt(info[1]), Integer.parseInt(info[2])+(i*Integer.parseInt(info[5])));
        			}
        		} else
        		g.drawString(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        		if(time!=0)strings_temp.add(entry.getKey() + "~" + info[0] + ":" + info[1] + ":" + info[2] + ":" + (time-1) + ":" + info[4] + ":" + info[5]);
        		
    		}
    		if(time==-1){
    			g.setColor(Color.decode(info[4]));
        		g.setFont(new Font("Helvetica", Font.BOLD, Integer.parseInt(info[5])));
        		if(info[0].contains("//n")){
        			String[] m = info[0].split("//n");
        			for(int i=0; i!=m.length;i++){
        				g.drawString(m[i], Integer.parseInt(info[1])*2, Integer.parseInt(info[2])+(i*Integer.parseInt(info[5])));
        			}
        		} else
        		g.drawString(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        		strings_temp.add(entry.getKey() + "~" +  info[0] + ":" + info[1] + ":" + info[2] + ":" + time + ":" + info[4] + ":" + info[5]);
    		}
    	}
    	messages.clear();
    	for(String string : strings_temp){
    		String[] info = string.split("~");
    		
    		messages.put(Integer.parseInt(info[0]), info[1]);
    		
    	}
    	strings_temp.clear();
    	
    	for(Entry<Integer, String> entry : messages_player.entrySet()){
    		String[] info = entry.getValue().split(":");
    		int time = Integer.parseInt(info[3]);
    		if(time!=-1){
    			g.setColor(Color.decode(info[4]));
        		g.setFont(new Font("Helvetica", Font.BOLD, Integer.parseInt(info[5])));
        		g.drawString(info[0], player.x + Integer.parseInt(info[1]), player.y + Integer.parseInt(info[2]));
        		if(entry.getKey().equals(2))
        			if(time!=0)strings_temp_player.add(entry.getKey() + "~" + info[0] + ":" + info[1] + ":" + info[2] + ":" + (time-1) + ":" + info[4] + ":" + info[5]);
        		
    		}
    		if(time==-1){
    			g.setColor(Color.decode(info[1]));
        		g.setFont(new Font("Helvetica", Font.BOLD, Integer.parseInt(info[5])));
        		g.drawString(info[0], Integer.parseInt(info[2]), Integer.parseInt(info[3]));
        		if(entry.getKey().equals(2))
        			strings_temp_player.add(entry.getKey() + "~" +  info[0] + ":" + info[1] + ":" + info[2] + ":" + (time) + ":" + info[4] + ":" + info[4]);
    		}
    	}
    	messages_player.clear();
    	for(String string : strings_temp_player){
    		String[] info = string.split("~");
    		messages_player.put(Integer.parseInt(info[0]), info[1]);
    		
    	}
    	strings_temp_player.clear();
    	
    	for(Clickable clickable : clickables){
    		g.drawPolygon(clickable.drawPolygon(g));
    		if(clickable instanceof Button){
    			Button button = (Button) clickable;
    			g.setColor((button).getForeground());
    			g.setFont(button.getFont());
    			g.drawString((button).getString(), button.x - (getFontMetrics(button.getFont()).stringWidth(button.getString())/2), button.y + (getFontMetrics(button.getFont()).getHeight()/4));
    		}
    	}
    	
    }
    
    

	private void drawGameOver(Graphics g) {
    	
    	
    	if(gameStatus.contains("gameover")){
    		
    		g.drawImage(Texture.loadTexture("/res/background.png"), 0, 0, null);
        	
        	

    		String gameover = "Game Over";
    		String reason = gameStatus.split(":")[1];
    		reason = reason.replaceAll("_", " ");
    		
            Button restart = new Button("Restart", B_WIDTH/2, B_HEIGHT/2, B_WIDTH/6, B_HEIGHT/10, Color.decode("#44cc44"), Color.white, new Font("Helvetica", Font.PLAIN, 15), ButtonMethod.RESTART);
            g.drawPolygon(restart.drawPolygon(g));
            g.setColor(restart.getForeground());
            g.setFont(restart.getFont());
            g.drawString(restart.getString(), restart.x - (getFontMetrics(restart.getFont()).stringWidth(restart.getString())/2), restart.y + (getFontMetrics(restart.getFont()).getHeight()/4));
            clickables.add(restart);
            
            Font small = new Font("Helvetica", Font.BOLD, 14);
            Font large = new Font("Helvetica", Font.BOLD, 20);
            FontMetrics fm = getFontMetrics(small);
            FontMetrics fml = getFontMetrics(large);

            g.setColor(Color.black);
            g.setFont(large);
            g.drawString(gameover, (B_WIDTH - fml.stringWidth(gameover)) / 2, B_HEIGHT / 3);
            g.setFont(small);
            g.drawString(reason, (B_WIDTH - fm.stringWidth(reason)) / 2, B_HEIGHT / 3+20);
            
        }
    	if(gameStatus.contains("won")){
    		g.drawImage(Texture.loadTexture("/res/background.png"), 0, 0, null);
    		g.setColor(Color.black);
    		String won = "You won level " + gameStatus.split(":")[1] +"!";
            
            Button levelup = new Button("Next Level", B_WIDTH/2, B_HEIGHT/2, B_WIDTH/6, B_HEIGHT/10, Color.decode("#44cc44"), Color.white, new Font("Helvetica", Font.PLAIN, 15), ButtonMethod.LEVEL_UP);
            clickables.add(levelup);
            
            g.drawPolygon(levelup.drawPolygon(g));
            g.setColor(levelup.getForeground());
            g.setFont(levelup.getFont());
            g.drawString(levelup.getString(), levelup.x - (getFontMetrics(levelup.getFont()).stringWidth(levelup.getString())/2), levelup.y + (getFontMetrics(levelup.getFont()).getHeight()/4));
            
            
            
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics fm = getFontMetrics(small);
            g.setFont(small);
            g.drawString(won, (B_WIDTH - fm.stringWidth(won)) / 2, B_HEIGHT / 3);
            
    	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
//        if(loaded) 
    	
    	if(!paused) update();

        repaint();
        
        
    
    }

    private void update() {
    	List<Moveable> temp_ = new ArrayList<>();
    	for(Moveable sprite : moveables){
    		((Moveable) sprite).move();
    		temp_.add(sprite);	
    	}
    	moveables.clear();
    		
    	for(Moveable sprite: temp_){
    		moveables.add(sprite);
    	}
    	
    	temp_.clear();
    	
    	for(Player player : players){
    		player.move();
    		temp_.add(player);	
    	}
    	players.clear();
    	
    	for(Moveable player : temp_){
    		players.add((Player) player);
    	}
    	
    	for(Moveable sprite : moveables_temp){
    		moveables.add(sprite);
    	}
    	moveables_temp.clear();
    	
    	for(Sprite sprite : removedSprites){
    		if(moveables.contains(sprite))
    			moveables.remove(sprite);
    		if(tools.contains(sprite)) tools.remove(sprite);
    		if(getLevel(Utils.player_level).contains(sprite)){
    			ArrayList<Sprite> list = levels.get(Utils.player_level);
    			list.remove(sprite);
    			levels.put(Utils.player_level, list);
    		}
    	}
    	removedSprites.clear();
    	
    	
    	if(!ready) ready = true;
    }


    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	for(Sprite sprite : getLevel(player.level))
        		if(sprite instanceof Keyable)
        			((Keyable) sprite).keyReleased(e);
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	
        	if(e.getKeyCode() == KeyEvent.VK_LEFT){
        		if(l == 0){
        			l = player.inventory.size()-1;
        		}
        		else {
        			l = l-1;
        		}
        	}
        	if(e.getKeyCode() == KeyEvent.VK_RIGHT){
        		if(l == player.inventory.size()-1){
        			l = 0;
        		}
        		else {
        			l = l+1;
        		}
        	}
        	
        	
        	
        	for(Sprite sprite : getLevel(player.level))
        		if(sprite instanceof Keyable)
        			((Keyable) sprite).keyPressed(e);
        	
        }
	}

	private class MMListener extends MouseMotionAdapter {
		
		
		public void mouseMoved(MouseEvent e) {
			for (Clickable clickable : clickables){
				if (clickable.getPolygon().contains(e.getPoint())){
					clickable.mouseMoved(e);
				}
			}
					
		}
	}
	private class MListener extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			try{
				for (Clickable clickable : clickables)
					if (clickable.getPolygon().contains(e.getPoint())){
						clickable.mousePressed(e);
					}
			}catch(ConcurrentModificationException ex) {}
		}

		public void mouseReleased(MouseEvent e) {
			for (Clickable clickable : clickables)
				if (clickable.getPolygon().contains(e.getPoint()))
					clickable.mouseReleased(e);
		}
	}

	public void toggleGravity() {
		if (player.gravity)
			player.gravity = false;
		else player.gravity = true;
	}
	
	public void toggleHitboxes(){
		if(hitboxes) hitboxes = false;
		else hitboxes = true;
	}
	
	public String getLocation(int x, int y){
		return x + ":" + y;
	}

	public void toggleDebugMode() {
		if(debug) debug = false;
		else debug = true;
	}

	public int getLevels() {
		return levels.size();
	}

	public void start() {
		this.initBoard(1);
	}

	public void pause() {
		paused = true;
		
		clickables.add(new Button("Resume", B_WIDTH/4, B_HEIGHT/2, B_WIDTH/6, 50, Color.gray, Color.white, new Font("Helvetica", Font.BOLD, 25), ButtonMethod.RESUME));
		clickables.add(new Button("Main Menu",(B_WIDTH/2 + B_WIDTH)/2, B_HEIGHT/2, B_WIDTH/4, 50, Color.gray, Color.white, new Font("Helvetica", Font.BOLD, 25), ButtonMethod.MAIN_MENU));
		
	}

	public void resume() {
		clickables.clear();
		for(Entry<Integer, String> entry : messages.entrySet()){
			String[] split = entry.getValue().split(":");
			if(Integer.parseInt(split[3]) == -1){
				strings_temp.add(entry.getKey() + "~" + split[0] + ":" + split[1] + ":" + split[2] + ":" + "1" + ":" + split[4] + ":" + split[5]);
			}
			else
				strings_temp.add(entry.getKey() + "~" + split[0] + ":" + split[1] + ":" + split[2] + ":" + split[3] + ":" + split[4] + ":" + split[5]);
		}
		messages.clear();
		for(String string : strings_temp){
    		String[] info = string.split("~");
    		
    		messages.put(Integer.parseInt(info[0]), info[1]);
    		
    	}
		strings_temp.clear();
		
		for(Entry<Integer, String> entry : messages_player.entrySet()){
			String[] split = entry.getValue().split(":");
			if(Integer.parseInt(split[3]) == -1){
				strings_temp_player.add(entry.getKey() + "~" + split[0] + ":" + split[1] + ":" + split[2] + ":" + "1" + ":" + split[4] + ":" + split[5]);
			}
			else
				strings_temp_player.add(entry.getKey() + "~" + split[0] + ":" + split[1] + ":" + split[2] + ":" + split[3] + ":" + split[4] + ":" + split[5]);
		}
		for(String string : strings_temp_player){
    		String[] info = string.split("~");
    		
    		messages_player.put(Integer.parseInt(info[0]), info[1]);
    		
    	}
		strings_temp_player.clear();
		paused = false;
		
	}

	public void drawRectangle(Rectangle point) {
//		recs.add(point);
	}

	public void addMoveable(Moveable moveable) {
		moveables_temp.add(moveable);
	}

	public void removeSprite(Sprite sprite) {
		removedSprites.add(sprite);
	}
}