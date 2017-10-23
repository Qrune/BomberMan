import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LoadSaveHandler {
	private final GameWorldComponent gameComp;
	private final JFileChooser chooser;

	public LoadSaveHandler(GameWorldComponent gameComp) {
		this.gameComp = gameComp;
		this.chooser = new JFileChooser();
	}

	public void loadGameState() throws IOException {
		this.gameComp.pause();
		if (this.chooser.showOpenDialog(this.gameComp) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File inputFile = this.chooser.getSelectedFile();
		Scanner inScanner = new Scanner(inputFile);
		Point2D heropoint = new Point2D.Double(inScanner.nextDouble(), inScanner.nextDouble());
		this.gameComp.getWorld().getLabel().setTime(inScanner.nextInt());
		this.gameComp.getWorld().getLabel().setLife(inScanner.nextInt());
		this.gameComp.getWorld().setUp(inScanner.nextInt());
		try {
			ArrayList<Map> newMap = new ArrayList<>();

			int n = inScanner.nextInt();
			for (int k = 0; k < n; k++) {
				int[][] tempfield = new int[26][13];
				int m = inScanner.nextInt();
				for (int i = 0; i < 13; i++)
					for (int j = 0; j < 26; j++) {
						tempfield[j][i] = inScanner.nextInt();
					}
				Map tempmap = new Map(tempfield, k + 1);
				newMap.add(tempmap);
			}
			this.gameComp.setMap(newMap);
		} finally {
			this.gameComp.reload(heropoint);
			inScanner.close();
		}
	}

	public void saveGameState() {
		this.gameComp.pause();
		ArrayList<Map> curmap = this.gameComp.getMap();
		if (this.chooser.showSaveDialog(this.gameComp) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File outputFile = this.chooser.getSelectedFile();
		try {
			PrintWriter writer = new PrintWriter(outputFile);
			try {
				writer.printf("%f %f%n", this.gameComp.getManLocation().getX(), this.gameComp.getManLocation().getY());
				writer.printf("%d%n", this.gameComp.getWorld().getLabel().getTime());
				writer.printf("%d%n", this.gameComp.getWorld().getLabel().getLife());
				writer.printf("%d%n", this.gameComp.getWorld().getLevel());
				writer.printf("%d%n", curmap.size());
				for (Map c : curmap) {
					writer.printf("%d%n", c.getLevel());
					for (int i = 0; i < 13; i++) {
						for (int j = 0; j < 26; j++) {
							writer.print(c.getField()[j][i] + " ");
						}
						writer.println();
					}
				}
			} finally {
				writer.close();
				this.gameComp.requestFocus();
			}
		} catch (FileNotFoundException fnfException) {
			String msg = "Unable to save game: " + fnfException.getMessage();
			JOptionPane.showMessageDialog(this.gameComp, msg, "Save Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
