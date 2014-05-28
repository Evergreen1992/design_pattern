package com.tankwar.entity;

import java.util.ArrayList;
import java.util.List;

import com.tankwar.domain.PropsContainer;
import com.tankwar.entity.interfaces.PropStatus;
import com.tankwar.entity.interfaces.PropType;
import com.tankwar.utils.Constant;
import com.tankwar.utils.SoundPlayFactory;
import com.tankwar.view.MainFrame;


/**
 *tanks' super class
 * @author Evergreen
 *
 */
public class Tank {
	/**
	 * direction
	 * the direction of the tank
	 */
	public static final int direction_up = 0 ;
	public static final int direction_down = 1 ;
	public static final int direction_left = 2 ;
	public static final int direction_right = 3 ;
	
	/**
	 * tank type
	 */
	public static final int HREO = 0 ;
	public static final int ENEMY = 1 ;
	
	/**
	 * tank's status
	 */
	public static final int ALIVE = 0 ;  //alive
	public static final int SHOTED = 1 ; //got shot
	public static final int DIED = 2 ;   //died
	public static final int SLEEP = 3 ; //睡眠中
	
	private int location_x = 0 ;//x location
	private int location_y = 0 ;//y location
	private int width = Constant.TANK_WIDTH ;     //width
	private int height = Constant.TANK_HEIGHT ;    //height
	private int speed = Constant.TANK_SPEED_HERO ;     //speed
	private int direction = 0 ; //direction
	private int active_area_x = Constant.active_area_x ;
	private int active_area_y = Constant.active_area_y ;
	private int tank_type = 0 ;
	private int status = ALIVE ;
	private List<Bullet> bullts = new ArrayList<Bullet>();//bullets list
	private MainFrame mainFrame = null ;//living frame
	
	private String[] mapArray = null ;//map array
	private int heroLifeValue = Constant.LIFE_VALUE_FULL ; //hero's life value
	
	public Tank(String[] mapArray){
		this.mapArray = mapArray ;
	}
	
	public String[] getMapArray() {
		return mapArray;
	}

