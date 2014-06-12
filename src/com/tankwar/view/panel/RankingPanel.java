package com.tankwar.view.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * 游戏得分排行榜面板
 * @author Administrator
 *
 */
public class RankingPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public Font defaultFontBig = new Font("微软雅黑", Font.PLAIN, 20);
	
	
	public RankingPanel(){
		try{
			//bgBlue = ImageIO.read(new File("source/images/background/blue.jpg"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g){
		Font font = new Font("alias", Font.PLAIN, 20);
		g.setFont(font);//设置字体
		g.setColor(Color.black);
		g.fillRect(0, 0, 900, 600);
		
		g.setColor(Color.white);
		g.drawString("排行榜", 200, 200);
	}
}