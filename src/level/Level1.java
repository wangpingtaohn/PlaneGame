package level;

import javax.microedition.lcdui.Display;

import packageForPlane.Package;
import planeGame.LostCanvas;
import planeGame.PlaneGameMIDlet;
import planes.Boss;
import planes.EnemyFactory;
import Terrain.Terrain;

public class Level1 extends PlaneGameCanvas{
	
	public Level1(){
		super();
		terrain = new Terrain("/bckgrd1.jpg");
	}
	
	protected void make() {
		if(random.nextInt() % 500 == 0 && packages.size() < 5){
			packages.addElement(new Package(Math.abs(random.nextInt())%6,Math.abs(random.nextInt())%200+20,Math.abs(random.nextInt())%200 +35));
		}
		
		if(!ifBoss){
			if(random.nextInt() % 200 == 0 && enemys.size() < 5){
				EnemyFactory.addEnemy(random.nextInt()%4+1,enemys);
				enemyCount ++;
			}
			
			if(enemyCount > 30 && end ==0){
				boss = new Boss("/boss1.png",600);
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
		if(end == 1){
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new LevelLoad(2));
		}
		else{
			Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(new LostCanvas());
		}
		end = 3;
		System.gc();
	}
}
