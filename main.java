import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

class Floyds{
    public static double[][] floydAPSP(double[][] best, int n) { //best = weight rows = n
        double[][] pred = new double[n][n];
        for(int k = 0; k < n; k++) {
            for(int u = 0; u < n; u++) {
                for(int v = 0; v < n; v++) {

                    if((best[u][k] + best[k][v]) < best[u][v]) {
                        best[u][v] = best[u][k] + best[k][v];
                        pred[u][v] = pred[k][v];
                    }
                }
            }
        }
        return best;
    }

    public static void main(String[] args) {
        File input = new File("input2.txt");
        try {
            Scanner scan = new Scanner(input);
            int numTotInts = Integer.parseInt(scan.next());
            int numRds = Integer.parseInt(scan.next());
            int numIntCities = Integer.parseInt(scan.next());
            scan.nextLine();
            double[][] weightMatrix = new double[numTotInts][numTotInts];
            HashMap<Integer, String> myCities = new HashMap<>();

            for (int i = 0; i < numRds; i++) {
                int x = Integer.parseInt(scan.next());
                int y = Integer.parseInt(scan.next());
                double dist = Double.parseDouble(scan.next());
                weightMatrix[x][y] = dist;
                scan.nextLine();
            }
            for (int i = 0; i < numTotInts; i++) {
                for (int j = 0; j < numTotInts; j++) {
                    if (weightMatrix[i][j] == 0) {
                        if (i == j) {
                            weightMatrix[i][j] = 0;
                        }
                        else {
                            weightMatrix[i][j] = Double.POSITIVE_INFINITY;
                        }
                    }
                }
            }

            for (int i = 0; i < numIntCities; i++) {
                int loc = Integer.parseInt(scan.next());
                String cityName = scan.nextLine();
                myCities.put(loc, cityName);
            }

            double[][] bestMatrix = floydAPSP(weightMatrix, numTotInts);

            scan.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("input file not found");
        }
    }
}
