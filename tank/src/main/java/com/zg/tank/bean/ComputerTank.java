package com.zg.tank.bean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zg.config.CustomImages;
import com.zg.tank.direction.Direction;
import com.zg.tank.panel.GamePlayingPanel;

public class ComputerTank extends Tank {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(ComputerTank.class);
	
	public ComputerTank(int posX, int posY, int width, int hight,int speed,String name,Direction direction,GamePlayingPanel gamePlayingPanel) {
		super(posX, posY, width, hight, speed, name, direction, gamePlayingPanel);
	}
	
	@Override
	public void draw() {
		logger.debug("drawing a computer tank...");
		GamePlayingPanel gamePlayingPanel = getGamePlayingPanel();
		gamePlayingPanel.getGraphics().drawImage(CustomImages.computerTankImg, getPosX(), getPosY(), getWidth(), getHight(), gamePlayingPanel);
	}

	@Override
	public void moving(int e) {
		
	}

	@Override
	public void shooting() {
		// TODO Auto-generated method stub
		
	}

}
