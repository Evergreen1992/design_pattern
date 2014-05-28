package com.tankwar.entity;

import com.tankwar.domain.EnemyTankController;
import com.tankwar.utils.Constant;
import com.tankwar.utils.Game;

/**
 * enemy
 * @author Administrator
 *
 */
public class Enemy extends Tank implements Runnable, TankAdapter, TankObserver{

	public boolean gameOverFlag = false ;
	
	
	public Enemy(String[] mapArray) {
		super(mapArray);
	}

	public void run() {
		int order = 0 ;
		int counter = 0 ;
		
		int sleepTime = 0 ; //睡眠时间
		
		while( true ){
			try{
				Thread.sleep(Constant.ENEMY_SLEEP_TIME);
				
				counter ++ ; 
				if( counter == 100)
					counter = 0 ;
				
				//正常状态
				if( this.getStatus() == Tank.ALIVE && gameOverFlag == false){
					if( Game.status == Game.STATUS_ON){
						
						order = EnemyTankController.getOrder(this);//获取行动指令
						
						switch(order){
						case Tank.direction_up :
							this.go_up();
							break ;
						case Tank.direction_down :
							this.go_down();
							break ;
						case Tank.direction_left :
							this.go_left();
							break ;
						case Tank.direction_right :
							this.go_right();
							break ;
						case 4 :
							this.fire();
							break ;
						}
						
						if( counter % 4 == 0 ){
							this.fire();
						}
					}
				}else if(this.getStatus() == Tank.SLEEP && gameOverFlag == false){//休眠中
					if( sleepTime == 10){
						this.setStatus(Tank.ALIVE);//恢复状态
						sleepTime = 0 ;
					}
					else
						sleepTime ++ ;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 敌人坦克运动算法
	 * @param enemy
	 */
	public void movement(Enemy enemy){
		
	}

	@Override
	public void fire() {
		super.fire();
	}

	@Override
	public void go_left() {
		super.go_left();
	}

	@Override
	public void go_right() {
		super.go_right();
	}

	@Override
	public void go_up() {
		super.go_up();
	}

	@Override
	public void go_down() {
		super.go_down();
	}

	@Override
	public void response() {
		//游戏结束。设置状态
		gameOverFlag = true ;
	}
}