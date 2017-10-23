import java.awt.geom.Point2D;

public abstract class Item implements Temporal {
	protected GameWorld world;
	protected Point2D centerPoint;

	public Item(GameWorld newWorld, Point2D newCenterPoint) {
		this.world = newWorld;
		this.centerPoint = newCenterPoint;
	}

	public GameWorld getWorld() {
		return this.world;
	}

	public void setWorld(GameWorld world) {
		this.world = world;
	}

	public void setCenterPoint(Point2D centerPoint) {
		this.centerPoint = centerPoint;
	}

	@Override
	public abstract void timePassed();

	@Override
	public abstract void die();

	@Override
	public abstract String getImageName();

	@Override
	public Point2D getCenterPoint() {
		return this.centerPoint;
	}

}
