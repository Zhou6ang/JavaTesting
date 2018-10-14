package com.zg.tank.bean;

public abstract class Tank1 {
	protected Tank tankProp;
	public Tank1(Tank tankProp) {
		this.tankProp = tankProp;
	}
	public Tank getTankProp(){
		return tankProp;
	}
	public abstract void moving(int e);
	
	public abstract void shooting();
	
	
	
}
