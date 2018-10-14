package com.zg.tank.panel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.zg.tank.bean.Tank;
import com.zg.tank.bullet.Bullet;

public class GamePlayingPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean isAlive = false;
	
	private List<Tank> tanksList = new ArrayList<>();
	
	private List<Bullet> bulletsList = new ArrayList<>();

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public void addTanks(Tank tank){
		tanksList.add(tank);
	}
	
	public List<Tank> getAllTanks(){
		return tanksList;
	}
	
	public void addBullets(Bullet bullet){
		bulletsList.add(bullet);
	}
	
	public List<Bullet> getAllBullets(){
		return bulletsList;
	}

}
