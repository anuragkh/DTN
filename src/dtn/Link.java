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

    static final double DELTA = 0.1;
    static final double K = 1;
    static final double Pt = 1;
    static final double GS_0 = 0.0316;

    public static double xij(Node i, Node j) {

        return Math.sqrt(Math.pow(i.posX - j.posX, 2) + Math.pow(i.posY - j.posY, 2));

    }

    public static double gainM_0(double gamma) {

        return 1.4 / (1 - Math.cos(gamma / 2));

    }

    public static double gain(Node i, Node j, int gamma) {

        if (gamma < 360) {
            double thetaX = Math.cos((Math.PI * j.currentOrientation) / 180);
            double thetaY = Math.sin((Math.PI * j.currentOrientation) / 180);
            double rX = (j.posX - i.posX)/xij(i,j), rY=(j.posY - i.posY)/xij(i,j);
            if ((thetaX * rX + thetaY * rY) > Math.cos(Math.PI * gamma / 360)) {
                return gainM_0(Math.PI * gamma / 180);
            }
            return GS_0;
        }
        return 1;

    }

    public static double power(Node i, Node j) {

        return K * Pt * gain(i, j, i.GAMMA_R) * gain(i, j, j.GAMMA_T) / Math.pow(xij(i, j), 2);

    }

    public static boolean isConnected(Node i, Node j) {

        if (power(i, j) > DELTA) {
            return true;
        }
        return false;

    }
}
