package com.tankwar.view.panel;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import com.tankwar.view.MainFrame;

/**
 * 游戏得分排行榜面板
 * @author Administrator
 *
 */
public class RankingPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public Font defaultFontBig = new Font("微软雅黑", Font.PLAIN, 20);
	
	JButton back = null ;
	
	JTable rankingTable = null ;
	
	String[][] rowData = new String[][]{{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
		,{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}
	};
	String[] columnNames = new String[]{"玩家","关卡","得分","日期"};
	
	JLabel label = new JLabel("排行榜");
	MainFrame main = null ;
	
	public RankingPanel(MainFrame frame){
		this.main = frame ;
		back = new JButton("返回");
		rankingTable = new JTable(rowData, columnNames);
		
	    JScrollPane sp = new JScrollPane();
	    sp.setBorder(new EmptyBorder(0,0,0,0));
	    sp.setViewportView(rankingTable);
        
	    back.setBounds(50, 50, 80, 30);
	    label.setBounds(50, 100, 50, 30);
		sp.setBounds(50, 140, 800, 350);
		
		back.addActionListener(this);
	    this.setLayout(null);
		this.add(back);
		this.add(label);
		this.add(sp);
	}

	public void actionPerformed(ActionEvent e) {
		
		if( e.getSource() == back){
			this.main.backToMenu();
		}
	}
}