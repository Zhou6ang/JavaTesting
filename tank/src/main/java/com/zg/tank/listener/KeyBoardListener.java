package com.zg.tank.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zg.tank.elements.Player;

public class KeyBoardListener implements KeyListener{
	
	private Player player;
	private static final Logger logger = LoggerFactory.getLogger(KeyBoardListener.class);
	
	
	public KeyBoardListener(Player player) {
		this.player = player;
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
		
		player.processKeyBoardEvent(e.getKeyCode());

	}
}
