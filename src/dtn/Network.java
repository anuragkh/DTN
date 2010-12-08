/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dtn;

/**
 *
 * @author Renegade
 */
public class Network {

    final double VEL = 0.1;
    final int N= 1000;
    final double L=1000;
    final int TI = 500;
    final int TR = 50;
    final double GS_0=0.0316;
    Node agentList[];
    Node infectedList[];
    double initialDistance[];
    int currentInfected;
    int routingTime[];
    int currentTime;
    int y;

    public Network() {

        y = 1;
        currentTime = 0;
        infectedList = new Node[N];
        agentList = new Node[N];
        agentList[0]=new Node(L);
        for (int i=1; i<N; i++)
            agentList[i]=new Node(agentList[0], L);
        for (int i=0; i<N; i++)
            agentList[i].initializeConnected();
        infectedList[0] = agentList[0];
        currentInfected=1;

    }

    public void broadcast(double pturn, double prot) {

        int i;
        int shift=0;
        int numberInfected=currentInfected;
        while(y < N) {
            currentTime++;
            for (i=0; i<N; i++) {
                agentList[i].updatePosition(VEL);
                agentList[i].updateDirection(pturn);
                agentList[i].updateOrientation(prot);
                agentList[i].initializeConnected();
                if(agentList[i].state=='R' && agentList[i].currentStateDuration > TR)
                    y=agentList[i].updateState('S', currentTime, y);
            }
            for (i=0; infectedList[i].currentStateDuration>TI; i++) {
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
