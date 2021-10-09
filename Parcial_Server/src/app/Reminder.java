package app;

import processing.core.PApplet;

public class Reminder {
	
	
	private PApplet app;
	private String color, text;
	private int posX, posY;
		

	public Reminder(String color, int posX, int posY, String text, PApplet app) {
			this.color=color;
			this.posX=posX;
			this.posY=posY;
			this.text=text;
			this.app=app;
	}
		
	public void drawData() {	
		app.fill(255,255,255);
		app.rect(posX, posY +25, 140, 50);
		if (color.equals("verde")) {
				
			app.fill(0,255,0);
		}else if (color.equals("amarillo")) {
				
			app.fill(255,255,0);
		}else if (color.equals("rojo")) {
				
			app.fill(255,0,0);
		}else {
			app.fill(0);
		}
			app.ellipse(posX, posY, 25, 25);	
			app.fill(0);
			app.text(text, posX, posY + 30);
		}
	
	
}
