package com.zg.tank.listener;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zg.tank.elements.Player;

public class KeyBoardListener implements KeyListener{
	
	private List<Player> players;
	private JPanel map;
	private static final Logger logger = LogManager.getLogger(KeyBoardListener.class);
	
//	public KeyBoardListener(List<Player> players) {
//		this.players = players;
//	}
	
	public KeyBoardListener(JPanel map) {
		this.map = map;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		logger.debug("keyTyped:"+e.getKeyCode()+","+e.getID()+","+e.paramString()+","+e.getModifiers());
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		logger.debug("keyReleased:" + e.getKeyCode() + "," + e.getID() + "," + e.paramString() + ","
				+ e.getModifiers());
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		
		for (Component comp : map.getComponents()) {
			if(comp instanceof Player){
				((Player)comp).processKeyBoardEvent(e.getKeyCode());
			}
		}
		
//		players.parallelStream().forEach(player ->{
//			player.processKeyBoardEvent(e.getKeyCode());
//		});;

	}
}
