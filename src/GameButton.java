import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GameButton extends JButton implements ActionListener {

	public GameButton(String name) {
		setText(name);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GameButton btn = (GameButton) e.getSource();
		String name = btn.getText();
		manipulate(name);
	}

	private void manipulate(String name) {
		if (name.equals("START")) {

		} else if (name.equals("PAUSE")) {

		} else if (name.equals("SAVE")) {

		} else if (name.equals("LOAD")) {

		} else {// RESET

		}
	}

}
