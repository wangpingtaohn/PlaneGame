package packageForPlane;


import utility.SpriteElement;

public class Package extends SpriteElement{

	//����
	private int type;
	
	//ҽҩ������
	public static int BULLET1 = 0;
	public static int BULLET2 = 1;
	public static int BULLET3 = 2;
	
	//ҽҩ������
	public static int HEALTH = 3;
	public static int LIFE   = 4;
	
	//��������
	public static int PROTECT = 5;
	
	public Package(int t,int x,int y) {
		super("/package"+t+".png");
		type = t;
		sprite.setPosition(x, y);
		// TODO Auto-generated constructor stub
	}

	public boolean move(){
		if(sprite.getY()>screenHeight){
			return true;
		}
		else{
			sprite.move(0, 1);
			return false;
		}
	}
	//��������
	public int getType() {
		return type;
	}

}
