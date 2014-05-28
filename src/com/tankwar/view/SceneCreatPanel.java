package com.tankwar.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import com.tankwar.utils.Constant;
import com.tankwar.utils.Game;

/**
 * 游戏场景设计面板
 * @author Administrator
 *
 */
public class SceneCreatPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//String[] array = new String[Constant.MAP_Array_SIZE];
	private int[][] array = new int[Constant.MAP_Array_SIZE][80];
	private int pointer_x = 0 , pointer_y = 0 ;//指针坐标
	private int current_type = 0 ; //当前地图元素类型
	public Image wall, grass, wallUndefeted, water = null , saveBtnImg = null, bgImage = null ;
	
	//设置地图属性值
	public void setMapValue(){
		if( pointer_x >=0 && pointer_x < Constant.MAP_Array_SIZE && pointer_y < 80 && pointer_y >=0)
			this.array[pointer_x][pointer_y] = this.current_type ;
	}
	
	public SceneCreatPanel(){
		try {
			grass = ImageIO.read(new File("source/images/grass.png"));
			wall = ImageIO.read(new File("source/images/wall.png"));
			wallUndefeted = ImageIO.read(new File("source/images/wall2.png"));
			water = ImageIO.read(new File("source/images/water.png"));
			saveBtnImg = ImageIO.read(new File("source/images/system/saveScene.png"));
			bgImage = ImageIO.read(new File("source/images/background/purple.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g){
		
		
		g.drawImage(bgImage, 0, 0, 1000, 1000, this);
		
		drawMap(g);
		drawCurrentBlock(g);
		
		g.setColor(Color.green);
		g.drawLine(50, 50 + 5 * 10, 850, 50 + 5 * 10);
		
	}
	
	public void drawMap(Graphics g){
		g.setColor(Color.gray);
		for( int i = 0; i<Constant.MAP_Array_SIZE; i++){
			for( int j = 0; j <80; j++){
				g.drawRect(j * 10 + 50, i * 10 + 50, 10, 10);
			}
		}
		
		drawBlocks(g);
		drawBossTank(g);
	}
	
	public void drawBossTank(Graphics g){
		g.setColor(Color.green);
		g.fillRect(Game.BOSS_TANK_Y * 10 + 50, Game.BOSS_TANK_X * 10 + 50, 30, 30);
	}
	
	public void drawBlocks(Graphics g){
		for( int i = 0; i<Constant.MAP_Array_SIZE; i++){
			for( int j = 0; j <80; j++){
				switch(this.array[i][j]){
				case 1:
					g.drawImage(grass, j * 10 + 50, i * 10 + 50, 10, 10, this);
					break ;
				case 2:
					g.drawImage(this.wall, j * 10 + 50, i * 10 + 50, 10, 10, this);
					break ;
				case 3:
					g.drawImage(this.wallUndefeted, j * 10 + 50, i * 10 + 50, 10, 10, this);
					break ;
				case 4:
					g.drawImage(this.water, j * 10 + 50, i * 10 + 50, 10, 10, this);
					break ;
				default:
					break;
				}
			}
		}
	}
	
	public void drawCurrentBlock(Graphics g){
		//画指针
		g.setColor(Color.red);
		switch(this.current_type){
		case 0:
			g.drawRect(pointer_y * 10 + 50, pointer_x * 10 + 50, 10, 10);
			break ;
		case 1:
			g.drawImage(grass, pointer_y * 10 + 50, pointer_x * 10 + 50, 10, 10, this);
			break ;
		case 2:
			g.drawImage(this.wall, pointer_y * 10 + 50, pointer_x * 10 + 50, 10, 10, this);
			break ;
		case 3:
			g.drawImage(this.wallUndefeted, pointer_y * 10 + 50, pointer_x * 10 + 50, 10, 10, this);
			break ;
		case 4:
			g.drawImage(this.water, pointer_y * 10 + 50, pointer_x * 10 + 50, 10, 10, this);
			break ;
		default:
			break;
		}
		
		g.drawRect(pointer_y * 10 + 50, pointer_x * 10 + 50, 10, 10);
	}
	
	
	public int getPointer_x() {
		return pointer_x;
	}

	public void setPointer_x(int pointer_x) {
		this.pointer_x = pointer_x;
	}

	public int getPointer_y() {
		return pointer_y;
	}

	public void setPointer_y(int pointer_y) {
		this.pointer_y = pointer_y;
	}
	public int getCurrent_type() {
		return current_type;
	}

	public void setCurrent_type(int current_type) {
		this.current_type = current_type;
	}
	
	public int[][] getArray() {
		return array;
	}

	public void setArray(int[][] array) {
		this.array = array;
	}
}