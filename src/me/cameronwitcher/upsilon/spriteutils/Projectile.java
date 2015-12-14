package me.cameronwitcher.upsilon.spriteutils;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.utils.DamageReason;
import me.cameronwitcher.upsilon.utils.Direction;
import me.cameronwitcher.upsilon.utils.Utils;

public class Projectile extends Entity implements Moveable {
	

	public int damage;
	public Direction direction;
	public int speed;
	public Entity shooter;
	
	
	public Projectile(int x, int y, Entity shooter) {
		super(x, y);
		this.shooter = shooter;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
	public int getDamage(){
		return damage;
	}
	public Direction getDirection(){
		return direction;
	}
	public Entity getShooter(){
		return shooter;
	}

	@Override
	public void move() {
		if(direction.equals(Direction.RIGHT))
			x = x+speed;
		if(direction.equals(Direction.LEFT))
			x = x-speed;
		for(Sprite sprite : Bridge.getGame().getBoard().getLevel(Utils.player_level)){
			if(!this.getPolygon().getBounds().intersects(sprite.getPolygon().getBounds())) continue;
			if(sprite instanceof Entity){
				if(!sprite.getType().equals(shooter.getType())){
					Utils.broadcastMessage(shooter.getType() + " : " + sprite.getType(), "Projectile.class (46)");
					((Entity) sprite).damage(this.damage, this, DamageReason.PROJECTILE);	
				} else {
					continue;
				}
			}
			remove();
		}
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