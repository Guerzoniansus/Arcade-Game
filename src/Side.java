
public enum Side {
	
	LEFT(130, "left"),
	RIGHT(370 - 32, "right"),
	LEFT_OBSTACLE(0, "left"),
	RIGHT_OBSTACLE(Main.WINDOW_WIDTH, "right");
	
	private int x;
	private String name;
	
	Side(int x, String name) {
		this.x = x;
		this.name = name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return 180;
	}
	
	public String getName() {
		return name;
	}

}
