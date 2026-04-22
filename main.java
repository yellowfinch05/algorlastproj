import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

class Floyds{
    public static void floydAPSP(double[][] best, int n, int[][] pred, double[][] problem) { //best = weight rows = n
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
        signMaking(best, pred, problem);
    }

    public static void signMaking(double[][] best, int[][] pred, double[][] problem) {

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
            int[][] predMatrix = new int[numTotInts][numTotInts];
            HashMap<Integer, String> myCities = new HashMap<>();

            for (int i = 0; i < numTotInts; i++) {
                for (int j = 0; j < numTotInts; j++) {
                    predMatrix[i][j] = -1;
                }
            }

            for (int i = 0; i < numRds; i++) {
                int x = Integer.parseInt(scan.next());
                int y = Integer.parseInt(scan.next());
                double dist = Double.parseDouble(scan.next());
                weightMatrix[x][y] = dist;
                predMatrix[x][y] = x;
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

            double[][] bestMatrix = floydAPSP(weightMatrix, numTotInts, predMatrix);

            int numSigns = Integer.parseInt(scan.nextLine());
            for (int i = 0; i < numSigns; i++) {
                int x = Integer.parseInt(scan.next());
                int y = Integer.parseInt(scan.next());
                double dist = Double.parseDouble(scan.next());
            }

            scan.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("input file not found");
        }
    }
}
