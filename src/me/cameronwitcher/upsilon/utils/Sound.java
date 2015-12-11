package me.cameronwitcher.upsilon.utils;

public enum Sound {
	
	
	BUTTON_CLICK("button_click.wav"),
	BOW_SHOOT("bow_shoot.wav"),
	JUMP("jump.wav"),
	SCORE("score.wav"),
	TEST("test.wav");
	
	String sound;
	
	Sound(String sound){
		this.sound = sound;
	}
	
	public Sound getSound(){
		return this;
	}
	public String getSoundString(){
		return sound;
	}

}
