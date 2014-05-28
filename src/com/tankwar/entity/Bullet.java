package com.tankwar.entity;

import com.tankwar.domain.PropsContainer;
import com.tankwar.utils.Constant;
import com.tankwar.utils.DBHandler;
import com.tankwar.utils.Game;
import com.tankwar.view.MainFrame;

/**
 * the bullet
 * @author Evergreen
 *
 */
public class Bullet extends Thread{
	
	/**
	 * direction status
	 */
	public static final int direction_up = 0 ;
	public static final int direction_left = 2 ;
	public static final int direction_down = 1 ;
	public static final int direction_right = 3 ;
	public static final int BULLET_HERO = 0 ; //hero's bullet
	public static final int BULLET_ENEMY = 1 ;//enemy's bullet
	
	/**
	 * bullet status
	 */
	public static final int ALIVE = 0 ;
	public static final int DEAD = 1 ; 
	
	private Tank tank = null ; 
	private int location_x = 0 ;
	private int location_y = 0 ;
	private int speed = Constant.BULLET_SPEED ;
	private int active_area_x = Constant.active_area_x ;
	private int active_area_y = Constant.active_area_y ;
	private int direction = 0 ;//direction
	private int width = Constant.BULLET_WIDTH;//bullet width
	private int status = Bullet.ALIVE;
	private MainFrame mainFrame = null ;//living frame
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	/**
	 * to judge if the current bullet hit something
	 * @return
	 */
	public void ifHitSomething(){
		//get map
		String[] sArray = this.mainFrame.mainPanel.sArray;
		//get index 
		int index_i = this.location_y / Constant.WIDTH_HEIGHT ;
		int index_j = this.location_x / Constant.WIDTH_HEIGHT ;
		
		if(index_i >= 0 && index_j>=0 && index_i < Constant.MAP_Array_SIZE && index_j < Constant.MAP_Array_SIZE_HEIGHT && sArray[index_i].charAt(index_j) != '0'){
			
			if( sArray[index_i].charAt(index_j) == '2'){
				
				char[] temp = this.mainFrame.mainPanel.sArray[index_i].toCharArray();
				temp[index_j] = '0';
				
				if( this.direction == Tank.direction_up && index_j + 1 <= Constant.MAP_Array_SIZE_HEIGHT){
					if(temp[index_j + 1] == '2')
						temp[index_j + 1] = '0';
					else if( index_j - 1 >= 0 && temp[index_j - 1] == '2')
						temp[index_j - 1] = '0';
					this.mainFrame.mainPanel.sArray[index_i] = new String(temp);
				}else if( this.direction == Tank.direction_down && index_j + 1 <= Constant.MAP_Array_SIZE_HEIGHT){
					if(temp[index_j + 1] == '2')
						temp[index_j + 1] = '0';
					else if( index_j - 1 >= 0 && temp[index_j - 1] == '2')
						temp[index_j - 1] = '0';
					this.mainFrame.mainPanel.sArray[index_i] = new String(temp);
				}else if( this.direction == Tank.direction_left && index_i + 1 <= Constant.MAP_Array_SIZE){
					this.mainFrame.mainPanel.sArray[index_i] = new String(temp);
					temp = this.mainFrame.mainPanel.sArray[index_i + 1].toCharArray();
					temp[index_j] = '0';
					this.mainFrame.mainPanel.sArray[index_i + 1] = new String(temp);
				}else if( this.direction == Tank.direction_right && index_i + 1 <= Constant.MAP_Array_SIZE){
					this.mainFrame.mainPanel.sArray[index_i] = new String(temp);
					temp = this.mainFrame.mainPanel.sArray[index_i + 1].toCharArray();
					temp[index_j] = '0';
					this.mainFrame.mainPanel.sArray[index_i + 1] = new String(temp);
				}
				
				
				//temp = this.mainFrame.mainPanel.sArray[index_i].toCharArray();
				//temp[index_j] = '0';
				this.setStatus(Bullet.DEAD);
			}else if(sArray[index_i].charAt(index_j) == '3'){
				this.setStatus(Bullet.DEAD);
			}
		}else{//hit tank
			if(this.type == Bullet.BULLET_ENEMY  ){//if current bullet hit the hero
				Hero hero = this.mainFrame.hero ;
				if( hero.getDirection() == Tank.direction_up || hero.getDirection() == Tank.direction_down){
					if( this.location_x >= hero.getLocation_x() && this.location_x <= (hero.getLocation_x() + hero.getWidth()) && 
							this.location_y >= hero.getLocation_y() && this.location_y <= (hero.getLocation_y() + hero.getHeight()) ){
							//生命值减去1
							hero.setHeroLifeValue(hero.getHeroLifeValue() - 1);
							if( hero.getHeroLifeValue() == 0 ){
								hero.setStatus(Tank.SHOTED);
								Game.status = Game.STATUS_GAME_OVER;//游戏结束
								DBHandler.updateBestScore(Game.enemy_killed * Constant.singleScore);
								this.mainFrame.enemyTankContainer.gameOver();
							}
							this.setStatus(DEAD);
					}
				}else{
					if( this.location_x >= hero.getLocation_x() && this.location_x <= (hero.getLocation_x() + hero.getHeight()) && 
							this.location_y >= hero.getLocation_y() && this.location_y <= (hero.getLocation_y() + hero.getWidth()) ){
							//生命值减去1
							hero.setHeroLifeValue(hero.getHeroLifeValue() - 1);
							if( hero.getHeroLifeValue() == 0 ){
								hero.setStatus(Tank.SHOTED);
								Game.status = Game.STATUS_GAME_OVER;//游戏结束
								DBHandler.updateBestScore(Game.enemy_killed * Constant.singleScore);
								this.mainFrame.enemyTankContainer.gameOver();
							}
							this.setStatus(DEAD);
					}
				}
			}else if( this.type == Bullet.BULLET_HERO ){//if hero's bullet hit the enemy
				for(Enemy enemy : this.mainFrame.enemyTankContainer.getEnemyList()){
					// if enemy is alive
					if( enemy.getStatus() != Tank.DIED){
						if( enemy.getDirection() == Tank.direction_up || enemy.getDirection() == Tank.direction_down ){
							if( this.location_x >= enemy.getLocation_x() && this.location_x <= (enemy.getLocation_x() + enemy.getWidth()) && 
									this.location_y >= enemy.getLocation_y() && this.location_y <= (enemy.getLocation_y() + enemy.getHeight())){
								enemy.setStatus(Tank.SHOTED);
								Game.enemy_killed ++ ;
								this.setStatus(DEAD);
								//产生一个道具
								PropsContainer.newProp();
							}
						}else{
							if( this.location_x >= enemy.getLocation_x() && this.location_x <= (enemy.getLocation_x() + enemy.getHeight()) && 
									this.location_y >= enemy.getLocation_y() && this.location_y <= (enemy.getLocation_y() + enemy.getWidth())){
								enemy.setStatus(Tank.SHOTED);
								Game.enemy_killed ++ ;
								this.setStatus(DEAD);
								//产生一个道具
								PropsContainer.newProp();
								
							}
						}
						if( Game.enemy_killed == Constant.defaultEnemiesNum){
							Game.enemy_killed = 0 ;
							this.mainFrame.nextStage();
						}
					}
				}
			}
			
			//是否击中了boss
			if( this.status == Bullet.ALIVE){
				if( this.location_x >= Game.BOSS_TANK_Y * Constant.WIDTH_HEIGHT && this.location_x <= (Game.BOSS_TANK_Y + 3) * Constant.WIDTH_HEIGHT &&
						this.location_y >= Game.BOSS_TANK_X * Constant.WIDTH_HEIGHT && this.location_y <= (Game.BOSS_TANK_X + 3) * Constant.WIDTH_HEIGHT){
					System.out.println("boss挂了！************** Game over!");
					this.setStatus(DEAD);
				}
			}
		}
	}
	
