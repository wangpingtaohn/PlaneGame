package bullets;

import java.io.IOException;
import java.util.Vector;


public class BulletFactory {

	
	//子弹类型
	public static int BULLET1 = 1;
	public static int BULLET2 = 2;
	public static int BULLET3 = 3;
	public static int BULLET4 = 4;
	public static int BULLET5 = 5;
	public static int BULLET6 = 6;
	

	
	//添加子弹
	public static void AddBullet(int type,int x,int y,Vector bullets){
		switch(type){
				case 1: {
					bullets.addElement(new Bullet1("/bullet1.png", 10, x,y));
					break;
				}
				case 2: {
					bullets.addElement(new Bullet2("/bullet2.png", 15, x,y));
					break;
				}
				case 3: {
					bullets.addElement(new Bullet1("/bullet3.png",10,x-6,y-2));
					bullets.addElement(new Bullet1("/bullet3.png",10,x+6,y-2));
					break;
				}
				case 4: {
					bullets.addElement(new Bullet1("/bullet4.png",30,x,y));
					break;
				}
				case 5: {
					bullets.addElement(new Bullet2("/bullet3.png",30,x-14,y+10));
					bullets.addElement(new Bullet2("/bullet3.png",30,x+14,y+10));
					break;
				}
				case 6: {
					bullets.addElement(new Bullet1("/bullet1.png",10,x,y));
					bullets.addElement(new Bullet3("/bullet1.png",10,x+8,y+2));
					bullets.addElement(new Bullet4("/bullet1.png",10,x-8,y+2));
				}
		}
	}
	
}
