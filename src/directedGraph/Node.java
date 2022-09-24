package directedGraph;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for directed graph nodes
 */
public class Node {

	// A node from a directed graph
	Object node;

	/**Creates an empty directed graph node
	 * 
	 */
	public Node() {
		
	}
	
	/**       Creates a directed graph node
	 * @param node as a node from a directed graph
	 */
	public Node(Object node){
		this.node = node;
	}
}