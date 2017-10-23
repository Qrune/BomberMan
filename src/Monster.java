import java.awt.geom.Point2D;

public abstract class Monster implements Temporal, Reloctable {

	private GameWorld world;
	private Point2D centerePoint;
	private IntPoint intCenterPoint;
	protected boolean isPausled;

	public Monster(GameWorld world, Point2D centerPoint) {
		this.world = world;
		this.centerePoint = centerPoint;
		this.intCenterPoint = Decoder.pixelToArrayAdress(centerPoint, GameWorldComponent.GRID_SIZE);
	}
	
	@Override
	public void timePassed() {

		if (this.isPausled) {
			return;
		}
		move();
		for (Temporal cur : this.world.getObjects()) {
			if (cur.getCenterPoint().distance(this.getCenterPoint()) < Math.sqrt(2 * 24 * 24)) {
				if ((cur.getClass().getSimpleName().equals("BomberMan"))) {
//					System.out.println(cur.getClass().getSimpleName());
					cur.die();
				}
			}
		}
	}

	@Override
	public Point2D getCenterPoint() {
		return this.centerePoint;
	}

	public abstract void move();

	@Override
	public void moveTo(Point2D point) {
		IntPoint newPoint = Decoder.pixelToArrayAdress(point, GameWorldComponent.GRID_SIZE);
		this.centerePoint = point;
		this.world.setMapPoint(this.intCenterPoint.getX(), this.intCenterPoint.getY(), 0);
		this.intCenterPoint = newPoint;
	}

	@Override
	public String getImageName() {
		String name = this.getClass().getSimpleName().toLowerCase();
		return "res/img/" + name + ".png";
	}

	@Override
	public void die() {
		this.world.addPoints(getPoints());
		this.world.removeObject(this);
		this.world.setMapPoint(this.intCenterPoint.getX(), this.intCenterPoint.getY(), 0);

	}

	public abstract int getPoints();

	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+ Decoder.pixelToArrayAdress(this.getCenterPoint(), 48).toString().replace("IntPoint", "");
	}

}