	/**
	 * to judge if the bullet is in the right area
	 * @return
	 */
	public boolean judgeIfIn(){
		return (this.location_x <= this.active_area_x && this.location_y <= this.active_area_y && this.location_x > 0 && this.location_y > 0)? true : false ;
	}
	
	public void run(){
		while(this.status == Bullet.ALIVE){
			try {
				Thread.sleep(Constant.bullet_sleep_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if( Game.status == Game.STATUS_ON){//judge the status
				if(judgeIfIn()){
					
					if( this.status == Bullet.ALIVE){	
						this.move();
						ifHitSomething();
					}
				}else{
					this.setStatus(Bullet.DEAD);
					break ;
				}
			}
		}
	}
	
	public void move(){
		if( this.direction == Bullet.direction_up){
			this.location_y -= this.speed ;
		}else if(this.direction == Bullet.direction_down){
			this.location_y += this.speed ;
		}else if(this.direction == Bullet.direction_left){
			this.location_x -= this.speed ;
		}else if(this.direction == Bullet.direction_right){
			this.location_x += this.speed ;
		}
		
		if( this.location_x <= 0 || this.location_x >= this.active_area_x || this.location_y <= 0 || this.location_y >= this.active_area_y)
			this.setStatus(DEAD);
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	private int type = BULLET_HERO ;//bullet type
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Bullet(Tank tank){
		this.tank = tank ;
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

	public void setLocation_x(int location_x) {
		this.location_x = location_x;
	}
	public void setLocation_y(int location_y) {
		this.location_y = location_y;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getActive_area_x() {
		return active_area_x;
	}

	public void setActive_area_x(int activeAreaX) {
		active_area_x = activeAreaX;
	}

	public int getActive_area_y() {
		return active_area_y;
	}

	public void setActive_area_y(int activeAreaY) {
		active_area_y = activeAreaY;
	}
	
	public Tank getTank() {
		return tank;
	}

	public void setTank(Tank tank) {
		this.tank = tank;
	}
}