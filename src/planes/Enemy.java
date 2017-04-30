package planes;

import java.util.Random;
import java.util.Vector;

import bullets.BulletFactory;

public abstract class Enemy extends Plane{

	//保存敌机子弹的向量
	protected static Vector bullets;
	
	protected static Random random = new Random();
	
	//辅助变量，控制飞机速度
	protected int speedCtrl = 0;
	
	Enemy(String imageName, int frameWidth, int frameHeight, int h) {
		super(imageName, frameWidth, frameHeight, h);
		// TODO Auto-generated constructor stub
	}
	
	Enemy(String imageName, int h) {
		super(imageName, h);
		// TODO Auto-generated constructor stub
	}

	//飞行
	public boolean fly() {
		if(sprite.getY() < screenHeight ){
			sprite.move(0, Math.abs(speed));
			return false;
		}
		else{
			return true;
		}

	}
	
	//射击
	protected void shoot(){
		//控制没两颗子弹的发射最小间距
		if( intervalCount < 0){
			BulletFactory.AddBullet(bulletType,sprite.getX()+sprite.getWidth()/2,sprite.getY()+sprite.getHeight(),bullets);
			intervalCount = interval;
		}
		else{
			intervalCount--;
		}
	}

	public static void setBullets(Vector bullets) {
		Enemy.bullets = bullets;
	}

}
