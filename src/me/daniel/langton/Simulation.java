package me.daniel.langton;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

public class Simulation extends Canvas implements Runnable {
	private Board board;
	private Ant[] ants;
	private static boolean isRunning = false, dialogshown = false, wasrandom=false, endednatural=true;
	private int clockspeed=100;
	private Thread thread;
	private String[][] initialBoard;
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static Date started, ended;
	public void paint(Graphics g) {
		board.paint(g);
		for(Ant a : ants) {
			a.paint(g);
		}
		if(!isRunning) {
			thread.stop();
			if(!dialogshown) {
				int n = JOptionPane.showConfirmDialog (null, "This simulation is finished. Would you like to dump the simulation data to a file?","LAS - Daniel Shaw",JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION) {
					try {
						dumpData();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Sorry, could not dump the information :(", "LAS - Dump Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				dialogshown = true;
			}
		}
		g.dispose();
	}
	
	
	public Simulation(int tsize, int speed, int width, int height, boolean random, int num) {
		Tile.TILE_WIDTH = tsize;
		Tile.TILE_HEIGHT = tsize;
		this.wasrandom = random;
		clockspeed = speed;
		board = new Board(width, height);
		board.initTiles(random);
		initialBoard = Board.toString(getBoard().getTiles());
		ants = new Ant[num];
		Random rng = new Random();
		for(int i=0;i<num;i++) {
			ants[i] = new Ant("ant" + i, (width/2)*Tile.TILE_WIDTH+(rng.nextInt(4)*Tile.TILE_WIDTH), (height/2)*Tile.TILE_HEIGHT+(rng.nextInt(4)*Tile.TILE_HEIGHT), rng.nextInt(3));
		}
	}
	
	private void dumpData() throws IOException {
		String user = System.getProperty("user.name");
	    String opath;
		if(System.getProperty("os.name").contains("Windows")) {
			if((int) Double.parseDouble(System.getProperty("os.version")) < 6) {
				//Win XP>=
				opath = "C:\\Documents and Settings\\" + user + "\\Desktop";
			} else {
				//Win XP<
				opath = "C:\\Users\\" + user + "\\Desktop"; 
			}
		} else if(System.getProperty("os.name").contains("Linux")) {
			//Linux
			opath = "/home/" + user + "/Desktop";
		} else if(System.getProperty("os.name").contains("Mac")) {
			//Mac OS X
			opath = "/Users/" + user + "/Desktop";
		} else {
			JOptionPane.showMessageDialog(null, "Unsupported OS, sorry :(", "LAS - Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	    File outfile = new File(opath + "/LAS Dump.txt");
		if (!outfile.exists()) {
			outfile.createNewFile();
		}
		FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		final String line = "-----------------------------------------------";
		bw.write("Langston's Ant Simulator - Generation data dump");
		bw.newLine();
		bw.write(line);
		bw.newLine();
		bw.write("Settings: ");
		bw.newLine();
		bw.write(line);
		bw.newLine();
		bw.write("\tGrid size: \t" + Tile.TILE_WIDTH + "x" + Tile.TILE_HEIGHT);
		bw.newLine();
		bw.write("\tClock speed: \t" + clockspeed + "ms per cycle");
		bw.newLine();
		bw.write("\tWindow size: \t" + Main.width + "x" + Main.height);
		bw.newLine();
		bw.write("\tRandom grid: \t" + wasrandom);
		bw.newLine();
		bw.write("\tNumber of ants:\t" + ants.length);
		bw.newLine();
		bw.write(line);
		bw.newLine();
		bw.write("Data at a glance:");
		bw.newLine();
		bw.write(line);
		bw.newLine();
		for(Ant a : ants) {
			bw.write("\t" + a.getID() + " moves:\t" + a.movesctr);
			bw.newLine();
		}
		bw.write("\tNatural finish: " + endednatural);
		bw.newLine();
		bw.write("\tTime started: \t" + dateFormat.format(started));
		bw.newLine();
		bw.write("\tTime finished: \t" + dateFormat.format(ended));
		bw.newLine();
		bw.write(line);
		bw.newLine();
		bw.write("Initial board:");	
		bw.newLine();
		bw.write(line);
		bw.newLine();
		for(String[] s : initialBoard) {
			for(String st : s) {
				bw.write(st);
			}
			bw.newLine();
		}
		bw.write(line);
		bw.newLine();
		bw.write("Final board:");
		bw.newLine();
		bw.write(line);
		bw.newLine();
		for(String[] s : Board.toString(getBoard().getTiles())) {
			for(String st : s) {
				bw.write(st);
			}
			bw.newLine();
		}
		for(Ant a : ants) {
			bw.write(line);
			bw.newLine();
			bw.write("List of " + a.getID() + " data throughout generation: ");
			bw.newLine();
			bw.write(line);
			bw.newLine();
			for(String s : a.getMoves()) {
				bw.write(s);
				bw.newLine();
			}
		}
		bw.close();
	}
	
	public static void stopSim(boolean natural) {
		isRunning = false;
		ended = new Date();
		endednatural = natural;
	}

	public Board getBoard() {
		return this.board;
	}
	
	public void initSim() {
		isRunning = true;
		started = new Date();
		thread = new Thread(this,"simulation");
		thread.start();
	}
	
	public void update(Graphics g) {
		//Double buffering
		Graphics offgc;
	    Image offscreen = null;
	    @SuppressWarnings("deprecation")
		Dimension d = size();
	    offscreen = createImage(d.width, d.height);
	    offgc = offscreen.getGraphics();
	    offgc.setColor(getBackground());
	    offgc.fillRect(0, 0, d.width, d.height);
	    offgc.setColor(getForeground());
	    paint(offgc);
	    g.drawImage(offscreen, 0, 0, this);
	}
	
	public void run() {
		while(isRunning) {
			try {
				for(Ant a : ants) {
					a.update(getBoard());
				}
				Thread.sleep(clockspeed);
				repaint();
			} catch(InterruptedException e) {}
		}
	}
}