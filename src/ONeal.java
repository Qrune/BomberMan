import java.awt.geom.Point2D;
import java.util.Random;

public class ONeal extends Monster {

	private GameWorld world;
	private Point2D centerPoint;
	private int speed;
	private int direction;
	private static final int POINT = 200;
	private Random r = new Random();
	private BomberMan man = null;

	public ONeal(GameWorld world, Point2D centerPoint, BomberMan man) {
		super(world, centerPoint);
		this.man = man;
		this.world = world;
		this.centerPoint = centerPoint;
		this.speed = 1;
		this.direction = this.r.nextInt(2); // direction=0: horizontal,
											// =1:vertical
	}

	@Override
	public void moveTo(Point2D point) {
		super.moveTo(point);
		IntPoint newPoint = Decoder.pixelToArrayAdress(point, GameWorldComponent.GRID_SIZE);
		this.world.setMapPoint(newPoint.getX(), newPoint.getY(), 2);
	}

	@Override
	public void move() {
		if (this.r.nextInt(40) == 2) {
			this.direction = this.r.nextInt(4);
		}
		double newX = this.centerPoint.getX();
		double newY = this.centerPoint.getY();
		Point2D newPoint;
		String mapDirection = "";
		Point2D ManPoint = this.man.getCenterPoint();
		if (Math.abs(ManPoint.getX()-this.centerPoint.getX())<GameWorldComponent.GRID_SIZE*2 && Math.abs(ManPoint.getY()-this.centerPoint.getY())<GameWorldComponent.GRID_SIZE*2)
			this.speed = 3;
		else
			this.speed = 1;
		switch (this.direction) {
		case 0:// up
			newY -= this.speed;
			mapDirection = "u";
			break;
		case 1:// down
			newY += this.speed;
			mapDirection = "d";
			break;
		case 2:// left
			newX -= this.speed;
			mapDirection = "l";
			break;
		default:// right
			newX += this.speed;
			mapDirection = "r";
			break;
		}
		newPoint = new Point2D.Double(newX, newY);
		if (!this.world.isPassable(this.centerPoint, mapDirection)) {
			switch (this.direction) {
			case 0:
				this.direction = 1;
				break;
			case 1:
				this.direction = 0;
				break;
			case 2:
				this.direction = 3;
				break;
			default:
				this.direction = 2;
				break;
			}
			return;
		}
		this.centerPoint = newPoint;
		moveTo(newPoint);
	}

	@Override
	public int getPoints() {
		return POINT;
	}

}
