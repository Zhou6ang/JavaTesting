/**
 * 
 */
package com.zg.tank.bullet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zg.config.Config;
import com.zg.config.CustomImages;
import com.zg.tank.direction.Direction;
import com.zg.tank.panel.GamePlayingPanel;

/**
 * @author Administrator
 *
 */
public class Bullet implements BulletAction{
	
	private Logger logger = LoggerFactory.getLogger(Bullet.class);

	private int posX;
	private int posY;
	private int width;
	private int hight;
	private int speed;
	private Direction direction;
	private int id;
	private String name;
	
	private GamePlayingPanel gamePlayingPanel;
	
	public Bullet(int x,int y,Direction direction,GamePlayingPanel gamePlayingPanel) {
		this.posX = x;
		this.posY = y;
//		this.width = width;
//		this.hight = hight;
//		this.speed = speed;
		this.width = Config.BULLET_WIDTH;
		this.hight = Config.BULLET_HIGHT;
		this.speed = Config.BULLET_SPEED;
		this.direction = direction;
		this.gamePlayingPanel = gamePlayingPanel;
	}
	
	public int getPosX() {
		return posX;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	private String color;
	
	private void draw() {
		logger.debug("drawing bullet image...");
		gamePlayingPanel.getGraphics().drawImage(CustomImages.bulletImg, getPosX(), getPosY(), getWidth(), getHight(), gamePlayingPanel);
	}
	
	@Override
	public boolean moving() {
		if(direction.equals(Direction.UP)){
			setPosY(getPosY() - getSpeed());
			if(getPosY()<0){
				gamePlayingPanel.getAllBullets().remove(this);
				return false;
			}
//			setLocation(x, y);
			draw();
			logger.debug("Bullet [UP] moving...");
		}else if(direction.equals(Direction.DOWN)){
//			y += offset;
			
			setPosY(getPosY()+getSpeed());
			
			if(getPosY() > Config.GAME_PLAYING_PANEL_HIGHT){
				gamePlayingPanel.getAllBullets().remove(this);
				return false;
			}
//			setLocation(x, y);
			logger.debug("Bullet [DOWN] moving...");
		}else if(direction.equals(Direction.LEFT)){
//			x -= offset;
			setPosX(getPosX()-getSpeed());
			if(getPosX() < 0){
				gamePlayingPanel.getAllBullets().remove(this);
				return false;
			}
//			setLocation(x, y);
			logger.debug("Bullet [LEFT] moving...");
		}else if(direction.equals(Direction.RIGHT)){
			
//			x += offset;
			setPosX(getPosX()+getSpeed());
			if( getPosX() > Config.GAME_PLAYING_PANEL_WIDTH){
				gamePlayingPanel.getAllBullets().remove(this);
				return false;
			}
//			setLocation(x, y);
			logger.debug("Bullet [RIGHT] moving...");
		}
		return true;
	}
	
}
