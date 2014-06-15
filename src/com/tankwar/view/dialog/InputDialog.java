package com.tankwar.view.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import com.tankwar.view.MainFrame;

/**
 * 输入框
 * @author Administrator
 *
 */
public class InputDialog extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextField input = null ;
	JButton ok = null ;
	MainFrame mf = null ;
	
	public InputDialog(MainFrame frame, String title){
		super(frame, "", true);
		
		this.mf = frame ;
		
		JRootPane rp = this.getRootPane();
        rp.setWindowDecorationStyle(JRootPane.FRAME); 
        
		input = new JTextField(30);
		input.setBounds(60, 10, 200, 30);
		ok = new JButton("确定");
		ok.setBounds(100, 50, 80, 30);
		ok.addActionListener(this);
		
		this.setUndecorated(true);
		this.setLayout(null);
		
		this.add(input);
		this.add(ok);
		
		this.setTitle(title);
		this.setSize(350, 130);
		this.setLocation((int)frame.getLocation().getX() + (frame.getWidth() - 350) / 2, (int)frame.getLocation().getY() + (frame.getHeight() - 130) / 2);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == ok){
			this.mf.saveScore(this.input.getText());//保存得分
			this.mf.backToMenu();
			this.setVisible(false);
		}
	}
}