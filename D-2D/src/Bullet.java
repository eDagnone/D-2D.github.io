import javax.swing.*;
import java.awt.*;

public class Bullet {
	
	//Declare all class variables
	private ImageIcon imgEast, imgWest;
	private int xPos, yPos, width, height;
	private int direction;
	private int speed;
	private boolean fired;
	public final static int EAST = 0;
	public final static int WEST = 1;
	
	//No arg constructor
	public Bullet() {
		
		imgEast = new ImageIcon("images\\bulletEast.png");
		imgWest = new ImageIcon("images\\bulletWest.png");
		direction = EAST;
		width = imgEast.getIconWidth();
		height = imgEast.getIconHeight();
		fired = false;
		speed = 30;
		xPos = 0;
		yPos = 0;
	}
	
	//Constructor with args (main constructor)
	public Bullet(int x, int y, int dir) {
		
		imgEast = new ImageIcon("images\\bulletEast.png");
		imgWest = new ImageIcon("images\\bulletWest.png");
		direction = dir;
		width = imgEast.getIconWidth();
		height = imgEast.getIconHeight();
		fired = false;
		speed = 30;
		xPos = x;
		yPos = y;
	}
	
	//Accessors return what they say they will.
	public int getX() {
		
		return xPos;
	}
	
	public int getY() {
		
		return yPos;
	}
	
	public ImageIcon getSprite()
	{
		return imgEast;
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
	
	//Returns whether or not the bullet exixts
	public boolean isFired() {
		
		return fired;
	}
	
	//Returns whether or not the bullet is offscreen
	public boolean isOffScreen(int rightEdge) {
		
		boolean offScreen = false;
		
		if (xPos + width >= rightEdge || xPos + width <= 0)
		{
			offScreen = true;
			fired = false;
		}
		else
			offScreen = false;
		
		return offScreen;
	}
	
	//Mutators set what they say they do
	
	public void setX(int x) {
		
		xPos = x;
	}
	
	public void setY(int y) {
		
		yPos = y;
	}
	public void setSprite(String name)
	{
		imgEast = new ImageIcon("images\\" + name + "East.png");
		imgWest = new ImageIcon("images\\" + name + "West.png");
	}

	
	public void setPosition(int playerX, int playerY, int dir) {
		
		direction = dir;
		
		if (direction == EAST)
			xPos = playerX + 85;
		else
			xPos = playerX - 10;
		
		yPos = playerY + 27;
		fired = true;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	

	//Moves the bullet in a certian direction at the specified speed.
	public void move() {
		
		if (direction == EAST)
		{
			xPos += speed;
		}
		else
		{
			xPos -= speed;
		}
	}
	

	//Destroys the bullet
	public void stopBullet() {
		
		fired = false;
	}
	
	//Draws the bullet
	public void draw(Graphics g2) {
		
		if (direction == EAST)
			g2.drawImage(imgEast.getImage(), xPos, yPos, null);
		else
			g2.drawImage(imgWest.getImage(), xPos, yPos, null);

	}
}