package bullets;

public class Bullet3 extends Bullet{

	protected Bullet3(String imageName, int d, int x, int y) {
		super(imageName, d, x, y);
		sprite.setPosition(x-sprite.getWidth()/2, y);
		// TODO Auto-generated constructor stub
	}
	
	//未出界就返回false
	public boolean fly() {
		
		if(sprite.getY() > 0 && sprite.getX() > 0){
			sprite.move(-1,-1);
			return false;
		}
		else{
			return true ;
		}
		
	}

}
