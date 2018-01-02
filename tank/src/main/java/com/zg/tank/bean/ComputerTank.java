package com.zg.tank.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zg.tank.pojo.TankEntity;

public class ComputerTank extends Tank {

//	private static final Logger logger = LogManager.getLogger(ComputerTank.class);
	
	public ComputerTank(TankEntity entry) {
		super(entry);
	}

	@Override
	public void moving(int keyCode) {
//		logger.debug("ComputerTank moving ...");
	}

	@Override
	public void shooting() {
//		logger.debug("ComputerTank shooting ...");
	}

	
	
}
