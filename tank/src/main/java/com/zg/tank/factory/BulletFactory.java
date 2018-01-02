package com.zg.tank.factory;

import com.zg.tank.bean.Tank;
import com.zg.tank.elements.Bullet;

public class BulletFactory {

	private BulletFactory(){}
	
	public static Bullet createBullet(Tank tank){
		return new Bullet(tank);
	}
	
}
