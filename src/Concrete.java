import java.awt.geom.Point2D;

public class Concrete extends Brick {

	public Concrete(GameWorld world, Point2D centerPoint) {
		super(world, centerPoint);
	}

	@Override
	public String getImageName() {
		return "res/img/concrete.png";
	}

	@Override
	public void timePassed() {
		// TODO Auto-generated method stub.

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub.

	}

}
