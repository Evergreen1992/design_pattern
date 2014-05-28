package com.tankwar.utils;

import java.sql.*;


//singleton(单例模式)
/**
 * @author Evergreen
 *
 */
public class ConnectionUtil {

	private static Connection conn = null ;
	
	private ConnectionUtil(){
		try {
			String url = "jdbc:mysql://222.18.159.6:3306/appTest?"+ "user=appTest&password=appTest"; 
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getInstance(){
		if( conn == null )
			new ConnectionUtil();
		return conn ;
	}
}
