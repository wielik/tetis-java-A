package com.wielik.tetris;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tetris implements Runnable {

	private final int WIDTH = 250;
	private final int HEIGHT = 625;
	private final String NAME = "TETRIS v0.1";
	private final double UPDATE_CAP = 1.0/60.0;
	
	private boolean running = false;
	private boolean shouldRender = false;
	private boolean unlimitedFPS = false;
	
	private Thread gameThread;
	
	
	private Window window;
	
	private Level level;
	private NextPieceContainer nextPieceCanvas;
	private Canvas scoreCanvas;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	
	private Renderer renderer;
	private Input input;
	
	public Tetris() {
	
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		
		level = new Level(this);
		nextPieceCanvas = new NextPieceContainer();
		scoreCanvas = new Canvas();
		
		nextPieceCanvas.setSize(100, HEIGHT/2);
		nextPieceCanvas.setPreferredSize(new Dimension(100, HEIGHT/2));
		nextPieceCanvas.setBackground(Color.BLUE);
		
		scoreCanvas.setSize(200, HEIGHT/2);
		scoreCanvas.setBackground(Color.WHITE);
		
		leftPanel.setSize(level.getPreferredSize());
		leftPanel.setLayout(new BorderLayout());
		rightPanel.setSize(200, HEIGHT);
		rightPanel.setLayout(new BoxLayout(rightPanel, 1));
		rightPanel.setLocation(leftPanel.getWidth(), leftPanel.getHeight());

		

		leftPanel.add(level);
		rightPanel.add(nextPieceCanvas);
		rightPanel.add(scoreCanvas);
		
		window = new Window(NAME, leftPanel.getWidth() + rightPanel.getWidth(), HEIGHT + 30, leftPanel, rightPanel);

		renderer = new Renderer(level);
		input = new Input();
		input.init(level);
		
		level.requestFocus();
	}
	
	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.start();
	}

	public synchronized void start() {
		running = true;
		gameThread = new Thread(this);
		gameThread.run();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		double ns = 1000000000.0;
		double lastTime = System.nanoTime() / ns;
		double nowTime = 0;
		double passedTime = 0;
		double unprocessedTime = 0;
		double counterTime = 0;
		
		int updateCount = 0;
		int renderCount  = 0;
		
		while(running) {
			nowTime = System.nanoTime() / ns;
			passedTime = nowTime - lastTime;
			lastTime = nowTime;
			
			unprocessedTime += passedTime;
			counterTime += passedTime;
			
			while (unprocessedTime >= UPDATE_CAP) {
				shouldRender = true;
				update();
				unprocessedTime -= UPDATE_CAP;
				updateCount++;
			}
			
			if(shouldRender || unlimitedFPS) {
				render();
				renderCount++;
				shouldRender = false;
			}
			
			else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (counterTime > 1) {
				window.setTitle(NAME + "  ||  " + "FPS: " + renderCount + " UPS: " + updateCount);
				System.out.println("FPS: " + renderCount + " UPS: " + updateCount);
				counterTime = 0;
				updateCount = 0;
				renderCount = 0;
			}
		}
 	}

	public void update() {
		level.update(input);
	}
	
	public void render() {
		renderer.clear();
		level.render(renderer);
		nextPieceCanvas.render(renderer);
		renderer.drawScreen();
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getWidth() {
		return WIDTH;
	}
}
