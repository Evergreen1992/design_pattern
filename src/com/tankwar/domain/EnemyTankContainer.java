package com.tankwar.domain;

import java.util.ArrayList;
import java.util.List;
import com.tankwar.entity.Enemy;
import com.tankwar.entity.Tank;
import com.tankwar.utils.StaticCreateFactory;
import com.tankwar.view.MainFrame;

/**
 *  enemy 坦克容器池(池里面初始化一定的坦克对象，可以被使用，被回收。)
 *  @author Evergreen
 *
 */
public class EnemyTankContainer {
	
	public MainFrame mainFrame = null ;
	private List<Enemy> enemyList = new ArrayList<Enemy>();//所有的坦克
	
	
	public EnemyTankContainer(MainFrame mainFrame){
		this.mainFrame = mainFrame ;
	}
	
	/**
	 * 游戏恢复
	 */
	public void resume(){
		for(Enemy e : this.enemyList){
			e.setMainFrame(mainFrame);
			new Thread(e).start();
		}
	}
	
	//清除线程数据
	public void stopThread(){

	}
	
	/**
	 * 
	 * @param size
	 */
	public void initEnemies(int enemySize){
		int x = 0 ; // < 800
		int y = 0 ; // < 200
		
		
		Enemy enemy = null ;
		Thread thread = null ;
		for( int i = 0; i<enemySize; i++){
			x = (int)(Math.random() * 700) ;
			y = (int)(Math.random() * 200) ;
			
			System.out.println(" x = " + x + " , y = " + y);
			
			enemy = StaticCreateFactory.newTank(x , y ,  (int)(Math.random() * 100 % 4), Tank.ENEMY, this.mainFrame.mapArray);
			thread = new Thread(enemy);
			thread.start();
			
			enemy.setMainFrame(this.mainFrame);
			this.enemyList.add(enemy);
		}
	}
	
	public void gameOver() {
		//游戏结束。通知其余对象
		System.out.println("Game is Over ..... ");
		//通知敌人坦克游戏结束
		for(Enemy enemy : this.enemyList){
			enemy.response();
		}
	}
	
	/**
	 * 获取游戏的敌人坦克（获取未死亡的坦克，并移除死亡坦克）
	 * @return
	 */
	public List<Enemy> getEnemyList() {
		/*validEnemyList = new ArrayList<Enemy>();
		
		for(Enemy e : this.enemyList){
			if(e.getStatus() != Tank.DIED){
				validEnemyList.add(e);
			}
		}
		
		return validEnemyList;*/
		return this.enemyList;
	}
	
	public int getAliveNumber(){
		int n = 0 ;
		
		for( Enemy e : this.enemyList){
			if(e.getStatus() != Tank.DIED)
				n ++ ;
		}
		
		return n ;
	}
	
	/**
	 * 设置坦克属性
	 * @param status
	 */
	public void setEnemyStatus(int status){
		for( Enemy e : this.enemyList){
			if( e.getStatus() == Tank.ALIVE)
				e.setStatus(status);
		}
	}

	public void setEnemyList(List<Enemy> enemyList) {
		this.enemyList = enemyList;
	}
	
}