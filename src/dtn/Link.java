/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dtn;

/**
 *
 * @author Renegade
 */
public class Link {

    static final double DELTA=0.05;
    static final double K=1;
    static final double Pt=1;
    static final double GS_0=0.0316;
    static final double GAMMA_T=60;
    static final double GAMMA_R=60;

    public static double xij(Node i, Node j) {

        return Math.sqrt(Math.pow(i.posX - j.posX, 2) + Math.pow(i.posY - j.posY, 2));

    }

    public static double gainM_0(double gamma) {

        return 1.4 / (1 - Math.cos(gamma / 2));

    }

    public static double gain(Node i, Node j, double gamma) {

        if(Math.cos(Math.PI * j.currentDirection / 180) * (i.posX - j.posX) + Math.sin(Math.PI * j.currentDirection / 180) * (i.posY - j.posY) < Math.cos(gamma / 2))
            return gainM_0(gamma);
        else
            return GS_0;

    }

    public static double power(Node i, Node j) {

        return K * Pt * gain(i, j, GAMMA_T) * gain(j, i, GAMMA_R) / Math.pow(xij(i, j), 2);

    }

    public static boolean isConnected(Node i, Node j) {

        if( power(i, j) > DELTA)
            return true;

        return false;
    }

}
