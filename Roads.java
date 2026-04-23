// Package Project5;

/*
*
* Authors: Janae Lansford and Abby Wurster
* Date: April 2026
* File Name: Roads.java
* Purpose: This class contains a few static methods and a main input function
* that are used to solve the problem of making signs on particular roads that 
* give distances to relavant cities. The algorithm implements the Floyd Warshall 
* algorithm to this end.
* 
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

public class Roads {

    private static HashMap<Integer, String> myCities = new HashMap<>();
    private static int numTotInts;
    private static int numSigns;
    private static int numIntCities;

    /**
     * This method implements the Floyd Warshall algorithm, which works by gradually 
     * updating a matrix that contains the distances between cities with smaller and 
     * smaller values, by trying different subsets of the cities. 
     * @param best is the weight matrix, this matrix reprsents the initial distances 
     * of directly connected cities
     * @param pred is the predcessor matrix, this matrix represents the second to 
     * last city in the route connecting two cities this value is fairly self evident 
     * in the beginning matrix, but is used to kickstart the function and is updated 
     * with more meaningful values within the function
     * @param problem is an array which contains the problem statement, including 
     * which roads need signs, this is simply passed through
     * @return this function returns nothing officially but passes best, which now 
     * contains the shortest distances between cities, pred, which contains the last 
     * city in the route from one city to another, and the problem statement into the 
     * signMaking function.
     */
    public static void floydAPSP(double[][] best, int[][] pred, double[][] problem) {
        // Run Floyd's algorithm
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

    public static void signMaking(double[][] bst, int[][] pred, double[][] problem) {
        // Print each sign
        for(int i = 0; i < numSigns; i++) {
            double from = problem[i][0];
            double to = problem[i][1];
            double distance = problem[i][2];
            String[] myOutput = new String[numIntCities*2];
            int v = 0;
            
            // Find which cities to add and their distance
            for(int j = 0; j < numTotInts; j++) {
                if((pred[j][(int)from] == (int)to) && myCities.containsKey((int)j)) {
                    double signValue = Math.round(bst[(int)from][j] - distance);
                    myOutput[v] = myCities.get((int)j);
                    myOutput[v+1] = (int)signValue + "";
                    v += 2;
                }
            }

            int idx = -1;
            Double temp;
            Double first;
            String spaceString;

            // Print out cities in order from smallest to largest distance
            for (int m = 0; m < numIntCities; m++) {
                first = Double.POSITIVE_INFINITY;
                for (int k = 1; k <= myOutput.length - 1; k+=2) {
                    if (myOutput[k] != null) {
                        temp = Double.parseDouble(myOutput[k]);
                        if (temp < first) {
                            first = Double.parseDouble(myOutput[k]);
                            idx = k;
                        }
                    }
                }

                // Format and print each line
                spaceString = "";
                if (Double.parseDouble(myOutput[idx]) != Double.POSITIVE_INFINITY) {
                    for (int x = 0; x < 20 - myOutput[idx - 1].length(); x++) {
                        spaceString += " ";
                    }
                    System.out.println(myOutput[idx - 1] + spaceString + 
                                       myOutput[idx]);
                }

                myOutput[idx] = "" + Double.POSITIVE_INFINITY;
            }

            // Print new line if not at the last sign 
            if (i != numSigns - 1) {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        File input = new File("input.txt");
        try {
            Scanner scan = new Scanner(input);

            // Parse initial input
            numTotInts = Integer.parseInt(scan.next());
            int numRds = Integer.parseInt(scan.next());
            numIntCities = Integer.parseInt(scan.next());
            scan.nextLine();
            
            double[][] weightMatrix = new double[numTotInts][numTotInts];
            int[][] predMatrix = new int[numTotInts][numTotInts];

            // Initialize predecessor matrix
            for (int i = 0; i < numTotInts; i++) {
                for (int j = 0; j < numTotInts; j++) {
                    predMatrix[i][j] = -1;
                }
            }

            // Build the weight matrix and predecessor matrix
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

            // Fill in the uninitialized values in the weight matrix
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

            // Add cities to hashmap
            for (int i = 0; i < numIntCities; i++) {
                int loc = Integer.parseInt(scan.next());
                String cityName = scan.next();
                myCities.put(loc, cityName);
                scan.nextLine();
            }

            // Build the sign problems requested
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

            // Call the function to solve the problem
            floydAPSP(weightMatrix, predMatrix, signs);

            scan.close();
        }

        catch (FileNotFoundException e) {
            System.err.println("input file not found");
        }
    }
}