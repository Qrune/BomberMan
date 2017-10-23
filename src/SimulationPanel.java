import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

public final class SimulationPanel extends JPanel {

	private GameWorldComponent component;

	public SimulationPanel(int level, int playerNum) {
		setLayout(new BorderLayout());
		GameWorld world = new GameWorld(level);
		DisplayLabel toDisplay = new DisplayLabel(world, 200, 0, 3);
		world.setLabel(toDisplay);
		Font font = new Font("Courier", Font.BOLD, 30);
		toDisplay.setFont(font);
		toDisplay.setBackground(new Color(60, 60, 60));
		toDisplay.setForeground(Color.WHITE);
		add(toDisplay, BorderLayout.NORTH);

		this.component = new GameWorldComponent(world, playerNum);
		this.component.requestFocus();
		world.setComponent(this.component);
		add(this.component, BorderLayout.CENTER);
	}

	public GameWorldComponent getComponent() {
		return this.component;
	}
}
