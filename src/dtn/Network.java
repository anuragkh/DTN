/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtn;

import gui.SimulationPanel;
import java.util.LinkedList;
import org.apache.commons.math.MathException;

/**
 *
 * @author Renegade
 */
public class Network {

    public static int NUM_NODES;
    private static int GRID_SIZE;
    public static int L;
    public Node[] agentList;
    private LinkedList<Node> infectedList;
    private Region[][] grid;
    private int currentTime;
    private int numGrids;
    private SimulationPanel sim;
    public int y;


    /* Constructor without Simulation */
    public Network(int N, int L, double fracDA, double pTurn, double pRot, int gamma) {

        currentTime = 0;
        y = 1;
        if(fracDA == 0)
            GRID_SIZE = 1;
        else
            GRID_SIZE = (int) Math.ceil(Link.gainM_0(Math.PI * gamma / 180)) ;

        NUM_NODES = N;
        Network.L = L;

        System.out.println("L :" + L + " Grid Size: " + GRID_SIZE);

        /* Initializing agentList */
        agentList = new Node[NUM_NODES];
        agentList[0] = new Node(null, L, 0, pTurn, pRot, 'I', true);
        for (int i = 1; i < agentList.length; i++) {
            agentList[i] = new Node(agentList[0], L, i, pTurn, pRot, 'S', false);
        }


        /* Assigning gamma to all nodes */
        int numDA = (int) (fracDA * agentList.length);
        for (Node n : agentList) {
            if (n.nodeIndex >= NUM_NODES - numDA) {
                n.setGamma(gamma, gamma);
            } else {
                n.setGamma(360, 360);
            }
        }

        /* Initializing Regions with coordinates */
        numGrids = (int) Math.ceil((double)L / GRID_SIZE);
        grid = new Region[numGrids][numGrids];
        for (int i = 0; i < numGrids; i++) {
            for (int j = 0; j < numGrids; j++) {
                grid[i][j] = new Region();
            }
        }
        for (int i = 0; i < agentList.length; i++) {
            grid[(int) (agentList[i].posX / GRID_SIZE)][(int) (agentList[i].posY / GRID_SIZE)].addAgent(agentList[i]);
            agentList[i].regionIndexX = (int) (agentList[i].posX / GRID_SIZE);
            agentList[i].regionIndexY = (int) (agentList[i].posY / GRID_SIZE);
        }

        /* Initializing infectedList */
        infectedList = new LinkedList<Node>();
        infectedList.add(agentList[0]);

        /* Setting simulator to null */
        sim = null;

    }

    /* Constructor with Simulation */
    public Network(int N, int L, double fracDA, double pTurn, double pRot, int gamma, SimulationPanel sim) {

        this(N, L, fracDA, pTurn, pRot, gamma);

        /* Initializing simulator */
        this.sim = sim;

    }

    /* Broadcasts message across network */
    public void broadcast() throws MathException, InterruptedException {

        int i, j;
        
        double newNeighbors;

        while (y < NUM_NODES) {

            newNeighbors = 0;
            currentTime++;

            for (i = 0; i < NUM_NODES; i++) {

                /* Updating each node */
                agentList[i].updatePosition();
                agentList[i].updateCurrentStateDuration();

                /* Updating region for current node */
                int regX = (int) (agentList[i].posX / GRID_SIZE), regY = (int) (agentList[i].posY / GRID_SIZE);
                if (regX != agentList[i].regionIndexX || regY != agentList[i].regionIndexY) {
                    grid[agentList[i].regionIndexX][agentList[i].regionIndexY].removeAgent(agentList[i]);
                    grid[regX][regY].addAgent(agentList[i]);
                    agentList[i].regionIndexX = regX;
                    agentList[i].regionIndexY = regY;
                }

                /* Updating orientation and Direction */
                agentList[i].updateDirection();
                agentList[i].updateOrientation();

                /* Updating state from R to S */
                if (agentList[i].state == 'R' && agentList[i].currentStateDuration > Node.tauR) {
                    y = agentList[i].updateState('S', currentTime, y);
                    agentList[i].currentStateDuration = 0;
                }
            }

            /* Updating state from I to R */
            while (infectedList.element().currentStateDuration > Node.tauI) {
                Node remove = infectedList.remove();
                remove.updateState('R', currentTime, y);
                remove.currentStateDuration = 0;
                for (i = 0; i < remove.isNeighbor.length; i++) {
                    remove.isNeighbor[i] = false;
                }
                if (infectedList.size() == 0) {
                    System.exit(1);
                }
            }

            /* Updating state from S to I */
            LinkedList<Node> temp = new LinkedList<Node>();
            for (Node n : infectedList) {

                n.wasNeighbor = n.isNeighbor;
                for (i = 0; i < n.isNeighbor.length; i++) {
                    n.isNeighbor[i] = false;
                }

                /* Infecting susceptible nodes in nearby regions */
                int regX = n.regionIndexX, regY = n.regionIndexY;
                int regMax = numGrids - 1;

                for (i = ((regX == 0) ? 0 : (regX - 1)); i <= ((regX == regMax) ? regMax : (regX + 1)); i++) {
                    for (j = ((regY == 0) ? 0 : (regY - 1)); j <= ((regY == regMax) ? regMax : (regY + 1)); j++) {
                        for (Node adj : grid[i][j].occupants) {
                            if (Link.isConnected(adj, n)) {
                                if (adj.state == 'S') {
                                    y = adj.updateState('I', currentTime, y);
                                    adj.currentStateDuration = 0;
                                    temp.add(adj);
                                    n.isNeighbor[adj.nodeIndex] = true;
                                }
                                if (!n.wasNeighbor[adj.nodeIndex]) {
                                    newNeighbors++;
                                }
                            }
                        }
                    }
                }

            }

            /* Average number of new neighbors encountered by each node in one time interval */
            newNeighbors /= infectedList.size();

            /* Appending newly infected nodes to the infectedList */
            for (Node n : temp) {
                infectedList.add(n);
            }

            /* Repaint for Simulated Broadcast */
            if (sim != null) {

                (sim).repaint();
                Thread.sleep(10);
            }
        }
        System.out.println("Brodcast Time: " + currentTime);
    }
}
