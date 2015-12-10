package me.cameronwitcher.upsilon.sprites;

import me.cameronwitcher.upsilon.spriteutils.Money;
import me.cameronwitcher.upsilon.spriteutils.Moveable;
import me.cameronwitcher.upsilon.spriteutils.Sprite;
import me.cameronwitcher.upsilon.spriteutils.SpriteType;

public class Gold extends Sprite implements Money,Moveable {
	
	int value;

    public Gold(int x, int y) {
        super(x, y);
        value = 10;
        initFloor();
    }
    
    @Override
    public SpriteType getType(){
    	return SpriteType.GOLD;
    }

    private void initFloor() {
        
        loadImage("/res/gold.png");
        getImageDimensions();
    }

	@Override
	public int getValue() {
		return value;
	}
	

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}
}