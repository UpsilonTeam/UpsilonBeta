package me.cameronwitcher.upsilon.sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.boards.GameBoard;
import me.cameronwitcher.upsilon.spriteutils.Entity;
import me.cameronwitcher.upsilon.spriteutils.Keyable;
import me.cameronwitcher.upsilon.spriteutils.Money;
import me.cameronwitcher.upsilon.spriteutils.Moveable;
import me.cameronwitcher.upsilon.spriteutils.PlayerModel;
import me.cameronwitcher.upsilon.spriteutils.Sprite;
import me.cameronwitcher.upsilon.spriteutils.SpriteSubType;
import me.cameronwitcher.upsilon.spriteutils.SpriteType;
import me.cameronwitcher.upsilon.spriteutils.tools.Tool;
import me.cameronwitcher.upsilon.utils.Button;
import me.cameronwitcher.upsilon.utils.ButtonMethod;
import me.cameronwitcher.upsilon.utils.DamageReason;
import me.cameronwitcher.upsilon.utils.Direction;
import me.cameronwitcher.upsilon.utils.Utils;

public class Player extends Entity implements Moveable,Keyable {

	public int dx;
	public int dy;
	public boolean onground;
	public boolean jumping;
	public boolean falling;
	public boolean climbing;
	public boolean gravity;
	private boolean disabled;
	public boolean shifting;
	public boolean left = false;
	public boolean right = false;
	public boolean up = false;
	public int score = 0;
	public int lives = 3;
	private int i;
	public int speedboost = 1;
	public int level = 1;
	public List<Tool> inventory = new ArrayList<>();
	public PlayerModel model = PlayerModel.YELLOW;
	private HashMap<Integer, Integer> jumpInfo = new HashMap<>();
	private boolean ctrl = false;

	public Player(int x, int y, int help) {
		super(x, y);
		onground = false;
		jumping = false;
		falling = true;
		gravity = true;
		initPlayer();
		health= 100;
		maxhealth = 100;
		
	}

	@Override
	public SpriteType getType() {
		return SpriteType.PLAYER;
	}
	
	
	

	public void setPlayerModel(PlayerModel model) {
		this.model = model;
		Utils.setPlayerModel(model);
		if(!walking){

			loadImage("/res/playermodels/" + model.toString().toLowerCase() + "/stand_" + getDirection().toString().toLowerCase() + ".png");
			setImageDimensions(13, 41, -2, -2);
		} else {

			loadImage("/res/playermodels/" + model.toString().toLowerCase() + "/walk_" + getDirection().toString().toLowerCase() + ".gif");
			setImageDimensions(29, 41, -2, -2);
		}
	}

	private void initPlayer() {
		Utils.checkPlayerInfo(this);
		loadImage("/res/playermodels/" + model.toString().toLowerCase() + "/stand_right.png");
		setImageDimensions(13, 41, -2, -2);
		setDirection(Direction.RIGHT);
		
		Utils.initPlayer(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!Bridge.getGame().getBoard().paused){

			int key = e.getKeyCode();
			if(key == KeyEvent.VK_CONTROL){
				ctrl = true;
			}
			if (key == KeyEvent.VK_SPACE) {
				jump();
			}

			if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				setDirection(Direction.LEFT);
				dx = -2;
				walking= true;
				

				loadImage("/res/playermodels/" + model.toString().toLowerCase() + "/walk_left.gif");
				setImageDimensions(29, 41, -2, -2);
				
				

			}

			if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				setDirection(Direction.RIGHT);
				dx = 2;
				walking= true;
				

				loadImage("/res/playermodels/" + model.toString().toLowerCase() + "/walk_right.gif");
				setImageDimensions(29, 41, -2, -2);
				
				

			}

