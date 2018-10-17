package com.wielik.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Renderer {

	Graphics g;
	BufferStrategy bs;
	BufferedImage image;
	
	Level level;
	
	public Renderer(Level level) {
		this.level = level;
		image = new BufferedImage(level.getWidth(), level.getHeight(), BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();

	}
	
	public void renderTile(int xPos, int yPos, int tileSize, Color color) {
		g.setColor(color);
		g.fillRect(xPos, yPos, tileSize, tileSize);
		g.setColor(Color.BLACK);
		g.drawRect(xPos, yPos, tileSize - 1, tileSize - 1);
		g.drawRect(xPos + 1, yPos + 1, tileSize - 2, tileSize - 2);
	}
	
	public void renderBorderTile(int xPos, int yPos, int tileSize, Color color) {
		g.setColor(color);
		g.drawRect(xPos, yPos, tileSize - 1, tileSize - 1);
	}
		
	public void drawScreen() {
		Graphics gameG = level.getGraphics();
		gameG.drawImage(image, 0, 0, null);
		gameG.dispose();
	}
	
	public void clear() {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
	}
}
