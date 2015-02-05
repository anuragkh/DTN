package results;

import dtn.Network;
import java.io.*;

/**
 *
 * @author Renegade
 */
public class NewNeighbors {

    private double[] newNeighbors;

    public NewNeighbors(Network network) {
        newNeighbors = new double[network.newNeighbors.size()];
        int i = 0;
        for(double currentNewNeighbor : network.newNeighbors) {
            newNeighbors[i++] = currentNewNeighbor;
        }
    }

    public void print() {
        double avg = 0;
        for (int i = 0; i < newNeighbors.length; i++) {
            System.out.println(newNeighbors[i] + "\n");
            avg += newNeighbors[i];
        }
        avg /= newNeighbors.length;
        System.out.println("Avg=" + avg);
    }
    public void writeToFile(String filename) throws IOException {

        /* Writing Data to file*/
        BufferedWriter output = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < newNeighbors.length; i++) {
            output.write(newNeighbors[i] + "\n");
        }
        output.close();
    }
}
