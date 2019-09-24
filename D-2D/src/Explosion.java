import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Explosion {
	
	//Declare all class variables
	private int frames;
	private ImageIcon [] explosionFrames;
	private ImageIcon currentSprite;
	private int [] xPos, yPos, width, height;
	private Ellipse2D [] hitboxes; //Hitboxes for each stage of the animation. 
	private boolean exists;
	private int currentFrame, framesPassed, frameSpeed;

	//No arg constructor
	public Explosion() {
		
		frames = 1;
		exists = false;
	}
	//Constructor with args
	public Explosion(int x, int y, int frames, String path) { //Explosion is CENTER justified! (because they change size)
		
		this.frames = frames;
		explosionFrames = new ImageIcon[frames];
		hitboxes = new Ellipse2D [frames];
		width = new int[frames];
		height = new int[frames];
		xPos = new int[frames];
		yPos = new int[frames];
		
		//Set up Sprite, width, height x and y position, and hitbox for each sprite of the explosion
		for(int i = 0; i<frames; i++)
		{
			explosionFrames[i] = new ImageIcon(path + i + ".png");
			width[i] = explosionFrames[i].getIconWidth();
			height[i] = explosionFrames[i].getIconHeight();
			xPos[i] = x - width[i]/2;
			yPos[i] = y - width[i]/2;
			hitboxes[i] = new Ellipse2D.Double(xPos[i], yPos[i], width[i], height[i]);
		}
		
		currentFrame = 0;
		currentSprite = explosionFrames[currentFrame];
		framesPassed = 0;
		frameSpeed = 1;
		exists = true;
		
	}
	//Accessor methods get what they say they do
	public int getWidth() {
		
		return width[currentFrame];
	}
	
	public int getHeight() {
		
		return height[currentFrame];
	}
	
	public ImageIcon getSprite()
	{
		return currentSprite;
	}
	
	public int getCurrentSprite() 
	{
		return currentFrame;
	}
	
	public Ellipse2D getHitbox() 
	{
		return hitboxes[currentFrame];
	}
	
	public boolean exists() //Returns whether or not the explosion exixts
	{
		return exists;
	}
	
	//Mutator methods set what they say they will
	
	public void setX(int x) {
		
		for(int i = 0; i<frames; i++)
			xPos[i] = x - width[i]/2;
	}
	
	public void setY(int y) {
		
		for(int i = 0; i<frames; i++)
			yPos[i] = y - height[i]/2;
	}
	
	public void setFrameSpeed(int speed)
	{
		frameSpeed = speed;
	}
	
	//Sets explosion sprites to something different based on the path
	public void setSprites(int numFrames, String path) {
		frames = numFrames;
		for(int i = 0; i < frames; i++)
			explosionFrames[i] = new ImageIcon(path + i + ".png");
	}
	
	//Destroys object
	public void destroyInstance() {
		
		exists = false;
	}

//Other Methods:
	
	//Advances the frame at the desired speed. Destroys the object when animation is over.
	public void continueAnimation() {
		framesPassed ++;
		if(currentFrame < frames-1)
		{
			if(framesPassed%frameSpeed == 0)
				currentFrame++;
		}
		else
			exists = false;
	}
	
	//Draws object
	public void draw(Graphics g2) {
		
			g2.drawImage(explosionFrames[currentFrame].getImage(), xPos[currentFrame], yPos[currentFrame], null);
			
	}
	

}