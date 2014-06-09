package com.tankwar.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * 游戏关卡切换面板
 * @author Administrator
 *
 */
public class StageResultPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private Image bgBlue = null ;
	
	/*private String[] strOption = new String[]{"选项1", "选项2",  "选项3", "选项4", "选项5",  "选项6"};
	private Color[] colorList = new Color[]{
			new Color(165,197,0),new Color(0,139,0),
			new Color(96,170,23),new Color(0,80,240),
			new Color(230,20,0),new Color(228,201,0), 
			new Color(27,162,227),new Color(100,118,136)
	};*/
	
	public Font defaultFontBig = new Font("微软雅黑", Font.PLAIN, 20);
	
	
	public StageResultPanel(){
		try{
			//bgBlue = ImageIO.read(new File("source/images/background/blue.jpg"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g){
		//g.drawImage(bgBlue, 0, 0, 900, 600, this);
		Font font = new Font("alias", Font.PLAIN, 20);
		g.setFont(font);//设置字体
		g.setColor(Color.black);
		g.fillRect(0, 0, 900, 600);
		
		/*for( int i = 0; i<strOption.length; i++){
			g.setColor(this.colorList[i % this.colorList.length]);
			g.fillRect(50 + i * 40 , 50 + i * 40, 400, 200);
		}*/
		
		g.setColor(Color.white);
		g.setFont(defaultFontBig);
		g.drawString("Next Stage...", 400, 100);
		g.drawString("totle killed : ", 400, 200);
		g.drawString("score : ", 400, 300);
	}

}
