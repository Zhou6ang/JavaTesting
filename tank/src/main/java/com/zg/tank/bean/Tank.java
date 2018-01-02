package com.zg.tank.bean;

import com.zg.tank.pojo.TankEntity;

public abstract class Tank {
	protected TankEntity tankProp;
	public Tank(TankEntity tankProp) {
		this.tankProp = tankProp;
	}
	public TankEntity getTankProp(){
		return tankProp;
	}
	public abstract void moving(int e);
	
	public abstract void shooting();
	
	
	
}
