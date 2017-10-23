import java.awt.geom.Point2D;

public class Victory extends Item {

	public Victory(GameWorld newWorld) {
		super(newWorld, new Point2D.Double(Main.GAMEWORLD_WIDTH/2, Main.GAMEWORLD_HEIGHT/2));

	}

	@Override
	public void timePassed() {
		//

	}

	@Override
	public void die() {
		//
	}

	@Override
	public String getImageName() {
		return "res/img/" + this.getClass().getSimpleName().toLowerCase() + ".png";
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+ String.format("(%f, %f)", super.getCenterPoint().getX(), super.getCenterPoint().getY());
	}

}
