import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ButtonListener implements ActionListener {

	private JFrame myframe;
	private GameWorld myworld;
	public ButtonListener(JFrame myframe,GameWorld world)
	{
		this.myframe = myframe;
		this.myworld = world;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.myframe.dispose();
		this.myworld.setIsPaused();
	}

}
