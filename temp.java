import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

public class temp {

    static void main() {
        File input = new File("input.txt");
        try {
            Scanner scan = new Scanner(input);
            int numTotInts = Integer.parseInt(scan.next());
            int numRds = Integer.parseInt(scan.next());
            int numIntCities = Integer.parseInt(scan.nextLine());
            double[][] weightMatrix = new double[numTotInts][numTotInts];
            HashMap<Integer, String> myCities = new HashMap<>();

            for (int i = 0; i < numRds; i++) {
                int x = Integer.parseInt(scan.next());
                int y = Integer.parseInt(scan.next());
                double dist = Integer.parseInt(scan.nextLine());
                weightMatrix[x][y] = dist;
            }
            for (int i = 0; i < numRds; i++) {
                for (int j = 0; j < numRds; j++) {
                    if (weightMatrix[i][j] == 0) {
                        weightMatrix[i][j] = Double.POSITIVE_INFINITY;
                    }
                }
            }

            for (int i = 0; i < numIntCities; i++) {
                int loc = Integer.parseInt(scan.next());
                String cityName = scan.nextLine();
                myCities.put(loc, cityName);
            }

            scan.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("input file not found");
        }
    }
}