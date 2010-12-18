/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.SimulationFrame;
import org.apache.commons.math.MathException;
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

        final SimulationFrame sim = new SimulationFrame();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                sim.setVisible(true);
            }
        });

    }
}
