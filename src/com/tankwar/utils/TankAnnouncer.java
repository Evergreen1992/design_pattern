package com.tankwar.utils;

import java.util.ArrayList;
import java.util.List;

import com.tankwar.entity.Enemy;

//抽象通知类
public abstract class TankAnnouncer {

	public List<Enemy> enemiesList = new ArrayList<Enemy>();
	
	public void attach(Enemy tank){
		this.enemiesList.add(tank);
	}
	
	public void dettach(Enemy tank){
		this.enemiesList.remove(tank);
	}
	
	public abstract void gameOver();
}
