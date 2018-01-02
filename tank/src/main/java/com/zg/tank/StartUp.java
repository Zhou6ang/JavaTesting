package com.zg.tank;

import java.awt.Component;

public class StartUp {
	
	public static void main(String[] args) {
		App app = new App();
		app.run();
//		new Thread(()->{
//			while(true){
//				Component comps[] = app.getComponents();
//				for (int i = 0;i< comps.length;i++) {
//					Component comp = comps[i];
//					if(comp instanceof Bullet){
//						if(!((Bullet)comp).moving()){
//							app.remove(comp);
//						}
//					}
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
	}

}
