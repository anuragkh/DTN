/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plot;

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

    void averageData() throws FileNotFoundException, IOException {
        double[] initDistance = null;
        double[] routingTime = null;
        int j;
        for (int i = 0; i < filename.length; i++) {
            j = 0;
            BufferedReader input = new BufferedReader(new FileReader(filename[i]));
            String line = null;
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
        BufferedWriter output = new BufferedWriter(new FileWriter("RTvsID_avg_final.txt"));
        for (int i = 0; i < routingTime.length; i++) {
            output.write(initDistance[i] + "\t" + routingTime[i] + "\n");
        }
        output.close();
    }

}
