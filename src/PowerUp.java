import java.awt.geom.Point2D;

public abstract class PowerUp extends Item {
	public PowerUp(GameWorld newWorld,Point2D newCenterPoint){
		super(newWorld, newCenterPoint);
		
	}

	@Override
	public void timePassed() {
		// TODO Auto-generated method stub.

	}

	@Override
	public void die() {
		super.getWorld().removeObject(this);	
	}
	
	
	@Override
	public String getImageName() {
		return "res/img/"+this.getClass().getSimpleName().toLowerCase()+".png";
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+String.format("(%f, %f)", super.getCenterPoint().getX(), super.getCenterPoint().getY());
	}
}
