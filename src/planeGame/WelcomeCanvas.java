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


	//����ͼƬ
	private Image imageBg;
	
	private Image imageOption;
	
	//�˳��ͽ��밴ť
	private Command exit;
	private Command enter;
	
	private int option = 1;
	
	private int interval = 0;
	
	private boolean ifEnter = false;
	
	public WelcomeCanvas() {
		super(true);
		
		//���ñ���
		this.setTitle("��ӭ��������ս����Ϸ");

		try {
			imageBg = Image.createImage("/welcome.jpg");
			imageOption = Image.createImage("/option.png");
		} catch (IOException e) {
			e.printStackTrace();		
		}

		//��ʼ����ť
		exit = new Command("�˳�", Command.OK, 1);
		enter = new Command("����",Command.BACK,1);
		
		//��Ӱ�ť
		this.addCommand(exit);
		this.addCommand(enter);
		
		//���ð�������
		this.setCommandListener(this);
		
		(new Thread(this)).start();
		
	}
	
	public void commandAction(Command c, Displayable d) {
		if(c == exit){//�˳���Ϸ
			PlaneGameMIDlet.getInstance().notifyDestroyed();
		}
		if(c == enter){//������Ϸ����
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
			//ˢ����Ļ
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
