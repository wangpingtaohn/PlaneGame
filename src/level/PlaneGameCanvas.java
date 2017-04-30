package level;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

import packageForPlane.Package;
import planeGame.LostCanvas;
import planeGame.PlaneGameMIDlet;
import planeGame.WelcomeCanvas;
import planes.Boss;
import planes.Enemy;
import planes.EnemyFactory;
import planes.MyPlane;
import utility.SpriteElement;
import Terrain.Terrain;
import bomb.Bomb;
import bullets.Bullet;

public abstract class PlaneGameCanvas extends GameCanvas implements Runnable,CommandListener{

	//��ͼ����
	private Graphics graphics; 
	
	private int screenHeight;//��Ļ�߶�
	private int screenWidth;//��Ļ���
	
	//��ǰ�����߳�
	private Thread myThread;
	
	private long startTime;//�߳�һ�����еĿ�ʼʱ��
	private long endTime;//�߳�һ�����еĽ���
	private int frameSpeed;//֡��
	
	
	private Command pause;//��ͣ����
	private Command back;//���ذ���
	private Command start;//��ʼ����
	private Command resume;//��������
	
	private boolean RUN = true;//�Ƿ�����
	private boolean START = false;//�Ƿ�ʼ
	private boolean BACK = false;//�Ƿ񷵻�

	
	protected Terrain terrain;//��ͼ
	
	private MyPlane myPlane;//�ҷ��ɻ�
	
	protected Vector enemys = new Vector();//����
	
	private Vector eBullets = new Vector();//�з��ӵ�
	private Vector myBullets = new Vector();//�з��ӵ�
	
	
	protected Boss boss;//�ϰ�
	protected boolean ifBoss = false;//�Ƿ�boss����
	
	protected Vector packages = new Vector();//������

	
	private Vector bombs = new Vector();
	
	
	Random random = new Random();

	
	private Image gui;
	
	protected int enemyCount = 0;
	
	protected int end = 0;
	

