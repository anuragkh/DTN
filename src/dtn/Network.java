/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dtn;

import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author Renegade
 */
public class Network {

    final int NUM_NODES = 1000;
    final double L = 1000;

    final double GS_0=0.0316;
    Node[] agentList;
    Queue<Node> infectedList;
    
    int currentTime;

    public Network() {
        currentTime = 0;
       
        agentList = new Node[NUM_NODES];
        agentList[0] = new Node(L);
        for (int i = 1; i < agentList.length; i++) {
            agentList[i] = new Node(agentList[0], L);
        }
        for (int i=0; i<agentList.length; i++) {
            agentList[i].initializeConnected();
        }
        infectedList.add(agentList[0]);

    }

    public void broadcast(double pturn, double prot) {

        int i;
        int shift=0;
        int y = 1;
        while(y < NUM_NODES) {
            currentTime++;
            for (i = 0; i < NUM_NODES; i++) {
                agentList[i].updatePosition();
                agentList[i].updateDirection();
                agentList[i].updateOrientation();
                agentList[i].initializeConnected();
                if(agentList[i].state=='R' && agentList[i].currentStateDuration > TR)
                    y = agentList[i].updateState('S', currentTime, y);
            }
            for (i = 0; infectedList[i].currentStateDuration>TI; i++) {
                y=infectedList[i].updateState('R', currentTime, y);
            }
            shift=i;
            for (i=shift; i<currentInfected; i++)
                infectedList[i-shift]=infectedList[i];
            currentInfected-=shift;
            numberInfected=currentInfected;
            for (i=0; i<currentInfected; i++) {
                for (int j=0; j<infectedList[i].numberConnected; j++) {
                    if(infectedList[i].connectedNodes[j].state == 'S') {
                        y=infectedList[i].connectedNodes[j].updateState('I', currentTime, y);
                        infectedList[numberInfected++]=infectedList[i].connectedNodes[j];
                    }
                }
            }
            currentInfected=numberInfected;
        }

    }
    
    public void averageRoutingTime() {
       
        initialDistance=new double[N];
        routingTime=new int[N];
        for (int i=0; i<N; i++) {
            initialDistance[i]=agentList[i].initDist;
            routingTime[i]=agentList[i].timeFirstInfected;
        }
        
        
    }






}
