import java.awt.geom.Point2D;

public interface Reloctable {

	void moveTo(Point2D point);

	Point2D getCenterPoint();

}
