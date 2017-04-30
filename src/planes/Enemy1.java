package planes;

import bullets.BulletFactory;

public class Enemy1 extends Enemy{
	

	
	public Enemy1(String imageName,int h){
		super(imageName, h);
		bulletType = BulletFactory.BULLET2;
		interval = 100;
		intervalCount = 0;
		sprite.setPosition(random.nextInt(screenWidth - sprite.getWidth()), 0);
	}
	
	//·ÉÐÐ
	public boolean fly() {
		
		super.shoot();
		
		speedCtrl +=Math.abs(speed);
		speedCtrl = speedCtrl % 2;
		
		if(sprite.getY() < screenHeight ){
			sprite.move(0, speedCtrl);
			return false;
		}
		else{
			return true;
		}
		
		

	}
}
