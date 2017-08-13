package me.daniel.langton;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JOptionPane;
/*
 * 3rd party key listener:
 * http://portfolio.planetjon.ca/2011/09/16/java-global-jframe-key-listener/
 * */
public class KeyDispatcher implements KeyEventDispatcher {
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_TYPED) {
        	int code = (int) e.getKeyChar();
        	if(code==27) {
        		int n = JOptionPane.showConfirmDialog (null, "Would you like to stop the simulation?","LAS - Daniel Shaw",JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION) {
					Simulation.stopSim(false);
				}
        	}
        }
        return false;
    }
}