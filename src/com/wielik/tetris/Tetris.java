package com.wielik.tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Tetris extends Canvas implements Runnable {

	private final int WIDTH = 350;
	private final int HEIGHT = 725;
	private final String NAME = "TETRIS v0.1";
	private final double UPDATE_CAP = 1.0/60.0;
	
	private boolean running = false;
	private boolean shouldRender = false;
	private boolean unlimitedFPS = false;
	
	private Thread gameThread;
	private JFrame frame;
	
	private Level level;
	private Renderer renderer;
	private Input input;
	
	public Tetris() {
		super();
		frame = new JFrame(NAME);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(size);
		frame.setMaximumSize(size);
		frame.setMinimumSize(size);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(this);
		frame.pack();
		
		level = new Level(this);
		renderer = new Renderer(this);
		input = new Input();
		input.init(this);
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
				frame.setTitle(NAME + "  ||  " + "FPS: " + renderCount + " UPS: " + updateCount);
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
