package dtn;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.*;

/**
 *
 * @author Renegade
 */
public class Node {

    private static double pRot;
    private static double pTurn;
    private static double velocity = 0.1;
    public static double tauI = 1300;
    public static double tauR = 50;
    
    /* Node ID */
    public int nodeIndex;

    /* Directioanal parameters */
    public int GAMMA_T;
    public int GAMMA_R;
    

    /* Time parameters */
    public int timeFirstInfected;
    public int currentStateDuration;

    /* Distance Parameters */
    public double posX, posY;
    public double initDist;

    /* Direction Parameters */
    public int currentDirection;
    public int currentOrientation;

    /* Stores the state of the Node */
    public char state;

    /* Stores the region to which the node belongs */
    public int regionIndexX;
    public int regionIndexY;
    private boolean hasBeenInfected;
    public boolean[] wasNeighbor;
    public boolean[] isNeighbor;
    public boolean[] wasInfectedNeighbor;
    public boolean[] isInfectedNeighbor;

    /* Poisson distribution function */
    PoissonDistributionImpl poissonDistribution;


    public Node(Node reference, double L, int index, double pTurn, double pRot, char state, boolean hasBeenInfected) {

        nodeIndex = index;
        Node.pTurn = pTurn;
        Node.pRot = pRot;
        this.state = state;
        posX = Math.random() * L;
        posY = Math.random() * L;
        currentDirection = (int) (Math.random() * 360);
        currentOrientation = (int) (Math.random() * 360);
        currentStateDuration = 0;
        this.hasBeenInfected = hasBeenInfected;
        isNeighbor = new boolean[Network.NUM_NODES];
        isInfectedNeighbor = new boolean[Network.NUM_NODES];
        wasNeighbor = new boolean[Network.NUM_NODES];
        for (int i = 0; i < Network.NUM_NODES; i++) {
            isNeighbor[i] = isInfectedNeighbor[i] = false;
        }
        if (reference != null) {
            initDist = Math.sqrt(Math.pow(posX - reference.posX, 2) + Math.pow(posY - reference.posY, 2));
        } else {
            initDist = 0;
        }

        poissonDistribution = new PoissonDistributionImpl(90);

    }

    public void setGamma(int gammaT, int gammaR) {

        GAMMA_T = gammaT;
        GAMMA_R = gammaR;

    }

    public void updatePosition() {

        double tempX, tempY;

        tempX = posX + velocity * Math.cos((Math.PI * currentDirection) / 180);
        tempY = posY + velocity * Math.sin((Math.PI * currentDirection) / 180);

        if (tempX < 0 || tempY < 0 || tempX >= Network.L || tempY >= Network.L) {
            currentDirection = 180 + currentDirection;
            posX = posX + velocity * Math.cos((Math.PI * currentDirection) / 180);
            posY = posY + velocity * Math.sin((Math.PI * currentDirection) / 180);
        } else {
            posX = tempX;
            posY = tempY;
        }

    }

    public void updateDirection() throws MathException {

        if (Math.random() < pTurn) {

            currentDirection += poissonDistribution.inverseCumulativeProbability(Math.random());

        }
    }

    public void updateOrientation() throws MathException {

        if (Math.random() < pRot) {

            currentOrientation += poissonDistribution.inverseCumulativeProbability(Math.random());
        }
    }

    public int updateState(char newState, int time, int y) {

        if (state != newState) {
            state = newState;
        }

        if (!hasBeenInfected && state == 'I') {
            hasBeenInfected = true;
            timeFirstInfected = time;
            y++;
        }

        return y;
    }

    public void updateCurrentStateDuration() {

        currentStateDuration++;

    }
}
