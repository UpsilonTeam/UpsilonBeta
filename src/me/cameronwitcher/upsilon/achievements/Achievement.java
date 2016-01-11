package me.cameronwitcher.upsilon.achievements;

import java.util.Random;

import me.cameronwitcher.upsilon.utils.Utils;

public enum Achievement {
	 
	LEVEL_1("You_have_complete_level_1"),
	TOOL("You_used_a_tool_for_the_first_time");
	
	String desc;
	
	private Achievement(String desc) {
		this.desc = desc;
	}
	
	public String getDescription(){
		return desc; 
	}
	
	public void broadcastAchievement(){
		Utils.displayMessage(new Random().nextInt(), this.toString(), 32, 32, 10, "#FFFFFF", 15);
	}
	
	


}
