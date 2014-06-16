package com.tankwar.util.network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;

//网络服务器
public class SystemServer extends JFrame implements ActionListener, Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel panel = null ;
	JLabel label = null ;
	JButton button = null ;
	
	int playsNum = 0 ; //玩家数量
	
	ServerSocket serverSocket = null ;
	boolean serverStatus = false ;
	
	/**
	 * 初始化
	 */
	public void init(){
		try {
			@SuppressWarnings("unused")
			Socket listeningSocket = null ;
			serverSocket = new ServerSocket(8888);
			System.out.println("*****启动服务器*****端口:" + 8888 );
			
			while(true){
				listeningSocket = serverSocket.accept();
				System.out.println("玩家进入*****");
				playsNum ++ ;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 停止服务器
	 */
	public void stopNetworkServer(){
		System.out.println("*****停止服务器*****");

	}

	public SystemServer(){
		
		//initNetworkServer();
		
		panel = new JPanel();
		label = new JLabel("在线玩家数量:" + playsNum);
		button = new JButton("启动服务器");
		button.addActionListener(this);
		
		panel.add(label);
		panel.add(button);
		
		this.setUndecorated(true);
		JRootPane rp = getRootPane();   
        rp.setWindowDecorationStyle(JRootPane.FRAME);
        this.add(panel);
        this.setTitle("坦克大战:服务器");
		this.setSize(400, 400);
		this.setLocation(0, 0);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		try {   
			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");   
	    } catch (Exception e) {   
	    	System.err.println("皮肤加载失败！");   
	    }
		
		new SystemServer();
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == button){
			if( serverStatus == false){
				
				this.button.setText("停止服务器");
				serverStatus = true ;
			}else{
				this.stopNetworkServer();
				this.button.setText("启动服务器");
				serverStatus = false ;
			}
		}
	}

	public void run() {
		init();
	}
}