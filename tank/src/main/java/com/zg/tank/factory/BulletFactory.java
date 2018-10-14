package com.zg.tank.factory;

import com.zg.tank.bullet.Bullet;
import com.zg.tank.direction.Direction;
import com.zg.tank.panel.GamePlayingPanel;

public class BulletFactory {

	private BulletFactory(){}
	
	public static Bullet createBullet(int x,int y,Direction direction,GamePlayingPanel gamePlayingPanel){
		return new Bullet(x, y, direction, gamePlayingPanel);
	}
	
}
