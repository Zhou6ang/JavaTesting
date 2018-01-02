package com.zg.tank.pojo;

import java.awt.Color;

import com.zg.tank.elements.Player;

public class TankEntity {
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	private int x;
	private int y;
	private int width;
	private int hight;
	private int speed;
	private Direction direction;
	private int id;
	private String name;
	private Color color;
	private int level;
	private Player player;
	
	public TankEntity(int x, int y, int width, int hight,String name,Player player) {
		this.x = x;
		this.y = y;
		this.width =width;
		this.hight = hight;
		this.speed = 0;
		this.direction = Direction.UP;
		this.name = name;
		this.color = Color.BLUE;
		this.level = 0;
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWigth() {
		return width;
	}
	public void setWigth(int width) {
		player.setSize(width, player.getHeight());
	}
	public int getHight() {
		return hight;
	}
	public void setHight(int hight) {
		player.setSize(player.getWidth(), hight);
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
