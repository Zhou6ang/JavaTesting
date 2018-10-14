package com.zg.config;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.sun.javafx.iio.ImageStorage;

@Component
public class CustomImages {
	
	public static Image playerTankImg;
	public static Image computerTankImg;
	public static Image bulletImg;
	
	@Value(value="classpath:img/tank.png")
	private Resource tank;
	
	@PostConstruct
	public static void setValue() throws Exception{
		
		
//		BufferedImage buferImg = ImageIO.read(tank.getInputStream());
		
//		tankImg = new ImageIcon(buferImg);
		Image buferImg = ImageIO.read(new File("F:\\workspace1\\JavaTesting\\tank\\src\\main\\resources\\img\\tank.png"));
		playerTankImg = buferImg;
		computerTankImg= buferImg;
		bulletImg = buferImg;
	}
	
	
}
