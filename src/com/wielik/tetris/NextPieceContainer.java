package com.wielik.tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

public class NextPieceContainer extends Canvas {

	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	
	private int tileWidth;
	private int tileHeight;
	private int tileSize = 25;
	
	private Piece nextPiece;
	
	public NextPieceContainer(int width, int height) {
		this.width = width;
		this.height = height;
		tileWidth = (int) Math.floor(this.width / this.tileSize);
		tileHeight = (int) Math.floor(this.height / this.tileSize);
	}

	public NextPieceContainer() {
		tileWidth = 4;
		tileHeight = 4;
		this.width = tileSize * 4;
		this.height = tileSize * 4;
	}
	
	public void setNextPiece(Piece piece) {
		this.nextPiece = piece;
	}
	
	public Piece getNextPiece() {
		return nextPiece;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public void update() {
		
	}
	
	public void render(Renderer r) {
		if(nextPiece != null) nextPiece.render(r);
	}
}
