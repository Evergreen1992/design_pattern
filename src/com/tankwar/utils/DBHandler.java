package com.tankwar.utils;

import java.sql.*;
import java.util.List;
import java.util.Vector;
import com.tankwar.entity.Enemy;
import com.tankwar.entity.Hero;
import com.tankwar.entity.Tank;

/**
 * database methods.数据库操作类
 * @author Evergreen
 *
 */
public class DBHandler extends DBFactory{
	
	/**
	 * 恢复数据
	 */
	public Vector<Enemy> resumeLastGame(String[] sArray) {
		Vector<Enemy> emList = new Vector<Enemy>();
		ResultSet rs = getResultSet("select * from t_enemy");
		
		try {
			Enemy enemy = null ;
			while(rs.next()){
				enemy = StaticCreateFactory.newTank(rs.getInt("x"), rs.getInt("y"), rs.getInt("direction"), Tank.ENEMY, sArray);
				emList.add(enemy);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(rs);
		}
		
		return emList ;
	}
	/**
	 * 未结束游戏,退出游戏时存档
	 */
	public void recordGameData(List<Enemy> emList, Hero hero ){
		//先生成游戏历史记录。t_resume
		String sql = "update t_resume set x = " + hero.getLocation_x() + " , y = " + hero.getLocation_y() + ", direction = " + hero.getDirection() + "  where id = 1";
		executeSql(sql);
		
		//首先删除旧记录
		executeSql("delete from t_enemy");
		//记录敌人信息
		String enemySql = "";
		for(Enemy enemy : emList){
			if( enemy.getStatus() == Tank.ALIVE){
				enemySql = "insert into t_enemy (x, y, resume_id, direction) values(" + enemy.getLocation_x() + ", " + enemy.getLocation_y() + ", 1, " + enemy.getDirection() + ");";
				executeSql(enemySql);
			}
		}
	}
	
	/**
	 * get best game score
	 * @return
	 */
	public static int getHistoryHigh(){
		Connection conn = ConnectionUtil.getInstance();
		PreparedStatement pstmt = null ;
		String sql = "select history_high from t_historyScore where id = 1";
		ResultSet rs = null ;
		int score = 0 ;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if( rs.next())
				score = rs.getInt("history_high");
		} catch (SQLException e) {
			System.out.println("error get best score...");
		}finally{
			close(rs);
			close(pstmt);
		}
		
		return score ;
	}
	
	/**
	 * update best game score
	 * @param newScore
	 */
	public static void updateBestScore(int newScore){
		int oldHigh = getHistoryHigh();
		if( newScore > oldHigh)
			executeSql("update t_historyScore set history_high = " + newScore + " where id = 1");
	}
	
	public static void main(String[] args) {
		//
		/*updateBestScore(100);
		System.out.println(getHistoryHigh());*/
		Hero h = new com.tankwar.entity.Hero(null);
		h.setLocation_x(200);
		h.setLocation_y(400);
		h.setDirection(3);
		new DBHandler().recordGameData(null, h);
	}
}