package me.daniel.langton;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	public static int width=800, height=600;
	public static void main(String ... args) {
		int tsize = 16, speed = 100, num=1;
		boolean isRandom=false;
		for(String s : args) {
			if(s.equalsIgnoreCase("-h")) {
				System.out.println("Langton's Ant Simulator - By Daniel Shaw");
				System.out.println("Simulates Langton's Ant graphically");
				System.out.println("\nArguments:");
				System.out.println("-h\t\t\tPrint help");
				System.out.println("-v\t\t\tDisplay version");
				System.out.println("--tile-size <int>\tSet the tile size (must be greater than 8 and less than 32)");
				System.out.println("--speed <ms>\t\tSet the speed of the simulation (one iteration every n milliseconds)\n\t\t\tHas to be greater than 0");
				System.out.println("--random\t\tGenerate a random grid");
				System.out.println("--width <int>\t\tSet grid width to n, must be greater than 100");
				System.out.println("--height <int>\t\tSet grid height to n, must be greater than 100");
				System.out.println("--ants <int>\t\tSet number of ants");
				return;
			} else if(s.startsWith("--tile-size")) {
				if(s.length()<="--tile-size".length()) {
					System.out.println("Invalid argument: \"" + s + "\"");
					continue;
				}
				try {
					tsize = Integer.parseInt(s.trim().split(" ")[1]);
					if(tsize > 32) tsize=32;
					else if(tsize < 8) tsize=8;
				} catch(NumberFormatException e) {
					System.out.println("Could not parse tile size from input, using tile size of " + tsize);
				}
			} else if(s.startsWith("--speed")) {
				if(s.length()<="--speed".length()) {
					System.out.println("Invalid argument usage: \"" + s + "\"");
					continue;
				}
				try {
					speed = Integer.parseInt(s.trim().split(" ")[1]);
					if(speed<=0) speed=1;
				} catch(NumberFormatException e) {
					System.out.println("Could not parse speed from input, using speed of " + speed);
				}
			} else if(s.startsWith("--random")) {
				isRandom = true;
			} else if(s.startsWith("--width")) {
				if(s.length()<="--width".length()) {
					System.out.println("Invalid argument usage: \"" + s + "\"");
					continue;
				}
				try {
					width = Integer.parseInt(s.trim().split(" ")[1]);
					if(width<=100) width=101;
				} catch(NumberFormatException e) {
					System.out.println("Could not parse width from input, using width of " + width);
				}
			} else if(s.startsWith("--height")) {
				if(s.length()<="--height".length()) {
					System.out.println("Invalid argument usage: \"" + s + "\"");
					continue;
				}
				try {
					height = Integer.parseInt(s.trim().split(" ")[1]);
					if(height<=100) height=101;
				} catch(NumberFormatException e) {
					System.out.println("Could not parse height from input, using height of " + height);
				}
			} else if(s.startsWith("--ants")) {
				if(s.length()<="--ants".length()) {
					System.out.println("Invalid argument usage: \"" + s + "\"");
					continue;
				}
				try {
					num = Integer.parseInt(s.trim().split(" ")[1]);
					if(num<=0) num=1;
				} catch(NumberFormatException e) {
					System.out.println("Could not parse number of ants from input, using " + num + " ant(s)");
				}
			} else if(s.equalsIgnoreCase("-v")) {
				System.out.println("[LAS version 1.0_4]");
				return;
			}
		}
		(new Main()).init(tsize, speed, width, height, isRandom, num);
	}
	
	public void init(int tsize, int speed, int width, int height, boolean random, int num) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException e) {} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		JFrame frame = new JFrame("Langton's Ant Simulator - By Daniel Shaw");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width+tsize/2,(int)(height+tsize*1.2));
		Simulation s = new Simulation(tsize, speed, width/tsize, height/tsize, random, num);
		/*
		 * 3rd party key listener:
		 * http://portfolio.planetjon.ca/2011/09/16/java-global-jframe-key-listener/
		 * */
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher( new KeyDispatcher() );
		frame.add(s);
		frame.setVisible(true);
		s.initSim();
	}
}