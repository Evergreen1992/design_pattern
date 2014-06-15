package com.tankwar.view.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.tankwar.utils.DBHandler;
import com.tankwar.view.MainFrame;

/**
 * 存档选择
 * @author Administrator
 *
 */
public class RecordChoseDialog extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	JButton ok = null, cancel = null , refresh = null ;
	MainFrame mf = null ;
	JTable recordTable = null ;
	JPanel panel = null ;
	JScrollPane sp = null ;
	
	String[][] rowData = null;
	String[] columnNames = new String[]{"名称","关卡","得分","日期"};
	
	public RecordChoseDialog(MainFrame frame, String title){
		super(frame, "", true);
		this.mf = frame ;
		
		panel = new JPanel();
		//加载数据
		List<String> data = new DBHandler().getRecordData();
		rowData = new String[data.size()][];
		
		for(int i = 0 ; i < rowData.length; i++){
			rowData[i] = data.get(i).split(",") ;
		}
		
		JRootPane rp = this.getRootPane();
        rp.setWindowDecorationStyle(JRootPane.FRAME); 
        
        recordTable = new JTable(rowData, columnNames);
		
		ok = new JButton("确定");
		ok.setBounds(230, 220, 80, 30);
		ok.addActionListener(this);
		
		refresh = new JButton("刷新");
		refresh.setBounds(30, 220, 80, 30);
		refresh.addActionListener(this);
		
		cancel = new JButton("取消");
		cancel.setBounds(130, 220, 80, 30);
		cancel.addActionListener(this);
		
		sp = new JScrollPane();
	    sp.setBorder(new EmptyBorder(0,0,0,0));
	    sp.setViewportView(recordTable);
	    sp.setBounds(10, 0, 385, 200);
		
	    this.setUndecorated(true);
	    this.setLayout(null);

	    this.add(refresh);
	    this.add(ok);
	    this.add(cancel);
	    this.add(sp);
		
		//this.add(panel);
		
		this.setTitle(title);
		this.setSize(400, 280);
		this.setLocation((int)frame.getLocation().getX() + (frame.getWidth() - 400) / 2, (int)frame.getLocation().getY() + (frame.getHeight() - 280) / 2);
		this.setVisible(true);
	}
	
	
	public void refreshData(){
		
		this.panel.remove(sp);
		
		System.out.println("刷新");
		//加载数据
		List<String> data = new DBHandler().getRecordData();
		rowData = new String[data.size()][];
		
		for(int i = 0 ; i < rowData.length; i++){
			rowData[i] = data.get(i).split(",") ;
		}
		
		this.recordTable = new JTable(this.rowData, this.columnNames);
		
		JScrollPane sp = new JScrollPane();
	    sp.setBorder(new EmptyBorder(0,0,0,0));
	    sp.setViewportView(recordTable);
	    sp.setBounds(10, 0, 385, 200);
		
	    this.panel.add(sp);
	    this.panel.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == cancel)
			this.setVisible(false);
		else if( e.getSource() == ok){
			int selected = this.recordTable.getSelectedRow();
			System.out.println(selected);
			if( selected == -1){
				JOptionPane.showMessageDialog(this, "请选择一条记录", "警告", 1);
			}else{
				this.setVisible(false);
				this.mf.gameResume(this.rowData[selected]);
			}
		}else if( e.getSource() == refresh){
			refreshData();
		}
	}
}