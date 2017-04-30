package planeGame;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class WinCanvas extends Canvas implements CommandListener{
	
	//图片资源
	private Image image;
	//退出按钮
	private Command exit;
	//重新开始按钮
	private Command restart;
	
	public WinCanvas() {//初始化各成员对象
		super();
		this.setTitle("恭喜！你赢了！");//设置标题

		try {
			image = Image.createImage("/win.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//初始化按钮
		exit = new Command("退出", Command.OK, 1);
		restart = new Command("重新开始游戏",Command.BACK,1);
		
		//添加按钮
		this.addCommand(exit);
		this.addCommand(restart);
		
		//设置按键监听
		this.setCommandListener(this);
	}
	public void commandAction(Command c, Displayable d) {
		if(c == exit){//退出程序
			PlaneGameMIDlet.getInstance().notifyDestroyed();
		}
		if(c == restart){//重新开始，返回欢迎界面
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new WelcomeCanvas());
			System.gc();
		}
	}
	
	//显示画面，画背景
	protected void paint(Graphics g) {
		g.drawImage(image, 0, 0, Graphics.LEFT|Graphics.TOP);
	}


}
