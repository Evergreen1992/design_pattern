package com.tankwar.view.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import com.tankwar.view.MainFrame;

public class Dialog extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton ok , cancel = null ;
	JPanel panel = null ;
	JLabel info = null ;

	public Dialog(MainFrame frame, String str){
		
		super(frame, "", true);
		
		JRootPane rp = this.getRootPane();
        rp.setWindowDecorationStyle(JRootPane.FRAME); 

        info = new JLabel(str);
        panel = new JPanel();
        ok = new JButton("确定");
        cancel = new JButton("取消");
        
        ok.addActionListener(this);
        cancel.addActionListener(this);
        
        panel.add(info, BorderLayout.CENTER);         
        panel.add(ok,BorderLayout.EAST);
        panel.add(cancel,BorderLayout.WEST);
        
        info.setBounds(80, 1, 200, 30);
        ok.setBounds(30, 35, 80, 30);
        cancel.setBounds(170, 35, 80, 30);
        this.setLayout(null);
        
        
        this.add(info);
        this.add(ok);
        this.add(cancel);
 
        this.setTitle("确认信息");
		this.setUndecorated(true);
		this.setSize(300, 100);
		this.setLocation((int)frame.getLocation().getX() + (frame.getWidth() - 300) / 2, (int)frame.getLocation().getY() + (frame.getHeight() - 100) / 2);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == this.ok){
			System.out.println("ok");
		}else if( e.getSource() == this.cancel){
			System.out.println("cancel");
		}
	}
}