/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dtn.Network;
import gui.GraphingData;
import org.apache.commons.math.MathException;
import gui.SimulationGUI;
import javax.swing.JFrame;

/**
 *
 * @author Renegade
 */
public class MainGUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MathException, InterruptedException {

        final SimulationGUI sim = new SimulationGUI();
        final GraphingData gd = new GraphingData();
        Network n = new Network(0, 0.0001, 0, 60, sim);
        sim.setNetwork(n);
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                sim.setVisible(true);
            }
        });
        n.broadcast();
        gd.setNetwork(n);
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.add(gd);
                f.setSize(600, 600);
                f.setLocation(200, 200);
                f.setVisible(true);
            }
        });
    }
}
