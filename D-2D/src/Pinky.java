import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Pinky {

	
	private ImageIcon currentSprite;
	private ImageIcon[] pinkySprites;
	private int pixelsMoved, spriteNumber; 
	private int xPos, yPos, width, height, direction;
	private boolean isDead;
	private Random rnd;
	public final static int EAST = 0; //Moving East
	public final static int WEST = 1;
	
	//Constructor
	public Pinky() 
	{
		pinkySprites = new ImageIcon[4];
		isDead = false;
		rnd = new Random();
		direction = rnd.nextInt(2);
		pixelsMoved = 0;
		
		for(int i = 0; i < 4; i++)
		{
			if(direction == 0)//East
				pinkySprites[i] = new ImageIcon("images\\Pinky\\PinkyEast" + i + ".png");
			else //West
				pinkySprites[i] = new ImageIcon("images\\Pinky\\PinkyWest" + i + ".png");
		}
		spriteNumber = rnd.nextInt(4);
		currentSprite = (pinkySprites[spriteNumber]);
		width = pinkySprites[0].getIconWidth();
		height = pinkySprites[0].getIconHeight();
		
		if (direction == EAST)
			xPos = -(rnd.nextInt(500) + width);
		else
			xPos = rnd.nextInt(500) + 920;

		yPos = rnd.nextInt(555 - height);
		
	}

	//Accessors get what they say they do
	
	public int getX() {

		return xPos;
	}

	public int getY() {

		return yPos;
	}

	public int getWidth() {

		return width;
	}

	public int getHeight() {

		return height;
	}

	public String getPosition() {
		
		return xPos + ", " + yPos;
	}
	
	public Rectangle getRect() {
		
		return new Rectangle(xPos, yPos, width, height);
	}
	
	public Rectangle getHitbox() {
		return new Rectangle(xPos , yPos + (int)(0.6*height), width, (int)(0.4*height));
	}
	
	public int getDirection() 
	{
		return direction;
	}
	
	//Returns whether or not the pinky is offscreen
	public boolean isOffScreen(int frameWidth) {

		if (direction == EAST)
		{
			if (xPos >= frameWidth)
				return true;
			else
				return false;
		}
		else //Direction == West
		{
			if (xPos + width <= 0)
				return true;
			else
				return false;
		}	
	}

	//Mutators set what they say they do
	public void setX(int x) {

		xPos = x;
	}

	public void setY(int y) {

		yPos = y;
	}

	public void setLocation(int frameWidth, int frameHeight) {

		if (direction == EAST)
		{
			xPos = -(rnd.nextInt(500) + width);
		}
		else
		{
			xPos = rnd.nextInt(500) + frameWidth;
		}

		yPos = rnd.nextInt(frameHeight - height);
	}
	
	public void kill() {//Kills pinky (isDead = true)

		isDead = true;
	}
	
	//Move the pinkies
	public void move() {
		
		if(!isDead) {//Do not move while dead. Blood splats shouldn't scroll across the screen
		
			//Animation
			pixelsMoved += 6;//Move 6 pixels per frame
		
			if(pixelsMoved % 18 == 0) //Change animation every 3 frames
			{
				if(spriteNumber < 3)
					spriteNumber++;
				else
					spriteNumber = 0;
				currentSprite = pinkySprites[spriteNumber];
			}
		
		
			if (direction == EAST)
				xPos += 6;
			else
				xPos -= 6;
		}
	}
		
	//Draws the pinky as long as it's not dead.
	public void draw(Graphics2D g2) {

		if (!isDead)
			g2.drawImage(currentSprite.getImage(), xPos, yPos, null);
				
	}
}