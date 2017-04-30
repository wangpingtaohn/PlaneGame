package bullets;

public class Bullet1 extends Bullet{



	public Bullet1(String imageName, int d,int x,int y) {
		super(imageName,d, x, y);
		sprite.setPosition(x-sprite.getWidth()/2, y);
		// TODO Auto-generated constructor stub
	}
	
	//未出界就返回false
	public boolean fly() {
		
		if(sprite.getY() > 0 ){
			sprite.move(0,-1);
			return false;
		}
		else{
			return true ;
		}
		
	}

}
