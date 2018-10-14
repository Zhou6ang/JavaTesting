package com.zg.tank.bean;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.zg.tank.direction.Direction;
import com.zg.tank.panel.GamePlayingPanel;

public abstract class Tank implements TankAction{
	
	//moving key
	protected int SHOOTING_KEY = KeyEvent.VK_SPACE;
	protected int UP_KEY = KeyEvent.VK_UP;
	protected int DOWN_KEY = KeyEvent.VK_DOWN;
	protected int LEFT_KEY = KeyEvent.VK_LEFT;
	protected int RIGHT_KEY = KeyEvent.VK_RIGHT;

	private int posX;
	private int posY;
	private int width;
	private int hight;
	private int speed;
	private Direction direction;
	private int id;
	private String name;
	private Color color;
	private int level;
	
	private GamePlayingPanel gamePlayingPanel;
	
	public Tank(int posX, int posY, int width, int hight,int speed,String name,Direction direction,GamePlayingPanel gamePlayingPanel) {
		this.posX = posX;
		this.posY = posY;
		this.width =width;
		this.hight = hight;
		this.speed = speed;
		this.direction = direction;
		this.name = name;
		this.color = Color.BLUE;
		this.level = 0;
		this.gamePlayingPanel = gamePlayingPanel;
		draw();
		gamePlayingPanel.addTanks(this);
	}
	
	public GamePlayingPanel getGamePlayingPanel() {
		return gamePlayingPanel;
	}

	public abstract void draw();

	public int getPosX() {
		return posX;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHight() {
		return hight;
	}

	public void setHight(int hight) {
		this.hight = hight;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}


	public int getPosY() {
		return posY;
	}


	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
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
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

}
