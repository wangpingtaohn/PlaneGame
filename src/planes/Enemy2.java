package planes;

import bullets.BulletFactory;

public class Enemy2 extends Enemy{

	//横向的速度
	private int v = 1;
	
	public Enemy2(String imageName,int h){
		super(imageName, h);
		bulletType = BulletFactory.BULLET5;
		interval = 100;
		intervalCount = 0;
		sprite.setPosition(random.nextInt(screenWidth - sprite.getWidth()), 0);
	}
	
	//飞行
	public boolean fly(){
		
		super.shoot();
		speedCtrl +=Math.abs(speed);
		speedCtrl = speedCtrl % 2;
		
		if(sprite.getX() < 0){
			v = 1;
		}
		else if( sprite.getX() > screenWidth - sprite.getWidth()){
			v = -1;
		}
		
		if(sprite.getY() < screenHeight){
			sprite.move(v, speedCtrl);
			
			
			return false;
		}
		else{
			return true;
		}
		
		

	}
}
