package planes;

import java.util.Random;
import java.util.Vector;

import bullets.BulletFactory;

public abstract class Enemy extends Plane{

	//����л��ӵ�������
	protected static Vector bullets;
	
	protected static Random random = new Random();
	
	//�������������Ʒɻ��ٶ�
	protected int speedCtrl = 0;
	
	Enemy(String imageName, int frameWidth, int frameHeight, int h) {
		super(imageName, frameWidth, frameHeight, h);
		// TODO Auto-generated constructor stub
	}
	
	Enemy(String imageName, int h) {
		super(imageName, h);
		// TODO Auto-generated constructor stub
	}

	//����
	public boolean fly() {
		if(sprite.getY() < screenHeight ){
			sprite.move(0, Math.abs(speed));
			return false;
		}
		else{
			return true;
		}

	}
	
	//���
	protected void shoot(){
		//����û�����ӵ��ķ�����С���
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
