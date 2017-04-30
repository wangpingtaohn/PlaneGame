package planes;

import java.util.Vector;

public class EnemyFactory {

	//飞机类型
	public static int ENEMY1 = 1;
	public static int ENEMY2 = 2;
	public static int ENEMY3 = 3;
	public static int ENEMY4 = 4;
	public static int ENEMY5 = 5;
	public static int BOSS1  = 5;
	public static int BOSS2  = 6;
	

	
	//添加敌机
	public static void addEnemy(int type,Vector enemys){
		switch(type){
				case 1: {
					enemys.addElement(new Enemy1("/enemy1.png", 5));
					break;
				}
				case 2: {
					enemys.addElement(new Enemy1("/enemy2.png", 15));
					break;
				}
				case 3: {
					enemys.addElement(new Enemy1("/enemy3.png",10));
					break;
				}
				case 4: {
					enemys.addElement(new Enemy1("/enemy4.png",30));
					break;
				}
				case 5: {
					enemys.addElement(new Enemy2("/enemy5.png",50));
					break;
				}
		}
	}
	
}
