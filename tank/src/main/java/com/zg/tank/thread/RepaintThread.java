package com.zg.tank.thread;

import com.zg.config.Config;
import com.zg.tank.panel.GamePlayingPanel;

public class RepaintThread implements Runnable{
	private GamePlayingPanel gamePlayingPanel;
	public RepaintThread(GamePlayingPanel panel) {
		gamePlayingPanel = panel;
	}

	@Override
	public void run() {
		try {
			while(gamePlayingPanel.isAlive()){
				
				gamePlayingPanel.repaint();
				Thread.sleep(Config.REPAINT_INTERVAL_TIME);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
