package planes;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import bullets.BulletFactory;

import utility.SpriteElement;

/*飞机类*/
public abstract class Plane extends SpriteElement{

	
	//血量
	protected int health;
	
	//速度
	protected int speed = 1;
	
	//子弹类型
	protected int bulletType;
	
	//子弹连发间隔
	protected int interval;
	
	protected int intervalCount = 0;
	
	//构造函数，无动画
	Plane(String imageName,int h) {
		super(imageName);
		health = h;
	}

	Plane(String imageName,int frameWidth,int frameHeight,int h){
		super(imageName,frameWidth,frameHeight);
		health = h;
	}

	
	//获得血量
	public int getHealth(){
		return health;
	}
	
	//加血
	public void addHealth(int addH){
		health += addH;
	}


	//设置速度
	public void setSpeed(int s) {
		speed = s;
	}
	
	//被击中
	public boolean shooted(int d){

		return (health-=d) <= 0;
	}
	
	
	//设置子弹类型
	public void setBulletType(int bulletType) {
		this.bulletType = bulletType;
	}
}
