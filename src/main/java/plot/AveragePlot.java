/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plot;

import dtn.Network;
import java.io.*;

/**
 *
 * @author Renegade
 */
public class AveragePlot {

    String[] filename;

    public AveragePlot(String[] filename) {
        this.filename = filename;
    }

    public void averageData(int  percentageDA, int binSize) throws FileNotFoundException, IOException {
        double initDistance[] = new double[(int) Math.ceil(Math.sqrt(2) * Network.L / binSize)];
        double routingTime[] = new double[(int) Math.ceil(Math.sqrt(2) * Network.L / binSize)];
        int j;
        for (int i = 0; i < filename.length; i++) {
            j = 0;
            String line = null;
            BufferedReader input = new BufferedReader(new FileReader(filename[i]));
            while ((line = input.readLine()) != null) {
                initDistance[j] += Double.parseDouble(line.substring(0, line.indexOf('\t')));
                routingTime[j] += Double.parseDouble(line.substring(line.indexOf('\t') + 1));
                j++;
            }
        }

        for (int i = 0; i < routingTime.length; i++) {
            routingTime[i] /= filename.length;
            initDistance[i] /= filename.length;
        }

        /* Writing Data to file*/
        BufferedWriter output = new BufferedWriter(new FileWriter("E:/Project/Results/RTvsID_avg_final_" + percentageDA + ".txt"));
        for (int i = 0; i < routingTime.length; i++) {
            output.write(initDistance[i] + "\t" + routingTime[i] + "\n");
        }
        output.close();
    }

    public void averageNumNeighbours(int percentageDA) throws FileNotFoundException, IOException {
        double numNeighbors = 0.0;
        int time = 0;
        for (int i = 0; i < filename.length; i++) {

            BufferedReader input = new BufferedReader(new FileReader(filename[i]));
            String line = null;
            while ((line = input.readLine()) != null) {
                numNeighbors += Double.parseDouble(line);
                time++;
            }
            
        }

        /* Writing Data to file*/
        BufferedWriter output = new BufferedWriter(new FileWriter("E:/Project/Results/avg_numNeighbors_" + percentageDA+ ".txt"));
        output.write((numNeighbors/time) + "\t");
        output.close();
    }
}
