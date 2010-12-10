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
    
    void addAgent(Node agent) {
       
        occupants.add(agent); 
        
    }
    
    void removeAgent(Node agent) {
        
        occupants.remove(agent);
        
    }

}
