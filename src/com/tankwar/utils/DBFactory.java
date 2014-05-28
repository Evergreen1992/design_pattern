package com.tankwar.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *工厂模式
 * @author Evergreen
 *
 */
public class DBFactory {

	public static void close(ResultSet rs){
		if( rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void close(PreparedStatement pstmt){
		if( pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void close(Connection conn){
		if( conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * execute some sql update, delete, insert
	 * @param sql
	 */
	public static void executeSql(String sql){
		System.out.println("执行:" + sql);
		Connection conn = ConnectionUtil.getInstance();
		PreparedStatement pstmt = null ;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error get best score...");
		}finally{
			close(pstmt);
		}
		
	}
	
	/**
	 * 查询结果集
	 * @param qSql
	 * @return
	 */
	public ResultSet getResultSet(String qSql){
		Connection conn = ConnectionUtil.getInstance();
		ResultSet rs = null ;
		
		try {
			rs = conn.prepareStatement(qSql).executeQuery() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs ;
	}

}
