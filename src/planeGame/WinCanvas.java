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
	
	//ͼƬ��Դ
	private Image image;
	//�˳���ť
	private Command exit;
	//���¿�ʼ��ť
	private Command restart;
	
	public WinCanvas() {//��ʼ������Ա����
		super();
		this.setTitle("��ϲ����Ӯ�ˣ�");//���ñ���

		try {
			image = Image.createImage("/win.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//��ʼ����ť
		exit = new Command("�˳�", Command.OK, 1);
		restart = new Command("���¿�ʼ��Ϸ",Command.BACK,1);
		
		//��Ӱ�ť
		this.addCommand(exit);
		this.addCommand(restart);
		
		//���ð�������
		this.setCommandListener(this);
	}
	public void commandAction(Command c, Displayable d) {
		if(c == exit){//�˳�����
			PlaneGameMIDlet.getInstance().notifyDestroyed();
		}
		if(c == restart){//���¿�ʼ�����ػ�ӭ����
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new WelcomeCanvas());
			System.gc();
		}
	}
	
	//��ʾ���棬������
	protected void paint(Graphics g) {
		g.drawImage(image, 0, 0, Graphics.LEFT|Graphics.TOP);
	}


}
