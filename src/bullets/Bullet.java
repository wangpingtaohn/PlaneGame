package bullets;

import utility.SpriteElement;

public abstract class Bullet extends SpriteElement{

	//��Ѫ��
	private int destorying;
	
	//���������캯��
	protected Bullet(String imageName, int frameWidth,int frameHeight,int d,int x,int y) {
		super( imageName, frameWidth, frameHeight);
		destorying = d;
		sprite.setPosition(x, y);
	}
	
	//�����������캯��
	protected Bullet( String imageName, int d,int x,int y) {
		super(imageName);
		destorying = d;
		sprite.setPosition(x, y);
	}
	
	public abstract boolean fly();

	//���ؿ�Ѫ��
	public int getDestorying() {
		return destorying;
	}

}
