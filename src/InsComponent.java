import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class InsComponent extends JComponent {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("res/img/ins.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0, 1252, 674, null);
	}

}
