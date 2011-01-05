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

    public void writeToFile(String filename) throws IOException {

        /* Writing Data to file*/
        BufferedWriter output = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < newNeighbors.length; i++) {
            output.write(newNeighbors[i] + "\n");
        }
        output.close();
    }
}
