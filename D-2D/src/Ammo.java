import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ammo {
	
	private ImageIcon ammoSprite;
	private int xPos, yPos, width, height, type;
	private String path;
	Random rnd;
	private boolean exists;
	
	//No arg constructor
	public Ammo() {
		
		ammoSprite = new ImageIcon(path + 0 + ".png");
		width = ammoSprite.getIconWidth();
		height = ammoSprite.getIconHeight();
		xPos = 0;
		yPos = 0;
		exists = false;
		path = "images\\ammo\\ammo";
	}

	//Constructor with args (main constructor)
	public Ammo(int x, int y, int maxType) {
		
		path = "images\\ammo\\ammo";
		rnd = new Random();
		type = rnd.nextInt(maxType + 1);
		ammoSprite = new ImageIcon(path + type + ".png");
		width = ammoSprite.getIconWidth();
		height = ammoSprite.getIconHeight();
		xPos = x;
		yPos = y;
		exists = true;
	}
	
	//Accessor methods get what they say they do
	
	public int getX() {
		
		return xPos;
	}
	
	public int getY() {
		
		return yPos;
	}
	
	public Rectangle getRect() {
		
		return new Rectangle(xPos, yPos, width, height);
	}
	
	public int getWidth() {
		
		return width;
	}
	
	public int getHeight() {
		
		return height;
	}
	
	public int getAmmoType() 
	{
		return type + 1;
	}
	
	//Returns whether ammo exists or not
	public boolean exists() {
		
		return exists;
	}
	
	//Mutator methods set what they say they will
	public void setX(int x) {
		
		xPos = x;
	}
	
	public void setY(int y) {
		
		yPos = y;
	}
	
	public void setPosition(int x, int y) {
		
		xPos = x;
		yPos = y;

	}
	
	//Destroys the ammo box
	public void destroyInstance() {
		
		exists = false;
	}
	
	//Returns whether or not the ammo is offscreen
	public boolean isOffScreen(int rightEdge) {
		
		boolean offScreen = false;
		
		if (xPos + width >= rightEdge || xPos <= 0)
			offScreen = true;
		else
			offScreen = false;
		
		return offScreen;
	}

//Other method	
	public void draw(Graphics g2) {
		
		g2.drawImage(ammoSprite.getImage(), xPos, yPos, null);

	}


	
	
}