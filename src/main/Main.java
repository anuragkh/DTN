package main;

import dtn.Network;
import java.io.File;
import java.io.IOException;
import org.apache.commons.math.MathException;
import plot.AveragePlot;
import results.*;

/**
 *
 * @author Renegade
 */
public class Main {

    public static void main(String[] args) throws MathException, InterruptedException, IOException {

        int numNodes = 140, L = 100, gamma = 60, binSize = 5;
        double pRot = 0.0, pTurn = 0.0001, fracDA;
        

        /* Graphs */
        for (int percentageDA = 0; percentageDA <= 100; percentageDA += 10) {
            String[] fileInitDist = new String[500], fileNumNeighbor = new String[500];
            for (int i = 0; i < 500; i++) {
                fracDA = (double) percentageDA / 100;
                Network network = new Network(numNodes, L, fracDA, pTurn, pRot, gamma);
                network.broadcast();
                File f1 = new File("E:/Project/Results/neighbors/" + percentageDA + "/"), f2 = new File("D:/Project/Results/sct/" + percentageDA + "/"), f3 = new File("D:/Project/Results/avg/" + percentageDA + "/");
                f1.mkdir();
                f2.mkdir();
                f3.mkdir();
                (new NewNeighbors(network)).writeToFile("E:/Project/Results/neighbors/" + percentageDA + "/AvgNewNeighbors_" + i + ".txt");
                (new RoutingTime(network)).writeToFile("E:/Project/Results/sct/" + percentageDA + "/RTvsID_" + i + ".txt");
                (new RoutingTime(network)).writeToFile(binSize, "E:/Project/Results/avg/" + percentageDA + "/RTvsID_" + i + ".txt");
                fileInitDist[i] = "E:/Project/Results/avg/" + percentageDA + "/RTvsID_" + i + ".txt";
                fileNumNeighbor[i] = "E:/Project/Results/neighbors/" + percentageDA + "/AvgNewNeighbors_" + i + ".txt";
            }
            (new AveragePlot(fileInitDist)).averageData(percentageDA, binSize);
            (new AveragePlot(fileNumNeighbor)).averageNumNeighbours(percentageDA);
        }
    }
}
