import java.awt.geom.Point2D;

public class BomberMan implements Reloctable, Temporal {
	public int ONE_STEP;
	private Point2D centerPoint;
	protected GameWorld world;
	private String direction;
	private int expRadius;
	boolean state = true;
	private boolean wudi = false;
	private int wudicount = 0;
	private int bombNumber;
	private int curBombNum = 0;
	private int num;

	public BomberMan(GameWorld world, int num) {
		this.world = world;
		this.num = num;
		if (num == 1) {
			this.centerPoint = new Point2D.Double(72, 72);
		} else {
			this.centerPoint = new Point2D.Double(72, 552);
		}
		this.direction = "d";
		this.expRadius = 2;
		this.bombNumber = 1;
		this.ONE_STEP = 8;
	}

	public int getNum() {
		return this.num;
	}

	public boolean addBomb() {
		if (this.curBombNum < this.bombNumber) {
			this.curBombNum++;
			return true;
		}
		return false;
	}

	public void removeBomb() {
		this.curBombNum--;
	}

	public void addBombNumber() {
		this.bombNumber++;
	}

	public int getONE_STEP() {
		return this.ONE_STEP;
	}

	public int getBombNumber() {
		return this.bombNumber;
	}

	public void setONE_STEP(int oNE_STEP) {
		this.ONE_STEP = oNE_STEP;
	}

	public void setBombNumber(int bombNumber) {
		this.bombNumber = bombNumber;
	}

	public void addSpeed() {
		this.ONE_STEP = this.ONE_STEP + 1;
	}

	public int getExpRadius() {
		return this.expRadius;
	}

	public void addExpRadius() {
		this.expRadius++;
	}

	public void setCenterPoint(Point2D centerPoint) {
		this.centerPoint = centerPoint;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return this.direction;
	}

	@Override
	public void moveTo(Point2D point) {
		setCenterPoint(point);
	}

	@Override
	public Point2D getCenterPoint() {
		return this.centerPoint;
	}

	@Override
	public String getImageName() {
		if (this.num == 1) {
			if (!this.state) {
				return "res/img/play_o.png";
			}
			return "res/img/play_" + this.direction + ".png";
		}
		if (!this.state) {
			return "res/img/play2_o.png";
		}
		return "res/img/play2_" + this.direction + ".png";
	}

	boolean classNameCheck(Temporal obj, String nameToCheck) {
		return obj.getClass().getName().toLowerCase().equals(nameToCheck.toLowerCase());
	}

	@Override
	public void timePassed() {
		// if(!this.isPalused){
		// return;
		// }
		if (this.wudi) {
			this.wudicount++;
			if (this.wudicount > 200) {
				this.wudi = false;
				this.wudicount = 0;
			}
		}

		for (Temporal cur : this.world.getObjects()) {
			if (cur instanceof PowerUp) {
				if (cur.getCenterPoint().distance(this.centerPoint) < 30) {
					if (classNameCheck(cur, "RangePowerUp") || classNameCheck(cur, "BombPowerUp")
							|| classNameCheck(cur, "SpeedPowerUp")) {
						System.out.println(cur.getClass().getSimpleName());

						cur.die();
						if (cur.getClass().getSimpleName().equals("RangePowerUp")) {
							this.addExpRadius();
						} else if (cur.getClass().getSimpleName().equals("SpeedPowerUp")) {
							this.addSpeed();
						} else {
							this.addBombNumber();
						}

					}
				}
			}
		}
	}

	@Override
	public void die() {
		// System.out.println("Bombman dead");
		if (this.world.getLabel().getLife() > 0) {
			if (this.state && !this.wudi) {
				this.world.getLabel().reduceLife();
				Runnable tickTock = new Runnable() {
					@Override
					public void run() {
						try {
							BomberMan.this.state = false;
							BomberMan.this.world.setIsPaused();
							Thread.sleep(1000);
							setCenterPoint(new Point2D.Double(72, 72));
							BomberMan.this.state = true;
							BomberMan.this.world.setIsPaused();
							BomberMan.this.wudi = true;
						} catch (InterruptedException exception) {
							// Stop when interrupted
						}
					}
				};
				new Thread(tickTock).start();
			}
		} else {

			try {
				BomberMan.this.state = false;
				Thread.sleep(1000);
				BomberMan.this.world.removeObject(this);
				BomberMan.this.world.addObject(new GameOver(BomberMan.this.world));
			} catch (InterruptedException exception) {
				// Stop when interrupted
			}

		}
	}

	public boolean getState() {
		return this.state;
	}

	@Override
	public String toString() {
		return this.getClass().getName()
				+ Decoder.pixelToArrayAdress(this.centerPoint, 48).toString().replace("IntPoint", " ");
	}
}
