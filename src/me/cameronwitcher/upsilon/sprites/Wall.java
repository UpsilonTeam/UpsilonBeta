package me.cameronwitcher.upsilon.sprites;

import me.cameronwitcher.upsilon.spriteutils.Sprite;
import me.cameronwitcher.upsilon.spriteutils.SpriteType;
import me.cameronwitcher.upsilon.spriteutils.State;
import me.cameronwitcher.upsilon.utils.Utils;

public class Wall extends Sprite {
	
	State state;

    public Wall(int x, int y, State state) {
        super(x, y);
        this.state = state;
        init();
    }
    
    @Override
    public SpriteType getType(){
    	return SpriteType.WALL;
    }

    private void init() {
    	
    	switch(state){
    	case LEFT_LOWER_CORNER:
    		loadImage("/res/wall/lowerLeftCorner.png");
    		break;
    	case LEFT_UPPER_CORNER:
    		loadImage("/res/wall/upperLeftCorner.png");
    		break;
    	case RIGHT_LOWER_CORNER:
    		loadImage("/res/wall/lowerRightCorner.png");
    		break;
    	case RIGHT_UPPER_CORNER:
    		loadImage("/res/wall/upperRightCorner.png");
    		break;
    	case VERTICAL:
    		loadImage("/res/wall/vertical.png");
    		break;
    	case HORIZONTAL:
    		loadImage("/res/wall/horizontal.png");
    		break;
    		default:
    			loadImage("/res/wall/vertical.png");
    			break;
    	}
        getImageDimensions();
    }
}