	public void setMapArray(String[] mapArray) {
		this.mapArray = mapArray;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Bullet> getBullts() {
		return bullts;
	}

	public void setBullts(List<Bullet> bullts) {
		this.bullts = bullts;
	}
	
	/**
	 * shot bullets
	 */
	public void fire(){
		Bullet bullet = new Bullet(this) ;
		
		if( this.direction == Tank.direction_up){
			bullet.setLocation_x(this.location_x + this.width/2 - bullet.getWidth()/2 - 1);
			bullet.setLocation_y(this.location_y);
		}else if(this.direction == Tank.direction_down){
			bullet.setLocation_x(this.location_x + this.width/2 - bullet.getWidth()/2 - 1);
			bullet.setLocation_y(this.location_y + this.height - 3);
		}else if(this.direction == Tank.direction_left){
			bullet.setLocation_x(this.location_x);
			bullet.setLocation_y(this.location_y + this.width/2 - 1);
		}else if(this.direction == Tank.direction_right){
			bullet.setLocation_x(this.location_x + this.height - 3);
			bullet.setLocation_y(this.location_y + this.width/2 - 1);
		}
		bullet.setDirection(direction);
		//set bullet type
		if( this.tank_type == Tank.HREO){
			bullet.setType(Bullet.BULLET_HERO);
		}else{
			bullet.setType(Bullet.BULLET_ENEMY);
		}
		
		bullet.setMainFrame(this.getMainFrame());
		this.bullts.add(bullet);
		bullet.start();
		
		//sound play
		if( this.tank_type == Tank.HREO){
			SoundPlayFactory.getSoundByName("heroFire");
		}
	}
	
	/**
	 * 检验是否捡到道具
	 */
	public void ifGetProps(){
		if( this.tank_type == Tank.HREO){
			for(Prop p : PropsContainer.getValidProps()){
				//判断道具的四个顶点是否在坦克的位置范围内
				
				//左上
				if( p.getX() >= this.location_x && p.getX() <= this.location_x + this.width && p.getY() >= this.location_y && p.getY() <= this.location_y + this.width){
					p.setStatus(PropStatus.DEAD);
					System.out.println("获得道具********");
					propEffect(p.getType());//道具生效
				}else if(p.getX() + Constant.PROP_WIDTH_HEIGHT >= this.location_x && p.getX() + Constant.PROP_WIDTH_HEIGHT <= this.location_x + this.width && p.getY() >= this.location_y && p.getY() <= this.location_y + this.width){ //右上
					p.setStatus(PropStatus.DEAD);
					System.out.println("获得道具********");
					propEffect(p.getType());//道具生效
				}else if(p.getX() >= this.location_x && p.getX() <= this.location_x + this.width && p.getY() + Constant.PROP_WIDTH_HEIGHT >= this.location_y && p.getY() + Constant.PROP_WIDTH_HEIGHT <= this.location_y + this.width){//左下
					p.setStatus(PropStatus.DEAD);
					System.out.println("获得道具********");
					propEffect(p.getType());//道具生效
				}else if(p.getX() + Constant.PROP_WIDTH_HEIGHT >= this.location_x && p.getX() + Constant.PROP_WIDTH_HEIGHT <= this.location_x + this.width && p.getY() + Constant.PROP_WIDTH_HEIGHT >= this.location_y && p.getY() + Constant.PROP_WIDTH_HEIGHT <= this.location_y + this.width){//右下
					p.setStatus(PropStatus.DEAD);
					System.out.println("获得道具********");
					propEffect(p.getType());//道具生效
				}
				
				
			}
		}
	}
	
	/**
	 * 道具生效
	 */
	public void propEffect(int pType){
		if( pType == PropType.life){
			this.setHeroLifeValue(Constant.LIFE_VALUE_FULL);
			System.out.println("满血!");
		}else if( pType == PropType.star){
			System.out.println("睡眠");
			this.getMainFrame().enemyTankContainer.setEnemyStatus(Tank.SLEEP);
		}else if( pType == PropType.mime){
			System.out.println("所有坦克死去");
			//this.getMainFrame().enemyTankContainer.setEnemyStatus(Tank.DIED);
		}else if( pType == PropType.hat){
			System.out.println("保护道具!");
		}
	}

	public void go_left(){
		if( this.direction != Tank.direction_left){
			this.direction = Tank.direction_left ;
		}
		else{
			if(this.location_x - this.speed >= 0  && ifCanWalkInThisDirection()){
				this.location_x -= this.speed ;
				ifGetProps();
			}
		}
	}
	
	public void go_right(){
		if( this.direction != Tank.direction_right){
			this.direction = Tank.direction_right ;
		}else{
			if(this.location_x + this.speed <= this.active_area_x - this.getWidth()  && ifCanWalkInThisDirection())
			{
				this.location_x += this.speed ;
				ifGetProps();
			}
		}
	}
	
	public void go_up(){
		if( this.direction != Tank.direction_up)
			this.direction = Tank.direction_up ;
		else{
			if(this.location_y - this.speed >= 0  && ifCanWalkInThisDirection())
			{
				this.location_y -= this.speed ;
				ifGetProps();
			}
		}
	}
	
	public void go_down(){
		if( this.direction != Tank.direction_down)
			this.direction = Tank.direction_down ;
		else{
			if(this.location_y + this.speed <= this.active_area_y - 2*this.getHeight() && ifCanWalkInThisDirection())
			{
				this.location_y += this.speed ;
				ifGetProps();
			}
		}
	}
	
	/**
	 * 判断坦克能否朝着某个方向行走。(是否有墙壁阻隔)
	 * @return
	 */
	public boolean ifCanWalkInThisDirection(){
		boolean flag = true ;
		//首先确定在数组中的索引
		int i = 0 ;
		int j = 0 ;
		
		if(this.direction == Tank.direction_up){
			i = this.getLocation_y() / Constant.WIDTH_HEIGHT ;
			j = this.getLocation_x() / Constant.WIDTH_HEIGHT ;
		}else if(this.direction == Tank.direction_down){
			i = (this.getLocation_y() + Constant.TANK_HEIGHT) / Constant.WIDTH_HEIGHT ;
			j = this.getLocation_x() / Constant.WIDTH_HEIGHT ;
		}else if(this.direction == Tank.direction_left){
			i = this.getLocation_y() / Constant.WIDTH_HEIGHT ;
			j = this.getLocation_x() / Constant.WIDTH_HEIGHT ;
		}else if(this.direction == Tank.direction_right){
			i = this.getLocation_y() / Constant.WIDTH_HEIGHT ;
			j = (this.getLocation_x() + Constant.TANK_HEIGHT) / Constant.WIDTH_HEIGHT ;
		}
		
		
		if( i >= 0 && i < Constant.MAP_Array_SIZE && j >= 0 && j < Constant.MAP_Array_SIZE_HEIGHT ){
			//in different directions
			switch(this.direction){
			case Tank.direction_up:
				/**
				 * 000022000
				 * 000011000
				 * 000011000
				 * 000011000
				 * 000011000
				 */
				if( i == 0 ){
					flag = false ;
				}else{
					for( int index = 0 ; index < Constant.TANK_WIDTH / Constant.WIDTH_HEIGHT; index ++ ){
						if( this.mapArray[i - 1].charAt(j + index) == '2' || this.mapArray[i - 1].charAt(j + index) == '3'){
							flag = false ;
							break ;
						}
					}
				}
				break ;
			case Tank.direction_down:
				/**
				 * 000011000
				 * 000011000
				 * 000011000
				 * 000011000
				 * 000022000
				 */
				if( i == Constant.MAP_Array_SIZE - 1 ){
					flag = false ;
				}else{
					for( int index = 0 ; index < Constant.TANK_WIDTH / Constant.WIDTH_HEIGHT; index ++ ){
						if( this.mapArray[i].charAt(j + index) == '2' || this.mapArray[i].charAt(j + index) == '3'){
							flag = false ;
							break ;
						}
					}
				}
				break ;
			case Tank.direction_left:
				/**
				 * 000211110
				 * 000211110
				 * 000000000
				 * 000000000
				 * 000000000
				 */
				if( j == 0 ){
					flag = false ;
				}else{
					for( int index = 0 ; index < Constant.TANK_WIDTH / Constant.WIDTH_HEIGHT; index ++ ){
						if( this.mapArray[i + index].charAt(j - 1) == '2' || this.mapArray[i + index].charAt(j - 1) == '3'){
							flag = false ;
							break ;
						}
					}
				}
				break ;
			case Tank.direction_right:
				/**
				 * 000011112
				 * 000011112
				 * 000000000
				 * 000000000
				 * 000000000
				 */
				if( j == Constant.MAP_Array_SIZE_HEIGHT - 1 ){
					flag = false ;
				}else{
					for( int index = 0 ; index < Constant.TANK_WIDTH / Constant.WIDTH_HEIGHT; index ++ ){
						if( this.mapArray[i + index].charAt(j) == '2' || this.mapArray[i + index].charAt(j) == '3'){
							flag = false ;
							break ;
						}
					}
				}
				break ;
			}
		}else{
			/*//坦克不能重合在一起!
			flag =  judege();*/
		}
		
		if( flag == false)
			return false ;
		else{
			//坦克不能重合在一起!
			flag =  judege();
			return flag ;
		}
	}
	
	/**
	 * 判断向前运动是否有坦克。有就返回false,没有返回true（x,y是坦克的下一个位置坐标）
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean judege(){
		boolean flag = true ;
		
		//int x = this.location_x , y = this.location_y ;
		
		if( this.tank_type == Tank.ENEMY){
			//根据方向设置位置坐标
			/*if( this.direction == Tank.direction_up)
				y -= Constant.ENEMY_SPEED ;
			else if( this.direction == Tank.direction_down)
				y += Constant.ENEMY_SPEED ;
			else if( this.direction == Tank.direction_left)
				x -= Constant.ENEMY_SPEED ;
			else if( this.direction == Tank.direction_right)
				x += Constant.ENEMY_SPEED ;*/
			
			/*for(Enemy e : this.mainFrame.enemyTankContainer.getEnemyList()){
				if( this != e){//不和自己比较
					if(x >= e.getLocation_x() && x <= e.getLocation_x() + e.getWidth()
							&& y >= e.getLocation_y() && y <= e.getLocation_y() + e.getHeight()){
						flag = false ;
					}else if(x + this.width>= e.getLocation_x() && x + this.width<= e.getLocation_x() + e.getWidth()
							&& y >= e.getLocation_y() && y <= e.getLocation_y() + e.getHeight()){
						flag = false ;
					}else if(x >= e.getLocation_x() && x <= e.getLocation_x() + e.getWidth()
							&& y + this.width >= e.getLocation_y() && y + this.width <= e.getLocation_y() + e.getHeight()){
						flag = false ;
					}else if(x + this.width>= e.getLocation_x() && x + this.width <= e.getLocation_x() + e.getWidth()
							&& y + this.width>= e.getLocation_y() && y + this.width<= e.getLocation_y() + e.getHeight()){
						flag = false ;
					}
				}
			}*/
			
		}else if( this.tank_type == Tank.HREO){
			//根据方向设置位置坐标
			/*if( this.direction == Tank.direction_up)
				y -= Constant.TANK_SPEED_HERO ;
			else if( this.direction == Tank.direction_down)
				y += Constant.TANK_SPEED_HERO ;
			else if( this.direction == Tank.direction_left)
				x -= Constant.TANK_SPEED_HERO ;
			else if( this.direction == Tank.direction_right)
				x += Constant.TANK_SPEED_HERO ;*/
			
		/*	for(Enemy e : this.mainFrame.enemyTankContainer.getEnemyList()){
				if(x >= e.getLocation_x() && x <= e.getLocation_x() + e.getWidth()
						&& y >= e.getLocation_y() && y <= e.getLocation_y() + e.getHeight()){
					flag = false ;
				}else if(x + this.width>= e.getLocation_x() && x + this.width<= e.getLocation_x() + e.getWidth()
						&& y >= e.getLocation_y() && y <= e.getLocation_y() + e.getHeight()){
					flag = false ;
				}else if(x >= e.getLocation_x() && x <= e.getLocation_x() + e.getWidth()
						&& y + this.width >= e.getLocation_y() && y + this.width <= e.getLocation_y() + e.getHeight()){
					flag = false ;
				}else if(x + this.width>= e.getLocation_x() && x + this.width <= e.getLocation_x() + e.getWidth()
						&& y + this.width>= e.getLocation_y() && y + this.width<= e.getLocation_y() + e.getHeight()){
					flag = false ;
				}
			}*/
		}
		
		return flag ;
	}
	
	public int getDirection() {
		return direction;
	}
	public int getHeight() {
		return height;
	}
	public int getLocation_x() {
		return location_x;
	}
	public int getLocation_y() {
		return location_y;
	}
	public int getSpeed() {
		return speed;
	}
	public int getWidth() {
		return width;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setLocation_x(int location_x) {
		this.location_x = location_x;
	}
	public void setLocation_y(int location_y) {
		this.location_y = location_y;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getActive_area_x() {
		return active_area_x;
	}
	public void setActive_area_x(int active_area_x) {
		this.active_area_x = active_area_x;
	}
	public int getActive_area_y() {
		return active_area_y;
	}
	public void setActive_area_y(int active_area_y) {
		this.active_area_y = active_area_y;
	}
	public int getTank_type() {
		return tank_type;
	}
	public void setTank_type(int tank_type) {
		this.tank_type = tank_type;
	}
	public int getHeroLifeValue() {
		return heroLifeValue;
	}
	public void setHeroLifeValue(int heroLifeValue) {
		this.heroLifeValue = heroLifeValue;
	}

	
}