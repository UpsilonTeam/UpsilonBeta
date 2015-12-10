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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.spriteutils.Clickable;
import me.cameronwitcher.upsilon.spriteutils.Sprite;
import me.cameronwitcher.upsilon.utils.ButtonCreator;
import me.cameronwitcher.upsilon.utils.Utils;
import res.Texture;

public class LevelDebugBoard extends JPanel implements ActionListener {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int L_WIDTH = 640;
	public int L_HEIGHT = 640;
	int i;
	
	public LevelDebugBoard(int i){
		this.i = i;
		init();
	}
	
	public JPanel init(){
		setLayout(null);
		
		startTimer();
		
		addKeyListener(new TAdapter());
        setFocusable(true);
        
        
        setPreferredSize(new Dimension(L_WIDTH, L_HEIGHT));
        
        return this;
	}
	
	public void startTimer(){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				repaint();
			}
		}, 50, 500);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMenu(g);
        Toolkit.getDefaultToolkit().sync();
    }
	
	public void drawMenu(Graphics g){
		
		g.drawRect(0, 0, 640, 640);
		
		for(Sprite sprite : new GameBoard(0).getLevel(i)){
			g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
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
	
	

}
