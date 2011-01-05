package main;

import dtn.Network;
import java.io.IOException;
import org.apache.commons.math.MathException;
import results.RoutingTime;

/**
 *
 * @author Renegade
 */
public class Main {

    public static void main(String[] args) throws MathException, InterruptedException, IOException {

        int numNodes = 1000, L = 150, gamma = 60, binSize = 5, percentageDA;
        double pRot = 0.0, pTurn = 0.0001, fracDA;

        /* Graphs */
        for (int i = 1; i <= 100; i++) {
            for (percentageDA = 0; percentageDA <= 100; percentageDA += 10) {
                fracDA = (double) percentageDA / 100;
                Network network = new Network(numNodes, L, fracDA, pTurn, pRot, gamma);
                network.broadcast();
                (new RoutingTime(network)).writeToFile("RTvsID_sct_" + "_" + percentageDA + "_" + i + ".txt");
                (new RoutingTime(network)).writeToFile(binSize, "RTvsID_avg_" + percentageDA + "_" + i + ".txt");
            }
        }
    }
}
