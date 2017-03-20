import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.TimerTask;


public class Obstacle {

	Side side;
	
	private int width = (int) Main.WINDOW_WIDTH / 2;
	private int height = 32;
	private int x;
	private int y;
	
	// Speed = amount of pixels traveled per frame
	private int speed;
	
	public Obstacle(int speed) {
		this.side = createRandomSide();
		
		if (side == Side.LEFT_OBSTACLE) {
			x = 0;
		} 
		
		else if (side == Side.RIGHT_OBSTACLE) {
			x = Main.WINDOW_WIDTH - width;		
		}
		
		this.y = Main.WINDOW_HEIGHT;
		this.speed = speed;
	}
	
	public Rectangle getRectangle() {
		Rectangle rect = new Rectangle(getX(), getY(), width, height);
		return rect;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setSpeed(int newSpeed) {
		this.speed = newSpeed;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void move() {
		y = y - speed;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(getX(), getY(), width, height);
	}
	
	private Side createRandomSide() {
		Random rand = new Random();
		int n = rand.nextInt(2);
		
		Side[] sides = new Side[] {Side.LEFT_OBSTACLE, Side.RIGHT_OBSTACLE};
		
		return sides[n];
	}
	
}
