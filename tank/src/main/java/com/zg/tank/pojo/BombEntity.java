package com.zg.tank.pojo;

import java.awt.Color;

public class BombEntity {

	enum Direction {
		NORTH, SOUTH, WEST, EAST
	}

	private int x;
	private int y;
	private int weight;
	private int hight;
	private int speed;
	private Direction direction;
	private int id;
	private String name;
	private Color color;
	private String voice;
	private String bombLevel;
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
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHight() {
		return hight;
	}
	public void setHight(int hight) {
		this.hight = hight;
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
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public String getBombLevel() {
		return bombLevel;
	}
	public void setBombLevel(String bombLevel) {
		this.bombLevel = bombLevel;
	}
	
}
