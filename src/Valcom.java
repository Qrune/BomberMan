import java.awt.geom.Point2D;
import java.util.Random;

public class Valcom extends Monster {

	private GameWorld world;
	private Point2D centerPoint;
	private int speed;
	private int direction;
	private static final int POINT = 100;
	private Random r = new Random();

	public Valcom(GameWorld world, Point2D centerPoint) {
		super(world, centerPoint);
		this.world = world;
		this.centerPoint = centerPoint;
		this.speed = 1;
		this.direction = this.r.nextInt(4); // direction:0=up,1=down,2=left,3=right
	}

	@Override
	public void moveTo(Point2D point) {
		super.moveTo(point);
		IntPoint newPoint = Decoder.pixelToArrayAdress(point, GameWorldComponent.GRID_SIZE);
		this.world.setMapPoint(newPoint.getX(), newPoint.getY(), 1);
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
