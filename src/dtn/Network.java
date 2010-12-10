/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dtn;

import java.util.Queue;
import org.apache.commons.math.MathException;

/**
 *
 * @author Renegade
 */
public class Network {

    final int DELTA=1;
    final int NUM_NODES = 1000;
    final double rho  = 0.06;
    final int GRID_SIZE=20;
    final double GS_0=0.0316;

    int L ;
    Node[] agentList;
    Queue<Node> infectedList;

    Region[][] grid;
    
    int currentTime;

    public Network() {
        currentTime = 0;

        L=(int)Math.sqrt(NUM_NODES/rho);
        L=(int) (Math.ceil((double) L / 10) * 10);
        agentList = new Node[NUM_NODES];
        agentList[0] = new Node(L);
        for (int i = 1; i < agentList.length; i++) {
            agentList[i] = new Node(agentList[0], L);
        }
        grid=new Region[L/GRID_SIZE][L/GRID_SIZE];
        /* Initializing Regions with coordinates */
        for (int i=0; i< L/GRID_SIZE; i++)
            for (int j=0; j< L/GRID_SIZE; j++)
                grid[i][j]=new Region();
        for (int i=0; i < agentList.length; i++) {
            grid[(int)agentList[i].posX/GRID_SIZE][(int)agentList[i].posY/GRID_SIZE].addAgent(agentList[i]);
            agentList[i].regionIndexX=(int)agentList[i].posX/GRID_SIZE;
            agentList[i].regionIndexY=(int)agentList[i].posY/GRID_SIZE;
        }

        infectedList.add(agentList[0]);

    }

    public boolean isConnected(Node n1, Node n2) {

        // if power > DELTA
            //return true;
        return false;

    }

    public void broadcast(double pturn, double prot) throws MathException {

        int i, j;
        int y = 1;
        while(y < NUM_NODES) {
            currentTime++;
            for (i = 0; i < NUM_NODES; i++) {
                agentList[i].updatePosition();
                /* Updating region for current node */
                if((int)agentList[i].posX/GRID_SIZE!=agentList[i].regionIndexX || (int)agentList[i].posY/GRID_SIZE!=agentList[i].regionIndexY) {
                    grid[agentList[i].regionIndexX][agentList[i].regionIndexY].removeAgent(agentList[i]);
                    grid[(int)agentList[i].posX/GRID_SIZE][(int)agentList[i].posY/GRID_SIZE].addAgent(agentList[i]);
                    agentList[i].regionIndexX=(int)agentList[i].posX/GRID_SIZE;
                    agentList[i].regionIndexY=(int)agentList[i].posY/GRID_SIZE;
                }
                agentList[i].updateDirection();
                agentList[i].updateOrientation();
                if(agentList[i].state=='R' && agentList[i].currentStateDuration > Node.tauR)
                    y = agentList[i].updateState('S', currentTime, y);
            }
            
            while(infectedList.element().currentStateDuration>Node.tauI)
                y = infectedList.remove().updateState('R', currentTime, y);

            for(Node n : infectedList) {
                int regX = n.regionIndexX, regY = n.regionIndexY;
                int regMax = L/GRID_SIZE - 1;

                for(i = ((regX == 0) ? 0 : (regX - 1)); i <= ((regX == regMax) ? regMax : (regX + 1)); i++) {
                    for(j = ((regY == 0) ? 0 : (regY - 1)); j <= ((regY == regMax) ? regMax : (regY + 1)); j++) {
                        if(i == regX && j == regY)
                            continue;
                        for(Node adj: grid[i][j].occupants) {
                            if(isConnected(n, adj) && adj.state=='S') {
                                y = adj.updateState('I', currentTime, y);
                            }
                        }
                    }
                }
            }
        }
    }

}