	public PlaneGameCanvas() {
		super(true);//�̳и��࣬������Ϸ��
		
		graphics = this.getGraphics();
		
		//�����Ļ����
		screenHeight = this.getHeight();
		screenWidth = this.getWidth();

		
		//��ʼ����ť��		
		pause = new Command("��ͣ",Command.BACK,1);
		back = new Command("����",Command.OK,1);
		start = new Command("��ʼ",Command.BACK,1);
		resume = new Command("����",Command.BACK,1);
		
		//��ӿ�ʼ��ť
		this.addCommand(start);
		
		//���û�����������
		this.setCommandListener(this);
		
		//��ʼ������Ҫ�õ������Ļ����
		SpriteElement.setGraphics(graphics);
		SpriteElement.setScreen(screenWidth, screenHeight);
		Terrain.setGraphics(graphics);
		Terrain.setScreen(screenWidth, screenHeight);
		Enemy.setBullets(eBullets);
		
		//terrain = new Terrain("/bckgrd2.jpg");
		
		myPlane = new MyPlane("/myPlane.png",40,40,100);
		
		
		try {
			gui = Image.createImage("/GUI.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//��ʼ�Ϳ�����ǰ�߳�
		myThread = new Thread(this);
		myThread.start();
		
	}
	
	//��������
	public void commandAction(Command c, Displayable arg1) {
		if(c == start){//��ʼ��ʱ�򣬸������λ�ã�ȷ����ĳ�ʼ���з���

			START = true;//���ÿ�ʼΪ��
			
			//���¹���������ť��ɾ������ʼ����ť�����ϡ���ͣ����ť
			this.removeCommand(start);
			this.addCommand(pause);
			
		}else if(c == pause){
			
			RUN = false;//�Ƿ����
			
			//���¹���������ť��ɾ������ͣ����ť�����ϡ��������������ء���ť
			this.removeCommand(pause);
			this.addCommand(resume);
			this.addCommand(back);
			
		}else if(c == resume){
			RUN = true;//�Ƿ����
			
			//���¹���������ť��ɾ�����������������ء���ť�����ϡ���ͣ����ť
			this.removeCommand(resume);
			this.removeCommand(back);
			this.addCommand(pause);
			
		}else if(c == back){//���÷��ر�־��ֹ֪ͨͣ�̣߳���������ӭҳ��
			BACK = true;
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new WelcomeCanvas());
		}
		
		
	}

	//��дRunnable�е�Run�����������߳�
	public void run()
	{
		//��õ�ǰ���е��߳�
		Thread currentThread = Thread.currentThread();
		frameSpeed = 10;
		while(end !=3 && currentThread == myThread){//��ǰ�߳�������Լ����̣߳�������
			startTime = System.currentTimeMillis();//��¼���ֿ�ʼʱ��
			

					
			if(START){
				if(RUN){
					make();
					processMovement();
					processColliding();
//					//���ǰһ֡
//					graphics.setColor(255, 255, 255);
//					graphics.fillRect(0, 0, screenWidth, screenHeight);
			        paint();
				}
			}
			else{
				terrain.paint();
				myPlane.paint();
				
			}
			
			
			
			//ˢ����Ļ
			this.flushGraphics();
			
			endTime = System.currentTimeMillis();//����ִ�н���ʱ��
			if((endTime - startTime) < frameSpeed){//�������ִ�������ٶ����ӣ��ȴ�
				try {
					Thread.sleep(frameSpeed - (endTime - startTime));
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			
		}
		
		
	}





	protected abstract void make();
	
	//����֡
	private void processMovement() {
		
		int i;
		terrain.move();
		
		if(ifBoss){
			boss.fly();
		}
		
		myPlane.fly(this.getKeyStates(),myBullets);
		
		for(i = 0; i< enemys.size(); i++){
			if(((Enemy)enemys.elementAt(i)).fly()){
				enemys.removeElementAt(i);
				i--;
			}
		}
		
		for(i = 0; i< eBullets.size(); i++){
			if(((Bullet)eBullets.elementAt(i)).fly()){
				eBullets.removeElementAt(i);
				i--;
			}
		}
		
		for(i = 0; i< myBullets.size(); i++){
			if(((Bullet)myBullets.elementAt(i)).fly()){
				myBullets.removeElementAt(i);
				i--;
			}
		}
		
		for(i = 0; i< packages.size(); i++){
			if(((Package)packages.elementAt(i)).move()){
				packages.removeElementAt(i);
				i--;
			}
		}

		
		for(i = 0; i< bombs.size(); i++){
			if(((Bomb)bombs.elementAt(i)).bomb()){
				bombs.removeElementAt(i);
				i--;
			}
		}
	}

	//������ײ
	private void processColliding() {
		int i,j;
		Bullet bullet;
		Enemy enemy;
		Package packageForPlane;
		
		//�ҷ��ӵ��͵з�����
		for(i = 0; i < myBullets.size();i++){
			
			bullet = (Bullet)myBullets.elementAt(i);
			
			if( ifBoss && bullet.getSprite().collidesWith(boss.getSprite(), true)){
				
				if(boss.shooted(bullet.getDestorying())){
					bombs.addElement(new Bomb(boss.getSprite().getX(),boss.getSprite().getY()));
					bombs.addElement(new Bomb(boss.getSprite().getX()+ boss.getSprite().getWidth(),boss.getSprite().getY()));
					bombs.addElement(new Bomb(boss.getSprite().getX(),boss.getSprite().getY()+ boss.getSprite().getHeight()));
					bombs.addElement(new Bomb(boss.getSprite().getX()+boss.getSprite().getWidth(),boss.getSprite().getY()+boss.getSprite().getHeight()));
					bombs.addElement(new Bomb(boss.getSprite().getX()+boss.getSprite().getWidth()/2,boss.getSprite().getY()+boss.getSprite().getHeight()/2));
				
					ifBoss = false;
					end = 1;
				}
	
				
				myBullets.removeElementAt(i);
				i--;
				
				continue;
			}
			
			
			
			
			
			for( j = 0; j< enemys.size(); j++){
				
				enemy = (Enemy)enemys.elementAt(j);
				
				
				if(bullet.getSprite().collidesWith(enemy.getSprite(), true)){
					myBullets.removeElementAt(i);
					i--;
					if(enemy.shooted(bullet.getDestorying())){
						bombs.addElement(new Bomb(enemy.getSprite().getX()+enemy.getSprite().getWidth()/2,
								enemy.getSprite().getY()+enemy.getSprite().getHeight()/2));
						enemys.removeElementAt(j);
						j--;
					}
					
				}
			}
		}
		
		//�з��ӵ����ҷ��ɻ�
		for(i = 0; i< eBullets.size();i++){
			bullet = (Bullet)eBullets.elementAt(i);
			if(bullet.getSprite().collidesWith(myPlane.getSprite(), true)){
				
				eBullets.removeElementAt(i);
				i--;
				
				//����ɻ��Ǳ���
				if( (!myPlane.isProtected()) && myPlane.shooted(bullet.getDestorying())){
					
					bombs.addElement(new Bomb(myPlane.getSprite().getX() + myPlane.getSprite().getWidth()/2,
							myPlane.getSprite().getY()+myPlane.getSprite().getHeight()/2));
					
					if(myPlane.revival(100)){
						end = 2;
				
					}
				}
			}
		}
		
		//�л����һ�
		for( i = 0; i< enemys.size(); i++){
			enemy = (Enemy)enemys.elementAt(i);
			
			if(enemy.getSprite().collidesWith(myPlane.getSprite(), true)){
				
				bombs.addElement(new Bomb(enemy.getSprite().getX()+enemy.getSprite().getWidth()/2,
						enemy.getSprite().getY()+enemy.getSprite().getHeight()/2));
				
				enemys.removeElementAt(i);
				i--;
				
				if(!myPlane.isProtected()){
					bombs.addElement(new Bomb(myPlane.getSprite().getX() + myPlane.getSprite().getWidth()/2,
							myPlane.getSprite().getY()+myPlane.getSprite().getHeight()/2));
					if(myPlane.revival(100)){
						end = 2;

					}
				}

		
			}
		}
		
		for(i = 0; i<packages.size();i++){
			packageForPlane = (Package)packages.elementAt(i);
			
			if(packageForPlane.getSprite().collidesWith(myPlane.getSprite(), true)){
				
				myPlane.processPG(packageForPlane.getType());
				
				packages.removeElementAt(i);
				i--;
			}
		}
	}
	
	
//	protected abstract void win();
//	private  void lost(){
//		Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new LostCanvas());
//	}

	private void paint(){
		int i;
		
		terrain.paint();
		
		for(i = 0; i< eBullets.size(); i++){
			((Bullet)eBullets.elementAt(i)).paint();
		}
		
		for(i = 0; i< myBullets.size(); i++){
			((Bullet)myBullets.elementAt(i)).paint();
		}
		
		for(i = 0; i< packages.size(); i++){
			((Package)packages.elementAt(i)).paint();
		}
		
		
		
		if(ifBoss){
			boss.paint();
		}
		
		for(i = 0; i< enemys.size(); i++){
			((Enemy)enemys.elementAt(i)).paint();
		}
		
		for(i = 0; i< bombs.size(); i++){
			((Bomb)bombs.elementAt(i)).paint();
		}
		
		if(end != 0 && bombs.size() == 0){
			end();
		}
		
		myPlane.paint();

		/***���濪ʼ��GUI***/
		
		//����ֵ
		for(i = 0 ; i < myPlane.getLife(); i++ ){
			graphics.drawRegion(gui, 
					0, 0, 10, 10, 
					Sprite.TRANS_NONE, i*15, 0, Graphics.LEFT | Graphics.TOP);
		}
		
		int num = myPlane.getHealth()/10;
		
		//Ѫ��
		int healthColor;
		if( num >= 6 ){
			healthColor = 1;
		}
		else if( num >=4 ){
			healthColor = 2;
		}
		else{
			healthColor = 3;
		}
		for(i = num; i> 0 ; i--){
			graphics.drawRegion(gui, 
					healthColor *10, 0, 10, 10, 
					Sprite.TRANS_NONE, screenWidth - i*6 -4, 0, Graphics.LEFT | Graphics.TOP);
		}
		
		if(ifBoss){
			num = boss.getHealth()/75 +1;	
			if( num >= 6 ){
				healthColor = 1;
			}
			else if( num >=4 ){
				healthColor = 2;
			}
			else{
				healthColor = 3;
			}
			for(i = 0; i< num ; i++){
				graphics.drawRegion(gui, 
						healthColor *10, 0, 10, 10, 
						Sprite.TRANS_NONE, i*6, screenHeight -10, Graphics.LEFT | Graphics.TOP);
			}
			
		}
		else{
			if(end == 1){
				graphics.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_LARGE));
				graphics.drawString("ʤ��", 100, 100, Graphics.LEFT | Graphics.TOP);
			}
			else if(end == 2){
				graphics.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_LARGE));
				graphics.drawString("ʧ��", 100, 100, Graphics.LEFT | Graphics.TOP);
			}
		}

		
		

	}
	
	protected abstract void end();


}
