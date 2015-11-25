package com.scxh.socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Window {
public static void main(String[] args) {
	JFrame frame = new JFrame();
	frame.setBounds(200, 200, 500, 350);
	frame.setLayout(null);
	
	JTextArea area = new JTextArea();
	area.setBounds(20, 20, 400, 200);
	area.setEditable(false);
	JTextField text = new JTextField();
	text.setBounds(20, 250, 400, 30);
	
	
	
	frame.add(area);
	frame.add(text);
	frame.setVisible(true);
}
}
