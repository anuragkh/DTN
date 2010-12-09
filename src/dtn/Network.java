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
public class Network {

    final int NUM_NODES = 1000;
    final double L = 1000;

    final double GS_0=0.0316;
    Node[] agentList;
    InfectedQueue infectedList;
    
    int currentTime;

    public Network() {
        currentTime = 0;
       
        agentList = new Node[NUM_NODES];
        agentList[0] = new Node(L);
        for (int i = 1; i < agentList.length; i++) {
            agentList[i] = new Node(agentList[0], L);
        }
        for (int i=0; i<agentList.length; i++) {
            //agentList[i].initializeConnected();
        }

        infectedList = new InfectedQueue(NUM_NODES);
        infectedList.add(agentList[0]);

    }

    public void broadcast(double pturn, double prot) throws MathException {

        int i;
        int shift=0;
        int y = 1;
        while(y < NUM_NODES) {
            currentTime++;
            for (i = 0; i < NUM_NODES; i++) {
                agentList[i].updatePosition();
                agentList[i].updateDirection();
                agentList[i].updateOrientation();
                //agentList[i].initializeConnected();
                if(agentList[i].state=='R' && agentList[i].currentStateDuration > Node.tauR)
                    y = agentList[i].updateState('S', currentTime, y);
            }
            
            for (i = infectedList.front; infectedList.get(i).currentStateDuration>Node.tauI; i++) 
                y= infectedList.remove().updateState('R', currentTime, y);

            for (i=infectedList.front; i<=infectedList.rear; i++) {
                //Update from S to I
            }
            
        }

    }

}
