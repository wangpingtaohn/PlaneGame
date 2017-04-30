package planes;

import java.util.Vector;

import javax.microedition.lcdui.game.GameCanvas;

import bullets.BulletFactory;

public class MyPlane extends Plane{

	//是否在转弯中
	private boolean ifTurn;
	
	//生命
	private int life = 3;
	
	//复活等待的计数器
	private int revivalCount = 0;
	private int protectCount = 120;
	
	private int lastTime = 0;
	
	public MyPlane(String imageName, int frameWidth, int frameHeight, int h){
		super(imageName, frameWidth, frameHeight, h);
		health = h;
		bulletType = BulletFactory.BULLET1;
		interval = 70;
		intervalCount = 0;
		sprite.setPosition(screenWidth/2 - sprite.getWidth()/2 , screenHeight - sprite.getHeight());
		sprite.setFrame(1);
	}
	
	public void fly(int input,Vector bullets) {
		
		if( revivalCount < 0){//没有在复活间隔期
			ifTurn = false;
			
			if((input & GameCanvas.LEFT_PRESSED)!= 0){
				moveX(-1);
				sprite.setFrame(0);
				ifTurn = !ifTurn;
			}
			if((input & GameCanvas.RIGHT_PRESSED)!= 0){
				moveX(1);
				sprite.setFrame(2);
				ifTurn = !ifTurn;
			}
			
			if((input & GameCanvas.UP_PRESSED)!= 0){
				moveY(-1);
			}
			if((input & GameCanvas.DOWN_PRESSED)!= 0){
				moveY(1);
			}
			
			if(!ifTurn){
				sprite.setFrame(1);
			}
			
			//控制没两颗子弹的发射最小间距
			if( (input & GameCanvas.FIRE_PRESSED)!= 0 && intervalCount < 0){
				BulletFactory.AddBullet(bulletType,sprite.getX()+sprite.getWidth()/2,sprite.getY(),bullets);
				intervalCount = interval;
			}
			else{
				intervalCount--;
			}
			
			if(protectCount-- > 0){
				sprite.setFrame(sprite.getFrame()%3+ protectCount%3*3);
			}
			
		}
		else {
				
			if(revivalCount-- == 0){//飞机出现
				sprite.setPosition(screenWidth/2 - sprite.getWidth()/2 , screenHeight - sprite.getHeight());
			}
			else {
				if( revivalCount % 5 > 2 && revivalCount < 80){
					sprite.setPosition(screenWidth/2 - sprite.getWidth()/2 , screenHeight - sprite.getHeight());
				}
				else{
					sprite.setPosition(-100 , -100);
				}
			}
				
			
		}
		
		
	}
	
	//左右移
	public void moveX(int x){
		if( x*speed > 0){
			if(screenWidth >= sprite.getX()+sprite.getWidth()){
				if(screenWidth > sprite.getX()+sprite.getWidth()+ x*speed){
					sprite.move(x*speed, 0);
				}
				else{
					sprite.move(screenWidth - sprite.getX()- sprite.getWidth(), 0);
				}
			}
		}
		else if( x*speed < 0){
			if(0 < sprite.getX()){
				if(0 <= sprite.getX()+ x*speed){
					sprite.move(x*speed, 0);
				}
				else{
					sprite.move(sprite.getX(), 0);
				}
			}
		}
	}
	
	//上下移
	public void moveY(int y){
		if( y*speed > 0){
			if(screenHeight >= sprite.getY()+sprite.getHeight()){
				if(screenHeight > sprite.getY()+sprite.getHeight()+ y*speed){
					sprite.move(0, y*speed);
				}
				else{
					sprite.move(0,screenHeight - sprite.getY()- sprite.getHeight());
				}
			}
		}
		else if( y*speed < 0){
			if(0 < sprite.getY()){
				if(0 <= sprite.getY()+ y*speed){
					sprite.move(0,y*speed);
				}
				else{
					sprite.move(0,sprite.getY());
				}
			}
		}
	}

	//复活
	public boolean revival(int h){
		if( --life > 0 ){
			health = h;
			bulletType = BulletFactory.BULLET1;
			
			sprite.setPosition(-100, -100);
			revivalCount = 120;
			protectCount = 120;
			lastTime = 0;
			return false;
		}
		else{
			
			return true;
		}
	}
	
	public void processPG(int type){
		switch(type){
			case 0: {
				bulletType =  BulletFactory.BULLET3; 
				break;
			}
			case 1: {
				bulletType =  BulletFactory.BULLET4;
				break;
			}
			case 2: {
				bulletType = BulletFactory.BULLET6;
				break;
			}
			case 3: {
				health = health+50 <=150? health+50: 150;
				break;
			}
			case 4: {
				if(life <5) life += 1;
				break;
			}
			case 5: {
				protectCount = 1000;
				break;
			}
		}
	}

	public int getLife() {
		return life;
	}
	
	public boolean isProtected(){
		return protectCount > 0;
	}

}
