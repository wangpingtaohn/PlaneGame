package bullets;

import utility.SpriteElement;

public abstract class Bullet extends SpriteElement{

	//扣血量
	private int destorying;
	
	//带动画构造函数
	protected Bullet(String imageName, int frameWidth,int frameHeight,int d,int x,int y) {
		super( imageName, frameWidth, frameHeight);
		destorying = d;
		sprite.setPosition(x, y);
	}
	
	//不带动画构造函数
	protected Bullet( String imageName, int d,int x,int y) {
		super(imageName);
		destorying = d;
		sprite.setPosition(x, y);
	}
	
	public abstract boolean fly();

	//返回扣血量
	public int getDestorying() {
		return destorying;
	}

}
