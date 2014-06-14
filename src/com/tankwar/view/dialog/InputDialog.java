package com.tankwar.view.dialog;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.JTextField;

/**
 * 输入框
 * @author Administrator
 *
 */
public class InputDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextField input = null ;
	JButton ok = null ;
	
	public InputDialog(){
		JRootPane rp = this.getRootPane();
        rp.setWindowDecorationStyle(JRootPane.FRAME); 
        
		input = new JTextField(30);
		input.setBounds(60, 10, 200, 30);
		ok = new JButton("确定");
		ok.setBounds(50, 50, 80, 30);
		
		this.setUndecorated(true);
		this.setLayout(null);
		
		this.add(input);
		this.add(ok);
		
		this.setSize(350, 130);
		this.setVisible(true);
	}
	
	public static void main(String[] grs){
		new InputDialog();
	}
}