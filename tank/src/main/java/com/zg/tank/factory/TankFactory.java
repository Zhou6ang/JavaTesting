package com.zg.tank.factory;

import com.zg.config.Config;
import com.zg.tank.bean.ComputerTank;
import com.zg.tank.bean.PlayerTank;
import com.zg.tank.bean.Tank;
import com.zg.tank.direction.Direction;
import com.zg.tank.panel.GamePlayingPanel;

public class TankFactory {

	private TankFactory(){}
	
	public static Tank createComputerTank(int posX, int posY,GamePlayingPanel gamePlayingPanel){
		return new ComputerTank(posX, posY, Config.COMPUTER_TANK_WIDTH, Config.COMPUTER_TANK_HIGHT, Config.COMPUTER_TANK_SPEED,
				Config.COMPUTER_TANK_NAME, Direction.DOWN,gamePlayingPanel);
	}
	
	public static Tank createPlayerTank(int posX, int posY,GamePlayingPanel gamePlayingPanel) {
		return new PlayerTank(posX, posY, Config.PLAYER_TANK_WIDTH, Config.PLAYER_TANK_HIGHT, Config.PLAYER_TANK_SPEED,
				Config.PLAYER_TANK_NAME, Direction.UP,gamePlayingPanel);
	}
}
