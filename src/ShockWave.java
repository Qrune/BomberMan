import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ShockWave extends BreakableBrick {
	protected GameWorld world;
	protected Point2D centerPoint;
	private int TimeCounter;

	public ShockWave(GameWorld newWorld, Point2D newCenterPoint) {
		super(newWorld, newCenterPoint);
		this.world = newWorld;
		this.centerPoint = newCenterPoint;
		ArrayList<Temporal> tempList = new ArrayList<>();
		for (Temporal cur : this.world.getObjects()) {
			if (cur.getCenterPoint().distance(this.centerPoint) < Math.sqrt(2 * 24 * 24)) {
				if (!classNameCheck(cur, "Bomb") && !classNameCheck(cur, "RangePowerUp")
						&& !classNameCheck(cur, "SpeedPowerUp") && !classNameCheck(cur, "BombPowerUp")
						&& (!tempList.contains(cur))) {
					cur.die();
				}
			}
		}
	}

	boolean classNameCheck(Temporal obj, String nameToCheck) {
		return obj.getClass().getSimpleName().toLowerCase().equals(nameToCheck.toLowerCase());
	}

	@Override
	public String getImageName() {
		return "res/img/fire.png";
	}

	@Override
	public void timePassed() {

		this.TimeCounter++;
		if (this.TimeCounter == 75) {
			this.die();
		}
		for (Temporal cur : this.world.getObjects()) {
			if (cur.getCenterPoint().distance(this.centerPoint) < Math.sqrt(2 * 24 * 24)) {
				if (!(cur instanceof Item || cur instanceof ShockWave)) {
//					System.out.println(cur.getClass().getSimpleName());
					cur.die();
				}
			}
		}
	}

	@Override
	public void die() {
		this.world.removeObject(this);

	}

	@Override
	public String toString() {
		return this.getClass().getName()
				+ Decoder.pixelToArrayAdress(this.centerPoint, 48).toString().replace("IntPoint", " ");
	}

}
