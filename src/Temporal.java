import java.awt.geom.Point2D;

public interface Temporal {
	/**
	 * Signals to this object that an "moment" of time has passed and the object
	 * should update to its next state in time.
	 */
	void timePassed();

	/**
	 * Signals to this object that it's useful life is over.
	 */
	void die();

	/**
	 * Returns the fill color that should be drawn to represent this object.
	 * 
	 * @return the fill color
	 */
	String getImageName();

	Point2D getCenterPoint();
}
