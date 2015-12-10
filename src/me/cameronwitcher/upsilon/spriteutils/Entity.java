package me.cameronwitcher.upsilon.spriteutils;

import java.util.Random;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.sprites.Player;
import me.cameronwitcher.upsilon.spriteutils.tools.Tool;
import me.cameronwitcher.upsilon.utils.DamageReason;
import me.cameronwitcher.upsilon.utils.Direction;
import me.cameronwitcher.upsilon.utils.Images;
import me.cameronwitcher.upsilon.utils.Utils;

public class Entity extends Sprite {

	public int health;
	public int maxhealth;
	protected boolean dead = false;
	public boolean invisible= false;
	protected int score;
	protected Tool tool;
	public boolean walking;
	Direction direction = Direction.RIGHT;
	
	public Entity(int x, int y) {
		super(x, y);
	}
	
	public int getHealth(){
		return health;
	}
	public int getMaxHealth(){
		return maxhealth;
	}
	public void damage(int i, Entity damager, DamageReason reason){
		health = health-i;
		Utils.displayMessage(new Random().nextInt(), "-" + i, x, y, 100, "#FF0000", 15);
		Utils.displayMessage(new Random().nextInt(), getHealth() + "/" + getMaxHealth(), x, y-15, 100, "#FF0000", 15);
		if(health<=0){
			this.kill(reason);
			if(damager instanceof Player){
				((Player) damager).score = ((Player) damager).score + this.score;
			}
		}
		
	}
	
	public void damage(int i, DamageReason reason){
		health = health-i;
		Utils.displayMessage(new Random().nextInt(), "-" + i, x, y, 100, "#FF0000", 15);
		Utils.displayMessage(new Random().nextInt(), getHealth() + "/" + getMaxHealth(), x, y-15, 100, "#FF0000", 15);

		if(health<=0){
			this.kill(reason);
		}
		
	}
	
	public void kill(DamageReason reason){
		dead = true;
	}
	public Tool getTool(){
		return tool;
	}
	public void setTool(Tool tool){
		tool.setEntity(this);
		this.tool = tool;
		
		
	}
	public boolean hasTool() {
		if(tool == null) return false;
		else return true;
	}
	public void setDirection(Direction d){
		direction = d;
	}
	public Direction getDirection(){
		return direction;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public void toggleInvisiblility() {
		if(!invisible){
			invisible = true;
			if(!walking)
				loadImage(Images.makeImageTranslucent(Images.toBufferedImage(getImage()), 0.5));
			
		} else {
			invisible = false;
			if(!walking){
				if(this instanceof Player){

				
					loadImage("/res/playermodels/" + (Bridge.getGame().getBoard().player.model.toString().toLowerCase()) + "/stand_" + direction.toString().toLowerCase() + ".png");
				}
			}
				
		}
	}
	

}
