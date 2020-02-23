package graph;

import java.util.ArrayList;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for graph cycles
 */
public class Cycle  {

	protected Object[] nodes;

	// The number of times to go through the graph node in this graph cycle
	protected int loops = 0;
	
	boolean loopHomology = true;

	/**Creates an empty cycle
	 * 
	 */
	public Cycle() {
	}
	
	/**       Creates a graph cycle with only one node
	 * @param graphNode as the graph node that makes up this graph cycle
	 * @param loops as the maximum number of times to go through the graph node in this graph cycle
	 */
	public Cycle(Object graphNode, int loops){
		super();
		this.nodes = new Object[1];
		this.nodes[0] = graphNode;
		this.loops = loops;
	}

	/**       Creates a graph cycle with only graph nodes
	 * @param graphNode as the graph nodes that make up this graph cycle
	 * @param loops as the maximum number of times to go through the graph nodes in this graph cycle
	 */
	public Cycle(Object[] graphNode, int loops){
		super();
		this.nodes = new Object[graphNode.length];
		for(int i = 0; i < graphNode.length; i++) {
			this.nodes[i] = graphNode[i];
		}
		this.loops = loops;
	}
	
	/**       Creates a graph cycle with only node
	 * @param graphNode as the graph node that makes up this graph cycle
	 * @param loops as the maximum number of times to go through the graph node in this graph cycle
	 * @param loopHomology as whether the number of loops is random
	 */
	public Cycle(Object graphNode, int loops, boolean loopHomology){
		super();
		this.nodes = new Object[1];
		this.nodes[0] = graphNode;
		this.loops = loops;
		this.loopHomology = loopHomology;
	}

	/**       Creates a graph cycle with only graph nodes
	 * @param graphNode as the graph nodes that make up this graph cycle
	 * @param loops as the maximum number of times to go through the graph nodes in this graph cycle
	 * @param loopHomology as whether the number of loops is random
	 */
	public Cycle(Object[] graphNode, int loops, boolean loopHomology){
		super();
		this.nodes = new Object[graphNode.length];
		for(int i = 0; i < graphNode.length; i++) {
			this.nodes[i] = graphNode[i];
		}
		this.loops = loops;
		this.loopHomology = loopHomology;
	}

	/**
	 * @return The graph nodes that make up this graph cycle
	 */
	public ArrayList<Object> fun() {
		ArrayList<Object> output = new ArrayList<Object>();
		Object tempGraphNode;
		int loops;
		if(loopHomology) {
			loops = this.loops;
		} else {
			loops = (int)Math.round(Math.random()*this.loops);
		}
		for(int j = 0; j < loops+1; j++) {
			for(int i = 0; i < nodes.length; i++) {
				tempGraphNode = nodes[i];
				if(tempGraphNode instanceof Cycle) {
					output.addAll(((Cycle)tempGraphNode).fun());
				} else {
					output.add(tempGraphNode);
				}
			}
		}
		return output;
	}
	
}