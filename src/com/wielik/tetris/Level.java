package com.wielik.tetris;

import java.awt.Color;
import java.util.Random;

import javafx.scene.input.KeyCode;

@SuppressWarnings("serial")
public class Level {
	
	Random random;
	
	private int width = 250;
	private int height = 625;
	
	private int spawn_x = 100;
	private int spawn_y = 25;
	
	private int tileWidth;
	private int tileHeight;
	private int tileSize = 25;
	
	private boolean[][] tiles;
	private Color[][] colors;
	
	private Piece currentPiece;
	private Piece nextPiece;
	
	private int clock = 0;
	private int difficulty_level = 1;
	

	public Level(Tetris game) {
		super();
	
		tileWidth = (int) Math.floor(this.width / this.tileSize);
		tileHeight = (int) Math.floor(this.height / this.tileSize);
		
		tiles = new boolean[tileWidth][tileHeight];
		colors = new Color[tileWidth][tileHeight];
		
	}
	
	private void removeRow(int row) {
		for(int i = 0; i < tileWidth; i++) {
			resetTile(i, row);
		}
	}
	
	public void setTile(int tileX, int tileY) {
		tiles[tileX][tileY] = true;
	}
	
	public void resetTile(int tileX, int tileY) {
		tiles[tileX][tileY] = false;
	}
	
	public void toggleTile(int tileX, int tileY) {
		tiles[tileX][tileY] = !tiles[tileX][tileY];
	}
	
	public int getTileCoordinateFromPixel(int pixelValue) {
		int tile = (int) Math.floor((pixelValue / tileSize));
		return tile;
	}
	
	private boolean isRowComplete(int row) {
		for(int i = 0; i < tileWidth; i++) {
			if(!tiles[i][row]) return false;
		}
		return true;
	}
	
	private void moveLevelDown(int startingRow) {
		for(int i = 0; i < tileWidth; i++) {
			for(int j = startingRow; j > 0; j--) {
				tiles[i][j] = tiles[i][j-1];
			}
			resetTile(i, 0);
		}
	}
	
	private Piece RandomizePiece() {
		random = new Random();
		Piece randomPiece = null;
		int r_int = random.nextInt(7);
		if(r_int == 0) randomPiece = new Piece(spawn_x, spawn_y, 25, Piece.TYPE.I);
		if(r_int == 1) randomPiece = new Piece(spawn_x, spawn_y, 25, Piece.TYPE.J);
		if(r_int == 2) randomPiece = new Piece(spawn_x, spawn_y, 25, Piece.TYPE.L);
		if(r_int == 3) randomPiece = new Piece(spawn_x, spawn_y, 25, Piece.TYPE.O);
		if(r_int == 4) randomPiece = new Piece(spawn_x, spawn_y, 25, Piece.TYPE.S);
		if(r_int == 5) randomPiece = new Piece(spawn_x, spawn_y, 25, Piece.TYPE.T);
		if(r_int == 6) randomPiece = new Piece(spawn_x, spawn_y, 25, Piece.TYPE.Z);
		return randomPiece;
	}
	
	private void readInput(Input input) {
		
		if(input.isMousePressed(1)) {
			int pixX = input.getMouseX();
			int pixY = input.getMouseY();
			if (pixX >= width || pixX < 0 || pixY < 0 || pixY >= height) return;
			setTile(getTileCoordinateFromPixel(pixX), getTileCoordinateFromPixel(pixY));
		}
		if(input.isKeyPressed(68) || input.isKeyPressed(39)) {
			//Pressed D or ->
			if(currentPiece.getX() + currentPiece.getXboundary() < width ) {
				currentPiece.move(1, 0);  
			}
		}
		if(input.isKeyPressed(65) || input.isKeyPressed(37)) {
			//Pressed A or <-
			if(currentPiece.getX() > 0) currentPiece.move(-1, 0); 
		}
		if(input.isKeyPressed(40) || input.isKeyPressed(32) || input.isKeyPressed(83)) {
			//Pressed S or down arrow or space
			currentPiece.move(0, 1); 
		}
		if(input.isKeyDown(81)) {
			//Pressed Q
			if(currentPiece.getX() > 0) currentPiece.rotateLeft();
		}
		if(input.isKeyDown(69)) {
			//Pressed E
			if(currentPiece.getX() + currentPiece.getXboundary() < width ) currentPiece.rotateRight();
		}	
		input.update();
	}
	
	
	public boolean isCollision(Piece piece) {
		int pieceWidth = piece.getBlocks().length;  //szerokosc klocka w kratkach
		int pieceHeight = piece.getBlocks()[0].length; //wysokosc klocka w kratkach
		int piecePosX = getTileCoordinateFromPixel(piece.getX()); //pozycja klocka w kratkach
		int piecePosY = getTileCoordinateFromPixel(piece.getY()) + 1; //plus 1 zeby sprawdzic kolizje z elementem pod klockiem
		
		for(int i = pieceWidth - 1; i >= 0; i--) {
			for(int j = pieceHeight - 1; j >= 0 ; j--) {
				//Sprawdzenie kolizji z podloga
				if(piece.getBlocks()[i][j] && ((piece.getY() + ((j + 1) * 25)) >= height)) return true;
				//Sprawdzenie kolizji z polozonymi klockami
				if(piece.getBlocks()[i][j]) {
					if(tiles[piecePosX + i][piecePosY + j]) return true;
				}
			}
		}
		return false;
	}
	
	public void transform(Piece piece) {
		int pieceWidth = piece.getBlocks().length;
		int pieceHeight = piece.getBlocks()[0].length;
		int piecePosX = getTileCoordinateFromPixel(piece.getX()); //pozycja klocka w kratkach
		int piecePosY = getTileCoordinateFromPixel(piece.getY());
		
		for(int i = 0; i < pieceWidth; i++) {
			for(int j = 0; j < pieceHeight; j++) {
				if(piece.getBlocks()[i][j]) {
					tiles[piecePosX + i][piecePosY + j] = true;
					colors[piecePosX + i][piecePosY + j] = piece.getColor();
				}
			}
		}
		currentPiece = null;
	}
	
	public void update(Input input) {
		if(currentPiece == null) currentPiece = RandomizePiece();
		
		clock++;
		
		if(isCollision(currentPiece)) {
			transform(currentPiece);
			System.out.print("KOLIZJA");
		}
		else {
			if(clock >= (60 / difficulty_level)) {
				currentPiece.update();
				clock = 0;
			}
			readInput(input);
		}
		
		
		for(int i = 0; i < tileHeight; i++) {
			if(isRowComplete(i)) {
				removeRow(i);
				moveLevelDown(i);
			}
		}
	}
	
	public void render(Renderer r) {
		for(int i = 0; i < tileWidth; i++) {
			for(int j = 0; j < tileHeight; j++)
			if(tiles[i][j]) r.renderTile(i * tileSize, j * tileSize, tileSize, colors[i][j]);
			else r.renderTile(i * tileSize, j * tileSize, tileSize, Color.LIGHT_GRAY);
		}
		if(currentPiece != null) currentPiece.render(r);
	}
}
