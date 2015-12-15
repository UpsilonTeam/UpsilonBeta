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

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.spriteutils.Clickable;
import me.cameronwitcher.upsilon.utils.Background;
import me.cameronwitcher.upsilon.utils.Button;
import me.cameronwitcher.upsilon.utils.ButtonMethod;
import me.cameronwitcher.upsilon.utils.Utils;
import res.Texture;

public class MenuBoard extends JPanel implements ActionListener {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int M_WIDTH = 960;
	public int M_HEIGHT = 640;
	public int LEVEL_DEBUG = 6;
	Timer timer;
	public List<Clickable> clickables = new ArrayList<>();
	
	public MenuBoard(){
		initBoard();
	}
	
	public void initBoard(){
		
		timer = new Timer(15, this);
		timer.start();
		
		addKeyListener(new TAdapter());
		addMouseMotionListener(new MMListener());
        addMouseListener(new MListener());
        
        clickables.add(new Button("Play", M_WIDTH/4, (M_HEIGHT/2 + M_HEIGHT)/2, M_WIDTH/4, (M_HEIGHT/20+10), Color.gray, Color.white, new Font("Helvetica", Font.BOLD, 25), ButtonMethod.START));
        
        
        clickables.add(new Button("Quit", ((M_WIDTH/2)+M_WIDTH)/2, (M_HEIGHT/2 + M_HEIGHT)/2, M_WIDTH/4, (M_HEIGHT/20+10), Color.gray, Color.white, new Font("Helvetica", Font.BOLD, 25), ButtonMethod.QUIT));
        
        
		setLayout(null);
		
		setFocusable(true);
        
        setPreferredSize(new Dimension(M_WIDTH, M_HEIGHT));
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMenu(g);
        Toolkit.getDefaultToolkit().sync();
    }
	
	public void drawMenu(Graphics g){
		
		g.drawImage(Texture.loadTexture("win-background.png"), 0, 0, M_WIDTH, M_HEIGHT, null);
		
		g.drawImage(Texture.loadTexture("playermodels/" 
		+ Utils.getPlayerModel().toLowerCase() 
		+ "/walk_right.png"), (M_WIDTH/2)-((13*5)/2), (M_HEIGHT/2)-((44*5)/2), 13*5, 44*5, null);
		
		g.setColor(Color.white);
		
		for(Clickable clickable : clickables){
    		g.drawPolygon(clickable.drawPolygon(g));
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
        public void keyPressed(KeyEvent e) {
        	
        	//Cameron
        	//Quit.
        	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        		Bridge.quit();
        	}
        	
        	//Cameron
        	//Open level debugger.
        	if(e.getKeyCode() == KeyEvent.VK_D){
        		Utils.player_level = LEVEL_DEBUG;
        		Bridge.openLevelDebug(LEVEL_DEBUG);
        	}
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
