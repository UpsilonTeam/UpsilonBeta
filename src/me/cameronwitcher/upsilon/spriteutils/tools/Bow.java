package me.cameronwitcher.upsilon.spriteutils.tools;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.sprites.Arrow;
import me.cameronwitcher.upsilon.spriteutils.SpriteType;

public class Bow extends Weapon {
	

	public Bow(int x, int y) {
		super(x, y);
		init();
	}
	
	public void init(){
		damage = 10;
		loadImage("/res/bow.png");
		setImageDimensions(16, 16,0,0);
	}
	
	@Override
	public SpriteType getType(){
		return SpriteType.BOW;
	}
	
	@Override
	public void use(){
		Arrow arrow = new Arrow(
				getEntity().x, 
				getEntity().y,
				getEntity().getDirection(),
				getEntity());
		arrow.direction = getEntity().getDirection();
		Bridge.getGame().getBoard().addMoveable(arrow);
		 
	}
	

}
