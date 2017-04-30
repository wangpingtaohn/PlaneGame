package level;

import javax.microedition.lcdui.Display;

import Terrain.Terrain;

import packageForPlane.Package;
import planeGame.LostCanvas;
import planeGame.PlaneGameMIDlet;
import planeGame.WinCanvas;
import planes.Boss;
import planes.EnemyFactory;

public class Level2 extends PlaneGameCanvas{

	public Level2(){
		super();
		terrain = new Terrain("/bckgrd2.jpg");
	}
	
	protected void make() {
		if(random.nextInt() % 600 == 0 && packages.size() < 5){
			packages.addElement(new Package(Math.abs(random.nextInt())%4,Math.abs(random.nextInt())%220+10,Math.abs(random.nextInt())%220 +35));
		}
		
		if(!ifBoss){
			if(random.nextInt() % 200 == 0 && enemys.size() < 5){
				EnemyFactory.addEnemy(random.nextInt()%5+1,enemys);
				enemyCount ++;
			}
			
			if(enemyCount > 40 && end ==0){
				boss = new Boss("/boss2.png",750);
				ifBoss = true;
				System.gc();
			}
			
		}
	}

	protected void end() {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(end == 1 ){
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new WinCanvas());
		}
		else{
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new LostCanvas());
		}
		
		end = 3;
		
		System.gc();
	}
}
