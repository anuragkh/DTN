/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtn;

import java.util.ArrayList;

/**
 *
 * @author Renegade
 */
public class Region {

    protected ArrayList<Node> occupants;

    public Region() {

        occupants = new ArrayList<Node>();

    }

    boolean addAgent(Node agent) {

        boolean add = occupants.add(agent);
        return add;

    }

    boolean removeAgent(Node agent) {

        boolean remove = occupants.remove(agent);
        return remove;

    }
}
