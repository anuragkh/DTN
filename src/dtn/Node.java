/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtn;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.*;

/**
 *
 * @author Renegade
 */
public class Node {

    static double pRot;
    static double pTurn;
    static double velocity = 0.1;
    static double tauI = 50;
    static double tauR = 5;

    
    int nodeIndex;

    /* Time parameters */
    public int timeFirstInfected;
    int currentStateDuration;

    /* Distance Parameters */
    public double posX, posY;
    public double initDist;

    /* Direction Parameters */
    int currentDirection;
    int currentOrientation;
    
    /* Stores the state of the Node */
    public char state;

    /* Stores the region to which the node belongs */
    int regionIndexX;
    int regionIndexY;

    boolean hasBeenInfected;
    boolean isDiscovered[];

    /* Poisson distribution function */
    PoissonDistributionImpl obj = new PoissonDistributionImpl(90);

    public Node(double L, int index, double pturn, double prot) {

        nodeIndex = index;
        pTurn = pturn;
        pRot = prot;
        state = 'I';
        posX = Math.random() * L;
        posY = Math.random() * L;
        currentDirection = (int) (Math.random() * 360);
        currentOrientation = (int) (Math.random() * 360);
        hasBeenInfected = true;
        currentStateDuration = 0;
        isDiscovered = new boolean[Network.NUM_NODES];
        for (boolean n : isDiscovered) {
            n = false;
        }

    }

    public Node(Node reference, double L, int index, double pturn, double prot) {

        nodeIndex = index;
        pTurn = pturn;
        pRot = prot;
        state = 'S';
        posX = Math.random() * L;
        posY = Math.random() * L;
        currentDirection = (int) (Math.random() * 360);
        currentOrientation = (int) (Math.random() * 360);

        initDist = Math.sqrt(Math.pow(posX - reference.posX, 2) + Math.pow(posY - reference.posY, 2));

        hasBeenInfected = false;
        currentStateDuration = 0;
        isDiscovered = new boolean[Network.NUM_NODES];
        for (boolean n : isDiscovered) {
            n = false;
        }

    }

    public void updatePosition() {

        double tempX, tempY;

        tempX = posX + velocity * Math.cos(Math.PI * currentDirection / 180);
        tempY = posY + velocity * Math.sin(Math.PI * currentDirection / 180);

        if (tempX < 0 || tempY < 0 || tempX >= Network.L || tempY >= Network.L) {
            currentDirection = 180 + currentDirection;
            posX = posX + velocity * Math.cos(Math.PI * currentDirection / 180);
            posY = posY + velocity * Math.sin(Math.PI * currentDirection / 180);
        } else {
            posX = tempX;
            posY = tempY;
        }

    }

    public void updateDirection() throws MathException {

        if (Math.random() < pTurn) {

            currentDirection += obj.inverseCumulativeProbability(Math.random());

        }
    }

    public void updateOrientation() throws MathException {

        if (Math.random() < pRot) {

            currentDirection += obj.inverseCumulativeProbability(Math.random());
        }
    }

    public int updateState(char newState, int time, int y) {

        if (state != newState) 
            state = newState;

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
