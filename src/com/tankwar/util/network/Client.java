package com.tankwar.util.network;

import java.net.Socket;

//客户端
public class Client {
	
	/**
	 * 连接
	 */
	public static void connect(){
		Socket socket = null ;
		
		try{
			socket = new Socket("127.0.0.1", 8888);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		connect();
	}

}
