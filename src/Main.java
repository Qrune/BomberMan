import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main class for your arcade game.
 * 
 * You can design your game any way you like, but make the game start
 * by running main here.
 * 
 * Also don't forget to write javadocs for your classes and functions!
 * 
 * @author Buffalo
 *
 */
/**
 * TODO The Main of Game Bomberman
 *
 * @author Haoqian Li, Yang Gao, Zhuochen Liu Created Oct 23, 2016.
 */
public class Main {
	protected static final int GAMEWORLD_WIDTH = 1252;
	protected static final int GAMEWORLD_HEIGHT = 674;
	protected static final int TITLE_BAR_HEIGHT = 28;

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		final JFrame start = new JFrame();
		JButton btn1 = new JButton();
		btn1.setText("1 Player");
		JButton btn2 = new JButton();
		btn2.setText("2 Player");
		InsComponent ins = new InsComponent();
		JPanel panel = new JPanel();
		panel.add(btn1);
		panel.add(btn2);
		start.setTitle("Bomber Man Arcade Game");
		start.add(ins, BorderLayout.CENTER);
		start.add(panel, BorderLayout.SOUTH);
		start.setSize(GAMEWORLD_WIDTH, GAMEWORLD_HEIGHT + TITLE_BAR_HEIGHT + 48);
		start.setVisible(true);

		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameWorldsFrame frame = new GameWorldsFrame(1);
				frame.setSize(GAMEWORLD_WIDTH, GAMEWORLD_HEIGHT + TITLE_BAR_HEIGHT);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				start.dispose();
			}

		});
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameWorldsFrame frame = new GameWorldsFrame(2);
				frame.setSize(GAMEWORLD_WIDTH, GAMEWORLD_HEIGHT + TITLE_BAR_HEIGHT);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				start.dispose();
			}

		});
	}

}
