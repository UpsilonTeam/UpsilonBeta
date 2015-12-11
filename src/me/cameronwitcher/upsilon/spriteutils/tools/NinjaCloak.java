package me.cameronwitcher.upsilon.spriteutils.tools;

import java.security.GeneralSecurityException;

import me.cameronwitcher.upsilon.spriteutils.SpriteType;

public class NinjaCloak extends Tool {
	

	public NinjaCloak(int x, int y) {
		super(x, y);
		init();
	}
	
	public void init(){
		loadImage("ninjacloak.png");
		setImageDimensions(16, 16,0,0);
	}
	
	@Override
	public SpriteType getType(){
		return SpriteType.NINJA_CLOAK;
	}
	
	@Override
	public void use(){
		getEntity().toggleInvisiblility();
		
	}
	

}
