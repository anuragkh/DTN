/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dtn;

import org.apache.commons.math.MathException;

/**
 *
 * @author Renegade
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MathException {
        Network n=new Network(0.1, 0.1);
        n.broadcast();
    }

}
