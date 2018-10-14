package com.zg.tank;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zg.config.Config;
import com.zg.config.CustomImages;
import com.zg.tank.bean.PlayerTank;
import com.zg.tank.direction.Direction;
import com.zg.tank.elements.Player;
import com.zg.tank.factory.TankFactory;
import com.zg.tank.listener.KeyBoardListener;
import com.zg.tank.panel.GamePlayingPanel;
import com.zg.tank.thread.BulletThread;
import com.zg.tank.thread.RepaintThread;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App extends JFrame implements CommandLineRunner
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int screenX = 400;
	private int screenY = 400;
	private GamePlayingPanel gamePanel = new GamePlayingPanel();
	private JPanel curShow;
	
	private JFrame outer;
	
	public static void main( String[] args )
    {
//		new App().run();
		SpringApplication.run(App.class, args);
    }
	
	public void run(){
		
		try {
			CustomImages.setValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setTitle("Tank");
		this.setBounds(0, 0, screenX+36, screenY+57+27);
		this.setAlwaysOnTop(true);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		//menus
		this.setJMenuBar(createMenuBar(gamePanel));
		
//		Container c = this.getContentPane();
//		BoxLayout boxLayout = new BoxLayout(c, BoxLayout.Y_AXIS);
//		c.setLayout(boxLayout);
//		c.add(Box.createVerticalGlue());
		
//		gamePanel.removeAll();
//		gamePanel.setLayout(null);
		gamePanel.setBounds(0, 0, 500, 500);
//		gamePanel.setSize(700, 700);
		gamePanel.add(new JLabel("Welcome to Tank War !!!"));
//		gamePanel.setBackground(Color.green);
		curShow = gamePanel;
		this.add(gamePanel);
//		c.add(Box.createVerticalGlue());
		this.setVisible(true);
		
		
		gamePanel.repaint();
		
//		gamePanel.setAlive(true);
		
//		new Thread(()->{
//			int i = 400;
//			while(i>0){
//				gamePanel.getGraphics().drawImage(CustomImages.playerTankImg, screenX/2, screenY, Config.PLAYER_TANK_WIDTH, Config.PLAYER_TANK_HIGHT, gamePanel);
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				screenY -= 10;
//			}
//			
//		}).start();
//		
//		new Thread(new RepaintThread(gamePanel)).start();
		
	}

	private JPanel createMap() {
		JPanel top = new JPanel();
		top.setBounds(10, 10, screenX, screenY);
		top.setBackground(Color.WHITE);
		top.setLayout(null);
		return top;
	}

	private JMenuBar createMenuBar(GamePlayingPanel gamePlayingPanel) {

		JMenuBar menu = new JMenuBar();
		menu.setLocation(0, 0);
		JMenu lau = new JMenu("Launching");
		menu.add(lau);
		JMenu opt = new JMenu("Option");
		menu.add(opt);
		JMenu set = new JMenu("Setting");
		menu.add(set);
		JMenu pre = new JMenu("Preferences");
		menu.add(pre);
		
		JMenuItem p1= new JMenuItem("Player x 1");
		lau.add(p1);
		p1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				p1.setEnabled(false);
				
				clearBattleField(gamePlayingPanel);
//				JPanel map = createMap();
				
				//create player can make as a thread to execute.
				Player player_1 = new Player(screenX/2, screenY,"player-1",gamePlayingPanel);
				
				
//				new Thread(()->{
//				int i = 400;
//				while(i>0){
//					gamePanel.getGraphics().drawImage(CustomImages.playerTankImg, screenX/2, screenY, Config.PLAYER_TANK_WIDTH, Config.PLAYER_TANK_HIGHT, gamePanel);
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e1) {
//						e1.printStackTrace();
//					}
//					screenY -= 5;
//				}
//				
//			}).start();
				
				
				System.out.println("111111111111");
				
//				curShow = map;
				gamePlayingPanel.addKeyListener(new KeyBoardListener(player_1));
				
				gamePanel.setAlive(true);
//				new Thread(new BulletThread(gamePanel)).start();
				new Thread(new RepaintThread(gamePanel)).start();
				System.out.println("22222");
				
			}


		});
		
		
		JMenuItem p2= new JMenuItem("Player x 2");
		lau.add(p2);
		p2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				p2.setEnabled(false);
				
//				clearBattleField(parent);
				
				JPanel map = createMap();
				Player player_1 = new Player(screenX/2, screenY,"player-1",gamePlayingPanel);
				Player player_2 = new Player(screenX/2, screenY/2, "player-2",gamePlayingPanel);
				player_2.setUpKey(KeyEvent.VK_W);
				player_2.setDownKey(KeyEvent.VK_S);
				player_2.setLeftKey(KeyEvent.VK_A);
				player_2.setRightKey(KeyEvent.VK_D);
				player_2.setShootingKey(KeyEvent.VK_J);
//				player_2.getTank().getTankProp().setWigth(50);
//				player_2.getTank().getTankProp().setHight(50);
//				map.add(player_1);
//				map.add(player_2);
				
				curShow = map;
//				parent.addKeyListener(new KeyBoardListener(map));
//				parent.add(map);
//				parent.repaint();
			}
		});
		
		JMenuItem rep= new JMenuItem("Re-Play");
		lau.add(rep);
		rep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		return menu;
	}
	
	

	private void clearBattleField(GamePlayingPanel gamePlayingPanel) {
		//reset map
		System.out.println("before "+gamePlayingPanel.getComponentCount());
//		curShow.removeAll();
//		parent.remove(curShow);	
		gamePlayingPanel.removeAll();
		for(KeyListener listener : gamePlayingPanel.getKeyListeners()){
			gamePlayingPanel.removeKeyListener(listener);
		}
		System.out.println("after "+gamePlayingPanel.getComponentCount());
		gamePlayingPanel.setLayout(null);
		gamePlayingPanel.repaint();
		//reset map done
	}

	@Override
	public void run(String... args) throws Exception {
		run();
	}
}
