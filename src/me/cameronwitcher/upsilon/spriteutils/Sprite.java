package me.cameronwitcher.upsilon.spriteutils;

import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.utils.Direction;
import me.cameronwitcher.upsilon.utils.Utils;
import res.Texture;

public class Sprite {

    public int x;
    public int y;
    public int id;
    public int bw=0;
    public int bh=0;
    protected int width;
    protected int height;
    protected boolean vis;
    private Image image;
    public String imagePath = "";
    @SuppressWarnings("unused")
	private Sprite sprite;
    public Polygon bounds;

    public Sprite(int x, int y) {
    	
    	this.id = Utils.getNextID();

    	this.sprite = this;
        this.x = x;
        this.y = y;
        vis = true;
       
        
        
    }
    

	public void remove(){
		Bridge.getGame().getBoard().removeSprite(this);
    }
    
    public SpriteType getType(){return null;}
    
    

    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    
    protected void setImageDimensions(int width, int height, int bw, int bh) {

        this.width = width;
        this.height = height;
        this.bw = bw;
        this.bh = bh;
        
        
    }

    protected void loadImage(String imageName) {
    	
    	imagePath = imageName;
    	
    	image = Texture.loadTexture(imageName);
    	

    	
    }
    
    public Direction getIntercectingDirection(Rectangle r){
    	
    	Utils.broadcastMessage(bounds.getBounds().getMaxY() - r.getY() + "", "Sprite.class (78)");
    	
    	if (bounds.getBounds().getMaxY() - r.getY() >= 0 && bounds.getBounds().getMaxY() - r.getY() <= 6) {
    		return Direction.DOWN;
    	}
    	
    	if(bounds.getBounds().getMaxX() - r.getX() >= 1.0 && bounds.getBounds().getMaxX() - r.getX() <= 17.0){
    		return Direction.RIGHT;
    	}
    	if(bounds.getBounds().getX() - r.getMaxX() <= -1.0 && bounds.getBounds().getX() - r.getMaxX() >= -17.0){
    		return Direction.LEFT;
    	}
    	if(bounds.getBounds().getY() - r.getMaxY() <= -1.0){
    		return Direction.UP;
    	}
    	return null;
    }
    
    protected void loadImage(Image image){
    	this.image = image;
    }
    
    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }

    public Polygon getPolygon() {
    	bw = image.getWidth(null);
    	bh = image.getHeight(null);
    	bounds = new Polygon(new int[] {x,x+bw,x+bw,x}, new int[] {y+bh,y+bh,y,y}, 4);
    	return bounds;
    }
    
    public String getLocation(){
    	return x+":"+y;
    }
	public int getID() {
		return id;
	}

	public SpriteSubType getSubType() {
		return getType().getSubType();
	}


	

}