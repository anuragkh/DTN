/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtn;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.*;
/**
 *
 * @author Ayush and Anurag
 */
public class Node {

    static double pRot;
    static double pTurn;
    static double velocity = 0.1;
    static double tauI = 500;
    static double tauR = 50;

    char state;
    int timeFirstInfected;
    int currentStateDuration;
    int regionIndexX;
    int regionIndexY;

    boolean hasBeenInfected;
    
    double posX, posY;
    double initDist;

    /* Direction Parameters */
    int currentDirection;
    int currentOrientation;

    //poisson distribution function
    PoissonDistributionImpl obj= new PoissonDistributionImpl(180);

    public Node ( double L ) {

        state = 'I';
        posX = Math.random()*L;
        posY = Math.random()*L;
        currentDirection = (int) Math.random()*180;
        currentOrientation = (int) Math.random()*180;
        hasBeenInfected = true;
        currentStateDuration = 0;

    }

    public Node ( Node reference, double L ) {

        state = 'S';
        posX = Math.random()*L;
        posY = Math.random()*L;
        currentDirection = (int) Math.random() * 180;
        currentOrientation = (int) Math.random()* 180;

        initDist = Math.sqrt(Math.pow(posX-reference.posX, 2) + Math.pow(posY-reference.posY, 2));

        hasBeenInfected = false;
        currentStateDuration = 0;

    }

    public void updatePosition() {

        posX = posX + velocity * Math.cos(Math.PI*currentDirection/180);
        posY = posY + velocity * Math.sin(Math.PI*currentDirection/180);
    }

    public void updateDirection () throws MathException {

        if(Math.random()< pTurn) {

            currentDirection = obj.inverseCumulativeProbability(pTurn);
        }
    }

    public void updateOrientation() throws MathException {

       if(Math.random()< pRot) {

            currentDirection = obj.inverseCumulativeProbability(pRot);
        }
    }

    public int updateState ( char newState, int time, int y ) {

        if(state==newState)
            currentStateDuration++;

        else {
            currentStateDuration=0;
            state=newState;
        }

        if(!hasBeenInfected && state=='I') {
            hasBeenInfected=true;
            timeFirstInfected=time;
            y++;
        }

        return y;
    }

}
