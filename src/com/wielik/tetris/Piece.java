package com.wielik.tetris;

import java.awt.Color;

public class Piece {
	private int x;
	private int y;
	private int tileSize;
	private Color color;
	private int tileWidth;
	private int tileHeight;
	
	private boolean[][] blocks;
	private boolean[][] voidBlock = new boolean[][] {{false, false, false, false}, {false, false, false, false}, {false, false, false, false}, {false, false, false, false}};
	private boolean[][] IBlock = new boolean[][] {{false, true, false, false}, {false, true, false, false}, {false, true, false, false}, {false, true, false, false}};
	private boolean[][] OBlock = new boolean[][] {{true, true}, {true, true}};
	private boolean[][] TBlock = new boolean[][] {{false, true, false}, {true, true, true}, {false, false, false}};
	private boolean[][] SBlock = new boolean[][] {{false, true, true}, {true, true, false}, {false, false, false}};
	private boolean[][] ZBlock = new boolean[][] {{true, true, false}, {false, true, true}, {false, false, false}};
	private boolean[][] JBlock = new boolean[][] {{true, false, false}, {true, true, true}, {false, false, false}};
	private boolean[][] LBlock = new boolean[][] {{false, false, true}, {true, true, true}, {false, false, false}};
	
	public enum TYPE {VOID, I, O, T, S, Z, J, L};
	
	public Piece(int x, int y, int tileSize, TYPE type) {
		this.x = x;
		this.y = y;
		this.tileSize = tileSize;
		
		if(type == TYPE.VOID) {
			blocks = voidBlock;
			this.color = Color.GRAY;
		}
		if(type == TYPE.I) {
			blocks = IBlock;
			this.color = Color.MAGENTA;
		}
		if(type == TYPE.O) {
			blocks = OBlock;
			this.color = Color.RED;
		}
		if(type == TYPE.T) {
			blocks = TBlock;
			this.color = Color.CYAN;
		}
		if(type == TYPE.S) {
			blocks = SBlock;
			this.color = Color.GREEN;
		}
		if(type == TYPE.Z) {
			blocks = ZBlock;
			this.color = Color.ORANGE;
		}
		if(type == TYPE.J) {
			blocks = JBlock;
			this.color = Color.YELLOW;
		}
		if(type == TYPE.L) {
			blocks = LBlock;
			this.color = Color.WHITE;
		}
		
		this.tileWidth = this.blocks.length;
		this.tileHeight = this.blocks[0].length;
	}
	
	public void rotateRight() {
		int length = blocks.length - 1;
		for(int i = 0; i <= length/2; i++) {
			for(int j = i; j < length - i; j++) {
				boolean temp = blocks[i][j];
				
				blocks[i][j] = blocks[j][length - i];
				blocks[j][length - i] = blocks[length - i][length - j];
				blocks[length - i][length - j] = blocks[length - j][i];
				blocks[length - j][i] = temp;
			}
		}	
	}
	
	public void rotateLeft() {
		int length = blocks.length - 1;
		for(int i = 0; i <= length/2; i++) {
			for(int j = i; j < length - i; j++) {
				boolean temp = blocks[i][j];
				
				blocks[i][j] = blocks[length - j][i];
				blocks[length - j][i] = blocks[length - i][length - j];
				blocks[length - i][length - j] = blocks[j][length - i];
				blocks[j][length - i] = temp;
			}
		}	
	}
	
	public boolean[][] getBlocks() {
		return blocks;
	}
	
	public void move(int tileX, int tileY) {
		x += tileX * tileSize;
		y += tileY * tileSize;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public int getXboundary() {
		for(int i = 0; i < blocks.length; i++) {
			for(int j = blocks[i].length - 1; j >= 0; j--) {
				if(blocks[i][j]) return (tileSize * (j + 1));
			}
		}
		return 0;
	}
	
	public int getYboundary() {
		for(int i = blocks.length - 1; i >= 0 ; i--) {
			for(int j = 0; j < blocks[i].length; j++) {
				if(blocks[i][j]) return (tileSize * (i + 1));
			}
		}
		return 0;
	}
	
	public int getPixelCoordinateAtTile_X(int xx) {
		return x + (tileSize * xx);
	}
	
	public int getPixelCoordinateAtTile_Y(int yy) {
		return y + (tileSize * yy);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void update() {
		y += tileSize;
	}

	public void render(Renderer r) {
		for(int i = 0; i < blocks.length; i++) {
			for(int j = 0; j < blocks[i].length; j++) {
				if(blocks[i][j]) r.renderTile(x + tileSize * i, y + tileSize * j, tileSize, color);
				else r.renderBorderTile(x + tileSize * i, y + tileSize * j, tileSize, color);
			}
		}
	}
	
}
