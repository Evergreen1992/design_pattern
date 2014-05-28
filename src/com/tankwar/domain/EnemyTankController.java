package com.tankwar.domain;

import com.tankwar.entity.Enemy;
import com.tankwar.entity.Tank;
import com.tankwar.utils.Constant;
import com.tankwar.utils.Game;

/**
 * 控制敌人坦克动作的控制类,让enemy自动以某种方式运动。
 * @author Administrator
 *
 */
public class EnemyTankController {
	
	/**
	 * 判断坦克能否朝着某个方向行走。(是否有墙壁阻隔)
	 * @param direction(当前坦克运动方向)
	 * @param i（当前坦克位置）
	 * @param j
	 * @param mapArray(场景数组)
	 * @return
	 */
	public static boolean ifCanWalkInThisDirection(int direction, int x, int y , String[] mapArray){
		boolean flag = true ;
		
		int i = 0 , j = 0 ;//坦克位置
		if(direction == Tank.direction_up){
			i = y / Constant.WIDTH_HEIGHT ;
			j = x / Constant.WIDTH_HEIGHT ;
		}else if( direction == Tank.direction_down){
			i = ( y + Constant.TANK_HEIGHT) / Constant.WIDTH_HEIGHT ;
			j = x / Constant.WIDTH_HEIGHT ;
		}else if( direction == Tank.direction_left){
			i = y / Constant.WIDTH_HEIGHT ;
			j = x / Constant.WIDTH_HEIGHT ;
		}else if( direction == Tank.direction_right){
			i = y / Constant.WIDTH_HEIGHT ;
			j = (x + Constant.TANK_HEIGHT) / Constant.WIDTH_HEIGHT ;
		}
		
		if( i >= 0 && i < Constant.MAP_Array_SIZE && j >= 0 && j < Constant.MAP_Array_SIZE_HEIGHT ){
			//in different directions
			switch(direction){
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
						if( mapArray[i - 1].charAt(j + index) == '2' || mapArray[i - 1].charAt(j + index) == '3'){
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
						if( mapArray[i].charAt(j + index) == '2' || mapArray[i].charAt(j + index) == '3'){
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
						if( mapArray[i + index].charAt(j - 1) == '2' || mapArray[i + index].charAt(j - 1) == '3'){
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
						if( mapArray[i + index].charAt(j) == '2' || mapArray[i + index].charAt(j) == '3'){
							flag = false ;
							break ;
						}
					}
				}
				break ;
			}
		}else{
			flag =  true ;
		}
		
		return flag ;
	}
	
	/**
	 * 运动方式1(朝着boss的方向行走)
	 * 返回一个命令:
	 * 0 向上
	 * 1 向下
	 * 2 向左
	 * 3 向右
	 * 4 射击
	 * @param enemy
	 * @param array
	 * @return
	 */
	public static int getOrder(Enemy enemy){
		//命令
		int order = 0 ;
		//boss坦克在数组中的位置
		int boss_y = Game.BOSS_TANK_X * Constant.WIDTH_HEIGHT ;
		int boss_x = Game.BOSS_TANK_Y * Constant.WIDTH_HEIGHT ;
		//敌人位置
		int x = enemy.getLocation_x() ;
		int y = enemy.getLocation_y() ;
		//
		String[] mapArray = enemy.getMainFrame().mapArray;
		
		if( enemy.getLocation_y() < boss_y  && ifCanWalkInThisDirection(Tank.direction_down, x, y, mapArray)){
			//if( ifCanWalkInThisDirection(Tank.direction_down, x, y, mapArray) == true){
				order = Tank.direction_down ; 
			//}else{
				//order = 4 ; 
			//}
			//System.out.println("******向下走******");
			
		}else if(enemy.getLocation_x() < boss_x && ifCanWalkInThisDirection(Tank.direction_right,  x, y, mapArray) == true){
			//System.out.println("******向右走******");
			order = Tank.direction_right ;
		}else if(enemy.getLocation_x() > boss_x && ifCanWalkInThisDirection(Tank.direction_left,  x, y, mapArray) == true){
			//System.out.println("******向左走******");
			order = Tank.direction_left ;
		}else{
			//当前方向行走
			if( ifCanWalkInThisDirection(enemy.getDirection(),  x, y, mapArray) == true){
				//System.out.println("******向本方向继续走完******");
				order = enemy.getDirection() ;
			}else{
				order = (int)(Math.random() * 100) % 4 ;
			}
		}
		
		return order ;
	}
}
