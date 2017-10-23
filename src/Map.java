public class Map {
	private int[][] field;
	private int level;

	public Map(int[][] field, int level) {
		this.setField(field);
		this.setLevel(level);
	}

	public int[][] getField() {
		return this.field;
	}

	public void setField(int[][] field) {
		this.field = field;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setFieldValue(int x, int y, int value) {
		this.field[x][y] = value;
	}
}
