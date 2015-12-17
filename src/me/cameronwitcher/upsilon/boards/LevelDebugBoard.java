package me.cameronwitcher.upsilon.boards;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import me.cameronwitcher.upsilon.spriteutils.Sprite;
import me.cameronwitcher.upsilon.utils.Board;
import me.cameronwitcher.upsilon.utils.BoardType;

public class LevelDebugBoard extends Board implements ActionListener {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int L_WIDTH = 640;
	public int L_HEIGHT = 640;
	int i;
	
	public LevelDebugBoard(int i){
		setType(BoardType.LEVEL_DEBUG);
		this.i = i;
		init();
	}
	
	public void init(){
		setLayout(null);
		
		startTimer();
		
		addKeyListener(new TAdapter());
        setFocusable(true);
        
        
        setPreferredSize(new Dimension(L_WIDTH, L_HEIGHT));
        
      
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
		
		for(Sprite sprite : new GameBoard().getLevel(i)){
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
