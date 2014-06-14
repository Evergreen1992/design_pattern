package com.tankwar.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.tankwar.domain.PropsContainer;
import com.tankwar.entity.Bullet;
import com.tankwar.entity.Enemy;
import com.tankwar.entity.Prop;
import com.tankwar.entity.Tank;
import com.tankwar.entity.interfaces.PropType;
import com.tankwar.utils.Colors;
import com.tankwar.utils.Constant;
import com.tankwar.utils.Game;

/**
 * 
 * @author Evergreen
 *
 */
public class MainPanel extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainFrame mainFrame = null ;
	public String[] sArray = null ; //map array
	public Image hero1Up, hero1Down, hero1Left, hero1Right, enemy1Up, enemy1Down, enemy1Left, enemy1Right, wall, grass, wall3, stageChoiceBtnImg = null ;
	public Image exploerdImage , button_start, button_level, button_score , gameOverImg, water, boss = null ;
	//道具图片
	public Image prop_mime, prop_hat , prop_star, prop_life = null ;
	//一些颜色定义
	public Color briterGreen = new Color(111, 143, 47);
	public Color deepGreen = new Color(216, 233, 196);
	public Color middleGreen = new Color(239, 246, 231);
	public Color bgColor = new Color(55, 133, 241);
	public Color purple = new Color(98, 0, 177);
	//字体定义
	public Font defaultFontBig = new Font("微软雅黑", Font.PLAIN, 20);
	public Font defaultFontSmall = new Font("微软雅黑", Font.BOLD, 15);
	//菜单文字定义
	public String[] menuStr = new String[]{"开始游戏", "继续游戏", "场景绘制", "排  行  榜", "关卡查看", "退出游戏"};
	public int currentOption = 0 ; //当前选中的菜单项目
	//游戏主界面菜单选项
	public String[] gameOption = new String[]{"回主菜单", "暂停/继续"};
	public int gameOptionCursor = 0 ; //选项游标
	public int number = 0 ;
	
	public MainPanel(MainFrame frame, String[] sArray){
		this.mainFrame = frame ;
		this.sArray = sArray ;
		//this.addMouseListener(this);
		try {
			hero1Up = ImageIO.read(new File("source/images/hero/hero1_up.png"));
			hero1Down = ImageIO.read(new File("source/images/hero/hero1_down.png"));
			hero1Left = ImageIO.read(new File("source/images/hero/hero1_left.png"));
			hero1Right = ImageIO.read(new File("source/images/hero/hero1_right.png"));
			enemy1Up = ImageIO.read(new File("source/images/enemy/enemy1_up.png"));
			enemy1Down = ImageIO.read(new File("source/images/enemy/enemy1_down.png"));
			enemy1Left = ImageIO.read(new File("source/images/enemy/enemy1_left.png"));
			enemy1Right = ImageIO.read(new File("source/images/enemy/enemy1_right.png"));
			grass = ImageIO.read(new File("source/images/grass.png"));
			wall = ImageIO.read(new File("source/images/wall.png"));
			wall3 = ImageIO.read(new File("source/images/wall2.png"));
			button_start = ImageIO.read(new File("source/images/system/start_game_button.png"));
			button_level = ImageIO.read(new File("source/images/system/level_select_button.png")); 
			button_score = ImageIO.read(new File("source/images/system/high_score_button.png"));
			exploerdImage = ImageIO.read(new File("source/images/system/explored1.png"));
			gameOverImg = ImageIO.read(new File("source/images/system/gameOver.jpg"));
			water = ImageIO.read(new File("source/images/water.png"));
			stageChoiceBtnImg = ImageIO.read(new File("source/images/system/stage_choice_button.png"));
			boss = ImageIO.read(new File("source/images/hero/boss.png"));
			prop_mime = ImageIO.read(new File("source/images/prop/mime.png"));
			prop_hat = ImageIO.read(new File("source/images/prop/hat.png"));
			prop_star = ImageIO.read(new File("source/images/prop/star.png"));
			prop_life = ImageIO.read(new File("source/images/prop/life.png")); ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.addMouseListener(this);
	}
	//设置选中的项目
	public void setCurrentOption(int value){
		if( value >= 0 && value < this.menuStr.length){
			this.currentOption = value ;
		}else if( value == this.menuStr.length){
			this.currentOption = 0 ;
		}else if( value < 0 ){
			this.currentOption = this.menuStr.length - 1 ;
		}
	}
	
	public void paint(Graphics g){
		number ++ ;
		if( number == 100)
			number = 0 ;
		
		//status
		if(Game.status == Game.STATUS_MENU){ //主菜单绘制
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.mainFrame.width, this.mainFrame.height);
			//背景颜色
			g.setColor(Color.black);
			g.fillRect(0, 0, 900, 600);
			g.setColor(Color.white);
			g.setFont(this.defaultFontBig);
			g.drawString("坦克大战 2014", 380, 100);
			//默认字体设置
			g.setColor(Color.black);
			g.setFont(defaultFontBig);//设置字体
		
			//菜单项目绘制
			for( int i = 0; i<this.menuStr.length; i++){
				if( i == currentOption){//当前选中的菜单项目
					//g.setFont(this.boldFont);
					g.setColor(purple);
					g.fillRect(0, 170 + 50 * i, 900, 50 );
					//g.setColor(Color.black);
				}else{
					g.setFont(this.defaultFontBig);
					//g.setColor(Color.black);
				}
				g.setColor(Color.white);
				g.drawString(this.menuStr[i], 410, 200 + i * 50);
			}
			//画边框
			//g.setColor(Color.BLACK);
			//g.drawRect(0, 0, 899, Constant.active_area_y - 1);
		}else if( Game.status == Game.STATUS_ON || Game.status == Game.STATUS_PAUSE){
			g.setColor(Color.black);
			g.fillRect(0, 0, this.mainFrame.width, this.mainFrame.height);
			g.setColor(purple);
			g.fillRect(800, 0, 100, 600);
			
			drawMap(g);
			drawTank(g);
			drawBossTank(g);
			drawGameOption(g);
			drawProps(g);
		}else if( Game.status == Game.STATUS_GAME_OVER){
			drawMap(g);
			//文字显示
			g.setColor(Color.green);
			g.fillRect(370, 250, 200, 100);
			g.setColor(Color.blue);
			g.setFont(this.defaultFontBig);
			g.drawString("Game Over", 420, 300);
		}
	
	}
	
	/**
	 * 绘制道具
	 * @param g
	 */
	public void drawProps(Graphics g){
		
		if( number % 4 == 0 ){
			for(Prop p : PropsContainer.getValidProps()){
				switch( p.getType()){
				case PropType.mime :
					g.drawImage(this.prop_mime, p.getX(), p.getY(), Constant.PROP_WIDTH_HEIGHT, Constant.PROP_WIDTH_HEIGHT, this);
					break ;
				case PropType.hat :
					g.drawImage(this.prop_hat, p.getX(), p.getY(), Constant.PROP_WIDTH_HEIGHT, Constant.PROP_WIDTH_HEIGHT, this);
					break ;
				case PropType.life :
					g.drawImage(this.prop_life, p.getX(), p.getY(), Constant.PROP_WIDTH_HEIGHT, Constant.PROP_WIDTH_HEIGHT, this);
					break ;
				case PropType.star :
					g.drawImage(this.prop_star, p.getX(), p.getY(), Constant.PROP_WIDTH_HEIGHT, Constant.PROP_WIDTH_HEIGHT, this);
					break ;
				}
			}
		}
	}
	
	/**
	 * 绘制游戏中菜单选项
	 * @param g
	 */
	public void drawGameOption(Graphics g){
		g.setFont(defaultFontSmall);//设置字体
		for(int i = 0; i<gameOption.length; i++){
			g.setColor(Colors.colorList[i]);
			g.fillRect(820 , 100 + i * 80, 75, 30);
			g.setColor(Color.white);
			g.drawString(gameOption[i], 825 , 120 + i * 80);
			if( i == this.gameOptionCursor){
				g.setColor(Color.white);
				g.drawRect(820 , 100 + i * 80, 75, 30);
				g.drawRect(819 , 99 + i * 80, 77, 32);
			}
		}
		
		g.drawString("第" + Game.stage + "关", 822, 31);
	}
	
	public void drawBossTank(Graphics g){
		g.setColor(Color.green);
		g.drawImage(boss, Game.BOSS_TANK_Y * 10, Game.BOSS_TANK_X * 10, 30, 30, this);
	}

	/**
	 * draw map
	 * @param g
	 */
	public void drawMap(Graphics g){
		for(int i = 0; i<sArray.length; i++){
			for( int j = 0; j<sArray[i].length(); j++){
				if(sArray[i].charAt(j) == '1'){
					g.drawImage(grass, j * Constant.WIDTH_HEIGHT, i * Constant.WIDTH_HEIGHT, Constant.WIDTH_HEIGHT, Constant.WIDTH_HEIGHT, this);
				}else if( sArray[i].charAt(j) == '2'){
					g.drawImage(wall, j * Constant.WIDTH_HEIGHT, i * Constant.WIDTH_HEIGHT, Constant.WIDTH_HEIGHT, Constant.WIDTH_HEIGHT, this);
				}else if( sArray[i].charAt(j) == '3'){
					g.drawImage(wall3, j * Constant.WIDTH_HEIGHT, i * Constant.WIDTH_HEIGHT, Constant.WIDTH_HEIGHT, Constant.WIDTH_HEIGHT, this);
				}else if( sArray[i].charAt(j) == '4'){
					g.drawImage(water, j * Constant.WIDTH_HEIGHT, i * Constant.WIDTH_HEIGHT, Constant.WIDTH_HEIGHT, Constant.WIDTH_HEIGHT, this);
				}
				/*g.setColor(Color.gray);
				g.drawLine(i * 10, 0, i * 10, 900);
				g.drawLine(0, j * 10, 800, j * 10);*/
			}
		}
	}
	/**
	 * draw tanks
	 */
	public synchronized void drawTank(Graphics g){
		
		//if hero is alive
		if( this.mainFrame.hero.getStatus() == Tank.ALIVE){
			drawLifeValue(g);
			//draw hero
			if( this.mainFrame.hero.getDirection() == Tank.direction_up){
				g.drawImage(hero1Up, this.mainFrame.hero.getLocation_x(), this.mainFrame.hero.getLocation_y(), this.mainFrame.hero.getWidth(), this.mainFrame.hero.getHeight(), this);
			}else if(this.mainFrame.hero.getDirection() == Tank.direction_down){
				g.drawImage(hero1Down, this.mainFrame.hero.getLocation_x(), this.mainFrame.hero.getLocation_y(), this.mainFrame.hero.getWidth(), this.mainFrame.hero.getHeight(), this);
			}else if(this.mainFrame.hero.getDirection() == Tank.direction_left){
				g.drawImage(hero1Left, this.mainFrame.hero.getLocation_x(), this.mainFrame.hero.getLocation_y(), this.mainFrame.hero.getHeight(), this.mainFrame.hero.getWidth(), this);
			}else if(this.mainFrame.hero.getDirection() == Tank.direction_right){
				g.drawImage(hero1Right, this.mainFrame.hero.getLocation_x(), this.mainFrame.hero.getLocation_y(), this.mainFrame.hero.getHeight(), this.mainFrame.hero.getWidth(), this);
			}
			//draw hero's bullets
			g.setColor(Color.red);
			for(Bullet bullet: this.mainFrame.hero.getBullts()){
				if( bullet.getStatus() == Bullet.ALIVE)
					g.fillOval(bullet.getLocation_x(), bullet.getLocation_y(), Constant.BULLET_WIDTH, Constant.BULLET_HEIGHT);
			}
			g.setColor(Color.red);
			g.drawRect(this.mainFrame.hero.getLocation_x(), this.mainFrame.hero.getLocation_y(), 30, 29);
		}
		
		//draw enemies
		for(Enemy enemy:this.mainFrame.enemyTankContainer.getEnemyList()){
			if( enemy.getStatus() == Tank.ALIVE || enemy.getStatus() == Tank.SLEEP){
				drawEnemies(g, enemy);
				//draw enemies' bullets
				g.setColor(Color.yellow);
				for(Bullet bullet : enemy.getBullts()){
					if( bullet.getStatus() == Bullet.ALIVE)
						g.fillOval(bullet.getLocation_x(), bullet.getLocation_y(), Constant.BULLET_WIDTH, Constant.BULLET_HEIGHT);
				}
			}else if( enemy.getStatus() == Tank.SHOTED){//被击中的爆炸效果
				g.drawImage(this.exploerdImage, enemy.getLocation_x(), enemy.getLocation_y(), 20, 20, this);
				enemy.setStatus(Tank.DIED);//坦克死去
			}
		}
	}
	
	/**
	 * draw hero's life value
	 * @param g
	 */
	public void drawLifeValue(Graphics g){
		//显示生命值
		g.setColor(Color.green);
		for(int i = 0 ; i < Constant.LIFE_VALUE_FULL ; i ++){
			if( i < mainFrame.hero.getHeroLifeValue())
				g.fillRect(mainFrame.hero.getLocation_x() + 20 , mainFrame.hero.getLocation_y() - 10, 10 * (i + 1), 5);
			else
				g.drawRect(mainFrame.hero.getLocation_x() + 20 , mainFrame.hero.getLocation_y() - 10, 10 * (i + 1), 5);
		}
		
	}
	
	/**
	 * draw enemies
	 * @param g
	 * @param enemy
	 */
	public void drawEnemies(Graphics g, Enemy enemy){
		if( enemy.getDirection() == Tank.direction_up){
			g.drawImage(enemy1Up, enemy.getLocation_x(), enemy.getLocation_y(), enemy.getWidth(), enemy.getHeight(), this);
		}else if(enemy.getDirection() == Tank.direction_down){
			g.drawImage(enemy1Down, enemy.getLocation_x(), enemy.getLocation_y(), enemy.getWidth(), enemy.getHeight(), this);
		}else if(enemy.getDirection() == Tank.direction_left){
			g.drawImage(enemy1Left, enemy.getLocation_x(), enemy.getLocation_y(), enemy.getHeight(), enemy.getWidth(), this);
		}else if(enemy.getDirection() == Tank.direction_right){
			g.drawImage(enemy1Right, enemy.getLocation_x(), enemy.getLocation_y(), enemy.getHeight(), enemy.getWidth(), this);
		}
	}
	
	public void setGameOption(int token){
		if( token == 0){
			if( gameOptionCursor < gameOption.length - 1)
				gameOptionCursor ++ ;
			else
				gameOptionCursor = 0 ;
		}else if( token == 1){
			if( gameOptionCursor > 0)
				gameOptionCursor -- ;
			else
				gameOptionCursor = gameOption.length - 1 ;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("mainPanel 点击鼠标");
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
}