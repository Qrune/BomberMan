import java.awt.geom.Point2D;

public abstract class Brick implements Temporal {
	private Point2D centerPoint;
	private GameWorld world;

	public Brick(GameWorld world) {
		this(world, new Point2D.Double());

	}

	public Brick(GameWorld world, Point2D centerPoint) {
		this.world = world;
		this.centerPoint = centerPoint;
	}

	protected void setCenterPoint(Point2D centerPoint) {
		this.centerPoint = centerPoint;
	}

	@Override
	public Point2D getCenterPoint() {
		return this.centerPoint;
	}

	protected GameWorld getWorld() {
		return this.world;
	}

	// Drawable Interface
	@Override
	public abstract String getImageName();

	// Temporal Interface
	@Override
	public abstract void timePassed();

	@Override
	public abstract void die();

}