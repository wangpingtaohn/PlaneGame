package planes;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import bullets.BulletFactory;

import utility.SpriteElement;

/*�ɻ���*/
public abstract class Plane extends SpriteElement{

	
	//Ѫ��
	protected int health;
	
	//�ٶ�
	protected int speed = 1;
	
	//�ӵ�����
	protected int bulletType;
	
	//�ӵ��������
	protected int interval;
	
	protected int intervalCount = 0;
	
	//���캯�����޶���
	Plane(String imageName,int h) {
		super(imageName);
		health = h;
	}

	Plane(String imageName,int frameWidth,int frameHeight,int h){
		super(imageName,frameWidth,frameHeight);
		health = h;
	}

	
	//���Ѫ��
	public int getHealth(){
		return health;
	}
	
	//��Ѫ
	public void addHealth(int addH){
		health += addH;
	}


	//�����ٶ�
	public void setSpeed(int s) {
		speed = s;
	}
	
	//������
	public boolean shooted(int d){

		return (health-=d) <= 0;
	}
	
	
	//�����ӵ�����
	public void setBulletType(int bulletType) {
		this.bulletType = bulletType;
	}
}
