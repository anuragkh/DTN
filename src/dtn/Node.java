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

    char state;
    int timeFirstInfected;
    int currentStateDuration;
    int numberConnected;
    boolean hasBeenInfected;
    double initDist;
    double posX, posY;
    double currentDirection;
    double currentOrientation;
    Node connectedNodes[];

    public Node(double L) {
        state='I';
        posX=Math.random()*L;
        posY=Math.random()*L;
        currentDirection=Math.random()*180;
        currentOrientation=Math.random()*180;
        hasBeenInfected=true;
        currentStateDuration=0;

    }

    public Node(Node reference, double L) {
        state='S';
        posX=Math.random()*L;
        posY=Math.random()*L;
        currentDirection=Math.random()*Math.PI;
        currentOrientation=Math.random()*Math.PI;
        initDist=Math.sqrt(Math.pow(posX-reference.posX, 2) + Math.pow(posY-reference.posY, 2));
        hasBeenInfected=false;
        currentStateDuration=0;

    }

    public void initializeConnected() {

    }

    public void updatePosition(double vel) {
        posX=posX+vel*Math.cos(currentDirection);
        posY=posY+vel*Math.sin(currentDirection);
    }

    public void updateDirection(double pturn) {
        //update direction using poisson generator
    }

    public void updateOrientation(double prot) {
        //update orientation using poisson generator
    }

    public int updateState(char newState, int time, int y) {

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
