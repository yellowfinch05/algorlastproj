import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

class Floyds{

    private static HashMap<Integer, String> myCities = new HashMap<>();
    private static int numTotInts;
    private static int numSigns;
    private static int numIntCities;

    public static void floydAPSP(double[][] best, int[][] pred, double[][] problem) { //best = weight rows = n
        for(int k = 0; k < numTotInts; k++) {
            for(int u = 0; u < numTotInts; u++) {
                for(int v = 0; v < numTotInts; v++) {

                    if((best[u][k] + best[k][v]) < best[u][v]) {
                        best[u][v] = best[u][k] + best[k][v];
                        pred[u][v] = pred[k][v];
                        pred[v][u] = pred[v][k];
                    }
                }
            }
        }
        signMaking(best, pred, problem);
    }

    public static void signMaking(double[][] best, int[][] pred, double[][] problem) {
        for(int i = 0; i < numSigns; i++) {
            double from = problem[i][0];
            double to = problem[i][1];
            double distance = problem[i][2];

            for(int j = 0; j < numTotInts; j++) {
                if((pred[j][(int)from] == to) && myCities.containsKey((int)to)) {
                    System.out.print(myCities.get((int)to));
                    System.out.println(best[j][(int)to] - distance);
                }

            }
        }
    }

    public static void main(String[] args) {
        File input = new File("input.txt");
        try {
            Scanner scan = new Scanner(input);
            numTotInts = Integer.parseInt(scan.next());
            int numRds = Integer.parseInt(scan.next());
            numIntCities = Integer.parseInt(scan.next());
            scan.nextLine();
            double[][] weightMatrix = new double[numTotInts][numTotInts];
            int[][] predMatrix = new int[numTotInts][numTotInts];
            

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
                weightMatrix[y][x] = dist;
                predMatrix[x][y] = x;
                predMatrix[y][x] = x;
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


            numSigns = Integer.parseInt(scan.nextLine());
            double[][] signs = new double[numSigns][3];
            for (int i = 0; i < numSigns; i++) {
                int x = Integer.parseInt(scan.next());
                int y = Integer.parseInt(scan.next());
                double dist = Double.parseDouble(scan.next());
                signs[i][0] = x;
                signs[i][1] = y;
                signs[i][2] = dist;
            }

            floydAPSP(weightMatrix, predMatrix, signs);

            scan.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("input file not found");
        }
    }
}
