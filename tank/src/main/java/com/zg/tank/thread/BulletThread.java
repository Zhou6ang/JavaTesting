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
				
				for (int i = 0; i < gamePlayingPanel.getAllBullets().size(); i++) {
					Bullet bullet = gamePlayingPanel.getAllBullets().get(i);
					bullet.moving();
				}
				
//				Set<Bullet> set = gamePlayingPanel.getBulletsMap().keySet();
//				for(int i = 0;i<set.size();i++){
//					Bullet bullet = set.get(i);
//					bullet.moving();
//				}
				
				Thread.sleep(Config.BULLET_INTERVAL_TIME);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
