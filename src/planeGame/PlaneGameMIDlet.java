package planeGame;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


//单例模式
public class PlaneGameMIDlet extends MIDlet {

	//唯一静态实例
	private static PlaneGameMIDlet instance;

	
	public PlaneGameMIDlet() {

		instance = this;
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		
		Display.getDisplay(this).setCurrent(new WelcomeCanvas());//进入欢迎界面

	}

	//返回单例
	public static PlaneGameMIDlet getInstance(){
		if(null == instance ){
			return (instance = new PlaneGameMIDlet());
		}
		else{
			return instance;
		}
	}
}
