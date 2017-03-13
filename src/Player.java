import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Player {

	Side side;
	public int width = 32;
	public int height = 32;
	
	public Player() {
		this.side = Side.LEFT;
	}
	
	public Rectangle getRectangle() {
		Rectangle rect = new Rectangle(getX(), getY(), width, height);
		return rect;
	}
	
	public int getX() {
		return side.getX();
	}
	
	public int getY() {
		return side.getY();
	}
	
	public void updateSide(Side newSide) {
		this.side = newSide;
	}
	
	public Side getSide() {
		return side;
	}
	
	public void draw(Graphics g) {	
		
		/* Drawing a wide rectangle behind the block so you can see the height better
		 
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, getY(), Main.WINDOW_WIDTH, height);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		*/
		
		g.setColor(Color.BLACK);
		g.fillRect(getX(), getY(), width, height);	
	}

}
