package me.daniel.langton;

import java.awt.Graphics;
import java.util.Random;

public class Board {

	private static int width;
	private static int height;
	private Tile[][] tiles;
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
	}
	
	public Tile getTileAt(int i, int j) {
		if(i<0 || i>=width || j<0 || j>=height) {
			return null;
		} else {
			return tiles[i][j];
		}
	}
	
	public void initTiles(boolean genRandom) {
		for(int i = 0; i<width; i++) {
			for(int j = 0; j<height; j++) {
				boolean dark = false;
				if(genRandom) {
					Random random = new Random();
					dark = random.nextBoolean();
				}
				tiles[i][j] = new Tile(dark, i*Tile.TILE_WIDTH, j*Tile.TILE_HEIGHT);
			}
		}
	}
	
	public Tile[][] getTiles() {
		return this.tiles;
	}
	
	public static String[][] toString(Tile[][] t) {
		String[][] string = new String[width][height];
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				if(t[i][j].isDark()) {
					string[i][j] = "*";
				} else {
					string[i][j] = ".";
				}
			}
		}
		return string;
	}
	
	public void paint(Graphics g) {
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				tiles[i][j].paint(g);
			}
		}
	}

}