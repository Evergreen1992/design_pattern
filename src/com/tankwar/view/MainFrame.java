package com.tankwar.view;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;
import com.tankwar.domain.DBProxyImpl;
import com.tankwar.domain.EnemyTankContainer;
import com.tankwar.entity.Hero;
import com.tankwar.entity.Tank;
import com.tankwar.utils.Constant;
import com.tankwar.utils.FileUtils;
import com.tankwar.utils.Game;
import com.tankwar.utils.SceneReaderFactory;
import com.tankwar.utils.SoundPlayFactory;

/**
 * main frame interface
 * @author Evergreen
 * date 2014-5-31
 *
 */
public class MainFrame extends JFrame implements KeyListener, MouseListener, Runnable , MouseInputListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int width = Constant.active_area_x ;
	public int height = Constant.active_area_y ;
	public int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width ;
	public int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height ;
	//游戏中的所用到的面板
	public MainPanel mainPanel ;//主面板
	public SceneCreatPanel scenePanel ; //游戏场景绘制面板
	public StageChosePanel sceneChosePanel = null ;//游戏场景选择面板
	public StageResultPanel stageResultPanel = null ;// 游戏关卡结果面板
	
	public Hero hero = null ;//hero
	public EnemyTankContainer enemyTankContainer ; 
	public String[] mapArray = null ;//the map of the background
	public int pointer_x = 0 , pointer_y = 0 ;//鼠标位置
	
	public MsgDialog messageDialog = null ;
	
	
	public MainFrame(){
		//initTank();
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
		enemyTankContainer.initEnemies(Constant.defaultEnemiesNum);
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
		//new Thread(mainPanel).start();
		setSize(width + 100, height);
		setLocation((screen_width - width)/2, (screen_height - height)/2);
		add(mainPanel);
		setTitle("Tank War ");
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void gamePause(){
		
	}
	
	public void nextStage(){
		Game.nextStage();
		this.mapArray = SceneReaderFactory.readMap(Game.stage);
		this.enemyTankContainer = new EnemyTankContainer(this);
		this.enemyTankContainer.initEnemies(Constant.defaultEnemiesNum);
		
		this.mainPanel.sArray = mapArray ;
		this.hero.setMapArray(mapArray);
		hero.setLocation_x(400);
		hero.setLocation_y(400);
	}
	
	public void backToMenu(){
		if( Game.stage <= Constant.MAP_Array_SIZE_HEIGHT ){
			//记录游戏数据
			new DBProxyImpl().recordGameData(enemyTankContainer.getEnemyList(), hero);
		}
		//set data to null
		enemyTankContainer.stopThread() ;
		System.exit(0);
	}
	
	/**
	 * 开始游戏
	 */
	public void gameStart(){
		initTank();
		mainPanel.sArray = mapArray;//set map data
		Game.start();
	}
	
	/**
	 * 游戏恢复（恢复上一次游戏）
	 */
	public void gameResume(){
		resumeTank(); 
		mainPanel.sArray = mapArray;//set map data
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
		if( event.getKeyCode() == KeyEvent.VK_L){
			switchPanel("sceneCreate");
		}else if( event.getKeyCode() == KeyEvent.VK_K){
			switchPanel("sceneChose");
		}else if( event.getKeyCode() == KeyEvent.VK_J){
			switchPanel("mainPanel");
		}else if( event.getKeyCode() == KeyEvent.VK_H){
			switchPanel("stageResult");
		}
		
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
					/*int result = JOptionPane.showConfirmessageDialogialog(this, "游戏未结束，确定要存档并退出?");
					switch(result){
					case 0: backToMenu();
					default : ;
					}*/
					if( this.messageDialog == null){
						this.messageDialog = new MsgDialog(this, "游戏未结束，确定要存档并退出?");
						this.messageDialog.setVisible(true);
					}else{
						this.messageDialog.setVisible(true);
						this.messageDialog.panel.title = "游戏未结束，确定要存档并退出?" ;
						this.messageDialog.panel.repaint();
					}
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
						
					}else if( mainPanel.gameOptionCursor == 0){
						this.enemyTankContainer = null;
						Game.status = Game.STATUS_MENU;
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
						
					}else if( this.mainPanel.currentOption == 2){
						
					}else if( this.mainPanel.currentOption == 3){
						System.exit(0);
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
				this.scenePanel = new SceneCreatPanel();
			getContentPane().add(scenePanel);//切换成游戏场景面板
			Game.switchPanelStatus("sceneCreate");//切换游戏面板状态
		}else if( token.equals("sceneChose")){
			if( this.sceneChosePanel == null )
				this.sceneChosePanel = new StageChosePanel();
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
		System.out.println(e.getX() + "," + e.getY());

		/*//主界面
		if( Game.PANEL_STATUS == Game.PANEL_STATUS_MAINPANEL){
			if( e.getX() >= 300 && e.getX() <= 500 && e.getY() >= 230 && e.getY() <= 260){
				gameStart();
			}else if(e.getX() >= 300 && e.getX() <= 500 && e.getY() >= 280 && e.getY() <= 320){
				
			}else if(e.getX() >= 300 && e.getX() <= 500 && e.getY() >= 350 && e.getY() <= 380){
				//游戏恢复
				gameResume();
			}
		}else if( Game.PANEL_STATUS == Game.PANEL_STATUS_SCENE_CHOSE){//游戏场景选择面板
			
		}*/
	}

	public void mousePressed(MouseEvent e) {
		this.pointer_x = e.getX();
		this.pointer_y = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		Point p = this.getLocation();
		setLocation(p.x + e.getX() - this.pointer_x, p.y + e.getY()- this.pointer_y);
	}

	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void run() {
		while(true){
			//System.out.println("主线程运行中...........");
			try {
				Thread.sleep(100);
				
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
		}
	}
	
	public static void main(String[] args){
		new Thread(new MainFrame()).start();;
	}
}