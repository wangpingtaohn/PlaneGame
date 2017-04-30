package level;


public class LevelFactory {

	public static int levelNum = 2;
	public static PlaneGameCanvas createLevel(int l){
		switch(l){
			case 1 : return new Level1();
			case 2 : return new Level2();
		}
		
		return null;
	}
}
