public class IntPoint {
	private int x;
	private int y;
	
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	@Override
	public String toString() {
		return String.format("IntPoint(%d, %d)", this.x,this.y);
	}
	@Override
	public boolean equals(Object arg0) {
		return this.toString().equals(arg0.toString());
	}
}