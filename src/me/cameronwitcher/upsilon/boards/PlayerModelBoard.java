package me.cameronwitcher.upsilon.boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import me.cameronwitcher.upsilon.spriteutils.Clickable;
import me.cameronwitcher.upsilon.spriteutils.PlayerModel;
import me.cameronwitcher.upsilon.utils.Button;
import me.cameronwitcher.upsilon.utils.ButtonMethod;
import me.cameronwitcher.upsilon.utils.Utils;
import res.Texture;

public class PlayerModelBoard extends JPanel implements ActionListener {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int P_WIDTH = 960;
	public int P_HEIGHT = 640;
	private static String model = "YELLOW";
	private static Timer timer;
	private List<Clickable> clickables = new ArrayList<>();
	
	Game game;
	public PlayerModelBoard(Game game){
		this.game = game;
		init();
	}
	
	public JPanel init(){
		setLayout(null);
		
		
		
		
		
		Font font = new Font("Helvetica", Font.BOLD, 25);
		
		clickables.add(new Button("<", P_WIDTH/4, P_HEIGHT/2, getFontMetrics(font).stringWidth("<")+20,  getFontMetrics(font).stringWidth("<")+20, Color.gray, Color.white, font, ButtonMethod.CYCLE_LEFT));
		
		clickables.add(new Button(">",  ((P_WIDTH + P_WIDTH/2)/2), P_HEIGHT/2, getFontMetrics(font).stringWidth("<")+20,  getFontMetrics(font).stringWidth("<")+20, Color.gray, Color.white, font, ButtonMethod.CYCLE_RIGHT));
		
		clickables.add(new Button("Main Menu",P_WIDTH/4, (P_HEIGHT + P_HEIGHT/2)/2, P_WIDTH/6, P_HEIGHT/20+10, Color.gray, Color.white, font, ButtonMethod.MAIN_MENU));

		clickables.add(new Button("Select Model", ((P_WIDTH + P_WIDTH/2)/2), (P_HEIGHT + P_HEIGHT/2)/2,  P_WIDTH/6, P_HEIGHT/20+10, Color.gray, Color.white, font, ButtonMethod.SELECT_MODEL));
		
		
		
		addKeyListener(new TAdapter());
		addMouseListener(new MListener());
		addMouseMotionListener(new MMListener());
        setFocusable(true);
        
        
        setPreferredSize(new Dimension(P_WIDTH, P_HEIGHT));
        
        timer = new Timer(15, this);
        timer.start();
        
        return this;
	}
	
	public static void cycleLeft() {
		switch(model){
		case "YELLOW":
			model = "PINK";
			break;
		case "GREEN":
			model = "YELLOW";
			break;
		case "BLUE":
			model = "GREEN";
			break;
		case "PURPLE":
			model = "BLUE";
			break;
		case "PINK":
			model = "PURPLE";
			break;
			default:
				break;
		}
	}

	public static void cycleRight() {
		switch(model){
		case "YELLOW":
			model = "GREEN";
			break;
		case "GREEN":
			model = "BLUE";
			break;
		case "BLUE":
			model = "PURPLE";
			break;
		case "PURPLE":
			model = "PINK";
			break;
		case "PINK":
			model = "YELLOW";
			break;
			default:
				break;
		}
	}

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Utils.broadcastMessage("Models paint", "PlayerModelBoard.class (129)");
        drawMenu(g);
        Toolkit.getDefaultToolkit().sync();
        ButtonMethod.loadPlayerModel(PlayerModel.valueOf(model));
    }
	
	public void drawMenu(Graphics g){
		String title = "Player Model Selector";
		
		g.drawImage(Texture.loadTexture("/res/background-win.png"), 0, 0, P_WIDTH, P_HEIGHT, null);
		
		
		g.drawImage(Texture.loadTexture("/res/playermodels/" + model.toLowerCase() + "/stand_right.png"), (P_WIDTH/2)-((13*5)/2), (P_HEIGHT/2)-((44*5)/2), 13*5, 44*5, null);

		
		Font titlefont = new Font("Helvetica", Font.BOLD, 35);
		g.setFont(titlefont);
		FontMetrics fm = getFontMetrics(titlefont);
		
		g.setColor(Color.white);
		
		g.drawString(title, (P_WIDTH - fm.stringWidth(title)) / 2, (int) fm.getStringBounds(title, g).getHeight());
		
		for(Clickable clickable : clickables){
    		g.drawPolygon(clickable.drawPolygon(g));
    		if(clickable instanceof Button){
    			Button button = (Button) clickable;
    			g.setColor((button).getForeground());
    			g.setFont(button.getFont());
    			g.drawString((button).getString(), button.x - (getFontMetrics(button.getFont()).stringWidth(button.getString())/2), button.y + (getFontMetrics(button.getFont()).getHeight())/4);
    		}
    	}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}
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
			for (Clickable clickable : clickables)
				if (clickable.getPolygon().contains(e.getPoint())){
					clickable.mousePressed(e);
					break;
				}
		}

		public void mouseReleased(MouseEvent e) {
			for (Clickable clickable : clickables)
				if (clickable.getPolygon().contains(e.getPoint()))
					clickable.mouseReleased(e);
		}
	}

	

}
