package me.cameronwitcher.upsilon.utils;

import me.cameronwitcher.upsilon.Bridge;
import me.cameronwitcher.upsilon.boards.GameBoard;
import me.cameronwitcher.upsilon.boards.PlayerModelBoard;
import me.cameronwitcher.upsilon.spriteutils.PlayerModel;

public enum ButtonMethod {
	
	LEVEL_UP("level_up"),
	MAIN_MENU("main_menu"),
	PLAYER_MODELS("player_models"),
	SELECT_MODEL("select_model"),
	CYCLE_LEFT("cycle_left"),
	CYCLE_RIGHT("cycle_right"),
	LEVEL_SELECT("level_select"),
	RESTART("restart"),
	RESUME("resume"),
	CLOSE_INVENTORY("close_inventory"),
	SELECT_TOOL("select_tool"),
	QUIT("quit"),
	START("start");
	
	String method;
	public static PlayerModel model;
	
	private ButtonMethod(String method) {
		this.method = method;
	}
	
	public static void loadPlayerModel(PlayerModel m){
		model = m;
	}
	
	public void clicked(){
		if(this.equals(LEVEL_UP)){
			Bridge.getGame().getBoard().player.levelUp();
			
		}
		if(this.equals(MAIN_MENU)){
			Bridge.getGame().openMenu();
		}
		if(this.equals(PLAYER_MODELS)){
			Bridge.getGame().openPlayerModels();
		}
		if(this.equals(SELECT_MODEL)){
			if(model.equals(null)) return;
			if(Utils.playerHasModel(model)){
				Utils.setPlayerModel(model);
				Bridge.getGame().openMenu();
			} else {
				Utils.buyModel(model);
			}
		}
		if(this.equals(CYCLE_LEFT)){
			PlayerModelBoard.cycleLeft();
		}
		if(this.equals(CYCLE_RIGHT)){
			PlayerModelBoard.cycleRight();
		}
		if(this.equals(LEVEL_SELECT)){
			Bridge.getGame().openLevelDebug(Bridge.getGame().DEBUG_LEVEL);
		}
		if(this.equals(RESTART)){
			Bridge.getGame().restart();
		}
		if(this.equals(START)){
			Bridge.start();
		}
		//Cameron
		//Terminates program from Runtime.
		if(this.equals(QUIT)){
			Bridge.quit();
		}
		if(this.equals(RESUME)){
			Bridge.getGame().getBoard().resume();
		}
		if(this.equals(CLOSE_INVENTORY)){
			Bridge.getGame().closeInventory();
			Bridge.getGame().getBoard().clickables.clear();
		}
		if(this.equals(SELECT_TOOL)){
			GameBoard b = Bridge.getGame().getBoard();
			b.player.setTool(b.player.inventory.get(b.l));
			
		}
		
	}

}
