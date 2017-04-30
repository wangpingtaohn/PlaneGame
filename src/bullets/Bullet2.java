package bullets;

public class Bullet2 extends Bullet{

	protected Bullet2(String imageName, int d, int x, int y) {
		super(imageName, d, x, y);
		sprite.setPosition(x-sprite.getWidth()/2, y - sprite.getHeight());
		// TODO Auto-generated constructor stub
	}

	//未出界就返回false
	public boolean fly() {
		
		if(sprite.getY() < screenHeight ){
			sprite.move(0,2);
			return false;
		}
		else{
			return true ;
		}
		
	}
}
