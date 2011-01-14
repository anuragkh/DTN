package main;

import dtn.Network;
import java.io.File;
import java.io.IOException;
import org.apache.commons.math.MathException;
import results.*;

/**
 *
 * @author Renegade
 */
public class Main {

    public static void main(String[] args) throws MathException, InterruptedException, IOException {

        int numNodes = 140, L = 100, gamma = 60, binSize = 5, percentageDA;
        double pRot = 0.0, pTurn = 0.0001, fracDA;

        /* Graphs */
        for (int i = 1; i <= 5; i++) {
            for (percentageDA = 0; percentageDA <= 100; percentageDA += 10) {
                fracDA = (double) percentageDA / 100;
                Network network = new Network(numNodes, L, fracDA, pTurn, pRot, gamma);
                network.broadcast();
                File f1 = new File("E:/Project/Results/neighbors/" + percentageDA + "/"), f2 = new File("E:/Project/Results/sct/" + percentageDA + "/"), f3 = new File("E:/Project/Results/avg/" + percentageDA + "/");
                f1.mkdir();
                f2.mkdir();
                f3.mkdir();
                (new NewNeighbors(network)).writeToFile("E:/Project/Results/neighbors/" + percentageDA + "/AvgNewNeighbors_" + i + ".txt");
                (new RoutingTime(network)).writeToFile("E:/Project/Results/sct/" + percentageDA + "/RTvsID_" + i + ".txt");
                (new RoutingTime(network)).writeToFile(binSize, "E:/Project/Results/avg/" + percentageDA + "/RTvsID_" + i + ".txt");
            }
        }
    }
}
