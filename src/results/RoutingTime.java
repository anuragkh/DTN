/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package results;

import dtn.Network;
import java.io.*;

/**
 *
 * @author Renegade
 */
public class RoutingTime {

    private double[] routingTime;
    private double[] initDistance;

    public RoutingTime(Network network) {

        routingTime = new double[Network.NUM_NODES];
        initDistance = new double[Network.NUM_NODES];

        /* Initializing routingTime and initDistance */
        for (int i = 0; i < initDistance.length; i++) {
            initDistance[i] = network.agentList[i].initDist;
            routingTime[i] = network.agentList[i].timeFirstInfected;
        }

    }

    public double[] getRoutingTime() {
        return routingTime;
    }

    public double[] getInitDistance() {
        return initDistance;
    }

    public void quickSort(double arrayX[], double arrayY[], int low, int n) {
        int lo = low;
        int hi = n;
        if (lo >= n) {
            return;
        }
        double mid = arrayX[(lo + hi) / 2];
        while (lo < hi) {
            while (lo < hi && arrayX[lo] < mid) {
                lo++;
            }
            while (lo < hi && arrayX[hi] > mid) {
                hi--;
            }
            if (lo < hi) {
                double T1 = arrayX[lo];
                double T2 = arrayY[lo];
                arrayX[lo] = arrayX[hi];
                arrayY[lo] = arrayY[hi];
                arrayX[hi] = T1;
                arrayY[hi] = T2;
            }
        }
        if (hi < lo) {
            int T = hi;
            hi = lo;
            lo = T;
        }
        quickSort(arrayX, arrayY, low, lo);
        quickSort(arrayX, arrayY, lo == low ? lo + 1 : lo, n);
    }

    public static double[] binSort(double arrayX[], double arrayY[], int binSize) {
        int dim = (int) Math.ceil(Math.sqrt(2) * Network.L / binSize);
        double[] bin = new double[dim];
        int[] freq = new int[dim];

        for (int i = 0; i < bin.length; i++) {
            bin[i] = 0;
            freq[i] = 0;
        }
        for (int i = 0; i < arrayX.length; i++) {
            bin[(int) (arrayX[i] / binSize)] += arrayY[i];
            freq[(int) (arrayX[i] / binSize)]++;
        }
        for (int i = 0; i < bin.length; i++) {
            bin[i] = (freq[i] != 0) ? bin[i] / freq[i] : -1;
        }

        return bin;

    }

    public void writeToFile() throws IOException {

        /* Sorting both arrays according to initDistance */
        quickSort(initDistance, routingTime, 0, initDistance.length - 1);

        /* Writing Data to file*/

        BufferedWriter output = new BufferedWriter(new FileWriter("RoutingTimeVsInitialDistance.txt"));
        for (int i = 0; i < initDistance.length; i++) {
            output.write(initDistance[i] + "\t" + routingTime[i] + "\n");
        }
        output.close();
    }
}
