package com.wielik.tetris;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements MouseMotionListener, MouseListener, KeyListener {

	private int mouseX;
	private int mouseY;
	
	private boolean[] keysPressed = new boolean[256];
	private boolean[] lastKeysPressed = new boolean[256];
	private boolean[] mousePressed = new boolean[5];
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keysPressed[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysPressed[e.getKeyCode()] = false;
		lastKeysPressed[e.getKeyCode()] = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public void init(Component comp) {
		comp.addKeyListener(this);
		comp.addMouseListener(this);
		comp.addMouseMotionListener(this);
	}
	
	public boolean isKeyPressed(int keyCode) {
		return keysPressed[keyCode];
	}
	
	public boolean isKeyUp(int keyCode) {
		return !keysPressed[keyCode] && lastKeysPressed[keyCode];
	}
	
	public boolean isKeyDown(int keyCode) {
		return keysPressed[keyCode] && !lastKeysPressed[keyCode];
	}
	
	public boolean isMousePressed(int keyCode) {
		return mousePressed[keyCode];
	}
	
	public void update() {
		for(int i = 0; i < 256; i++) {
			lastKeysPressed[i] = keysPressed[i];
		}
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}

}
