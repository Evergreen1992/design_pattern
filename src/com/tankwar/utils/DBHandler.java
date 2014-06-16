package com.tankwar.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.tankwar.entity.Enemy;
import com.tankwar.entity.Hero;
import com.tankwar.entity.Tank;

/**
 * database methods.数据库操作类
 * @author admin
 */
public class DBHandler extends DBFactory{
	
	/**
	 * 获取存档数据集合
	 * @return
	 */
	public List<String> getRecordData(){
		List<String> recordData = new ArrayList<String>();
		ResultSet rs = this.getResultSet("select * from t_record order by time desc");
		StringBuffer sb = null ;
		
		try {
			while( rs.next() ){
				sb = new StringBuffer();
				sb.append(rs.getString("name") + ",");
				sb.append(rs.getInt("stage") + ",");
				sb.append(rs.getInt("score") + ",");
				sb.append(rs.getString("time"));
				recordData.add(sb.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recordData ;
	}
	
	/**
	 * 保存存档记录
	 */
	public void saveRecord(String name){
		if( name == null || name.equals(""))
			name = "未命名存档";
		
		int score = Game.enemy_killed * 100 + Game.propScore ;
		PreparedStatement pstmt = null ;
		try{
			pstmt = ConnectionUtil.getInstance().prepareStatement(""
					+ "insert into t_record(id, time, stage, score, name) values(id_creator.nextval,?,?,?,?)");
			pstmt.setDate(1, new java.sql.Date (new java.util.Date().getTime()));
			pstmt.setInt(2, Game.stage);
			pstmt.setInt(3, score);
			pstmt.setString(4, name);
			
			pstmt.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if( pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 保存排名记录
	 * @param player
	 */
	public void saveRanking(String player){
		if( player == null || player.equals(""))
			player = "无名";

		int score = Game.enemy_killed * 100 + Game.propScore ;
		PreparedStatement pstmt = null ;
		try{
			pstmt = ConnectionUtil.getInstance().prepareStatement(""
					+ "insert into t_ranking(id, player, score, stage, time) values(id_creator.nextval,?,?,?,?)");
			pstmt.setString(1, player);
			pstmt.setInt(2, score);
			pstmt.setInt(3, Game.stage);
			pstmt.setDate(4, new java.sql.Date (new java.util.Date().getTime()));
			
			pstmt.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if( pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 获取排名榜集合
	 * @return
	 */
	public List<String> getRankingData(){
		List<String> ranking = new ArrayList<String>();
		ResultSet rs = this.getResultSet("select * from t_ranking order by score desc");
		StringBuffer sb = null ;
		
		try {
			while( rs.next() ){
				sb = new StringBuffer();
				sb.append(rs.getString("player") + ",");
				sb.append(rs.getInt("stage") + ",");
				sb.append(rs.getInt("score") + ",");
				sb.append(rs.getString("time"));
				ranking.add(sb.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ranking ;
	}
	
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
	public int getHistoryHigh(){
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
	public void updateBestScore(int newScore){
		int oldHigh = getHistoryHigh();
		if( newScore > oldHigh)
			executeSql("update t_historyScore set history_high = " + newScore + " where id = 1");
	}
	
	public static void main(String[] args) {
		List<String> list = new DBHandler().getRankingData();
		for(String s : list){
			System.out.println(s);
		}
	}
}