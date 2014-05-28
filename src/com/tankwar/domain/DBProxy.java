package com.tankwar.domain;

import java.util.List;
import com.tankwar.entity.Enemy;
import com.tankwar.entity.Hero;


/**
 * 数据库代理对象接口
 * @author Evergreen
 *
 */
public interface DBProxy {
	public void recordGameData(List<Enemy> emList, Hero hero );//记录退出时的游戏存档
	public List<Enemy> resumeLastGame(String[] sArray);//游戏记录恢复
}
