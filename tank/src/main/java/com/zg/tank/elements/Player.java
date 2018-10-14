package com.zg.tank.elements;

import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zg.tank.bean.PlayerTank;
import com.zg.tank.bean.Tank;
import com.zg.tank.factory.TankFactory;
import com.zg.tank.panel.GamePlayingPanel;

public class Player {

	private static final Logger logger = LogManager.getLogger(PlayerTank.class);
	private int id;
	private int age;
	private Tank tank;
	private String playerName;
	
	private int shootingKey = KeyEvent.VK_SPACE;
	private int upKey = KeyEvent.VK_UP;
	private int downKey = KeyEvent.VK_DOWN;
	private int leftKey = KeyEvent.VK_LEFT;
	private int rightKey = KeyEvent.VK_RIGHT;
	
	public Player(int x, int y, String playerName,GamePlayingPanel gamePlayingPanel) {
		logger.debug("Create Player ...");
		tank = TankFactory.createPlayerTank(x,y,gamePlayingPanel);
	}
	
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Tank getTank() {
		return tank;
	}
	public void setTank(Tank tank) {
		this.tank = tank;
	}
	
	public int getShootingKey() {
		return shootingKey;
	}

	public void setShootingKey(int shootingKey) {
		this.shootingKey = shootingKey;
	}
	
	public int getUpKey() {
		return upKey;
	}

	public void setUpKey(int upKey) {
		this.upKey = upKey;
	}

	public int getDownKey() {
		return downKey;
	}

	public void setDownKey(int downKey) {
		this.downKey = downKey;
	}

	public int getLeftKey() {
		return leftKey;
	}

	public void setLeftKey(int leftKey) {
		this.leftKey = leftKey;
	}

	public int getRightKey() {
		return rightKey;
	}

	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
	}

	public void processKeyBoardEvent(int keyCode){
		
		if(upKey == keyCode || downKey == keyCode || leftKey == keyCode|| rightKey == keyCode){
			tank.moving(keyCode);
		}else if(shootingKey == keyCode){
			tank.shooting();
		}
	}
}
