/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
        Network network = new Network(1, 0.0001, 0.1, 60);
        network.broadcast();
        RoutingTime data = new RoutingTime(network);
        data.writeToFile();
    }
}
