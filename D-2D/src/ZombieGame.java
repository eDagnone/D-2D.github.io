//README:
//Controls are mouse to move, space to shoot, w to cycle weapons up, and s to cycle weapons down.
//Sprite clipping is a thing. Use it to your advantage
//This game requires skills. If you have none, you can cheat by editing the code ;)
	//Line 80: set to 100. Line 584: set to 1, 2, and 3.
//It is recommended that you use a proper mouse for this game (i.e. not mousepad). Good luck.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

@SuppressWarnings("serial")
public class ZombieGame extends JPanel implements KeyListener, MouseMotionListener, MouseListener, 
	ActionListener {
	
	//Declare ALL class variables
	private Random rnd;
	private ImageIcon imgBackground;
	private Player player;
	private Bullet b, rocket;
	private Timer bulletTimer, pinkyTimer, deadTimer;
	private Pinky[] pinkies;
	private boolean start;
	private int seconds;
	private Explosion[] explosions, deadPinkies;
	private Explosion bfgExplosion;
	private Ammo[] ammoBoxes; 
	private int [] ammoQuantities;
	private int maxAmmoType;
	private int currentWeapon;
	private int [] weapons;
	private ImageIcon [] weaponSpritesEast, weaponSpritesWest;
	private Polygon shotgunSpray;
	

	public static void main(String[] args) {
		new ZombieGame();
	}

	public ZombieGame() {
		
		//Declare everything
		player = new Player();
		b = new Bullet();
		rocket = new Bullet();
		rocket.setSprite("rocket");
		rocket.setSpeed(20);
		pinkies = new Pinky[10];//Max pinkies
		explosions = new Explosion [3];//Max explosions
		deadPinkies = new Explosion[10];//Max dead pinkies
		bfgExplosion = new Explosion();
		ammoBoxes = new Ammo [5];//Max number of ammo boxes
		ammoQuantities = new int [5];
		weapons = new int[5];
		weaponSpritesEast = new ImageIcon[5];
		weaponSpritesWest = new ImageIcon[5];
		shotgunSpray = new Polygon();
		
		rnd = new Random();
		
		for(int i = 0; i < explosions.length; i++)
			explosions[i] = new Explosion();
		
		for(int i = 0; i<ammoBoxes.length; i++)
			ammoBoxes[i] = new Ammo();
		
		//Weapons setup
		currentWeapon = 1;
		for(int i = 0; i< weapons.length; i++) {
			weapons[i] = i;
			weaponSpritesEast[i] = new ImageIcon("images\\Weapons\\weapon" + i + "East.png");
			weaponSpritesWest[i] = new ImageIcon("images\\Weapons\\weapon" + i + "West.png");
		}
		
		//Ammo Setup
		ammoQuantities[0] = 1;
		ammoQuantities[1] = 20;
		for(int i = 2; i < ammoQuantities.length; i++)
			ammoQuantities[i] = 0;
				
		
		//Timers setup. 1 frame = 16 ms(approx)
		bulletTimer = new Timer(4, this);//4* per frame
		pinkyTimer = new Timer(16, this);//Every Frame. This is the game timer.
		deadTimer = new Timer(1000, this);//Every 1second		
		
		
		//Input setup
		setLayout(null);
		setFocusable(true);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);

		

		//JFrame setup
		imgBackground = new ImageIcon("images\\background.png");
		JFrame frame = new JFrame();
		frame.setContentPane(this);
		frame.setSize(imgBackground.getIconWidth(), imgBackground.getIconHeight());
		frame.setLocationRelativeTo(null);
		frame.setTitle("Pinky Attack");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
		
		//Pinky Setup
		boolean overlap;
		for (int i = 0; i < pinkies.length; i++)
		{
			pinkies[i] = new Pinky();//Populate array (Make all the pinkies)
			deadPinkies[i] = new Explosion();//Populate array of dead pinkies
			deadPinkies[i].setFrameSpeed(4);//Set animation speed
			
			//Make sure the pinkies don't overlap
			do
			{
				overlap = false;
				pinkies[i].setLocation(getWidth(), getHeight());
				
				for (int j = i - 1; j > 0; j--)
				{
					if (pinkies[i].getRect().intersects(pinkies[j].getRect()))
					{
						overlap = true;
						break;
					}
				}
			}
			while (overlap == true);
		}
		
		
		//Spawn player, Start the Game
		player.setLocation(0, getHeight() / 2 - player.getHeight() / 2);
		start = true;
		pinkyTimer.start();
	}

	
	
	
	//Draw EVERYTHING
	
	public void paintComponent(Graphics g) { //Only runs once every frame

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(imgBackground.getImage(), 0, 0, this);
		if (start) //Start
		{
			for (int i = 0; i < pinkies.length; i++)//Draw pinkies + dead pinkies
			{
				if(deadPinkies[i].exists())//Draw any existent dead pinkies.
				{
					deadPinkies[i].draw(g2);
					deadPinkies[i].continueAnimation();
				}
				
				pinkies[i].draw(g2);//Draw all pinkies
			}
			//Draw bullet if it exists
			if (b.isFired())
				b.draw(g2);
			//Draw rocket if it exists
			if(rocket.isFired())
				rocket.draw(g2);
			
			if(player.isDead() == false)//When player is alive:
			{
				//Draw Gun
				if(player.getDirection() == Player.EAST)//Player facing east
					g2.drawImage(weaponSpritesEast[currentWeapon].getImage(), player.getX() + player.getWidth()-8, player.getY() + weaponSpritesEast[currentWeapon].getIconHeight() + 10, this);
				else//Player facing west
					g2.drawImage(weaponSpritesWest[currentWeapon].getImage(), player.getX() - weaponSpritesWest[currentWeapon].getIconWidth() + 8, player.getY() + weaponSpritesWest[currentWeapon].getIconHeight() + 10, this);
				
				//Draw Shotgun spray
				g2.setColor(Color.RED);
				g2.drawPolygon(shotgunSpray);
				
				//Draw HUD
				g2.setColor(Color.WHITE);
				g2.drawString("Kills: " + player.getKills(), 50, 50);
				g2.drawImage(weaponSpritesWest[currentWeapon].getImage(), 50,60,this);
				g2.drawString("Ammo: " + ammoQuantities[currentWeapon], 200, 50);
				g2.drawString("Weapon: " + (currentWeapon+1), 50,100);
			}
			
			player.draw(g2); //Draw the player
			
			//draw any existent Ammo Boxes
			for(int i = 0; i<ammoBoxes.length; i++)
			{
				if(ammoBoxes[i].exists())
					ammoBoxes[i].draw(g2);
			}
			
			//Draw any existent RPG explosions
			for(int i = 0; i < explosions.length; i++)
			{
				if(explosions[i].exists())
				{
					explosions[i].draw(g2);
					explosions[i].continueAnimation();
				}
			}
			
			//Draw any existent BFG explosions
			if(bfgExplosion.exists())
			{
				bfgExplosion.draw(g2);
				bfgExplosion.continueAnimation();
			}
			shotgunSpray = new Polygon(); //Reset
		}
	}

	//Keyboard controls
		public void keyPressed(KeyEvent e) { 
 
			//W switches weapon up, S switches weapon down
			if (e.getKeyCode() == KeyEvent.VK_W)
				switchUp();
			else if (e.getKeyCode() == KeyEvent.VK_S)
				switchDown();
			
			//A turns left D turns right
			if (e.getKeyCode() == KeyEvent.VK_A) 
				player.setDirection(Player.WEST);
			else if (e.getKeyCode() == KeyEvent.VK_D)
				player.setDirection(Player.EAST);
		}

		public void keyReleased(KeyEvent e) 
		{
			//Space to shoot on key release (anti-spam)
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
				shoot();
		}
		
		public void keyTyped(KeyEvent e) {/*do nothing*/}


		public void actionPerformed(ActionEvent e) { //Things triggered by timers, used to check collisions

			if (e.getSource() == bulletTimer) //Bullet Onscreen.
			{
				if(b.isFired())
				{
					b.move();
					//Destroy bullet if offscreen
					if (b.isOffScreen(getWidth()))
						bulletTimer.stop();
					for (int i = 0; i < pinkies.length; i++)//Check collision between bullet and pinky
					{
						if (b.getRect().intersects(pinkies[i].getRect()) && b.isFired()) //Bullet hits Pinky -->kill
						{
							
							bulletTimer.stop();//Stop checking for bullet collisions
							b.stopBullet();
							killPinkySequence(i);
						}	
					}
				}
				
				if(rocket.isFired())
				{
					rocket.move();
					//Destroy rocket if offscreen
					if (rocket.isOffScreen(getWidth()))
						bulletTimer.stop();
					for (int i = 0; i < pinkies.length; i++)//Check collision between bullet and pinky
					{
						if (rocket.getRect().intersects(pinkies[i].getRect()) && rocket.isFired()) //Rocket hits Pinky - Explosion
						{
							bulletTimer.stop();//Stop checking for rocket collisions
							
							//Create an RPG explosion where the rocket is.
							for(int freeExplosion = 0; freeExplosion < explosions.length; freeExplosion++)
							{
								if(explosions[freeExplosion].exists() == false)
								{
									explosions[freeExplosion] = new Explosion(rocket.getX() + rocket.getWidth()/2, rocket.getY() + rocket.getHeight()/2, 14, "images\\Explosions\\explosion");
									explosions[freeExplosion].setFrameSpeed(2);
									rocket.stopBullet();
									break;
								}
							}
						}	
					}
				}
			}
			
			else if (e.getSource() == pinkyTimer) //This timer ticks once every frame. So, all this stuff will happen once a frame.
			{
				for (int i = 0; i < pinkies.length; i++) //For all Pinkies:
				{	
					
					pinkies[i].move();//Move them
					
					//Respawn them when offscreen
					if (pinkies[i].isOffScreen(getWidth()) == true)
						pinkies[i] = new Pinky();
					
					//Check if they are in RPG explosion, kill them if they are 
					for(int j = 0; j < explosions.length; j++)//For all RPG explosions
					{
						if(explosions[j].exists())
						{
							if(explosions[j].getHitbox().intersects(pinkies[i].getRect()))//If RPG explosion hitbox intersects pinky hitbox 
							{
								killPinkySequence(i);//kill pinky
							}
						}
					}
					//Check if they are in BFG explosion, kill them if they are 
					if (bfgExplosion.exists())
					{
						if(bfgExplosion.getHitbox().intersects(pinkies[i].getRect()))//If BFG explosion hitbox intersects pinky hitbox 
						{
							killPinkySequence(i);//Kill pinky
						}
					}
		
				}
				checkPlayerDeath();
				checkCollision_PlayerAmmo();
				repaint();//GLOBAL REPAINT
			}
			
			
			else if (e.getSource() == deadTimer) //Player is DEAD
			{
				seconds++;
				
				if (seconds == 2) //2 Death Timer up: Spawn Player
				{
					//Spawn Pinkies back in.
					for(int counter = 0; counter < pinkies.length; counter++)
					{
						pinkies[counter].kill();
						pinkies[counter] = new Pinky();
					}
					
					seconds = 0;//Resets seconds counted on death timer
					deadTimer.stop();//Stops death timer
					
					//Reset ammo
					maxAmmoType = 0;//Resets max ammo drop to pistol ammo (until player gets more kills)
					ammoQuantities[1] = 20;//Resets pistol ammo to 20.
					for(int i = 2; i<ammoQuantities.length; i++)//Set all ammo quantities to 0.
						ammoQuantities[i] = 0;
					player.setKills(0);//Resets kills to 0.
					currentWeapon = 1;//Sets current weapon to pistol
					player.setLocation(getWidth()/2 - player.getWidth()/2, getHeight() / 2 - player.getHeight() / 2);//Put player in center
					addMouseMotionListener(this);//Allow smouse input
					addKeyListener(this);//Allows keyboard input
					pinkyTimer.start();//Starts timer to make pinkies move (ticks once every frame)
				}
			}	
		}
		//End of Checking event-triggered stuff.
		
		
		
		
		
		//Checks if player has collided with zombie. Kills them.
		public void checkPlayerDeath() 
		{
			for (int i = 0; i < pinkies.length; i++)//Check for all pinkies:
			{
				if (pinkies[i].getHitbox().intersects(player.getHitbox()))//if pinky's hitbox collides with player's hitbox..
				{
					player.kill();//Kill the player
					
					//Destroy all ammo boxes on the ground
					for(int j = 0; j < ammoBoxes.length; j++)
						ammoBoxes[j].destroyInstance();
					deadTimer.start();//Start the death penalty timer
					//Don't let the player move/shoot
					removeMouseMotionListener(this);
					removeKeyListener(this);
					//Redraw scene
					repaint(); 
				}
			}
		}
		
		//Checks if player is on ammo box, and gives them ammo.
		public void checkCollision_PlayerAmmo()
		{
			for(int i = 0; i < ammoBoxes.length; i++)
			{
				if((ammoBoxes[i].getRect()).intersects(player.getRect())) //Check if player's hitbox intersects ammo box's hitbox
				{
					if(ammoBoxes[i].exists())
						if(ammoBoxes[i].getAmmoType() == 4)//contains BFG ammo
							ammoQuantities[ammoBoxes[i].getAmmoType()] += 1;//give 1 BFG ammo
						else if(ammoBoxes[i].getAmmoType() == 2)//contains shotgun ammo
							ammoQuantities[ammoBoxes[i].getAmmoType()] += 4;//give 4 shotgun ammo
						else
							ammoQuantities[ammoBoxes[i].getAmmoType()] += 5;//Anything else --> give 5 ammo
					
					ammoBoxes[i].destroyInstance(); //get rid of the ammo box after collecting ammo
				}
			}
		}

		
		//Mouse Controls
		public void mouseMoved(MouseEvent e) {
			
			//Set player's location to mouse location
			player.setLocation(e.getX() - player.getWidth() / 2, e.getY() - player.getHeight() / 2);
			
			//Keep Player within window bounds
			if (player.getX() <= 0)
				player.setX(0);
			if (player.getX() + player.getWidth() >= getWidth())
				player.setX(getWidth() - player.getWidth());
			if (player.getY() <= 0)
				player.setY(0);
			if (player.getY() + player.getHeight() >= getHeight())
				player.setY(getHeight() - player.getHeight());

		}

		public void mouseClicked(MouseEvent e) {/*Do nothing*/}
		public void mouseDragged(MouseEvent arg0) {/*Do nothing*/}
		public void mouseEntered(MouseEvent e) {/*Do nothing*/}
		public void mouseExited(MouseEvent e) {/*Do nothing*/}
		public void mousePressed(MouseEvent e) {/*Do nothing*/}
		public void mouseReleased(MouseEvent e) {/*Do nothing*/}
		
		
		//Weapons stuff
		
		public void shoot()
		{
			if(currentWeapon == 0) //Fist
			{
				int xOffset;
				int yOffset;
				if(player.getDirection() == Player.EAST)//Player is facing east
				{
					for(int i = 0; i < pinkies.length; i++) //Check all pinkies
					{
						//Establish Punching range
						xOffset = pinkies[i].getX() - (player.getX() + player.getWidth());
						yOffset = player.getY() - pinkies[i].getY();
						if(xOffset < 40 && xOffset > 0 && yOffset < pinkies[i].getHeight()-20 && yOffset > -10)//Within punching range
						{
							killPinkySequence(i);//Kill pinky
						}
					}
				}
				else//Player is facing west
				{
					for(int i = 0; i < pinkies.length; i++) //Check all pinkies
					{
						//Establish Punching range
						xOffset = player.getX()- (pinkies[i].getX() + pinkies[i].getWidth());
						yOffset = player.getY() - pinkies[i].getY();
						if(xOffset < 40 && xOffset > 0 && yOffset < pinkies[i].getHeight()-20 &&yOffset > -10)//Check if pinky is within punching range
						{
							killPinkySequence(i);//Kill pinky
						}
					}
				}
			}
			
			else if(currentWeapon == 1&& ammoQuantities[1] > 0) //Pistol
			{
				if (b.isFired() == false ) //Don't let player shoot if bullet is already onscreen (anti-spam)
				{
					b.setPosition(player.getX(), player.getY(), player.getDirection());//create a new pistol bullet
					ammoQuantities[1] -= 1;//Consume 1 pistol ammo
					bulletTimer.start();//Allows program to start checking whether bullet has collided with Pinky.
				}
			}
			
			else if(currentWeapon == 2 && ammoQuantities[2] > 0) //Shotgun
			{
				shotgunSpray = new Polygon(); //Reset shot AOE polygon, just in case.
				
				//Setup for adding the points of the shot's area of effect (AOE)
				int shotX;
				int shotY = player.getY() + 30;
				
				if(player.getDirection() == Player.EAST) //Player is facing east
				{
					shotX = player.getX() + player.getWidth() + 40;
					shotgunSpray.addPoint(shotX, shotY); //adds point at barrel of shotgun
					shotgunSpray.addPoint(getWidth(), shotY + 20);//Adds upper point on left wall
					shotgunSpray.addPoint(getWidth(), shotY - 20);//Adds lower point on left wall
				}
				else//Player is facing West
				{
					shotX = player.getX() - 40;
					shotgunSpray.addPoint(shotX, shotY);//adds point at barrel of shotgun
					shotgunSpray.addPoint(-1, shotY + 20);//Adds upper point on left wall
					shotgunSpray.addPoint(-1, shotY - 20);//Adds lower point on left wall
				}
				for(int i = 0; i < pinkies.length; i++)
				{
					if(shotgunSpray.intersects(pinkies[i].getRect()))//Checks if pinkies are inside AOE
							killPinkySequence(i); //Kill pinkies inside AOE
				}
				ammoQuantities[2] -= 1;//Consume 1 shotgun ammo
				 
			}
			
			//Rocket Launcher
			else if(currentWeapon == 3 && ammoQuantities[3] > 0)
			{
				if (rocket.isFired() == false)//Don't let player shoot if rocket is already onscreen (anti-spam)
				{
					rocket.setPosition(player.getX(), player.getY(), player.getDirection());//Fire rocket
					rocket.setSpeed(20);
					ammoQuantities[3] --;//Consume 1 RPG ammo
					bulletTimer.start();//Allows program to start checking whether rocket has collided with Pinky.
				}
			}
			
			//BFG 9000
			else if(currentWeapon == 4 && ammoQuantities[4] > 0) {
				
				if(bfgExplosion.exists() == false)//Don't let player shoot if explosion is already occurring (anti-spam).
				{
					bfgExplosion = new Explosion(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, 7, "images\\Explosions\\bfgExplosion");//Make an explosion.
					bfgExplosion.setFrameSpeed(8);
				}
				ammoQuantities[4]--;//Consume 1 BFG ammo.
			}
			else//Out of ammo for current weapon
			{
				while(ammoQuantities[currentWeapon] == 0)
					switchDown();
			shoot();
			}
			
		}
		
		//Switches weapons up, wraps to first weapon if holding last weapon
		public void switchUp()
		{
			if(currentWeapon <4)
				currentWeapon ++;
			else
				currentWeapon = 0;
		}
		
		//Switches weapons down, wraps to last weapon if holding first weapon
		public void switchDown() {
			if(currentWeapon > 0)
				currentWeapon--;
			else
				currentWeapon = 4;
		}
		
		
		
		//Kills enemy, animates death, adds to score, drops ammo, respawns enemy. This is what happens whenever you kill an enemy.
		public void killPinkySequence(int i) 
		{
			//Kill the pinky, add  to player kill counter
			pinkies[i].kill();
			player.addKills(1);
			
			
			//Start death animation, choose based on direction
			if(player.getDirection() == Player.EAST)
				deadPinkies[i] = new Explosion(pinkies[i].getX() + pinkies[i].getWidth()/2, pinkies[i].getY()+ pinkies[i].getHeight()/2, 4, "images\\Explosions\\pinkyExplosionEast");//Pinky death animation for direction East 
			else
				deadPinkies[i] = new Explosion(pinkies[i].getX()+ pinkies[i].getWidth()/2, pinkies[i].getY()+ pinkies[i].getHeight()/2, 4, "images\\Explosions\\pinkyExplosionWest");//Pinky death animation for direction West
			deadPinkies[i].setFrameSpeed(7);
			
			
			//See if max ammo type needs to be increased
			if (player.getKills() == 20||player.getKills() == 40||player.getKills() == 100)
				maxAmmoType ++;//unlock Shotgun at 20 kills||unlock RPG at 40 kills ||Unlock BFG 9000 at 100 kills
			
			//Spawns ammo randomly. Kills with BFG will NOT drop any ammo.
			if(rnd.nextInt(3) == 0 && currentWeapon !=4)
			{
				for(int j = 0; j<ammoBoxes.length; j++)//Finds a place in array to hold ammobox obj.
				{
					if(ammoBoxes[j].exists() == false)
					{
						ammoBoxes[j] = new Ammo(pinkies[i].getX()+pinkies[i].getWidth()/2 - 6, pinkies[i].getY()+pinkies[i].getHeight() - 30, maxAmmoType); //Centered on pinky's feet
						if (ammoBoxes[j].isOffScreen(getWidth())) //Don't let ammo spawn offscreen.
						{
							ammoBoxes[j].destroyInstance();
						}
						break;
					}
				}

			}
			
			//Respawn Pinky
			pinkies[i] = new Pinky();
			pinkies[i].setLocation(getWidth(), getHeight());
		}
	}