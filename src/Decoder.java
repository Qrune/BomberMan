import java.awt.geom.Point2D;

public class Decoder {

	public static IntPoint pixelToArrayAdress(Point2D newPoint, int blockSize) {

		int newX = (int) (newPoint.getX() / blockSize);
		int newY = (int) (newPoint.getY() / blockSize);
		IntPoint ans = new IntPoint(newX, newY);
		return ans;
	}

	public static Point2D arrayAdressToPixel(int x, int y, int blockSize) {
		return new Point2D.Double(x * blockSize + 24, y * blockSize + 24);
	}

	public static Point2D toLeftPoint(Point2D point) {
		return new Point2D.Double(point.getX() - 10, point.getY());
	}

	public static Point2D toRightPoint(Point2D point) {
		return new Point2D.Double(point.getX() + 5, point.getY());
	}

	public static Point2D toUpPoint(Point2D point) {
		return new Point2D.Double(point.getX(), point.getY() - 10);
	}

	public static Point2D toDownPoint(Point2D point) {
		return new Point2D.Double(point.getX(), point.getY() + 13);
	}

	public static String int2DarrytoString(int[][] input) {
		String toReturn = "";
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length; j++) {
				if (input[j][i] < -0.4) {
					toReturn += " " + input[j][i];
				}
				if (input[j][i] > -0.4) {
					toReturn += "  " + input[j][i];
				}
			}
			toReturn += "\n";

		}
		return toReturn;
	}

	public static boolean classNameCheck(Temporal obj, String nameToCheck) {
		return obj.getClass().getSimpleName().toLowerCase().equals(nameToCheck.toLowerCase());
	}
	public static String spaceBarGenerator(int n){
		String toReturn="";
		for (int i = 0; i < n; i++) {
			toReturn+=" ";
		}
		return toReturn;
	}
}
