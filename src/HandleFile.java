import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HandleFile {

	public static ArrayList<Map> openFile() throws FileNotFoundException {
		ArrayList<Map> toReturn = new ArrayList<>();
		File file = new File("res/levelfile/level.txt");
		Scanner scanner = new Scanner(file);
		int n = scanner.nextInt();
		for (int k = 0; k < n; k++) {
			int[][] tempfield = new int[26][13];
			int m = scanner.nextInt();
			for (int i = 0; i < 13; i++)
				for (int j = 0; j < 26; j++) {
					tempfield[j][i] = scanner.nextInt();
				}
			Map tempmap = new Map(tempfield, k + 1);
			toReturn.add(tempmap);
		}

		scanner.close();
		return toReturn;
	}

}
