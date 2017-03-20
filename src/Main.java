import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;


public class Main extends JFrame implements KeyListener {

	private static JFrame frame;
	public static final int WINDOW_WIDTH = 500;
	public static final int WINDOW_HEIGHT = 900;
	
	public static Player player;
	public static Timer timer;
	
	public boolean isPlaying = true;
	
	public static int currentSpeed;
	public static final int STARTING_SPEED = 5;
	public static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	public static int currentTime;
	public static final int STARTING_TIME = 1000;
	
	static int score;
	static int highscore;
	static boolean isAlive;
	
	public static Timer obstacleTimer;
	public static TimerTask obstacleTimerTask;
	
	
	public static void main(String[] args) {
		player = new Player();
		frame = new Main();
		setupWindow("Arcade game", WINDOW_WIDTH, WINDOW_HEIGHT);
			
		setupGame();
		setupTimers();
		gameLoop();
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		
		Graphics offgc;
	    Image offscreen = null;
	    Dimension d = size();
	    
	    offscreen = createImage(d.width, d.height);
	    offgc = offscreen.getGraphics();
	    
	    offgc.setColor(getBackground());
	    offgc.fillRect(0, 0, d.width, d.height);
	    offgc.setColor(getForeground());
	    
		player.draw(offgc);
			
		for (Obstacle obstacle : obstacles) {
			if (obstacle != null) {
				obstacle.draw(offgc);
			}
		}
		
		drawScore(offgc);
		
		if (isAlive == false) {
			
			drawDeathScreen(offgc);
			
		}
		
		g.drawImage(offscreen, 0, 0, this);
		
	}
	
	public static void setupTimers() {
		
		/*
		 *  Was just trying different things, I wasn't sure what the best way to do it was 
		 *  and this code is a bit unfinished
		 *  
		 *  There needs to be a timer that runs every second incrementing score with one
		 *  
		 *  A timer that spawns obstacles every second but spawns them faster over time
		 *  
		 *  A timer that runs and increases the speed of the obstacles over time
		 *  
		 *  BUGS: I mostly faced the problem of how to make it so when I "restart" the game you
		 *  don't end up with a new instance of the timer while the same timers from last game are still running
		 *  
		 */
		
		//obstacleTimer = new Timer();
		
		/*
		final TimerTask obstacleTimerTask = new TimerTask() {
			public void run() {
		    	if (isAlive) {
		    		
		    		Obstacle obstacle = new Obstacle(currentSpeed);
					obstacles.add(obstacle);
					
					obstacleTimer.cancel();
					obstacleTimer.schedule(obstacleTimerTask, currentTime);
					
		    	} else {
		    		
		    	}
		    }
		};
		*/
		
		obstacleTimer = new Timer();
		
		/*
		obstacleTimerTask = new TimerTask() {
			public void run() {
		    	if (isAlive) {		    		
		    		
		    		Obstacle obstacle = new Obstacle(currentSpeed);
					obstacles.add(obstacle);
					
					changeSpeed();
					
		    	} else {
		    		
		    	}
		    }
		};
		*/
		
		obstacleTimer.schedule(new ObstacleTimerTask(), 0, 1 * currentTime);
		
		/*
		obstacleTimer.schedule(new TimerTask() {
		    public void run() {
		    	if (isAlive) {		    		
		    		
		    		Obstacle obstacle = new Obstacle(currentSpeed);
					obstacles.add(obstacle);
					
					changeSpeed();
					
		    	} else {
		    		
		    	}
		    }
		    
		 }, 0, 2 * currentTime);
		 */
		
		
		Timer timerScore = new Timer();
		timerScore.schedule(new TimerTask() {
		    public void run() {
		    	if (isAlive) {
		    		score++;
		    		changeSpeed();
		    	} else {
		    		
		    	}
		    }
		 }, 0, 1000);
		
	}
	
