import java.awt.geom.Point2D;

import javax.swing.JTextField;

public class DisplayLabel extends JTextField implements Temporal {
	private int time, score, life, space;
	long initialtime;
	boolean initialstate;
	private boolean timeFlag1 = true;
	private boolean timeFlag2 = true;
	private GameWorld world;

	public DisplayLabel(GameWorld world, int newTime, int newScore, int newLife) {
		this.world = world;
		this.time = newTime;
		this.space = 37;
		this.score = newScore;
		this.life = newLife;
		this.initialstate = true;
		this.setEditable(false);
		this.setText(String.format("TIME   %d", this.time) + Decoder.spaceBarGenerator(this.space-Integer.toString(this.score).length())
				+ String.format("POINT   %d   LEFT  %d", this.score, this.life));
	}

	public void setDisplayText() {
		this.setText(String.format("TIME   %d", this.time) + Decoder.spaceBarGenerator(this.space-Integer.toString(this.score).length())
				+ String.format("POINT   %d   LEFT  %d", this.score, this.life));
	}

	public void getDisplayText() {
		this.getText();
	}

	public void reset() {
		this.initialstate = true;
		this.time = 200;

	}

	public int getTime() {
		return this.time;
	}

	public int getScore() {
		return this.score;
	}

	public int getLife() {
		return this.life;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void reduceLife() {
		this.life--;
	}

	@Override
	public void timePassed() {
		long timepass = System.currentTimeMillis() - this.initialtime;
		if (this.world.getIsPaused()) {
			if (timepass > 1000) {
				// System.out.println("Pass");
				this.initialtime += 1000;
			}
			return;
		}
		this.setText(String.format("TIME   %d", this.time) + Decoder.spaceBarGenerator(this.space-Integer.toString(this.score).length())
				+ String.format("POINT   %d   LEFT  %d", this.score, this.life));
		if (this.initialstate) {
			this.initialtime = System.currentTimeMillis();
			this.initialstate = false;
		} else {
			if (timepass > 1000) {
				// System.out.println("Pass 1");
				this.initialtime += 1000;
				this.time--;
				if (this.time < 100 && this.timeFlag1) {
					this.timeFlag1 = false;
					this.space++;
				}
				if (this.time < 10 && this.timeFlag2) {
					this.timeFlag2 = false;
					this.space++;
				}
			}

		}
	}

	@Override
	public void die() {
		this.life--;

	}

	@Override
	public String getImageName() {
		return null;
	}

	@Override
	public Point2D getCenterPoint() {
		return null;
	}

}