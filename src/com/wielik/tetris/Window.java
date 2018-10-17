package com.wielik.tetris;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private int WIDTH;
	private int HEIGHT;
	private String title;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	public Window(String title, int width, int height, JPanel leftPanel, JPanel rightPanel) {
		super(title);
		
		this.title = title;
		this.WIDTH = width;
		this.HEIGHT = height;
		
		this.leftPanel = leftPanel;
		this.rightPanel = rightPanel;
		
		setLayout(new BorderLayout());
		
		setup();
	}

	private void setup() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setSize(size);
		setTitle(title);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		add(leftPanel);
		add(rightPanel);
		pack();
	}
}
