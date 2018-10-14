package com.zg.tank.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ComputerTank1 extends Tank1 {

//	private static final Logger logger = LogManager.getLogger(ComputerTank.class);
	
	public ComputerTank1(Tank entry) {
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
