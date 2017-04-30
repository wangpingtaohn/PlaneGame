package Terrain;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import utility.SpriteElement;

public class Terrain{

	//�������
	protected Sprite sprite1,sprite2;
	
	//��ͼ����
	private static Graphics graphics;
	
	//�����ͼƬ��Դ
	private static Image image;
	
	//��Ļ����
	protected static int screenHeight,screenWidth;
	
	public Terrain(String imageName){
		
		try {
			image = Image.createImage(imageName);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sprite1 = new Sprite(image);
		sprite2 = new Sprite(image);
		
		sprite1.setPosition(0, -sprite1.getHeight());
		sprite2.setPosition(0, 0);
	}
	
	public void move(){

		if(sprite1.getY() > screenHeight){
			sprite1.setPosition(0, sprite2.getY()-sprite1.getHeight());
		}
		
		if(sprite2.getY() > screenHeight){
			sprite2.setPosition(0, sprite1.getY()-sprite2.getHeight());
		}
		sprite1.move(0, 1);
		sprite2.move(0, 1);


	}
	
	public void paint(){
		sprite1.paint(graphics);
		sprite2.paint(graphics);
	}
	
	
	//���û�ͼ����
	public static void setGraphics(Graphics graphics) {
		Terrain.graphics = graphics;
	}
	
	//������Ļ����
	public static void setScreen(int sw,int sh){
		screenWidth = sw;
		screenHeight = sh;
	}
}
