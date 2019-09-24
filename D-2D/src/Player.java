import javax.swing.*;
import java.awt.*;

public class Player {
	
	//declare all class variables
	private ImageIcon imgEast, imgWest, imgDead;
	private int xPos, yPos, width, height;
	private int direction;
	private int kills;
	private boolean dead;
	public final static int EAST = 0;
	public final static int WEST = 1;
	
	//No arg constructor, initializes all class variables.
	public Player() {
		
		imgEast = new ImageIcon("images\\Player\\playerEast0.png");
		imgWest = new ImageIcon("images\\Player\\playerWest0.png");
		imgDead = new ImageIcon("images\\deadPlayer.png");
		
		width = imgEast.getIconWidth();
		height = imgEast.getIconHeight();
		direction = EAST;
		kills = 0;
		dead = false;
	}
	
	//Accessors get what they say they will.
	
public int getX() {
		
		return xPos;
	}
	
	public int getY() {
		
		return yPos;
	}
	
	public int getWidth() {
		
		return imgEast.getIconWidth();
	}
	
	public int getHeight() {
		
		return imgEast.getIconHeight();
	}
	
	public int getDirection() {
		
		return direction;
	}
	
	public int getKills()
	{
		return kills;
	}
	
	public Rectangle getRect() {
		
		return new Rectangle(xPos, yPos, width, height);
	}
	
	public Rectangle getHitbox() {
		
		return new Rectangle(xPos, yPos + (int)(height*0.6), width, (int)(0.4*height));
	}
	
	//Returns whether player is dead or not
	public boolean isDead() {
		
		return dead;
	}
	
	
	//Mutator methods set what they say they do to a specified value.

	public void setKills(int kills)
	{
		this.kills = kills;
	}
	
	//Adds a specified number of kills to counter
	public void addKills(int kills)
	{
		this.kills += kills;
	}
	
	public void setLocation(int x, int y) {
		
		xPos = x;
		yPos = y;
		dead = false;
	}

	public void setDirection(int dir) {
		
		direction = dir;
	}
	
	public void setX(int x) {
		
		xPos = x;
	}
	
	public void setY(int y) {
		
		yPos = y;
	}
	
	//Moves the player a specified number of pixels in the y direction.
	public void move(int y) {
		
		yPos += y;
	}
	
	//Destroys the player
	public void kill() {
		
		dead = true;
	}
	
	//Draws the player, different sprite based on direction, and whether player is alive or not.
	public void draw(Graphics2D g2) {
		
		if (dead == true) //Player's dead - Blood splat
		{
			g2.drawImage(imgDead.getImage(), xPos- (imgDead.getIconWidth()-imgEast.getIconWidth())/2, yPos- (imgDead.getIconHeight() - imgWest.getIconHeight())/2, null);
			g2.setFont(new Font("Arial", 0, 20));
			g2.setColor(Color.WHITE);
			g2.drawString("You slayed " + kills + " demons before being trampled by a Pinky.", 40, 40);
		}
		else //Player's alive
		{
			if (direction == EAST)
				g2.drawImage(imgEast.getImage(), xPos, yPos, null);
			else
				g2.drawImage(imgWest.getImage(), xPos, yPos, null);
			
		}
	}

}