package utility;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public abstract class SpriteElement {

	//精灵对象
	protected Sprite sprite;
	
	//画图对象
	private static Graphics graphics;
	
	//球对象图片资源
	private static Image image;
	
	//屏幕长宽
	protected static int screenHeight,screenWidth;
	
	//带动画的构造函数
	protected SpriteElement(String imageName,int frameWidth,int frameHeight){

		
		try {
			image = Image.createImage(imageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sprite = new Sprite(image, frameWidth, frameHeight);
	}
	
	//不带动画的构造函数
	protected SpriteElement(String imageName){
		try {
			image = Image.createImage(imageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sprite = new Sprite(image);
	}
	
	//画图接口方法
	public void paint(){
		sprite.paint(graphics);
	}
	
	//获得精灵对象
	public Sprite getSprite(){
		return sprite;
	}

	//设置画图对象
	public static void setGraphics(Graphics graphics) {
		SpriteElement.graphics = graphics;
	}
	
	//设置屏幕长宽
	public static void setScreen(int sw,int sh){
		screenWidth = sw;
		screenHeight = sh;
	}

}
