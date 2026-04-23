/*
*
* This class contains a few static methods and a main input function
* that are used to solve the problem of making signs on particular roads that give distances to relavant cities
* The algorithim implements the Floyd Warshall algoritim to this end
* 
* @authors: Janae Lansford and Abby Wurster
* Date: April 2026
* File Name: Roads.java
* 
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

class Floyds{

    private static HashMap<Integer, String> myCities = new HashMap<>();
    private static int numTotInts;
    private static int numSigns;
    private static int numIntCities;

    /**
     * This method implements the Floyd Warshall algorithim, which works by gradually updating a matrix that contains
     * the distances between cities with smaller and smaller values, by trying different subsets of the cities. 
     * @param best is the weight matrix, this matrix reprsents the initial distances of directly connected cities
     * @param pred is the predcessor matrix, this matrix represents the second to last city in the route connecting two cities
     * this value is fairly self evident in the beginning matrix, but is used to kickstart the function
     * and is updated with more meaningful values within the function
     * @param problem is an array which contains the problem statement, including which roads need signs, this is simply passed through
     * @return this function returns nothing officially but passes best, which now contains the shortest distances between cities, 
     * pred, which contains the last city in the route from one city to another, and the problem statement into the signMaking function
     */
    public static void floydAPSP(double[][] best, int[][] pred, double[][] problem) { //best = weight
        for(int k = 0; k < numTotInts; k++) {
            for(int u = 0; u < numTotInts; u++) {
                for(int v = 0; v < numTotInts; v++) {
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
        for(int i = 0; i < numSigns; i++) {
            double from = problem[i][0];
            double to = problem[i][1];
            double distance = problem[i][2];

            String[] myOutput = new String[numIntCities*2];
            int v = 0;
            for(int j = 0; j < numTotInts; j++) {
                if((pred[j][(int)from] == (int)to) && myCities.containsKey((int)j)) {
                    double signValue = Math.round(best[(int)from][j] - distance);
                    myOutput[v] = myCities.get((int)j);
                    myOutput[v+1] = (int)signValue + "";
                    v += 2;
                }
            }

            //Double first = Double.POSITIVE_INFINITY;
            int index = -1;
            Double temp;
            for (int m = 0; m < numIntCities; m++) {
                Double first = Double.POSITIVE_INFINITY;
                for (int k = 1; k <= myOutput.length - 1; k+=2) {
                    if (myOutput[k] == null) {
                        // DO NOTHING;
                    }
                    else {
                        temp = Double.parseDouble(myOutput[k]);
                        if (temp < first) {
                            first = Double.parseDouble(myOutput[k]);
                            index = k;
                        }
                    }
                }

                String spaceString = "";
                if (Double.parseDouble(myOutput[index]) != Double.POSITIVE_INFINITY) {
                    for (int x = 0; x < 20 - myOutput[index - 1].length(); x++) {
                        spaceString += " ";
                    }
                    System.out.println(myOutput[index - 1] + spaceString + myOutput[index]);
                }

                myOutput[index] = "" + Double.POSITIVE_INFINITY;

            }
            if (i != numSigns - 1) {
                System.out.println();
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
                predMatrix[y][x] = y;
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
                String cityName = scan.next();
                myCities.put(loc, cityName);
                scan.nextLine();
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
