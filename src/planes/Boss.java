package planes;

import java.util.Vector;

import bullets.BulletFactory;

public class Boss extends Enemy{

	//横向的速度
	private int v = 2;
	
//	public Boss(String imageName, int frameWidth, int frameHeight, int h) {
//		super(imageName, frameWidth, frameHeight, h);
//		
//	}
	
	public Boss(String imageName, int h){
		super(imageName, h);
		bulletType = BulletFactory.BULLET5;
		interval = 48;
		// TODO Auto-generated constructor stub
	}

	public boolean fly() {
		super.shoot();
		
		
		if(sprite.getX() < 0){
			v = 2;
		}
		else if( sprite.getX() > screenWidth - sprite.getWidth()){
			v = -2;
		}
		
		if(sprite.getY() < screenHeight){
			sprite.move(v, 0);
			
			
			return false;
		}
		else{
			return true;
		}
		
	}


}
