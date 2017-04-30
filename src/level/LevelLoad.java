package level;

import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

import planeGame.PlaneGameMIDlet;


import utility.AlphaBlend;

public class LevelLoad extends GameCanvas implements Runnable{

	private int level = 0;
	
	private Image imageBg;
	private Image imageLevel;
	
	private AlphaBlend alphaLevel;

	public LevelLoad(int i) {
		super(false);
		level = i;
		
		try {
			imageBg = Image.createImage("/bckgrd"+level+".jpg");
			imageLevel = Image.createImage("/level"+level + ".png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		alphaLevel = new AlphaBlend(imageLevel);
		
		(new Thread(this)).start();
		// TODO Auto-generated constructor stub
	}

	public void run() {
		while(paint()){
			//Ë¢ÐÂÆÁÄ»
			this.flushGraphics();
		}

		Display.getDisplay(PlaneGameMIDlet.getInstance()).setCurrent(LevelFactory.createLevel(level));
		System.gc();
		
	}
	
	private boolean paint(){
		this.getGraphics().setColor(255, 255, 255);
		this.getGraphics().fillRect(0, 0, this.getWidth(), this.getHeight());
		this.getGraphics().drawImage(imageBg, 0, 0, Graphics.LEFT | Graphics.TOP);
		return alphaLevel.paintImage(this.getGraphics());
	}
	

	
	

}
