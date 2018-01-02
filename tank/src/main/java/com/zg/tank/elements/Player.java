package com.zg.tank.elements;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zg.tank.bean.Tank;
import com.zg.tank.factory.TankFactory;
import com.zg.tank.pojo.TankEntity;

public class Player extends JPanel {

	/**
	 * 
	 */
	private static final Logger logger = LogManager.getLogger(Player.class);
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int age;
	private Tank tank;
	private int shootingKey = KeyEvent.VK_SPACE;
	private int upKey = KeyEvent.VK_UP;
	private int downKey = KeyEvent.VK_DOWN;
	private int leftKey = KeyEvent.VK_LEFT;
	private int rightKey = KeyEvent.VK_RIGHT;
	
	
	public Player(int x, int y, String playerName) {
		logger.debug("create Player ...");
		tank = TankFactory.createPlayerTank(new TankEntity(x, y, 20, 20, playerName,this));
		int x1 = tank.getTankProp().getX()-tank.getTankProp().getWigth();
		int y1 = tank.getTankProp().getY()-tank.getTankProp().getHight();
		this.setBounds(x1, y1, tank.getTankProp().getWigth(), tank.getTankProp().getHight());
		this.setBackground(tank.getTankProp().getColor());
		this.setName(tank.getTankProp().getName());
		
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
