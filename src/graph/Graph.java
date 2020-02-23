package graph;

import java.util.ArrayList;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for graphs
 */
public class Graph {

	// The graph cycles that make up this graph
	Cycle[] graphCycles;
	
	/**       Creates a graph
	 * @param graphCycle as the graph cycle that makes up this graph
	 */
	public Graph(Cycle graphCycle){
		this.graphCycles = new Cycle[1];
			this.graphCycles[0] = graphCycle;
	}
	
	/**
	 * @return The object array that represent a traversal over this graph
	 */
	public ArrayList<Object> fun() {
		ArrayList<Object> output = new ArrayList<Object>();
		for(int i = 0; i < graphCycles.length; i++) {
			output.addAll(graphCycles[i].fun());
		}
		return output;
	}
}