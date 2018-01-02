package com.zg.tank;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import com.zg.tank.elements.Player;
import com.zg.tank.listener.KeyBoardListener;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int screenX = 1000;
	private int screenY = 800;
	private JPanel curShow;
	
	public static void main( String[] args )
    {
		new App().run();
    }
	
	public void run(){
		this.setTitle("Tank");
		this.setBounds(0, 0, screenX+36, screenY+57+27);
		this.setAlwaysOnTop(true);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		//menus
		this.setJMenuBar(createMenuBar(this));
		
		JPanel wel = new JPanel();
		wel.add(new JLabel("welcome to Tank War !!!"));
		wel.setBounds(0, 0, 500, 500);
		curShow = wel;
		this.add(wel);
		this.setVisible(true);
	}

	private JPanel createMap() {
		JPanel top = new JPanel();
		top.setBounds(10, 10, screenX, screenY);
		top.setBackground(Color.WHITE);
		top.setLayout(null);
		return top;
	}

	private JMenuBar createMenuBar(JFrame parent) {

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
				
				clearBattleField(parent);
				JPanel map = createMap();
				
				Player player_1 = new Player(screenX/2, screenY,"player-1");
				map.add(player_1);
				
				curShow = map;
				parent.addKeyListener(new KeyBoardListener(map));
				parent.add(map);
				parent.repaint();
				
			}


		});
		
		
		JMenuItem p2= new JMenuItem("Player x 2");
		lau.add(p2);
		p2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				p2.setEnabled(false);
				
				clearBattleField(parent);
				
				JPanel map = createMap();
				Player player_1 = new Player(screenX/2, screenY,"player-1");
				Player player_2 = new Player(screenX/2, screenY/2, "player-2");
				player_2.setUpKey(KeyEvent.VK_W);
				player_2.setDownKey(KeyEvent.VK_S);
				player_2.setLeftKey(KeyEvent.VK_A);
				player_2.setRightKey(KeyEvent.VK_D);
				player_2.setShootingKey(KeyEvent.VK_J);
				player_2.getTank().getTankProp().setWigth(50);
				player_2.getTank().getTankProp().setHight(50);
				map.add(player_1);
				map.add(player_2);
				
				curShow = map;
				parent.addKeyListener(new KeyBoardListener(map));
				parent.add(map);
				parent.repaint();
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
	
	private void clearBattleField(JFrame parent) {
		//reset map
		System.out.println("before "+parent.getComponentCount());
		curShow.removeAll();
		parent.remove(curShow);				
		for(KeyListener listener : parent.getKeyListeners()){
			parent.removeKeyListener(listener);
		}
		System.out.println("after "+parent.getComponentCount());
		//reset map done
	}
}
