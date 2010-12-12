/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dtn.Network;
import org.apache.commons.math.MathException;
import gui.SimulationGUI;
import javax.swing.JFrame;

/**
 *
 * @author Renegade
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MathException, InterruptedException {

        final SimulationGUI sim = new SimulationGUI();
        Network n = new Network(0.1, 0.1, sim);
        sim.setNetwork(n);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                sim.setVisible(true);
            }
        });
        n.broadcast();
        
    }
}
