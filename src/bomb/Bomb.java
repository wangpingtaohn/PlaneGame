package bomb;

import utility.SpriteElement;

public class Bomb extends SpriteElement{

	//±¬Õ¨Ö¡¼ÆÊýÆ÷
	private int bombCount = 0;
	
	public Bomb(int x, int y) {
		///super(imageName, frameWidth, frameHeight);
		super("/bomb.png", 30, 30);
		sprite.setFrame(34);
		sprite.setPosition(x-10, y-10);
	}
	
	public boolean bomb(){
		sprite.nextFrame();
		bombCount ++;
		return bombCount > 36;
	}

}
