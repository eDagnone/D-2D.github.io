import javax.swing.*;
import java.awt.*;

public class EnemyBullet {
	
	private ImageIcon imgEast, imgWest;
	private int xPos, yPos, width, height;
	private int direction;
	private boolean fired;
	public final static int EAST = 0;
	public final static int WEST = 1;
	
	public EnemyBullet() {
		
		imgEast = new ImageIcon("images\\bulletEast.png");
		imgWest = new ImageIcon("images\\bulletWest.png");
		direction = EAST;
		width = imgEast.getIconWidth();
		height = imgEast.getIconHeight();
		fired = false;
		xPos = 0;
		yPos = 0;
	}
	
	public EnemyBullet(int x, int y) {
		
		imgEast = new ImageIcon("images\\bulletEast.png");
		imgWest = new ImageIcon("images\\bulletWest.png");
		direction = EAST;
		width = imgEast.getIconWidth();
		height = imgEast.getIconHeight();
		fired = false;
		xPos = x;
		yPos = y;
	}
	
	public int getX() {
		
		return xPos;
	}
	
	public int getY() {
		
		return yPos;
	}
	
	public void setX(int x) {
		
		xPos = x;
	}
	
	public void setY(int y) {
		
		yPos = y;
	}
	
	public void setPosition(int playerX, int playerY, int dir) {
		
		direction = dir;
		
		if (direction == EAST)
		{
			xPos = playerX + 75;
		}
		else
		{
			xPos = playerX;
		}
		
		yPos = playerY + 40;
		fired = true;
	}
	
	public Rectangle getRect() {
		
		return new Rectangle(xPos, yPos, width, height);
	}
	
	public boolean isFired() {
		
		return fired;
	}
	
	public void move() {
		
		if (direction == EAST)
		{
			xPos += 10;
		}
		else
		{
			xPos -= 10;
		}
	}
	
	public int getWidth() {
		
		return width;
	}
	
	public int getHeight() {
		
		return height;
	}
	
	public void stopBullet() {
		
		fired = false;
	}
	
	public void draw(Graphics g2) {
		
		if (direction == EAST)
		{
			g2.drawImage(imgEast.getImage(), xPos, yPos, null);
		}
		else
		{
			g2.drawImage(imgWest.getImage(), xPos, yPos, null);
		}
	}
	
	public boolean isOffScreen(int rightEdge) {
		
		boolean offScreen = false;
		
		if (xPos + width >= rightEdge || xPos + width <= 0)
		{
			offScreen = true;
			fired = false;
		}
		else
		{
			offScreen = false;
		}
		
		return offScreen;
	}
}