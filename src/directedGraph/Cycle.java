package directedGraph;

import java.util.ArrayList;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for directed graph cycles
 */
public class Cycle  {

	protected Object[] nodes;

	// The number of times to go through the directed graph node in this directed graph cycle
	protected int loops = 0;
	
	boolean loopHomology = true;

	/**Creates an empty cycle
	 * 
	 */
	public Cycle() {
	}
	
	/**       Creates a directed graph cycle with only one node
	 * @param directedGraphNode as the directed graph node that makes up this directed graph cycle
	 * @param loops as the maximum number of times to go through the directed graph node in this directed graph cycle
	 */
	public Cycle(Object directedGraphNode, int loops){
		super();
		this.nodes = new Object[1];
		this.nodes[0] = directedGraphNode;
		this.loops = loops;
	}

	/**       Creates a directed graph cycle with only directed graph nodes
	 * @param directedGraphNode as the directed graph nodes that make up this directed graph cycle
	 * @param loops as the maximum number of times to go through the directed graph nodes in this directed graph cycle
	 */
	public Cycle(Object[] directedGraphNode, int loops){
		super();
		this.nodes = new Object[directedGraphNode.length];
		for(int i = 0; i < directedGraphNode.length; i++) {
			this.nodes[i] = directedGraphNode[i];
		}
		this.loops = loops;
	}
	
	/**       Creates a directed graph cycle with only node
	 * @param directedGraphNode as the directed graph node that makes up this directed graph cycle
	 * @param loops as the maximum number of times to go through the directed graph node in this directed graph cycle
	 * @param loopHomology as whether the number of loops is random
	 */
	public Cycle(Object directedGraphNode, int loops, boolean loopHomology){
		super();
		this.nodes = new Object[1];
		this.nodes[0] = directedGraphNode;
		this.loops = loops;
		this.loopHomology = loopHomology;
	}

	/**       Creates a directed graph cycle with only directed graph nodes
	 * @param directedGraphNode as the directed graph nodes that make up this directed graph cycle
	 * @param loops as the maximum number of times to go through the directed graph nodes in this directed graph cycle
	 * @param loopHomology as whether the number of loops is random
	 */
	public Cycle(Object[] directedGraphNode, int loops, boolean loopHomology){
		super();
		this.nodes = new Object[directedGraphNode.length];
		for(int i = 0; i < directedGraphNode.length; i++) {
			this.nodes[i] = directedGraphNode[i];
		}
		this.loops = loops;
		this.loopHomology = loopHomology;
	}

	/**
	 * @return The directed graph nodes that make up this directed graph cycle
	 */
	public ArrayList<Object> fun() {
		ArrayList<Object> output = new ArrayList<Object>();
		Object tempDirectedGraphNode;
		int loops;
		if(loopHomology) {
			loops = this.loops;
		} else {
			loops = (int)Math.round(Math.random()*this.loops);
		}
		for(int j = 0; j < loops+1; j++) {
			for(int i = 0; i < nodes.length; i++) {
				tempDirectedGraphNode = nodes[i];
				if(tempDirectedGraphNode instanceof Cycle) {
					output.addAll(((Cycle)tempDirectedGraphNode).fun());
				} else {
					output.add(tempDirectedGraphNode);
				}
			}
		}
		return output;
	}
	
}