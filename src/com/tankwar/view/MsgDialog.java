package com.tankwar.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import com.tankwar.utils.Colors;

/**
 * 提示信息框
 * @author admin
 *
 */
public class MsgDialog extends JDialog implements KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int width = 300, height = 100 ;
	public MsgPanel panel = null ;//面板
	private MainFrame frame = null ;

	//信息提示面板
	class MsgPanel extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private Font defaultFont = new Font("微软雅黑", Font.PLAIN, 15);
		public String title ;
		private String[] optionStr = new String[]{"确定", "取消"};
		private int option = 0 ; //选项
		
		public MsgPanel(String title){
			this.title = title;
		}
		
		public void setOption(int token){
			if( token == 0){
				if( this.option > 0)
					option -- ;
				else
					option = this.optionStr.length - 1 ;
			}else if( token == 1){
				if( this.option < this.optionStr.length - 1)
					option ++ ;
				else
					option = 0 ;
			}
		}
		
		public void paint(Graphics g){
			System.out.println("重回一次....");
			g.setColor(Colors.colorList[0]);
			g.fillRect(0, 0, 400, 200);
			g.setFont(defaultFont);
			g.setColor(Color.white);
			g.drawString(title, 5, 20);
			
			for( int i = 0 ; i < optionStr.length; i ++){
				g.setColor(Colors.colorList[i + 3]);
				g.fillRect(90 + i * 80, 50, 50, 30);
				g.setColor(Color.white);
				g.drawString(optionStr[i], 95 + i * 80, 70 );
				
				if( i == this.option){
					g.setColor(Color.white);
					g.drawRect(90 + i * 80, 50, 50, 30);
				}
			}
		}
	}
	
	public MsgDialog(MainFrame frame, String title){
		super(frame, title, true);
		this.frame = frame ;
		Point p = frame.getLocation() ;
		this.setLocation((int)(p.getX() + (frame.getWidth() - width)/2) , (int)(p.getY() + (frame.getHeight() - height)/2));
		panel = new MsgPanel(title);
		this.add(panel);
		this.setSize(width, height);
		this.addKeyListener(this);
		this.setUndecorated(true);
		this.setVisible(true);
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
			panel.setOption(0) ;
			this.panel.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
			panel.setOption(1) ;
			this.panel.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			System.out.println("选择了...");
			this.setVisible(false);
			this.frame.dialogOption( this.panel.option);//ifGameExit
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}
}