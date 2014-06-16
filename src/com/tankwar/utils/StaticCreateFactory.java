package com.tankwar.utils;

import com.tankwar.entity.Enemy;

/**
 * 简单工程模式
 * @author admin
 *
 */
public class StaticCreateFactory {
	
	/**
	 * get enemy object
	 * @return
	 */
	public static Enemy newTank(int x, int y, int direction, int type, String[] mArray){
		
		if( x % 10 != 0 ){
			x = x - x % 10 ;
		}
		
		if( y % 10 != 0 ){
			y = y - y % 10 ;
		}
		
		Enemy enemy = new Enemy(mArray);
		enemy.setLocation_x(x);
		enemy.setLocation_y(y);
		enemy.setDirection(direction);
		enemy.setTank_type(type);
		
		return enemy ;
	}

	public static void main(String[] args) {
	
	}

}
