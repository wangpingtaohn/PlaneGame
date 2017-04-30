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

	//画图对象
	private Graphics graphics; 
	
	private int screenHeight;//屏幕高度
	private int screenWidth;//屏幕宽度
	
	//当前画布线程
	private Thread myThread;
	
	private long startTime;//线程一轮运行的开始时间
	private long endTime;//线程一轮运行的结束
	private int frameSpeed;//帧速
	
	
	private Command pause;//暂停按键
	private Command back;//返回按键
	private Command start;//开始按键
	private Command resume;//继续按键
	
	private boolean RUN = true;//是否运行
	private boolean START = false;//是否开始
	private boolean BACK = false;//是否返回

	
	protected Terrain terrain;//地图
	
	private MyPlane myPlane;//我方飞机
	
	protected Vector enemys = new Vector();//敌人
	
	private Vector eBullets = new Vector();//敌方子弹
	private Vector myBullets = new Vector();//敌方子弹
	
	
	protected Boss boss;//老板
	protected boolean ifBoss = false;//是否boss出现
	
	protected Vector packages = new Vector();//补给包

	
	private Vector bombs = new Vector();
	
	
	Random random = new Random();

	
	private Image gui;
	
	protected int enemyCount = 0;
	
	protected int end = 0;
	

	public PlaneGameCanvas() {
		super(true);//继承父类，开启游戏键
		
		graphics = this.getGraphics();
		
		//获得屏幕长宽
		screenHeight = this.getHeight();
		screenWidth = this.getWidth();

		
		//初始化按钮们		
		pause = new Command("暂停",Command.BACK,1);
		back = new Command("返回",Command.OK,1);
		start = new Command("开始",Command.BACK,1);
		resume = new Command("继续",Command.BACK,1);
		
		//添加开始按钮
		this.addCommand(start);
		
		//设置画布按键监听
		this.setCommandListener(this);
		
		//初始化各种要用的类的屏幕长宽
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
		
		//初始和开启当前线程
		myThread = new Thread(this);
		myThread.start();
		
	}
	
	//按键监听
	public void commandAction(Command c, Displayable arg1) {
		if(c == start){//开始的时候，根据球的位置，确定球的初始运行方向

			START = true;//设置开始为真
			
			//重新构建画布按钮，删除“开始”按钮，加上“暂停”按钮
			this.removeCommand(start);
			this.addCommand(pause);
			
		}else if(c == pause){
			
			RUN = false;//是否继续
			
			//重新构建画布按钮，删除“暂停”按钮，加上“继续”、“返回”按钮
			this.removeCommand(pause);
			this.addCommand(resume);
			this.addCommand(back);
			
		}else if(c == resume){
			RUN = true;//是否继续
			
			//重新构建画布按钮，删除“继续”、“返回”按钮，加上“暂停”按钮
			this.removeCommand(resume);
			this.removeCommand(back);
			this.addCommand(pause);
			
		}else if(c == back){//设置返回标志，通知停止线程，并跳到欢迎页面
			BACK = true;
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new WelcomeCanvas());
		}
		
		
	}

	//重写Runnable中的Run（）方法，线程
	public void run()
	{
		//获得当前运行的线程
		Thread currentThread = Thread.currentThread();
		frameSpeed = 10;
		while(end !=3 && currentThread == myThread){//当前线程如果是自己的线程，就运行
			startTime = System.currentTimeMillis();//记录本轮开始时间
			

					
			if(START){
				if(RUN){
					make();
					processMovement();
					processColliding();
//					//清空前一帧
//					graphics.setColor(255, 255, 255);
//					graphics.fillRect(0, 0, screenWidth, screenHeight);
			        paint();
				}
			}
			else{
				terrain.paint();
				myPlane.paint();
				
			}
			
			
			
			//刷新屏幕
			this.flushGraphics();
			
			endTime = System.currentTimeMillis();//本轮执行结束时间
			if((endTime - startTime) < frameSpeed){//如果本轮执行少于速度因子，等待
				try {
					Thread.sleep(frameSpeed - (endTime - startTime));
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			
		}
		
		
	}





	protected abstract void make();
	
	//更新帧
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

	//处理碰撞
	private void processColliding() {
		int i,j;
		Bullet bullet;
		Enemy enemy;
		Package packageForPlane;
		
		//我方子弹和敌方相碰
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
		
		//敌方子弹和我方飞机
		for(i = 0; i< eBullets.size();i++){
			bullet = (Bullet)eBullets.elementAt(i);
			if(bullet.getSprite().collidesWith(myPlane.getSprite(), true)){
				
				eBullets.removeElementAt(i);
				i--;
				
				//如果飞机非保护
				if( (!myPlane.isProtected()) && myPlane.shooted(bullet.getDestorying())){
					
					bombs.addElement(new Bomb(myPlane.getSprite().getX() + myPlane.getSprite().getWidth()/2,
							myPlane.getSprite().getY()+myPlane.getSprite().getHeight()/2));
					
					if(myPlane.revival(100)){
						end = 2;
				
					}
				}
			}
		}
		
		//敌机和我机
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

		/***下面开始画GUI***/
		
		//生命值
		for(i = 0 ; i < myPlane.getLife(); i++ ){
			graphics.drawRegion(gui, 
					0, 0, 10, 10, 
					Sprite.TRANS_NONE, i*15, 0, Graphics.LEFT | Graphics.TOP);
		}
		
		int num = myPlane.getHealth()/10;
		
		//血量
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
				graphics.drawString("胜利", 100, 100, Graphics.LEFT | Graphics.TOP);
			}
			else if(end == 2){
				graphics.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_LARGE));
				graphics.drawString("失败", 100, 100, Graphics.LEFT | Graphics.TOP);
			}
		}

		
		

	}
	
	protected abstract void end();


}
