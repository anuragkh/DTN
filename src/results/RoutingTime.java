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

    int[] routingTime;
    double[] initDistance;
    Network network;

    public RoutingTime(Network network) {

        this.network = network;
        routingTime = new int[Network.NUM_NODES];
        initDistance = new double[Network.NUM_NODES];

        /* Initializing routingTime and initDistance */
        for (int i = 0; i < initDistance.length; i++) {
            initDistance[i] = network.agentList[i].initDist;
            routingTime[i] = network.agentList[i].timeFirstInfected;
        }

    }

    public void quickSort(double arrayX[], int arrayY[], int low, int n) {
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
                int T2 = arrayY[lo];
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

    public void writeToFile() throws IOException {

        /* Sorting both arrays according to initDistance */
        quickSort(initDistance, routingTime, 0, initDistance.length - 1);

        /* Writing Data to file*/

        Writer output = new BufferedWriter(new FileWriter("RoutingTimeVsInitialDistance.txt"));
        String str;
        for (int i = 0; i < initDistance.length; i++) {
            str = Double.toString(initDistance[i]) + "\t" + Integer.toString(routingTime[i]) + "\n";
            System.out.print(str);
            output.append(str);
        }
        output.close();
    }
}