	public static void changeSpeed() {
		
		
		//obstacleTimer = new Timer();
		
		Random rand = new Random();
		
		int x = rand.nextInt(2);
		
		if (x == 0) {
			currentSpeed++;
		}
		
		for (Obstacle obstacle : obstacles) {
			if (obstacle != null) {			
				obstacle.setSpeed(currentSpeed);
			}
		}
		
		
		//obstacleTimer.schedule(new ObstacleTimerTask(), 0, 2 * currentTime);
	}
	
	static class ObstacleTimerTask extends TimerTask {
		public void run() {
	    	if (isAlive) {		    		
	    		
	    		Obstacle obstacle = new Obstacle(currentSpeed);
				obstacles.add(obstacle);
				
				changeSpeed();
				
	    	} else {
	    		
	    	}
	    }
	}
	
	
	public static void setupGame() {
		
		//frame.getGraphics().fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		System.out.println("creating new game");
		
		obstacles.clear();
		
		score = 0;
		isAlive = true;
		currentSpeed = STARTING_SPEED;
		currentTime = STARTING_TIME;
	}
	
	
	public static void gameLoop() {
		while(true) {
			
			if (isAlive) {
				if (isColliding()) {
					
					if (score > highscore) {
						highscore = score;
					}
					
					isAlive = false;
				}
			}
			
			if (isAlive) {
				
				for (int i = 0; i < obstacles.size(); i++) {
					if (obstacles.get(i) != null) {
						
						if (obstacles.get(i).getY() < 0) {
							obstacles.remove(obstacles.get(i));
						}
						
						else {
							obstacles.get(i).move();
						}
						
					}
				}			
			}
			
			else if (isAlive == false) {
				
			}
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			frame.repaint();
		}
	}
	
	public void restartGame() {
		
		if (score > highscore) {
			highscore = score;
		}
		
		score = 0;
		
		setupGame();
	}
	
	public void drawDeathScreen(Graphics g) {
		
		// Draw rectangular message box saying press space to restart
		
		int boxWidth = 300;
		int boxHeight = 300;
		int boxX = (WINDOW_WIDTH / 2) - (boxWidth / 2);
		int boxY = (WINDOW_HEIGHT / 2) - (boxHeight / 2);
			
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(boxX, boxY, boxWidth, boxHeight);
		g.setColor(Color.BLACK);
		g.drawRect(boxX, boxY, boxWidth, boxHeight);
		
		g.setColor(Color.BLACK);
		drawCenteredString(g, "Highscore: " + highscore, boxY + (boxHeight / 2) - 50);
		drawCenteredString(g, "Score: " + score, boxY + (boxHeight / 2) - 20);
		drawCenteredString(g, "Press space to play again", boxY + (boxHeight / 2) + 70);
		
	}
	
	public static boolean isColliding() {
		for (int i = 0; i < obstacles.size(); i++) {	
			if (obstacles.get(i).getRectangle().intersects(player.getRectangle())) {
				return true;
			}		
		}
		
		return false;
	}
	
	public void drawScore(Graphics g) {
		
		// Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(g.getFont());
	    // Determine the X coordinate for the text
	    int x = (new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT).width - metrics.stringWidth(Integer.toString(score))) / 2;
	    g.setFont(g.getFont());
	    g.setColor(Color.BLACK);
	    g.drawString(Integer.toString(score), x, 100);
		
	}
	
	public void drawCenteredString(Graphics g, String text, int y) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(g.getFont());
	    // Determine the X coordinate for the text
	    int x = (new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT).width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    //int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(g.getFont());
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	private static void setupWindow(String title, int width, int height) {
		frame.setTitle(title);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setLocationRelativeTo(null);
		frame.addKeyListener((KeyListener) frame);
		//Graphics g = frame.getGraphics();
		//g.drawRect(player.getX(), player.getY(), 64, 64);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
		if (isAlive) {
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT && player.getSide() != Side.LEFT) {
				player.updateSide(Side.LEFT);
				//System.out.println(player.getSide().getName());
			}
			
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT && player.getSide() != Side.RIGHT) {
				player.updateSide(Side.RIGHT);
				//System.out.println(player.getSide().getName());
			}
			
		}
		
		else if (isAlive == false) {
			
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				restartGame();
			}
			
		}
			
		
		repaint();
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
