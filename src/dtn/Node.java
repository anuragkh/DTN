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

    static double pRot=0;
    static double pTurn;
    static double velocity = 0.1;
    static double tauI = 500;
    static double tauR = 50;

    char state;
    int nodeIndex;
    int timeFirstInfected;
    int currentStateDuration;
    int regionIndexX;
    int regionIndexY;
    //int newNeighbors;
    boolean hasBeenInfected;
    double posX, posY;
    double initDist;
    boolean isDiscovered[];

    /* Direction Parameters */
    int currentDirection;
    int currentOrientation;
    //poisson distribution function
    PoissonDistributionImpl obj = new PoissonDistributionImpl(180);

    public Node(double L, int index) {

        nodeIndex=index;
        state = 'I';
        posX = Math.random() * L;
        posY = Math.random() * L;
        currentDirection = (int) Math.random() * 180;
        currentOrientation = (int) Math.random() * 180;
        hasBeenInfected = true;
        currentStateDuration = 0;
        isDiscovered=new boolean[Network.NUM_NODES];
        for (boolean n: isDiscovered)
            n=false;
        //newNeighbors=0;

    }

    public Node(Node reference, double L, int index) {

        nodeIndex=index;
        state = 'S';
        posX = Math.random() * L;
        posY = Math.random() * L;
        currentDirection = (int) Math.random() * 180;
        currentOrientation = (int) Math.random() * 180;

        initDist = Math.sqrt(Math.pow(posX - reference.posX, 2) + Math.pow(posY - reference.posY, 2));

        hasBeenInfected = false;
        currentStateDuration = 0;
        isDiscovered=new boolean[Network.NUM_NODES];
        for (boolean n: isDiscovered)
            n=false;
        //newNeighbors=0;

    }

    public void updatePosition() {

        double tempX, tempY;

        tempX = posX + velocity * Math.cos(Math.PI * currentDirection / 180);
        tempY = posY + velocity * Math.sin(Math.PI * currentDirection / 180);

        if (tempX < 0 || tempY < 0) {
            currentDirection = 180 - currentDirection;
            posX = posX + velocity * Math.cos(Math.PI * currentDirection / 180);
            posY = posY + velocity * Math.sin(Math.PI * currentDirection / 180);
        } else {
            posX = tempX;
            posY = tempY;
        }

    }

    public void updateDirection() throws MathException {

        if (Math.random() < pTurn) {

            currentDirection = obj.inverseCumulativeProbability(pTurn);
        }
    }

    public void updateOrientation() throws MathException {

        if (Math.random() < pRot) {

            currentDirection = obj.inverseCumulativeProbability(pRot);
        }
    }

    public int updateState(char newState, int time, int y) {

        if (state == newState) {
            currentStateDuration++;
        } else {
            currentStateDuration = 0;
            state = newState;
        }

        if (!hasBeenInfected && state == 'I') {
            hasBeenInfected = true;
            timeFirstInfected = time;
            y++;
        }

        return y;
    }
}
