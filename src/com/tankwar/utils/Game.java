package com.tankwar.utils;

/**
 * game data (such as : stage, status ...)
 * @author admin
 *
 */
public class Game {

	public static int stage = 1 ; 
	public static int status = 0 ;
	public static int enemy_killed = 0 ;
	public static int propScore = 0 ; //迟到的道具分数
	
	public static final int STATUS_MENU = 0; //在菜单页面
	public static final int STATUS_ON = 1; //游戏进行中
	public static final int STATUS_PAUSE = 2; //游戏暂停
	public static final int STATUS_GAME_OVER = 3 ;//游戏结束
	
	public static final int PANEL_STATUS_MAINPANEL = 0 ; //主面板中
	public static final int PANEL_STATUS_SCENE_BUILD = 1 ; //游戏场景设计状态
	public static final int PANEL_STATUS_SCENE_CHOSE = 2 ; //游戏场景选择
	public static final int PANEL_STATUS_SCENE_STAGE_RESULT = 3 ; //游戏结果汇报
	
	public static final int BOSS_TANK_X = 47 ; //玩家需要保护的坦克在数组中的位置
	public static final int BOSS_TANK_Y = 40 ;

	//游戏场景状态
	public static int PANEL_STATUS = 0 ;//
	
	
	public static boolean isBossAlive = true ; 
	
	public static void switchPanelStatus(String name){
		
		if( name.equals("sceneCreate") ){
			Game.PANEL_STATUS = Game.PANEL_STATUS_SCENE_BUILD;
		}else if(name.equals("mainPanel")){
			Game.PANEL_STATUS = Game.PANEL_STATUS_MAINPANEL;
		}else if( name.equals("sceneChose")){
			Game.PANEL_STATUS = Game.PANEL_STATUS_SCENE_CHOSE;
		}else if( name.equals("stageResult")){
			Game.PANEL_STATUS = PANEL_STATUS_SCENE_STAGE_RESULT ;
		}
	}
	
	public static void restart(){
		
	}
	
	public static void start(){
		status = Game.STATUS_ON;
	}
	
	public static void pause(){
		status = Game.STATUS_PAUSE;
	}
	
	public static void resume(){
		status = Game.STATUS_ON;
	}
	
	public static void toMenu(){
		status = Game.STATUS_MENU;
	}
	
	public static void nextStage(){
		stage ++ ;
	}
	
	public static void beforeStage(){
		stage -- ;
	}
}
