package com.zg.tank.bean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zg.config.Config;
import com.zg.config.CustomImages;
import com.zg.tank.bullet.Bullet;
import com.zg.tank.direction.Direction;
import com.zg.tank.factory.BulletFactory;
import com.zg.tank.panel.GamePlayingPanel;

public class PlayerTank extends Tank {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(PlayerTank.class);
	
	public PlayerTank(int posX, int posY, int width, int hight,int speed,String name,Direction direction,GamePlayingPanel gamePlayingPanel) {
		super(posX, posY, width, hight, speed, name, direction, gamePlayingPanel);
	}
	
	@Override
	public void draw() {
		GamePlayingPanel gamePlayingPanel = getGamePlayingPanel();
		logger.debug("drawing a player tank...");
//		gamePlayingPanel.getGraphics().drawImage(CustomImages.playerTankImg, getPosX(), getPosY(), getWidth(), getHight(), gamePlayingPanel);
		
		new Thread(()->{
			int i = 400;
			while(i>0){
				gamePlayingPanel.getGraphics().drawImage(CustomImages.playerTankImg, getPosX(), i, Config.PLAYER_TANK_WIDTH, Config.PLAYER_TANK_HIGHT, gamePlayingPanel);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				i -= 5;
			}
			
		}).start();
		
	}

	@Override
	public void moving(int keyCode) {
//		logger.debug("PlayerTank moving ...");
//		Player player = tankProp.getPlayer();
//		player.setBackground(Color.red);
		if(LEFT_KEY == keyCode){
			setDirection(Direction.LEFT);
//			player.setLocation(player.getLocation().x - 1, player.getLocation().y);
			setPosX(getPosX() - getSpeed());
			draw();
			
		}else if(RIGHT_KEY == keyCode){
			setDirection(Direction.RIGHT);
//			player.setLocation(player.getLocation().x + 1, player.getLocation().y);
			setPosX(getPosX() + getSpeed());
			draw();
		}else if(UP_KEY == keyCode){
			setDirection(Direction.UP);
//			player.setLocation(player.getLocation().x, player.getLocation().y - 1);
			setPosY(getPosY() - getSpeed());
			draw();
			
		}else if(DOWN_KEY == keyCode){
			setDirection(Direction.DOWN);
//			player.setLocation(player.getLocation().x, player.getLocation().y + 1);
			setPosY(getPosY() + getSpeed());
			draw();
		}
		
	}

	@Override
	public void shooting() {
		logger.debug("PlayerTank shooting ...");

		Bullet bullet = BulletFactory.createBullet(getPosX(), getPosY(),getDirection(), getGamePlayingPanel());
		getGamePlayingPanel().getAllBullets().add(bullet);
//		new Thread(() -> {
//			Bullet bullet = new Bullet(this);
//			tankProp.getPlayer().getParent().add(bullet);
//			while (true) {
//				//if the player be reset, then complete immediately.
//				if (tankProp.getPlayer().getParent() == null)
//					return;
//				System.out.println(tankProp.getPlayer().getParent().getComponentCount());
//				for (Component comp : tankProp.getPlayer().getParent().getComponents()) {
//					if (comp instanceof Bullet) {
//						if (!((Bullet) comp).moving()) {
//							tankProp.getPlayer().getParent().remove(comp);
//							tankProp.getPlayer().getParent().repaint();
//							return;
//						}
//					}
//				}
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//
//		}).start();
	}
	
	

}
