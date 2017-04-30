package packageForPlane;


import utility.SpriteElement;

public class Package extends SpriteElement{

	//类型
	private int type;
	
	//医药包类型
	public static int BULLET1 = 0;
	public static int BULLET2 = 1;
	public static int BULLET3 = 2;
	
	//医药包类型
	public static int HEALTH = 3;
	public static int LIFE   = 4;
	
	//防护类型
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
	//返回类型
	public int getType() {
		return type;
	}

}
