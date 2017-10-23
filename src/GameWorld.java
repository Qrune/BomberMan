import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {
	protected static long UPDATE_INTERVAL_MS = 10;
	private boolean isPaused = false;
	public Runnable tickTock;
	private int level;
	private boolean winFlag = false;
	protected List<Temporal> objects = new ArrayList<>();
	private List<Temporal> toAddObjects = new ArrayList<>();
	private List<Temporal> toRemoveObjects = new ArrayList<>();
	protected int totalPoints;
	protected ArrayList<Map> myMaps;
	private GameWorldComponent component;
	private int timeLag = 1;
	private boolean gameOverFlag = false;
	private DisplayLabel label;

	public GameWorld(int level) {
		this.level = level;
		this.totalPoints = 0;
		try {
			this.myMaps = HandleFile.openFile();
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}

		this.tickTock = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(GameWorld.UPDATE_INTERVAL_MS);
						timePassed();
					}
				} catch (InterruptedException exception) {
					// Stop when interrupted
				}
			}
		};
		new Thread(this.tickTock).start();
		// this.timeLag=true;
	}

	public void setLabel(DisplayLabel label) {
		this.label = label;
	}

	public DisplayLabel getLabel() {
		return this.label;
	}

	public Map getMap() {
		return this.myMaps.get(this.level - 1);
	}

	public synchronized void addObject(Temporal object) {
		this.toAddObjects.add(object);
	}

	public synchronized void removeObject(Temporal object) {
		this.toRemoveObjects.add(object);
	}

	public List<Temporal> getToRemoveObjects() {
		return this.toRemoveObjects;
	}

	public synchronized void timePassed() {
		if (this.label != null) {
			this.label.timePassed();
			this.label.setScore(GameWorld.this.totalPoints);
		}
		if (this.label != null && this.label.getTime() < 0) {
			this.isPaused = true;

			int count = 0;
			for (Temporal t : this.objects) {
				if (t instanceof GameOver) {
					count++;
				}
				if (count == 0 && !this.gameOverFlag) {
					this.gameOverFlag = true;
					this.addObject(new GameOver(this));
					this.isPaused = true;
				}
			}
		}
		if (this.isPaused) {
			return;
		}
		if (this.getObjects().size() >= 1 && this.timeLag == 0) {
			if (this.checkVictory()) {
				if (this.level < this.myMaps.size()) {
					try {
						Thread.sleep(1000);
						this.levelUp();
					} catch (InterruptedException exception) {
						// Stop
					}

				} else if (!this.winFlag) {
					this.winFlag = true;
					this.addObject(new Victory(this));
					this.isPaused = true;
				}
			}
		}
		for (Temporal t : this.objects) {
			t.timePassed();
		}

		this.objects.removeAll(this.toRemoveObjects);
		this.toRemoveObjects.clear();
		this.objects.addAll(this.toAddObjects);
		this.toAddObjects.clear();
	}

	public void setIsPaused() {
		this.isPaused = !this.isPaused;

	}

	public boolean getIsPaused() {
		return this.isPaused;
	}

	public synchronized List<Temporal> getDrawableParts() {
		return new ArrayList<>(this.objects);
	}

	public boolean isPassable(Point2D currentPoint, String direction) {
		IntPoint currentIntPoint = Decoder.pixelToArrayAdress(currentPoint, GameWorldComponent.GRID_SIZE);
		int col = currentIntPoint.getX();
		int row = currentIntPoint.getY();
		Point2D leftPoint = Decoder.toLeftPoint(currentPoint);
		Point2D rightPoint = Decoder.toRightPoint(currentPoint);
		Point2D upPoint = Decoder.toUpPoint(currentPoint);
		Point2D downPoint = Decoder.toDownPoint(currentPoint);
		IntPoint leftIntPoint = Decoder.pixelToArrayAdress(leftPoint, GameWorldComponent.GRID_SIZE);
		IntPoint rightIntPoint = Decoder.pixelToArrayAdress(rightPoint, GameWorldComponent.GRID_SIZE);
		IntPoint upIntPoint = Decoder.pixelToArrayAdress(upPoint, GameWorldComponent.GRID_SIZE);
		IntPoint downIntPoint = Decoder.pixelToArrayAdress(downPoint, GameWorldComponent.GRID_SIZE);
		Map thisLevelMap = this.myMaps.get(this.level - 1);
		if (direction.equals("u")) {
			int left = thisLevelMap.getField()[leftIntPoint.getX()][leftIntPoint.getY() - 1];
			int right = thisLevelMap.getField()[rightIntPoint.getX()][rightIntPoint.getY() - 1];
			int cur = thisLevelMap.getField()[col][row - 1];
			if (cur < 0 || left < 0 || right < 0) {
				Point2D newPoint = Decoder.arrayAdressToPixel(col, row - 1, GameWorldComponent.GRID_SIZE);
				int distance = 45;
				return leftPoint.getY() - newPoint.getY() > distance && currentPoint.getY() - newPoint.getY() > distance
						&& rightPoint.getY() - newPoint.getY() > distance;
			}
			return true;
		}
		if (direction.equals("d")) {
			int left = thisLevelMap.getField()[leftIntPoint.getX()][leftIntPoint.getY() + 1];
			int right = thisLevelMap.getField()[rightIntPoint.getX()][rightIntPoint.getY() + 1];
			int cur = thisLevelMap.getField()[col][row + 1];
			if (cur < 0 || left < 0 || right < 0) {
				Point2D newPoint = Decoder.arrayAdressToPixel(col, row + 1, GameWorldComponent.GRID_SIZE);
				int distance = 46;
				return newPoint.getY() - currentPoint.getY() > distance && newPoint.getY() - leftPoint.getY() > distance
						&& newPoint.getY() - rightPoint.getY() > distance;
			}
			return true;
		}
		if (direction.equals("l")) {
			int up = thisLevelMap.getField()[upIntPoint.getX() - 1][upIntPoint.getY()];
			int down = thisLevelMap.getField()[downIntPoint.getX() - 1][downIntPoint.getY()];
			int cur = thisLevelMap.getField()[col - 1][row];
			// System.out.println("l");
			// System.out.println(up);
			// System.out.println(down);
			if (cur < 0 || up < 0 || down < 0) {
				Point2D newPoint = Decoder.arrayAdressToPixel(col - 1, row, GameWorldComponent.GRID_SIZE);
				int distance = 40;
				return currentPoint.getX() - newPoint.getX() > distance && upPoint.getX() - newPoint.getX() > distance
						&& downPoint.getX() - newPoint.getX() > distance;
			}
			return true;
		}
		int up = thisLevelMap.getField()[upIntPoint.getX() + 1][upIntPoint.getY()];
		int down = thisLevelMap.getField()[downIntPoint.getX() + 1][downIntPoint.getY()];
		int cur = thisLevelMap.getField()[col + 1][row];
		if (up < 0 || cur < 0 || down < 0) {

			Point2D newPoint = Decoder.arrayAdressToPixel(col + 1, row, GameWorldComponent.GRID_SIZE);
			int distance = 41;
			return newPoint.getX() - currentPoint.getX() > distance && newPoint.getX() - upPoint.getX() > distance
					&& newPoint.getX() - downPoint.getX() > distance;
		}
		return true;
	}

	public Temporal Objectat(IntPoint point) {
		for (Temporal t : this.objects) {
			if (Decoder.pixelToArrayAdress(t.getCenterPoint(), GameWorldComponent.GRID_SIZE).equals(point)) {
				return t;
			}

		}
		return null;

	}

	public void setMapPoint(int x, int y, int value) {
		this.myMaps.get(this.level - 1).setFieldValue(x, y, value);
	}

	public synchronized int getLevel() {
		return this.level;
	}

	synchronized public void levelUp() {
		if (this.level != this.myMaps.size()) {
			this.level++;
			BomberMan player = null;
			BomberMan player2 = null;
			for (Temporal t : this.objects) {
				if (t instanceof BomberMan) {
					if (((BomberMan) t).getNum() == 1) {
						player = (BomberMan) t;
					} else {
						player2 = (BomberMan) t;
					}
				}
			}
			if (player != null) {
				player.moveTo(Decoder.arrayAdressToPixel(1, 1, GameWorldComponent.GRID_SIZE));
			}
			if (player2 != null) {
				player2.moveTo(Decoder.arrayAdressToPixel(1, 11, GameWorldComponent.GRID_SIZE));
			}
			this.objects.removeAll(this.objects);
			this.objects.add(player);
			if (player2 != null) {
				this.objects.add(player2);
			}
			this.component.load();
			this.label.reset();
		}

	}

	synchronized public void setUp(int level) {
		this.level = level;
		BomberMan player = null;
		BomberMan player2 = null;
		for (Temporal t : this.objects) {
			if (t instanceof BomberMan) {
				if (((BomberMan) t).getNum() == 1) {
					player = (BomberMan) t;
				} else {
					player2 = (BomberMan) t;
				}
			}
		}
		if (player != null) {
			player.moveTo(Decoder.arrayAdressToPixel(1, 1, GameWorldComponent.GRID_SIZE));
		}
		if (player2 != null) {
			player2.moveTo(Decoder.arrayAdressToPixel(1, 11, GameWorldComponent.GRID_SIZE));
		}
		this.objects.removeAll(this.objects);
		this.objects.add(player);
		if (player2 != null) {
			this.objects.add(player2);
		}
	}

	public void restall() {
		this.objects = new ArrayList<>();
		this.toAddObjects = new ArrayList<>();
		this.toRemoveObjects = new ArrayList<>();
	}

	synchronized public void levelDown() {
		if (this.level != 1) {
			this.level--;
			BomberMan player = null;
			BomberMan player2 = null;
			for (Temporal t : this.objects) {
				if (t instanceof BomberMan) {
					if (((BomberMan) t).getNum() == 1) {
						player = (BomberMan) t;
					} else {
						player2 = (BomberMan) t;
					}
				}
			}
			if (player != null) {
				player.moveTo(Decoder.arrayAdressToPixel(1, 1, GameWorldComponent.GRID_SIZE));
			}
			if (player2 != null) {
				player2.moveTo(Decoder.arrayAdressToPixel(1, 11, GameWorldComponent.GRID_SIZE));
			}
			this.objects.removeAll(this.objects);
			this.objects.add(player);
			if (player2 != null) {
				this.objects.add(player2);
			}
			this.component.load();
			this.label.reset();
		}
	}

	public int getTimeLag() {
		return this.timeLag;
	}

	public void setTimeLag(int timeLag) {
		this.timeLag = timeLag;
	}

	public synchronized void addPoints(int pointsToAdd) {
		this.totalPoints += pointsToAdd;
	}

	public synchronized int getPoints() {
		return this.totalPoints;
	}

	public List<Temporal> getObjects() {
		return this.objects;
	}

	public void setComponent(GameWorldComponent component) {
		this.component = component;
	}

	public boolean checkVictory() {
		int count = 0;
		for (Temporal cur : this.getObjects()) {
			if (cur instanceof Monster) {
				count++;
			}
		}
		if (count == 0) {
			return true;
		}
		return false;
	}

	public void reloadpoint(Point2D point) {
		BomberMan player = null;
		for (Temporal t : this.objects) {
			if (t instanceof BomberMan) {
				player = (BomberMan) t;
			}
		}
		if (player != null) {
			player.moveTo(Decoder.arrayAdressToPixel(1, 1, GameWorldComponent.GRID_SIZE));
		}
		this.objects.removeAll(this.objects);
		player.setCenterPoint(point);
		this.objects.add(player);
		this.component.load();
		// System.out.println("player" + player.getCenterPoint());
	}

}
