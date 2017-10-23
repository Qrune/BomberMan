import java.awt.geom.Point2D;
import java.util.Random;

public class BreakableBrick extends Brick {
	private IntPoint intCenterPoint;
	private Random r = new Random();

	public BreakableBrick(GameWorld world, Point2D centerPoint) {
		super(world, centerPoint);
		this.intCenterPoint = Decoder.pixelToArrayAdress(centerPoint, GameWorldComponent.GRID_SIZE);

	}

	@Override
	public String getImageName() {
		return "res/img/breakbrick.png";
	}

	@Override
	public void die() {
		super.getWorld().removeObject(this);
		super.getWorld().setMapPoint(this.intCenterPoint.getX(), this.intCenterPoint.getY(), 0);
		if (this.r.nextInt(3) == 1) {
			// System.out.print("Pass ");
			int det = this.r.nextInt(5);
			if (det == 0 || det == 1) {
				// System.out.println("Range PowerUp");
				RangePowerUp rangePowerUp = new RangePowerUp(super.getWorld(), super.getCenterPoint());
				super.getWorld().addObject(rangePowerUp);
			} else if (det == 2 || det == 3) {
				// System.out.println("Speed PowerUp");
				SpeedPowerUp speedPowerUp = new SpeedPowerUp(super.getWorld(), super.getCenterPoint());
				super.getWorld().addObject(speedPowerUp);
			} else if (det == 4) {
				// System.out.println("Bomb PowerUp");
				BombPowerUp bombPowerUp = new BombPowerUp(super.getWorld(), super.getCenterPoint());
				super.getWorld().addObject(bombPowerUp);
			}
		}

	}

	@Override
	public void timePassed() {
		//
	}

	@Override
	public String toString() {
		return this.getClass().getName()
				+ Decoder.pixelToArrayAdress(super.getCenterPoint(), 48).toString().replace("IntPoint", " ");
	}

}