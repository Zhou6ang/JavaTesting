package com.zg.tank.factory;

import com.zg.tank.bean.ComputerTank;
import com.zg.tank.bean.PlayerTank;
import com.zg.tank.bean.Tank;
import com.zg.tank.pojo.TankEntity;

public class TankFactory {

	private TankFactory(){}
	
	public static Tank createComputerTank(TankEntity tank){
		return new ComputerTank(tank);
	}
	
	public static Tank createPlayerTank(TankEntity tank){
		return new PlayerTank(tank);
	}
}
