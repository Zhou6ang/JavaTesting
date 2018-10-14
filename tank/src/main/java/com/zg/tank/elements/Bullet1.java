//package com.zg.tank.elements;
//
//import java.awt.Color;
//
//import javax.swing.JPanel;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.zg.tank.bean.Tank1;
//import com.zg.tank.bean.Tank.Direction;
//import com.zg.tank.bullet.Bullet;
//
//public class Bullet1  extends JPanel {
//	/**
//	 * 
//	 */
//	private static final Logger logger = LogManager.getLogger(Bullet.class);
//	private static final long serialVersionUID = -4889390935305410077L;
//	private Bullet entry;
//	private int x;
//	private int y;
//	private int offset = 20;
//	private Direction direction;
//	private int scopeX;
//	private int scopeY;
//
//	public Bullet1(Tank1 tank) {
//		logger.debug("create Bullet ...");
//		setBackground(Color.BLACK);
//		setSize(5, 5);
//		x = tank.getTankProp().getPlayer().getLocation().x;
//		y = tank.getTankProp().getPlayer().getLocation().y;
//		direction = tank.getTankProp().getDirection();
//		scopeX = tank.getTankProp().getPlayer().getParent().getWidth();
//		scopeY = tank.getTankProp().getPlayer().getParent().getHeight();
//		
//	}
//	public Bullet getEntry() {
//		return entry;
//	}
//
//	public void setEntry(Bullet entry) {
//		this.entry = entry;
//	}
//	
//	public boolean moving(){
//		if(direction.equals(Direction.UP)){
//			y -= offset;
//			if(y<0){
//				return false;
//			}
//			setLocation(x, y);
//			logger.debug("Bullet [UP] moving...");
//		}else if(direction.equals(Direction.DOWN)){
//			y += offset;
//			if(y > scopeY){
//				return false;
//			}
//			setLocation(x, y);
//			logger.debug("Bullet [DOWN] moving...");
//		}else if(direction.equals(Direction.LEFT)){
//			x -= offset;
//			if(x < 0){
//				return false;
//			}
//			setLocation(x, y);
//			logger.debug("Bullet [LEFT] moving...");
//		}else if(direction.equals(Direction.RIGHT)){
//			
//			x += offset;
//			if(x > scopeX){
//				return false;
//			}
//			setLocation(x, y);
//			logger.debug("Bullet [RIGHT] moving...");
//		}
//		return true;
//	}
//	
//}
