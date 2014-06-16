package com.tankwar.utils;


/**
 * some useful constant value
 * @author admin
 *
 */
public class Constant {
	/**
	 * speed
	 */
	public static final int BULLET_SPEED = 10 ;
	public static final int TANK_SPEED_HERO = 10 ;
	public static final int ENEMY_SPEED = 10 ;
	public static final int active_area_x = 800 ;
	public static final int active_area_y = 600 ;
	

	/**
	 * bullets' properties
	 */
	public static final int bullet_sleep_time = 100 ;
	public static final int BULLET_WIDTH = 3 ;
	public static final int BULLET_HEIGHT = 3 ;
	
	//坦克的宽度和高度最好一样
	public static final int TANK_WIDTH = 30 ;
	public static final int TANK_HEIGHT = 30 ;
	
	public static final int ENEMY_SLEEP_TIME = 500 ;
	
	/*
	 * default enemy number
	 */
	public static final int defaultEnemiesNum = 10 ;//默认每一关卡最大的敌军坦克数量
	
	/*
	 * rectangle(the walls') width and height
	 */
	public static final int WIDTH_HEIGHT = 10 ;
	
	/**
	 * map array property
	 */
	public static final int MAP_Array_SIZE = 50 ;
	public static final int MAP_Array_SIZE_HEIGHT = 80 ;
	
	/**
	 * life value
	 * 
	 */
	public static final int LIFE_VALUE_FULL = 3 ;
	
	public static final int singleScore = 10 ;
	
	/**
	 * max stage
	 */
	public static final int MAX_STAGE = 3 ; //最大的关卡
	
	/**
	 * 道具出现的总时间
	 */
	public static final int MAX_PROP_TIME = 10 ; //10秒
	
	/**
	 * 道具的长度和宽度
	 */
	public static final int PROP_WIDTH_HEIGHT = 20 ;
	
	/**
	 * 数据库类型
	 */
	public static final String dbType = "oracle";
	
	
	/*public static String[][] rowData = null ;
	
	//加载排行榜信息
	static{
		//加载数据
		List<String> data = new DBHandler().getRankingData();
		rowData = new String[data.size()][];
		
		for(int i = 0 ; i < rowData.length; i++){
			rowData[i] = data.get(i).split(",") ;
		}
	}*/
}
