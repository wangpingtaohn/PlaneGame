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

	//ͼƬ��Դ
	private Image image;
	//�˳���ť
	private Command exit;
	//���¿�ʼ��ť
	private Command back;
	
	HelpCanvas(){
		super();
		
		try {
			image = Image.createImage("/help.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//��ʼ����ť
		exit = new Command("�˳�", Command.OK, 1);
		back = new Command("����",Command.BACK,1);
		
		//��Ӱ�ť
		this.addCommand(exit);
		this.addCommand(back);
	
		//���ð�������
		this.setCommandListener(this);
}
	public void commandAction(Command c, Displayable d) {
		if(c == exit){//�˳�����
			PlaneGameMIDlet.getInstance().notifyDestroyed();
		}
		if(c == back){//���¿�ʼ�����ػ�ӭ����
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new WelcomeCanvas());
			System.gc();
		}
	}
	protected void paint(Graphics g) {//��ʾ����
		g.drawImage(image, 0, 0, Graphics.LEFT|Graphics.TOP);
	}


}