			if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				if (climbing) {
					setDirection(Direction.UP);
					dy = -2;
				}
				if (!gravity)
					dy = -2;
			}

			if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				if (climbing) {
					setDirection(Direction.DOWN);
					dy = 2;
				}
				if (!gravity)
					dy = 2;
			}
			if (key == KeyEvent.VK_F) {
				Bridge.getGame().getBoard().toggleGravity();
			}
			if (key == KeyEvent.VK_H) {
				Bridge.getGame().getBoard().toggleHitboxes();
			}
			if (key == KeyEvent.VK_EQUALS) {
				speedboost = speedboost + 1;
			}
			if (key == KeyEvent.VK_MINUS) {
				if ((speedboost - 1) >= 1)
					speedboost = speedboost - 1;
			}
			if (key == KeyEvent.VK_F3) {
				Bridge.getGame().getBoard().toggleDebugMode();
			}
			
			if (key == KeyEvent.VK_SHIFT) {
				if(hasTool() && !shifting) tool.use();
				shifting = true;
			}
			if (key == KeyEvent.VK_E) {
				openInventory(Bridge.getGame().getBoard());
			}
			
			if (key == KeyEvent.VK_O && shifting) {

				Bridge.getGame().getBoard().gameStatus = "won:" + level;
				Bridge.getGame().getBoard().ingame = false;
				x = 0;
				y = 0;
			
			}
			
			
				
			
			
			
			
			if (key == KeyEvent.VK_1) {
				kill(DamageReason.VOID);
//				Bridge.getGame().getBoard().clickables.add(new Button("Test", 43, 43, 50, 30, Color.MAGENTA, Color.white, new Font("Helvetica", Font.BOLD, 10), ButtonMethod.LEVEL_UP));
			}
		}
	}

	private void openInventory(GameBoard b) {
		Bridge.getGame().openInventory(inventory);
	}

	private void jump() {
		if (jumping || falling)
			return;
		onground = false;
		jumping = true;
		falling = false;
		jumpInfo.put(1, 1);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(!Bridge.getGame().getBoard().paused){
			
			if (key == KeyEvent.VK_SHIFT) {
				shifting = false;
			}
			
			if(key == KeyEvent.VK_CONTROL){
				ctrl = false;
			}

			

			if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				dx = 0;
				walking= false;
				loadImage("/res/playermodels/" + model.toString().toLowerCase() + "/stand_left.png");
				if(invisible)
					toggleInvisiblility();
				
				setImageDimensions(13, 41, -2, -2);
			}

			if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				dx = 0;
				walking= false;
				loadImage("/res/playermodels/" + model.toString().toLowerCase() + "/stand_right.png");
				if(invisible)
					toggleInvisiblility();
				setImageDimensions(13, 41, -2, -2);
			}

			if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				dy = 0;
			}

			

			if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				dy = 0;
			}
			
		}
		if (key == KeyEvent.VK_ESCAPE) {
			if (Bridge.getGame().getBoard().paused) {
				Bridge.getGame().getBoard().resume();
			} else
				Bridge.getGame().getBoard().pause();
		}
	}

	public void setOnGround() {
		onground = true;
		falling = false;
		jumping = false;
	}

	public boolean isOnGround() {
		return onground;
	}

	public void setOnNotGround() {
		onground = false;
		if (!jumping)
			falling = true;
	}

	public void levelUp() {
		Utils.player_level += level + 1;
		level = level +1;
		Bridge.getGame().getBoard().sprites.clear();
		Bridge.getGame().getBoard().clickables.clear();
		Bridge.getGame().getBoard().moveables.clear();
		Bridge.getGame().getBoard().tools.clear();
		Bridge.getGame().getBoard().moveables_temp.clear();
		Bridge.getGame().getBoard().removedSprites.clear();
		
		x=0;
		y=0;

		Utils.savePlayerInfo(this);
		Bridge.getGame().getBoard().loadLevel();
		Bridge.getGame().getBoard().ingame = true;
		
	}

	public void setLevel(int level) {
		this.level = level;
		Bridge.getGame().getBoard().ingame = true;
		Bridge.getGame().getBoard().loadLevel();
	}

	public int getLevel() {
		return level;
	}

	@Override
	public void move() {
		if(ctrl) speedboost = 2;
		else speedboost = 1;
		if (disabled)
			return;
		Utils.checkPlayerInfo(this);
		if (!Bridge.getGame().getBoard().ingame) {
			return;
		}
		if (x >= 650 || x <= -1 || y >= 650 || y <= -1) {
			kill(DamageReason.VOID);
		}
		
		
		
		
		
		if (gravity) {

			onground = false;
			up = false;
			left = false;
			right = false;
			climbing = false;
			
			

			try {
				for (Sprite sprite : Bridge.getGame().getBoard().getLevel(level)) {
					if(sprite instanceof Player) continue;
					if (!getPolygon().intersects(sprite.getPolygon().getBounds()))continue;
					if (sprite instanceof Money) {
						climbing = false;
						Money money = (Money) sprite;
						score = score + (money).getValue();
						sprite.remove();
						Utils.player_score = score;
					}
					if(sprite instanceof Tool){
						setTool(((Tool) sprite));
						inventory.add(((Tool) sprite));
						Utils.broadcastMessage("Added Tool: " + ((Tool) sprite).getType(), "Player.class (353)");
					}
					if(sprite.getSubType().equals(SpriteSubType.BAD_THINGS)){
						damage(5, DamageReason.BAD_THINGS);
						dy=0;
						jump();
					}
					if (sprite.getSubType().equals(SpriteSubType.PARTIAL_COLLIDEABLE) && !jumping) {
						if (getPolygon().getBounds().getMaxY() - sprite.getPolygon().getBounds().getMinY() <= 5
								&& getPolygon().getBounds().getMaxY() - sprite.getPolygon().getBounds().getMinY() >= 0) {
							if(sprite.getType().equals(SpriteType.FALLING_FLOOR)){
								if(!((FallingFloor) sprite).t) ((FallingFloor) sprite).startFalling();
								y = sprite.getY() - (height-2);
							}else {
								y = sprite.getY() - (height-2);
							}
							if(!jumping){
								onground = true;
								falling = false;
							}
							
						}
					}

					if (sprite.getType().equals(SpriteType.KNOBBER)) {
						if (getPolygon().getBounds().getMaxX() - sprite.getPolygon().getBounds().getMinX() >= -5
								&& getPolygon().getBounds().getMaxX() - sprite.getPolygon().getBounds().getMinX() <= 0) {
							damage(((Knobber) sprite).getPower(), this, DamageReason.ENEMY);
						}
						if (getPolygon().getBounds().getMinX() - sprite.getPolygon().getBounds().getMaxX() >= -5
								&& getPolygon().getBounds().getMinX() - sprite.getPolygon().getBounds().getMaxX() <= 0) {
							damage(((Knobber) sprite).getPower(), this, DamageReason.ENEMY);
						}
						if (getPolygon().getBounds().getMaxY() - sprite.getPolygon().getBounds().getMinY() <= 5
								&& getPolygon().getBounds().getMaxY() - sprite.getPolygon().getBounds().getMinY() >= 0) {
							((Knobber) sprite).damage(10,this, DamageReason.ENEMY);
						}
					}
					if (sprite.getType().getSubType().equals(SpriteSubType.CLIMABLE) && !jumping) {
						climbing = true;
						if (falling)
							dy = 0;
						falling = false;
					} else {
						climbing = false;
					}
					if(sprite.getType().equals(SpriteType.GATE)){
						Bridge.getGame().getBoard().gameStatus = "won:" + level;
						Bridge.getGame().getBoard().ingame = false;
						x = 0;
						y = 0;
					}
					
					if(sprite.getSubType().equals(SpriteSubType.COLLIDEABLE)){
						switch(getIntercectingDirection(sprite.getPolygon().getBounds())){
						case LEFT:
							if(!onground) onground = false;
							left = true;
							break;
						case RIGHT:
							if(!onground) onground = false;
							right = true;
							break;
						case DOWN:
							onground = true;
							falling = false;
							break;
						case UP:
							falling = true;
							jumping = false;
							break;
							default:
								left = false;
								right = false;
								
								if(!jumping){
									falling = true;
								}
								break;
						}
					}
				}

			} catch (Exception e1) {
				if(!onground && !jumping && !climbing) falling = true;

			}


		
			if (climbing) {
				x += dx * (speedboost);
				y += dy;
				if (x < 1)
					x = 1;
				if (y < 1)
					y = 1;
				return;
			}
			if(!climbing && !falling &!jumping && !onground) falling = true;
			

			if (onground)
				dy = 0;
			if (falling){
				dy = 4;
			}
			if (jumping) {
				if (jumpInfo.get(1) == 1) {
					if (i == 0)
						i = 1;
					dy = -4;
					jumpInfo.put(2, i + 1);
					i = i + 1;
					if (i >= 20) {
						dy = 0;
						if (i >= 25) {
							jumpInfo.put(1, 2);
							jumpInfo.put(2, 0);
							i = 0;
						}
					}
				}
				if (jumpInfo.get(1) == 2) {
					jumping = false;
					falling = true;
				}
			}

			if (getDirection().equals(Direction.LEFT) && left) {
				dx = 0;
			}
			if (getDirection().equals(Direction.RIGHT) && right) {
				dx = 0;
			}
			if (getDirection().equals(Direction.LEFT) && !left && walking) {
				dx = -2;
			}
			if (getDirection().equals(Direction.RIGHT) && !right && walking) {
				dx = 2;
			}
			
		}

		x += dx * (speedboost);
		y += dy;

		if (x < 1)
			x = 1;
		if (y < 1)
			y = 1;
		if (x > 640 - getImage().getWidth(null))
			x = 640 - getImage().getWidth(null);
		if (y > 640 - getImage().getHeight(null))
			y = 640 - getImage().getHeight(null);
	}

	

	@Override
	public void kill(DamageReason reason) {
		lives = lives - 1;
		x = 0;
		y = 0;
		health = 100;
		if (lives <= 0) {
			Bridge.getGame().getBoard().gameStatus = "gameover:" + reason.getMessage();
			Bridge.getGame().getBoard().ingame = false;
		}
	}

	@Override
	public void disable() {
		disabled = true;
	}

	@Override
	public void enable() {
		disabled = false;
	}

	public void drawHealthBar(Graphics g, int x, int y, int width, int height) {
		
		Color c = g.getColor();
		
		int bar = health*(width/maxhealth);
		width = maxhealth*(width/maxhealth);
		
		g.drawString(health + "%", (x+(width/2))-((g.getFontMetrics().stringWidth(health + "%")/2)), y);
		
		
//		g.drawRect(x, y, ((health/2)), 5);
		g.drawRect(x, y, width, height);
		if(health >= 75)
			g.setColor(Color.GREEN);
		if(health < 75 && health >= 25)
			g.setColor(Color.YELLOW);
		if(health < 25)
			g.setColor(Color.RED);
		
		g.fillRect(x, y, bar, height);
		
		g.setColor(c);
		
		
	}
	
	
}