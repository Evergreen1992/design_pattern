package com.tankwar.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import com.tankwar.utils.Constant;
import com.tankwar.utils.Game;

/**
 * 游戏场景设计面板
 * @author Administrator
 *
 */
public class SceneCreatPanel extends JPanel implements MouseInputListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//String[] array = new String[Constant.MAP_Array_SIZE];
	private int[][] array = new int[Constant.MAP_Array_SIZE][80];
	private int pointer_x = 0 , pointer_y = 0 ;//指针坐标
	private int current_type = 0 ; //当前地图元素类型
	public Image wall, grass, wallUndefeted, water = null , saveBtnImg = null, bgImage = null ;
	
	private int model = 0 ; //操作方式(0:鼠标模式  1:键盘模式)
	private int blockType = 0 ; //方块类型
	
	private Image returnBtn1 , returnBtn2 = null ;
	private boolean returnBtnFlag = false ;//鼠标是否在返回按钮上面(5, 5, 45, 45)
	
	private int current_x = 0 , current_y = 0 ;//拖拽鼠标起点
	
	private MainFrame mf  = null ;
	
	public void setReturnBtnFlag(boolean returnBtnFlag) {
		System.out.println("9999999");
		this.returnBtnFlag = returnBtnFlag;
	}

	//设置地图属性值
	public void setMapValue(){
		if( pointer_x >=0 && pointer_x < Constant.MAP_Array_SIZE && pointer_y < 80 && pointer_y >=0)
			this.array[pointer_x][pointer_y] = this.current_type ;
	}
	
	public SceneCreatPanel(MainFrame mf){
		this.mf = mf ;
		try {
			grass = ImageIO.read(new File("source/images/grass.png"));
			wall = ImageIO.read(new File("source/images/wall.png"));
			wallUndefeted = ImageIO.read(new File("source/images/wall2.png"));
			water = ImageIO.read(new File("source/images/water.png"));
			saveBtnImg = ImageIO.read(new File("source/images/system/saveScene.png"));
			bgImage = ImageIO.read(new File("source/images/background/purple.png"));
			
			returnBtn1 = ImageIO.read(new File("source/images/system/buttons/return_1.png"));
			returnBtn2 = ImageIO.read(new File("source/images/system/buttons/return_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void paint(Graphics g){
		
		
		g.drawImage(bgImage, 0, 0, 1000, 1000, this);
		
		drawMap(g);
		drawCurrentBlock(g);
		
		g.setColor(Color.green);
		g.drawLine(50, 50 + 5 * 10, 850, 50 + 5 * 10);
		drawButtons(g);
	}
	
	/**
	 * 绘制按钮
	 * @param g
	 */
	public void drawButtons(Graphics g){
		g.setColor(Color.green);
		g.fill3DRect(50, 555, 100, 40, this.model == 0 ? true : false);
		g.fill3DRect(160, 555, 100, 40, this.model == 1 ? true : false);
		g.fillRect(270, 555, 100, 40);
		//清除
		g.setColor(Color.red);
		g.fillRect(380, 555, 100, 40);
		
		g.drawImage(this.grass, 500, 555, 40, 40, this);
		g.drawImage(this.wall, 550, 555, 40, 40, this);
		g.drawImage(this.wallUndefeted, 600, 555, 40, 40, this);
		g.drawImage(this.water, 650, 555, 40, 40, this);
		g.setColor(Color.white);
		g.drawRect(700, 555, 40, 40);
		
		g.setColor(Color.white);
		g.drawString("鼠标模式", 75, 575);
		g.drawString("键盘模式", 185, 575);
		g.drawString("保存", 295, 575);
		g.drawString("清除", 395, 575);
		
		g.setColor(Color.red);

		//左边按钮状态
		if( this.model == 0 ){
			g.drawRect(50, 555, 100, 40);
		}else{
			g.drawRect(160, 555, 100, 40);
		}
		
		//右边图形方块选中状态
		if( this.blockType == 0 ){
			g.drawRect(500, 555, 40, 40);
		}else if( this.blockType == 1){
			g.drawRect(550, 555, 40, 40);
		}else if( this.blockType == 2){
			g.drawRect(600, 555, 40, 40);
		}else if( this.blockType == 3){
			g.drawRect(650, 555, 40, 40);
		}else if( this.blockType == 4){
			g.drawRect(700, 555, 40, 40);
		}
		
		
		//返回按钮
		if( returnBtnFlag == true ){
			g.drawImage(returnBtn2, 5, 5, 40, 40, this);
		}
		else{
			g.drawImage(returnBtn1, 5, 5, 40, 40, this);
		}
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
	
	/**
	 * 设置类型
	 */
	public void setBlockType(int i, int j){
		if( i >= 0 && i < Constant.MAP_Array_SIZE && j >= 0 && j < 80){
			if( this.blockType == 0 ){
				this.array[i][j] = 1;
			}else if( this.blockType == 1){
				this.array[i][j] = 2;
			}else if( this.blockType == 2){
				this.array[i][j] = 3;
			}else if( this.blockType == 3){
				this.array[i][j] = 4;
			}else if( this.blockType == 4){
				this.array[i][j] = 0;
			}
		}
		
	}
	/**
	 * 批量设置类型
	 * @return
	 */
	public void batchHandle(int x1, int y1, int x2, int y2){
		int startX = (x1 <= x2 ? x1 : x2) ;
		int endX = x1 + x2 - startX ;
		int startY = (y1 <= y2 ? y1 : y2 ); 
		int endY = y1 + y2 - startY ; 
		
		int startI = (startY - 50) / 10 ;
		int startJ = (startX - 50 ) / 10 ;
		
		for( int i = startI ; i <= startI + (endY - startY)/10; i ++){
			for( int j = startJ; j < startJ + ( endX - startX ) / 10; j ++){
				setBlockType(i, j);
			}
		}
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
	
	public int getModel(){
		return this.model;
	}
	
	public void setModel(int model){
		this.model = model ;
	}
	
	public int getBlockType() {
		return blockType;
	}

	public void setBlockType(int blockType) {
		this.blockType = blockType;
	}

	
	public void mouseClicked(MouseEvent e) {
		
		if( e.getX() >= 5 && e.getX() <= 45 && e.getY() >= 5 && e.getY() <= 45){
			this.mf.backToMenu();
		}
		
		//菜单选项
		if( e.getX() >= 50 && e.getX() <= 150 && e.getY() >= 555 && e.getY() <= 595){
			setModel(0);
		}else if(e.getX() >= 160 && e.getX() <= 260 && e.getY() >= 555 && e.getY() <= 595){
			setModel(1);
		}else if(e.getX() >= 270 && e.getX() <= 370 && e.getY() >= 555 && e.getY() <= 595){
			System.out.println("3");
		}else if(e.getX() >= 380 && e.getX() <= 480 && e.getY() >= 555 && e.getY() <= 595){
			System.out.println("4");
		}else if(e.getX() >= 500 && e.getX() <= 540 && e.getY() >= 555 && e.getY() <= 595){
			setBlockType(0);
		}else if(e.getX() >= 550 && e.getX() <= 590 && e.getY() >= 555 && e.getY() <= 595){
			setBlockType(1);
		}else if(e.getX() >= 600 && e.getX() <= 640 && e.getY() >= 555 && e.getY() <= 595){
			setBlockType(2);
		}else if(e.getX() >= 650 && e.getX() <= 690 && e.getY() >= 555 && e.getY() <= 595){
			setBlockType(3);
		}else if(e.getX() >= 700 && e.getX() <= 740 && e.getY() >= 555 && e.getY() <= 595){
			setBlockType(4);
		}
		
		//鼠标模式
		if( getModel() == 0  ){
			//判断
			if( (e.getY() - 50)/10 >= 0 && (e.getY() - 50)/10 < Constant.MAP_Array_SIZE && (e.getX() - 50)/10 >= 0 && (e.getX() - 50)/10 <= 79){
				setBlockType((e.getY() - 50)/10 , (e.getX() - 50)/10 );
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		this.current_x = e.getX();
		this.current_y = e.getY();
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		if( getModel() == 0)
		{
			 batchHandle(this.current_x, this.current_y , e.getX(), e.getY());
		}
	}

	public void mouseMoved(MouseEvent e) {
		if( e.getX() >= 5 && e.getX() <= 45 && e.getY() >= 5 && e.getY() <= 45){
			this.returnBtnFlag = true ;
		}else{
			this.returnBtnFlag = false ;
		}
	}
}