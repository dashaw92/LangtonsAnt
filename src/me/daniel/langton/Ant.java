package me.daniel.langton;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Ant {

	/*
	 * Directions:
	 * 0: Up
	 * 1: Right
	 * 2: Down
	 * 3: Left
	 * */
	private int x, y, direction, SIZE=8;
	public int movesctr = 0;
	private String id;
	private List<String> moves = new ArrayList<String>();
	public Ant(String id, int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.SIZE = Tile.TILE_WIDTH/2;
		this.direction = direction;
	}
	
	public List<String> getMoves() {
		return this.moves;
	}
	
	public String getID() {
		return this.id;
	}
	
	
	public void update(Board b) {
		Tile currentTile = b.getTileAt(x/Tile.TILE_WIDTH, y/Tile.TILE_HEIGHT);
		if(currentTile == null) {
			Simulation.stopSim(true);
		} else {
			if(currentTile.isDark()) {
				//Tile is dark, turn left
				switch(direction) {
					case 0:
						direction = 1;
						x+=Tile.TILE_WIDTH;
						break;
					case 1:
						direction = 2;
						y+=Tile.TILE_HEIGHT;
						break;
					case 2:
						direction = 3;
						x-=Tile.TILE_WIDTH;
						break;
					case 3:
						direction = 0;
						y-=Tile.TILE_WIDTH;
						break;
					default:
						direction = 0;
						break;
				}
			} else {
				//Tile is light, turn right
				switch(direction) {
					case 0:
						direction = 3;
						x-=Tile.TILE_WIDTH;
						break;
					case 1:
						direction = 0;
						y-=Tile.TILE_HEIGHT;
						break;
					case 2:
						direction = 1;
						x+=Tile.TILE_WIDTH;
						break;
					case 3:
						direction = 2;
						y+=Tile.TILE_WIDTH;
						break;
					default:
						direction = 0;
						break;
				}
			}
			currentTile.toggleState();
			Tile newTile = b.getTileAt(x/Tile.TILE_WIDTH, y/Tile.TILE_HEIGHT);
			if(newTile == null) {
				newTile = currentTile;
			}
			moves.add(getID() + "{X:" + x + ", Y:" + y + ", Direction:" + direction + ", CurrTileState: " + ((newTile.isDark())? "dark" : "light") + ", Move#:" + movesctr++ + "}");
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x+Tile.TILE_WIDTH/2-SIZE/2, y+Tile.TILE_HEIGHT/2-SIZE/2, SIZE, SIZE);
		g.setColor(Color.BLACK);
		switch(direction) {
			case 0:
				g.drawLine(x, y+Tile.TILE_HEIGHT, x+Tile.TILE_WIDTH/2, y);
				g.drawLine(x+Tile.TILE_WIDTH/2, y, x+Tile.TILE_WIDTH, y+Tile.TILE_HEIGHT);
				break;
			case 1:
				g.drawLine(x, y, x+Tile.TILE_WIDTH, y+Tile.TILE_HEIGHT/2);
				g.drawLine(x+Tile.TILE_WIDTH, y+Tile.TILE_HEIGHT/2, x, y+Tile.TILE_HEIGHT);
				break;
			case 2:
				g.drawLine(x, y, x+Tile.TILE_WIDTH/2, y+Tile.TILE_HEIGHT);
				g.drawLine(x+Tile.TILE_WIDTH/2, y+Tile.TILE_HEIGHT, x+Tile.TILE_WIDTH, y);
				break;
			case 3:
				g.drawLine(x+Tile.TILE_WIDTH, y, x, y+Tile.TILE_HEIGHT/2);
				g.drawLine(x,y+Tile.TILE_HEIGHT/2, x+Tile.TILE_WIDTH, y+Tile.TILE_HEIGHT);
		}
	}

}