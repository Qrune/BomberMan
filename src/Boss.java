import java.awt.geom.Point2D;
import java.util.Random;

public class Boss extends Monster {
	private int bossLevel;
	private static final int POINT = 300;
	protected int bossLife = 11;
	protected final static int BOSS_MAX_LIFE = 11;
	private GameWorld world;
	private Point2D centerPoint;
	private IntPoint intCenterPoint;
	private boolean wudi1 = false;
	private int wudicount1 = 0;
	private BomberMan man = null;
	private Random r = new Random();
	private int speed;
	private int direction;

	public Boss(GameWorld world, Point2D newCenterPoint, BomberMan man) {
		this(world, newCenterPoint, 3, false, man);
	}

	public Boss(GameWorld world, Point2D newCenterPoint, int newBossLevel, boolean invicible, BomberMan man) {
		super(world, newCenterPoint);
		this.bossLevel = newBossLevel;
		this.world = world;
		this.centerPoint = newCenterPoint;
		this.intCenterPoint = Decoder.pixelToArrayAdress(newCenterPoint, GameWorldComponent.GRID_SIZE);
		this.wudi1 = invicible;
		this.man = man;
	}

	@Override
	public void timePassed() {
		if (this.wudi1) {
			this.wudicount1++;
			if (this.wudicount1 > 150) {
				this.wudi1 = false;
				this.wudicount1 = 0;
			}

		}
		if (super.isPausled) {
			return;
		}
		move();
		for (Temporal cur : this.world.getObjects()) {
			if (cur.getCenterPoint().distance(this.getCenterPoint()) < Math.sqrt(2 * 24 * 24)) {
				if ((cur.getClass().getSimpleName().equals("BomberMan"))) {
					System.out.println(cur.getClass().getSimpleName());
					cur.die();
				}
			}
		}
	}
	@Override
	public void moveTo(Point2D point) {
		super.moveTo(point);
		IntPoint newPoint = Decoder.pixelToArrayAdress(point, GameWorldComponent.GRID_SIZE);
		this.world.setMapPoint(newPoint.getX(), newPoint.getY(), 6-this.bossLevel);
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
		if (Math.abs(ManPoint.getX() - this.centerPoint.getX()) < GameWorldComponent.GRID_SIZE * 2
				&& Math.abs(ManPoint.getY() - this.centerPoint.getY()) < GameWorldComponent.GRID_SIZE * 2)
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
		return POINT * this.bossLevel;
	}

	@Override
	public String getImageName() {
		String name = this.getClass().getSimpleName().toLowerCase();
		return "res/img/" + name + this.bossLevel + ".png";
	}

	@Override
	public void die() {
		if (!this.wudi1) {
			this.world.addPoints(getPoints());
			this.world.removeObject(this);
			this.world.setMapPoint(this.intCenterPoint.getX(), this.intCenterPoint.getY(), 0);
			if (this.bossLevel > 1) {
				for (int i = 0; i < 2; i++) {
				Boss son = new Boss(this.world, this.centerPoint, this.bossLevel - 1, true, this.man);
				this.world.addObject(son);
				}
			}
		}
	}
}
