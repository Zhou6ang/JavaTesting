//package com.zg.tank.bean;
//
//import java.awt.Color;
//import java.awt.Component;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.zg.tank.bean.Tank.Direction;
//import com.zg.tank.elements.Bullet;
//import com.zg.tank.elements.Player;
//
//public class PlayerTank1 extends Tank{
//	
////	private final Logger logger = LogManager.getLogger(PlayerTank.class);
//	
//	public PlayerTank1(Tank entry) {
//		super(entry);
//	}
//
//	@Override
//	public void moving(int keyCode) {
////		logger.debug("PlayerTank moving ...");
//		Player player = tankProp.getPlayer();
//		player.setBackground(Color.red);
//		if(player.getLeftKey() == keyCode){
//			tankProp.setDirection(Direction.LEFT);
//			player.setLocation(player.getLocation().x - 1, player.getLocation().y);
//		}else if(player.getRightKey() == keyCode){
//			tankProp.setDirection(Direction.RIGHT);
//			player.setLocation(player.getLocation().x + 1, player.getLocation().y);
//		}else if(player.getUpKey() == keyCode){
//			tankProp.setDirection(Direction.UP);
//			player.setLocation(player.getLocation().x, player.getLocation().y - 1);
//		}else if(player.getDownKey() == keyCode){
//			tankProp.setDirection(Direction.DOWN);
//			player.setLocation(player.getLocation().x, player.getLocation().y + 1);
//		}
//		
//	}
//
//	@Override
//	public void shooting() {
////		logger.debug(this.getTankProp().getName() + " PlayerTank shooting ...");
//
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
//	}
//
//
//	
//}
