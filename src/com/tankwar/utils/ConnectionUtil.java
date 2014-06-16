package com.tankwar.utils;

import java.sql.*;


//singleton(单例模式)
/**
 * @author admin
 *
 */
public class ConnectionUtil {

	private static Connection conn = null ;
	
	private String dbType = Constant.dbType;//数据库类型
	
	String urlOracle = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
	String urlMysql = "jdbc:mysql://222.18.159.6:3306/appTest?"+ "user=appTest&password=appTest"; 
	
	
	private ConnectionUtil(){
		
		if( dbType.equals("mysql")){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(urlMysql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if( dbType.equals("oracle")){
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(urlOracle, "tankwar", "tankwar");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getInstance(){
		if( conn == null )
			new ConnectionUtil();
		return conn ;
	}
}
