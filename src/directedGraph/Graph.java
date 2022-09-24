package directedGraph;

import java.util.ArrayList;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for directed graphs
 */
public class Graph {

	// The directed graph cycles that make up this directed graph
	Cycle[] directedGraphCycles;
	
	/**       Creates a directed graph
	 * @param directedGraphCycle as the directed graph cycle that makes up this directed graph
	 */
	public Graph(Cycle directedGraphCycle){
		this.directedGraphCycles = new Cycle[1];
			this.directedGraphCycles[0] = directedGraphCycle;
	}
	
	/**
	 * @return The object array that represent a traversal over this directed graph
	 */
	public ArrayList<Object> fun() {
		ArrayList<Object> output = new ArrayList<Object>();
		for(int i = 0; i < directedGraphCycles.length; i++) {
			output.addAll(directedGraphCycles[i].fun());
		}
		return output;
	}
}