package com.tankwar.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.UIManager;

import com.tankwar.domain.DBProxyImpl;
import com.tankwar.domain.EnemyTankContainer;
import com.tankwar.domain.PropsContainer;
import com.tankwar.entity.Hero;
import com.tankwar.entity.Tank;
import com.tankwar.utils.Constant;
import com.tankwar.utils.DBHandler;
import com.tankwar.utils.FileUtils;
import com.tankwar.utils.Game;
import com.tankwar.utils.SceneReaderFactory;
import com.tankwar.utils.SoundPlayFactory;
import com.tankwar.view.dialog.ExitDialog;
import com.tankwar.view.dialog.InputDialog;
import com.tankwar.view.dialog.RecordChoseDialog;
import com.tankwar.view.panel.RankingPanel;

/**
 * main frame interface
 * @author admin
 * date 2014-5-31
 *
 */
public class MainFrame extends JFrame implements Runnable , KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int width = Constant.active_area_x ;
	public int height = Constant.active_area_y ;
	public int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width ;
	public int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height ;
	private Image imageIcon = null ;//icon
	
	public MainPanel mainPanel ;//主面板
	public SceneCreatPanel scenePanel ; //游戏场景绘制面板
	public StageChosePanel sceneChosePanel = null ;//游戏场景选择面板
	public StageResultPanel stageResultPanel = null ;// 游戏关卡结果面板
	public RankingPanel rankingPanel = null ; //游戏排行榜面板
	public RecordChoseDialog rcd = null ;//
	
	public Hero hero = null ;//hero
	public EnemyTankContainer enemyTankContainer ; 
	public String[] mapArray = null ;//the map of the background
	public int pointer_x = 0 , pointer_y = 0 ;//鼠标位置
	
	
	public MainFrame(){
		interfaceInie();
	}
	
	public void initTank(){
		//map initial
		mapArray = SceneReaderFactory.readMap(Game.stage);
		//hero initial
		hero = new Hero(mapArray);
		hero.setTank_type(Tank.HREO);
		hero.setActive_area_x(width);
		hero.setActive_area_y(height);
		hero.setDirection(Tank.direction_up);
		hero.setLocation_x(400);
		hero.setLocation_y(400);
		hero.setMainFrame(this);
		//enemies initial
		enemyTankContainer = new EnemyTankContainer(this);
		enemyTankContainer.initEnemies(3);
	}
	
	public void resumeTank(){
		//map initial
		mapArray = SceneReaderFactory.readMap(Game.stage);
		//hero initial
		hero = new Hero(mapArray);
		hero.setTank_type(Tank.HREO);
		hero.setActive_area_x(width);
		hero.setActive_area_y(height);
		hero.setDirection(Tank.direction_up);
		hero.setLocation_x(400);
		hero.setLocation_y(400);
		hero.setMainFrame(this);
		//enemies initial
		enemyTankContainer = new EnemyTankContainer(this);
		enemyTankContainer.setEnemyList(new DBProxyImpl().resumeLastGame(mapArray));
		//enemyTankContainer.mainFrame = this ;
		enemyTankContainer.resume();
	}
	
	public void interfaceInie(){

		mainPanel = new MainPanel(this , null);
		try {
			imageIcon = ImageIO.read(new File("source/images/system/imageIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//new Thread(mainPanel).start();
		setSize(width + 100, height);
		setLocation((screen_width - width)/2, (screen_height - height)/2);
		
		JRootPane rp = getRootPane();   
        rp.setWindowDecorationStyle(JRootPane.FRAME); 
		
		add(mainPanel);
		setTitle("坦克大战");
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		addKeyListener(this);
		/*addMouseListener(this);
		addMouseMotionListener(this);*/
		
		
		this.setIconImage(imageIcon);
	}
	
	public void gamePause(){
		
	}
	
	/**
	 * 保存游戏得分
	 */
	public void saveScore(String username){
		new DBHandler().saveRanking(username);
		System.out.println("游戏得分已经保存!");
	}
	
	public void nextStage(){
		Game.nextStage();
		this.mapArray = SceneReaderFactory.readMap(Game.stage);
		this.enemyTankContainer = new EnemyTankContainer(this);
		this.enemyTankContainer.initEnemies(3);
		this.mainPanel.sArray = mapArray ;
		this.hero.setMapArray(mapArray);
		hero.setLocation_x(400);
		hero.setLocation_y(400);
		PropsContainer.clearProps();//清除道具
	}
	
	/**
	 * 返回主菜单
	 */
	public void backToMenu(){
		System.out.println("回主菜单***************");
		
		
		/*if( Game.stage <= Constant.MAP_Array_SIZE_HEIGHT ){
			//记录游戏数据
			new DBProxyImpl().recordGameData(enemyTankContainer.getEnemyList(), hero);
		}
		//set data to null
		enemyTankContainer.stopThread() ;
		System.exit(0);*/
		
		this.switchPanel("mainPanel");
		
		if( enemyTankContainer != null ){	
			enemyTankContainer.stopEnemies();
			enemyTankContainer = null ;
		}
		
		Game.status = Game.STATUS_MENU ;
		Game.PANEL_STATUS = Game.PANEL_STATUS_MAINPANEL ;
	}
	
	/**
	 * 开始游戏
	 */
	public void gameStart(){
		initTank();
		hero.setHeroLifeValue(3);
		Game.isBossAlive = true ;
		mainPanel.sArray = mapArray;//set map data
		Game.status = Game.STATUS_ON ;
		Game.start();
	}
	
	/**
	 * 游戏恢复（恢复上一次游戏）
	 */
	public void gameResume(String[] data){
		
		Game.stage = Integer.parseInt(data[1]);
		Game.enemy_killed = Integer.parseInt(data[2]) / 100 ;
		
		initTank();
		hero.setHeroLifeValue(3);
		Game.isBossAlive = true ;
		mainPanel.sArray = SceneReaderFactory.readMap(Game.stage);
		Game.status = Game.STATUS_ON ;
		Game.start();
	}
	
	public void dialogOption(int token){
		System.out.println("选择的选项是:" + token);
		if( token == 0){       //退出游戏
			//this.messageDialog.setVisible(false);
		}else if( token == 1){ //返回游戏
			
		}
	}
	
	/**
	 * key event
	p */
	public void keyPressed(KeyEvent event) {
		//游戏主面板事件处理
		if( Game.PANEL_STATUS == Game.PANEL_STATUS_MAINPANEL ){
			
			if( Game.status == Game.STATUS_ON || Game.status == Game.STATUS_PAUSE){
				if( event.getKeyCode() == KeyEvent.VK_W){
					hero.go_up();
					SoundPlayFactory.getSoundByName("move");
				}else if(event.getKeyCode() == KeyEvent.VK_S){
					hero.go_down();
					SoundPlayFactory.getSoundByName("move");
				}else if(event.getKeyCode() == KeyEvent.VK_A){
					hero.go_left();
					SoundPlayFactory.getSoundByName("move");
				}else if( event.getKeyCode() == KeyEvent.VK_D){
					hero.go_right();
					SoundPlayFactory.getSoundByName("move");
				}else if( event.getKeyCode() == KeyEvent.VK_P){
					hero.fire();
				}else if( event.getKeyCode() == KeyEvent.VK_ESCAPE){
					//JOptionPane.showConfirmDialog(this, "游戏未结束，确定要存档并退出?");
					new ExitDialog(this, "游戏未结束，确定要存档并退出?");
				}else if( event.getKeyCode() == KeyEvent.VK_UP){
					mainPanel.setGameOption(0);
				}else if(event.getKeyCode() == KeyEvent.VK_DOWN){
					mainPanel.setGameOption(1);
				}else if( event.getKeyCode() == KeyEvent.VK_ENTER){
					if( mainPanel.gameOptionCursor == 1){		//开始和暂停游戏
						if( Game.status == Game.STATUS_ON){
							Game.status = Game.STATUS_PAUSE;
							System.out.println("暂停游戏");
						}else if( Game.status == Game.STATUS_PAUSE){
							Game.status = Game.STATUS_ON ;
							System.out.println("开始游戏");
						}
						
					}else if( mainPanel.gameOptionCursor == 0){//回主菜单
						/*this.enemyTankContainer = null;
						Game.status = Game.STATUS_MENU;*/
						this.backToMenu();
					}
				}
			}else if( Game.status == Game.STATUS_MENU){//主菜单页面
				if( event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_UP){
					mainPanel.setCurrentOption( -- mainPanel.currentOption);
				}else if(event.getKeyCode() == KeyEvent.VK_S || event.getKeyCode() == KeyEvent.VK_DOWN){
					mainPanel.setCurrentOption( ++ mainPanel.currentOption);
				}else if( event.getKeyCode() == KeyEvent.VK_ENTER){
					if( this.mainPanel.currentOption == 0){
						//开始游戏
						gameStart();
					}else if( this.mainPanel.currentOption == 1){
						if( rcd == null ){
							rcd = new RecordChoseDialog(this, "选择一个存档，开始游戏");
						}
						else
						{
							rcd.setVisible(true);
						}
					}else if( this.mainPanel.currentOption == 2){
						switchPanel("sceneCreate");
					}else if( this.mainPanel.currentOption == 3){
						switchPanel("rankingPanel");
					}else if( this.mainPanel.currentOption == 4){
						switchPanel("sceneChose");
					}else if( this.mainPanel.currentOption == 5){
						new ExitDialog(this, "确定要退出游戏吗?");
					}
				}
			}
		}else if( Game.PANEL_STATUS == Game.PANEL_STATUS_SCENE_BUILD){//场景绘制面板
			if( event.getKeyCode() == KeyEvent.VK_W){
				this.scenePanel.setPointer_x(this.scenePanel.getPointer_x() - 1);
				this.scenePanel.setMapValue();
			}else if(event.getKeyCode() == KeyEvent.VK_S){
				this.scenePanel.setPointer_x(this.scenePanel.getPointer_x() + 1);
				this.scenePanel.setMapValue();
			}else if(event.getKeyCode() == KeyEvent.VK_A){
				this.scenePanel.setPointer_y(this.scenePanel.getPointer_y() - 1);
				this.scenePanel.setMapValue();
			}else if( event.getKeyCode() == KeyEvent.VK_D){
				this.scenePanel.setPointer_y(this.scenePanel.getPointer_y() + 1);
				this.scenePanel.setMapValue();
			}else if( event.getKeyCode() == KeyEvent.VK_0){
				this.scenePanel.setCurrent_type(0);
			}else if( event.getKeyCode() == KeyEvent.VK_1){
				this.scenePanel.setCurrent_type(1);
			}else if( event.getKeyCode() == KeyEvent.VK_2){
				this.scenePanel.setCurrent_type(2);
			}else if( event.getKeyCode() == KeyEvent.VK_3){
				this.scenePanel.setCurrent_type(3);
			}else if( event.getKeyCode() == KeyEvent.VK_4){
				this.scenePanel.setCurrent_type(4);
			}else if( event.getKeyCode() == KeyEvent.VK_ENTER){
				System.out.println("保存场景");
				FileUtils.saveScene(scenePanel.getArray());
			}
		}else if( Game.PANEL_STATUS == Game.PANEL_STATUS_SCENE_CHOSE){//场景选择面板
			if( event.getKeyCode() == KeyEvent.VK_D){
				sceneChosePanel.setCurrentStage(0);
			}else if( event.getKeyCode() == KeyEvent.VK_A){
				sceneChosePanel.setCurrentStage(1);
			}else if(event.getKeyCode() == KeyEvent.VK_ENTER){
				System.out.println("开始游戏");
			}
		}
	}
	
	//切换面板
	public void switchPanel(String token){
		getContentPane().removeAll();
		
		if( token.equals("sceneCreate")){
			if( this.scenePanel == null )
				this.scenePanel = new SceneCreatPanel(this);
			getContentPane().add(scenePanel);//切换成游戏场景面板
			Game.switchPanelStatus("sceneCreate");//切换游戏面板状态
		}else if( token.equals("sceneChose")){
			if( this.sceneChosePanel == null )
				this.sceneChosePanel = new StageChosePanel(this);
			getContentPane().add(sceneChosePanel);//切换到游戏场景选择面板
			Game.switchPanelStatus("sceneChose");//切换游戏面板状态
		}
		else if( token.equals("mainPanel")){
			getContentPane().add(mainPanel); //切换成游戏主面板
			Game.switchPanelStatus("mainPanel");//切换游戏面板状态
		}else if( token.equals("stageResult")){//关卡切换面板
			if( this.stageResultPanel == null )
				this.stageResultPanel = new StageResultPanel();
			getContentPane().add(stageResultPanel); //切换成游戏主面板
			Game.switchPanelStatus("stageResult");//切换游戏面板状态
		}else if( token.equals("rankingPanel")){
			if( this.rankingPanel == null )
				this.rankingPanel = new RankingPanel(this);
			getContentPane().add(rankingPanel);
			Game.switchPanelStatus("rankingPanel");
		}
		
		getContentPane().validate();
		getContentPane().repaint();
	}

	public void keyReleased(KeyEvent event) {
		
	}

	public void keyTyped(KeyEvent arg0) {
		
	}
	
	//mouse event
	public void mouseClicked(MouseEvent e) {
		
		//主界面
		if( Game.PANEL_STATUS == Game.PANEL_STATUS_SCENE_BUILD){
			
			//菜单选项
			if( e.getX() >= 50 && e.getX() <= 150 && e.getY() >= 555 && e.getY() <= 595){
				this.scenePanel.setModel(0);
			}else if(e.getX() >= 160 && e.getX() <= 260 && e.getY() >= 555 && e.getY() <= 595){
				this.scenePanel.setModel(1);
			}else if(e.getX() >= 270 && e.getX() <= 370 && e.getY() >= 555 && e.getY() <= 595){
				System.out.println("3");
			}else if(e.getX() >= 380 && e.getX() <= 480 && e.getY() >= 555 && e.getY() <= 595){
				System.out.println("4");
			}else if(e.getX() >= 500 && e.getX() <= 540 && e.getY() >= 555 && e.getY() <= 595){
				this.scenePanel.setBlockType(0);
			}else if(e.getX() >= 550 && e.getX() <= 590 && e.getY() >= 555 && e.getY() <= 595){
				this.scenePanel.setBlockType(1);
			}else if(e.getX() >= 600 && e.getX() <= 640 && e.getY() >= 555 && e.getY() <= 595){
				this.scenePanel.setBlockType(2);
			}else if(e.getX() >= 650 && e.getX() <= 690 && e.getY() >= 555 && e.getY() <= 595){
				this.scenePanel.setBlockType(3);
			}else if(e.getX() >= 700 && e.getX() <= 740 && e.getY() >= 555 && e.getY() <= 595){
				this.scenePanel.setBlockType(4);
			}
			
			//鼠标模式
			if( this.scenePanel.getModel() == 0  ){
				//判断
				if( (e.getY() - 50)/10 >= 0 && (e.getY() - 50)/10 < Constant.MAP_Array_SIZE && (e.getX() - 50)/10 >= 0 && (e.getX() - 50)/10 <= 79){
					this.scenePanel.setBlockType((e.getY() - 50)/10 , (e.getX() - 50)/10 );
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		this.pointer_x = e.getX();
		this.pointer_y = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		/*//鼠标进入返回按钮区域
		if( e.getX() >= 5 && e.getX() <= 45 && e.getY() >= 5 && e.getY() <= 45){
			
			if( Game.PANEL_STATUS == Game.PANEL_STATUS_SCENE_BUILD && this.scenePanel != null){
				this.scenePanel.setReturnBtnFlag(true);
			}
		}else{
			if( Game.PANEL_STATUS == Game.PANEL_STATUS_SCENE_BUILD && this.scenePanel != null){
				this.scenePanel.setReturnBtnFlag(f);
			}
		}*/
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		//Point p = this.getLocation();
		if( Game.PANEL_STATUS != Game.PANEL_STATUS_SCENE_BUILD ){
			//setLocation(p.x + e.getX() - this.pointer_x, p.y + e.getY()- this.pointer_y);
		}
		else{
			if( this.scenePanel.getModel() == 0)
			{
				this.scenePanel.batchHandle(this.pointer_x, this.pointer_y , e.getX(), e.getY());
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void run() {
		int totleSleepTime = 0 ; 
		while(true){
			try {
				Thread.sleep(100);
				totleSleepTime ++ ;
				if( totleSleepTime == 100)
					totleSleepTime = 0 ; 
				
				//重绘主面板
				if(  Game.PANEL_STATUS == Game.PANEL_STATUS_MAINPANEL ){
					this.mainPanel.repaint();
				}
				
				//重绘场景面板
				if( Game.PANEL_STATUS == Game.PANEL_STATUS_SCENE_BUILD){
					//System.out.println("游戏场景面板重绘中.....");
					if( this.scenePanel != null)
						this.scenePanel.repaint();
				}
				
				//重绘关卡选择面板
				if( Game.PANEL_STATUS == Game.PANEL_STATUS_SCENE_CHOSE){
					if( this.sceneChosePanel != null)
						this.sceneChosePanel.repaint();
				}
				
				//重绘关卡结果面板
				if( Game.PANEL_STATUS == Game.PANEL_STATUS_SCENE_STAGE_RESULT){
					if( this.stageResultPanel != null )
						this.stageResultPanel.repaint();
				}
				
				/*//判断是否开始下一关
				if( this.enemyTankContainer != null && this.enemyTankContainer.getAliveNumber() == 0)
					this.nextStage();*/
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//敌军坦克数量控制
			if( this.enemyTankContainer != null ){
				if( totleSleepTime % 10 == 0 )//每一秒判断一次
				{
					//游戏还未结束
					if( Game.isBossAlive && this.hero.getHeroLifeValue() >= 1){
					
						if( this.enemyTankContainer.getAliveNum() < 3 && this.enemyTankContainer.getEnemyList().size() < Constant.defaultEnemiesNum){
							if( totleSleepTime % 20 == 0)
								this.enemyTankContainer.initEnemies(1);
						}else if( this.enemyTankContainer.getTotleKilledThisStage() == Constant.defaultEnemiesNum){
							//关卡提示
							int option = JOptionPane.showConfirmDialog(this, "是否开始下一关卡游戏?");
							
							if( option == 0 ){
								this.nextStage();
							}
							else if( option == 1){
								this.backToMenu();
							}
							//new Dialog(this, "是否开始下一关卡游戏?");
						}
					}else{//游戏结束
						Game.status = Game.STATUS_GAME_OVER ;
						new InputDialog(this, "游戏结束，请输入你的大名");
					}
				}
			}
		}
	}
	
	public static void main(String[] args){
		try {   
			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");   
	    } catch (Exception e) {   
	    	System.err.println("皮肤加载失败！");   
	    }   
		
		new Thread(new MainFrame()).start();
	}
}