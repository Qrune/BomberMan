import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Bomb extends BreakableBrick {
	private Point2D centerPoint;
	private GameWorld world;
	private int explosionRadius;
	private IntPoint intCenterPoint;
	private int TimeCounter;
	private BomberMan owner;

	public Bomb(GameWorld newWorld, int newExplosionRadius, Point2D newCenterPoint, BomberMan owner) {
		super(newWorld, newCenterPoint);
		this.world = newWorld;
		this.explosionRadius = newExplosionRadius;
		this.centerPoint = newCenterPoint;
		this.owner = owner;
		this.intCenterPoint = Decoder.pixelToArrayAdress(newCenterPoint, GameWorldComponent.GRID_SIZE);
		this.world.addObject(this);
	}

	@Override
	public void timePassed() {
		// if(this.isPaused){
		// return;
		// }
		this.TimeCounter++;
		if (this.TimeCounter == 500) {
			this.die();
		}
	}

	@Override
	public void die() {
		// this.world.getMap().getField()[this.intCenterPoint.getX()][this.intCenterPoint.getY()]
		// = 0;
		this.world.setMapPoint(this.intCenterPoint.getX(), this.intCenterPoint.getY(), 0);
		for (IntPoint cur : this.toExplode()) {
			this.world.addObject(new ShockWave(this.world, Decoder.arrayAdressToPixel(cur.getX(), cur.getY(), 48)));
		}
		this.world.removeObject(this);
		this.owner.removeBomb();
	}

	@Override
	public String getImageName() {
		return "res/img/bomb.png";
	}

	@Override
	public Point2D getCenterPoint() {
		return this.centerPoint;
	}

	public ArrayList<IntPoint> toExplode() {
		ArrayList<IntPoint> potentialShockWave = new ArrayList<IntPoint>();
		IntPoint decodedCenter = Decoder.pixelToArrayAdress(this.centerPoint, 48);
		int[][] cField = this.world.getMap().getField();
		for (int i = 1; i <= this.explosionRadius; i++) {
			boolean upCheck = (decodedCenter.getX()) > 0 && (decodedCenter.getX()) < 26
					&& (decodedCenter.getY() - i) > 0 && (decodedCenter.getY() - i) < 13;
			if (cField[decodedCenter.getX()][decodedCenter.getY() - i] == -1) {
				break;
			}
			if (cField[decodedCenter.getX()][decodedCenter.getY() - i] == -2) {
				potentialShockWave.add(new IntPoint(decodedCenter.getX(), decodedCenter.getY() - i));
				break;
			}

			if (upCheck) {
				potentialShockWave.add(new IntPoint(decodedCenter.getX(), decodedCenter.getY() - i));
			}

		}
		for (int i = 0; i <= this.explosionRadius; i++) {
			boolean downCheck = (decodedCenter.getX()) > 0 && (decodedCenter.getX()) < 26
					&& (decodedCenter.getY() + i) > 0 && (decodedCenter.getY() + i) < 13;
			if (cField[decodedCenter.getX()][decodedCenter.getY() + i] == -1) {
				break;
			}
			if (cField[decodedCenter.getX()][decodedCenter.getY() + i] == -2) {
				potentialShockWave.add(new IntPoint(decodedCenter.getX(), decodedCenter.getY() + i));
				break;
			}
			if (downCheck) {
				potentialShockWave.add(new IntPoint(decodedCenter.getX(), decodedCenter.getY() + i));
			}
		}
		for (int i = 1; i <= this.explosionRadius; i++) {
			boolean leftCheck = (decodedCenter.getX() - i) > 0 && (decodedCenter.getX() - i) < 26
					&& (decodedCenter.getY()) > 0 && (decodedCenter.getY()) < 13;
			if (cField[decodedCenter.getX() - i][decodedCenter.getY()] == -1) {
				break;
			}
			if (cField[decodedCenter.getX() - i][decodedCenter.getY()] == -2) {
				potentialShockWave.add(new IntPoint(decodedCenter.getX() - i, decodedCenter.getY()));
				break;
			}

			if (leftCheck) {
				potentialShockWave.add(new IntPoint(decodedCenter.getX() - i, decodedCenter.getY()));
			}
		}
		for (int i = 1; i <= this.explosionRadius; i++) {
			boolean rightCheck = (decodedCenter.getX() + i) > 0 && (decodedCenter.getX() + i) < 26
					&& (decodedCenter.getY()) > 0 && (decodedCenter.getY()) < 13;
			if (cField[decodedCenter.getX() + i][decodedCenter.getY()] == -1) {
				break;
			}
			if (cField[decodedCenter.getX() + i][decodedCenter.getY()] == -2) {
				potentialShockWave.add(new IntPoint(decodedCenter.getX() + i, decodedCenter.getY()));
				break;
			}
			if (rightCheck) {
				potentialShockWave.add(new IntPoint(decodedCenter.getX() + i, decodedCenter.getY()));
			}
		}

		return potentialShockWave;
	}

	@Override
	public String toString() {
		return this.getClass().getName()
				+ Decoder.pixelToArrayAdress(this.centerPoint, 48).toString().replace("IntPoint", " ");
	}
}