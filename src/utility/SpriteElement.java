package utility;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public abstract class SpriteElement {

	//�������
	protected Sprite sprite;
	
	//��ͼ����
	private static Graphics graphics;
	
	//�����ͼƬ��Դ
	private static Image image;
	
	//��Ļ����
	protected static int screenHeight,screenWidth;
	
	//�������Ĺ��캯��
	protected SpriteElement(String imageName,int frameWidth,int frameHeight){

		
		try {
			image = Image.createImage(imageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sprite = new Sprite(image, frameWidth, frameHeight);
	}
	
	//���������Ĺ��캯��
	protected SpriteElement(String imageName){
		try {
			image = Image.createImage(imageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sprite = new Sprite(image);
	}
	
	//��ͼ�ӿڷ���
	public void paint(){
		sprite.paint(graphics);
	}
	
	//��þ������
	public Sprite getSprite(){
		return sprite;
	}

	//���û�ͼ����
	public static void setGraphics(Graphics graphics) {
		SpriteElement.graphics = graphics;
	}
	
	//������Ļ����
	public static void setScreen(int sw,int sh){
		screenWidth = sw;
		screenHeight = sh;
	}

}
