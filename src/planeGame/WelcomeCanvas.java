package planeGame;

import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

import level.LevelLoad;

public class WelcomeCanvas extends GameCanvas implements CommandListener,Runnable{


	//背景图片
	private Image imageBg;
	
	private Image imageOption;
	
	//退出和进入按钮
	private Command exit;
	private Command enter;
	
	private int option = 1;
	
	private int interval = 0;
	
	private boolean ifEnter = false;
	
	public WelcomeCanvas() {
		super(true);
		
		//设置标题
		this.setTitle("欢迎来到雷霆战机游戏");

		try {
			imageBg = Image.createImage("/welcome.jpg");
			imageOption = Image.createImage("/option.png");
		} catch (IOException e) {
			e.printStackTrace();		
		}

		//初始化按钮
		exit = new Command("退出", Command.OK, 1);
		enter = new Command("进入",Command.BACK,1);
		
		//添加按钮
		this.addCommand(exit);
		this.addCommand(enter);
		
		//设置按键监听
		this.setCommandListener(this);
		
		(new Thread(this)).start();
		
	}
	
	public void commandAction(Command c, Displayable d) {
		if(c == exit){//退出游戏
			PlaneGameMIDlet.getInstance().notifyDestroyed();
		}
		if(c == enter){//进入游戏界面
			ifEnter = true;
		}
	}
	

	public void run() {
		
		while(!ifEnter){
			if(interval==0 && (this.getKeyStates() & GameCanvas.UP_PRESSED) != 0 && option > 1 ){
				option --;
				interval = 100;
			}
			else if(interval==0 && (this.getKeyStates() & GameCanvas.DOWN_PRESSED) != 0 && option < 3){
				option ++;
				interval = 100;
			}else if(interval==0 && (this.getKeyStates() & GameCanvas.FIRE_PRESSED) != 0 ){
				ifEnter = true;
			}
			
			this.getGraphics().drawImage(imageBg, 0, 0, Graphics.LEFT|Graphics.TOP);
			this.getGraphics().drawImage(imageOption, 122, option * 27 + 110, Graphics.LEFT|Graphics.TOP);
			//刷新屏幕
			this.flushGraphics();
			interval = (--interval)>0 ? interval -1 : 0;
		}
		
		
		switch(option){
			case 1:{
				Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new LevelLoad(1));
				System.gc();
				break;
			}
			case 2:{
				Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new HelpCanvas());
				System.gc();
				break;
			}
			case 3:{
				PlaneGameMIDlet.getInstance().notifyDestroyed();
				break;
			}
		}
		
		
	}




}
