package me.daniel.langton;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {

	private boolean dark;
	public int x, y;
	public static int TILE_WIDTH=8, TILE_HEIGHT=8;
	public Tile(boolean dark, int x, int y) {
		this.dark = dark;
		this.x = x;
		this.y = y;
	}
	
	public boolean isDark() { return this.dark; }
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public void toggleState() { this.dark = !this.dark; }
	public void setState(boolean state) { this.dark = state; }
	
	public void paint(Graphics g) {
		if(isDark()) {
			g.setColor(new Color(125,75,0));
			g.fillRect(x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
		}
		g.setColor(Color.BLACK);
		g.drawRect(x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
	}

}