package com.zg.tank.thread;

import com.zg.config.Config;
import com.zg.tank.bullet.Bullet;
import com.zg.tank.panel.GamePlayingPanel;

public class BulletThread implements Runnable{
	private GamePlayingPanel gamePlayingPanel;
	public BulletThread(GamePlayingPanel panel) {
		gamePlayingPanel = panel;
	}

	@Override
	public void run() {
		try {
			while(gamePlayingPanel.isAlive){
				for (Bullet bullet : gamePlayingPanel.getAllBullets()) {
					bullet.moving();
				}
				Thread.sleep(Config.BULLET_INTERVAL_TIME);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
