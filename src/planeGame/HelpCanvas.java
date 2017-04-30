package planeGame;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class HelpCanvas  extends Canvas implements CommandListener{

	//图片资源
	private Image image;
	//退出按钮
	private Command exit;
	//重新开始按钮
	private Command back;
	
	HelpCanvas(){
		super();
		
		try {
			image = Image.createImage("/help.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//初始化按钮
		exit = new Command("退出", Command.OK, 1);
		back = new Command("返回",Command.BACK,1);
		
		//添加按钮
		this.addCommand(exit);
		this.addCommand(back);
	
		//设置按键监听
		this.setCommandListener(this);
}
	public void commandAction(Command c, Displayable d) {
		if(c == exit){//退出程序
			PlaneGameMIDlet.getInstance().notifyDestroyed();
		}
		if(c == back){//重新开始，返回欢迎界面
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new WelcomeCanvas());
			System.gc();
		}
	}
	protected void paint(Graphics g) {//显示画面
		g.drawImage(image, 0, 0, Graphics.LEFT|Graphics.TOP);
	}


}
