import java.awt.geom.Point2D;

public class GameOver extends Item {

	public GameOver(GameWorld newWorld) {
		super(newWorld, new Point2D.Double(Main.GAMEWORLD_WIDTH/2, Main.GAMEWORLD_HEIGHT/2));
		// TODO Auto-generated constructor stub.
	}

	@Override
	public void timePassed() {
		// TODO Auto-generated method stub.

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub.
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
