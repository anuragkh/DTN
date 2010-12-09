/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtn;
import org.apache.commons.math.distribution.*;
/**
 *
 * @author Ayush and Anurag
 */
public class Node {

    static double pRot;
    static double pTurn;
    static double velocity = 0.1;
    static double tauI = 10;
    static double tauR = 20;

    char state;
    int timeFirstInfected;
    int currentStateDuration;

    boolean hasBeenInfected;
    
    double posX, posY;
    double initDist;

    /* Direction Parameters */
    int currentDirection;
    int currentOrientation;

    /*
    Node connectedNodes[];
    int numberConnected;
    */

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

    public void initializeConnected() {

    }

    public void updatePosition() {
        posX = posX + velocity * Math.cos(currentDirection);
        posY = posY + velocity * Math.sin(currentDirection);
    }

    public void updateDirection () {
        //update direction using poisson generator
    }

    public void updateOrientation() {
        //update orientation using poisson generator
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
