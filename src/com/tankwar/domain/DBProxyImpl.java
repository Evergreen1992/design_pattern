package com.tankwar.domain;

import java.util.List;
import com.tankwar.entity.Enemy;
import com.tankwar.entity.Hero;
import com.tankwar.utils.DBHandler;

public class DBProxyImpl implements DBProxy{
	
	DBHandler dh = new DBHandler();
	
	public void recordGameData(List<Enemy> emList, Hero hero) {
		dh.recordGameData(emList, hero);
	}

	/**
	 * 恢复游戏
	 */
	public List<Enemy> resumeLastGame(String[] sArray) {
		return dh.resumeLastGame(sArray);
	}
}
