package com.tankwar.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import com.tankwar.utils.FileUtils;
import com.tankwar.utils.SceneReaderFactory;

/**
 * 关卡选择面板
 * @author Administrator
 *
 */
public class StageChosePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int stageNum = 0 ;
	public Image wall, grass, wallUndefeted, water = null ;
	private String[] array = null ;//场景数组
	private int currentStage = 1 ;//当前选择的关卡
	
	private Image bgBlue = null ;
	
	//颜色值
	private Color[] colorList = new Color[]{
			new Color(165,197,0),new Color(0,139,0),
			new Color(96,170,23),new Color(0,80,240),
			new Color(230,20,0),new Color(228,201,0), 
			new Color(27,162,227),new Color(100,118,136)
	};
	
	public StageChosePanel(){
		array = SceneReaderFactory.readMap(currentStage);
		try {
			grass = ImageIO.read(new File("source/images/grass.png"));
			wall = ImageIO.read(new File("source/images/wall.png"));
			wallUndefeted = ImageIO.read(new File("source/images/wall2.png"));
			water = ImageIO.read(new File("source/images/water.png"));
			bgBlue = ImageIO.read(new File("source/images/background/green.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g){
		g.drawImage(bgBlue, 0, 0, 900, 600, this);
		Font font = new Font("alias", Font.PLAIN, 20);
		g.setFont(font);//设置字体
		stageNum = FileUtils.getStagesSize();
		
		//g.setColor(Color.black);
		//g.fillRect(0, 0, 1000, 1000);
		g.setColor(Color.blue);
		g.drawString("选择关卡", 400, 30);
		
		for( int i = 0 ; i<((stageNum%5 == 0)?(stageNum/5):(stageNum/5 + 1)); i ++){
			for( int j = 0 ; j <5; j++){
				if( i * 5 + j + 1 <= stageNum){
					g.setColor(Color.blue);
					g.fill3DRect(j * 80 + 250, i * 80 + 100 , 50, 50, (i * 5 + j + 1) == this.currentStage ? true : false);
					
					g.setColor(Color.white);
					g.drawString("" + (i * 5 + j + 1), j * 80 + 250 + 20, i * 80 + 100 + 30);
				}
				//绘制当前关卡
				if( i * 5 + j + 1 == this.currentStage){
					g.setColor(Color.red);
					g.draw3DRect(j * 80 + 250, i * 80 + 100 , 50, 50, j % 2 == 0 ? true : false);
					
				}
			}
		}
		
		
		//绘制滑动面板
		/*for( int i = 0; i<stageNum; i++){
			g.setColor(this.colorList[i % this.colorList.length]);
			g.fillRect(50 + i * 40 , 50 + i * 40, 500, 300);
			if( i == stageNum - 1){
				g.setColor(Color.black);
				g.drawRect(50 + i * 40 , 50 + i * 40, 500, 300);
			}
		}*/
		drawScene(g);
		
		drawButtons(g);
	}
	
	/**
	 * 绘制按钮
	 */
	public void drawButtons(Graphics g){
		
	}
	
	/**
	 * 绘制场景
	 * @param g
	 */
	public void drawScene(Graphics g){
		int startPosition = 100 + (this.stageNum - 1) * 40 ;
		//50 + i * 40 , 50 + i * 40
		g.setColor(Color.black);
		g.fillRect(50 + (this.stageNum - 1) * 40, 50 + (this.stageNum - 1) * 40, 450, 300);
		
		for( int i = 0; i<array.length; i++){
			for( int j = 0; j<array[i].length(); j++){
				switch(array[i].charAt(j)){
				case '1':
					g.drawImage(grass, j * 5 + startPosition, i * 5 + startPosition, 5, 5, this);
					break ;
				case '2':
					g.drawImage(this.wall, j * 5 + startPosition, i * 5 + startPosition, 5, 5, this);
					break ;
				case '3':
					g.drawImage(this.wallUndefeted, j * 5 + startPosition , i * 5 + startPosition, 5, 5, this);
					break ;
				case '4':
					g.drawImage(this.water, j * 5 + startPosition, i * 5 + startPosition, 5, 5, this);
					break ;
				default:
					break;
				}
			}
		}
	}
	
	public int getCurrentStage() {
		return currentStage;
	}
	
	/**
	 *   1 2 3 4 5
	 *   5 1 2 3 4
	 *   2 3 4 5 1
	 * @param token
	 */
	public void setCurrentStage(int token) {
		if( token == 0){
			//next
			Color temp = this.colorList[0] ;
			for( int i = 0; i <this.colorList.length - 1 ; i++){
				this.colorList[i] = this.colorList[ i + 1];
			}
			this.colorList[this.colorList.length - 1] = temp ;
			
			if( this.currentStage < this.stageNum){
				this.currentStage ++ ;
			}else
				this.currentStage = 1 ;
		}else if( token == 1){
			//before
			Color temp = this.colorList[this.colorList.length - 1] ;
			for( int i = this.colorList.length - 1; i > 0  ; i--){
				this.colorList[i] = this.colorList[ i - 1];
			}
			this.colorList[0] = temp ;
			
			if( this.currentStage > 1){
				this.currentStage -- ;
			}else
				this.currentStage = this.stageNum ;
		}
		
		array = SceneReaderFactory.readMap(currentStage);
	}
}