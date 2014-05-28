package com.tankwar.entity;

import com.tankwar.entity.interfaces.PropStatus;
import com.tankwar.entity.interfaces.PropType;

/**
 * 游戏道具
 * @author Administrator
 *
 */
public class Prop {
	
	private int x = 100 ;//坐标
	private int y = 100 ;//
	private int type = PropType.mime ; //类型
	private int startTime = 0 ; //一共要出现的时间
	private int status = PropStatus.ALIVE ; //状态 0: alive 1:dead 

	
	public Prop(){
		this.x = (int)(Math.random() * 400); 
		this.y = (int)(Math.random() * 400); 
		this.type = (int)(Math.random() * 400 % 4) ;
		this.startTime = (int)(System.currentTimeMillis() / 1000 );
		System.out.println("产生道具............" + this.x + " , " + this.y);
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setType(int type) {
		this.type = type;
	} 
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
}