package graph;

public class ThreeDimensionalGraphGenerator {

	/**        Generates one graph cycle, where one node loops
	 * @param  node as the graph node that will make up the generated graph cycle
	 * @param  loops as the maximum number of times the nodes should loop
	 * @param  skippable as whether or not the returned cycle can be null
	 * @param  loopingNodeHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return one graph cycle, where one node loops
	 */
	public static ThreeDimensionalCycle generateSingleNodeLoopCycle(Object node, int loops, boolean skippable, boolean loopingNodeHomogenenuity) {
		int randomLoops;
		if(skippable) {
			randomLoops = (int)Math.round(Math.random()*(loops+1)-1);
		} else {
			randomLoops = (int)Math.round(Math.random()*(loops));
		}
		if(randomLoops == -1) {
			return null;
		}
		return new ThreeDimensionalCycle(node, randomLoops, loopingNodeHomogenenuity);
	}

	/**        Generates a linear cycle where the nodes follow one after the other
	 * @param  nodes as the array of nodes in the order that they will appear in the linear cycle 	 
	 * @return A linear cycle where the nodes follow one after the other
	 */
	public static ThreeDimensionalCycle generateLinearCycle(Object[] nodes) {
		return new ThreeDimensionalCycle(nodes, 0);
	}

	/**        Generates a split node
	 * @param  nodes as the nodes to follow the starting node
	 * @param  startingNode as the node that the cycle starts at before going to the next nodes	 
	 * @return A cycle where the first node is followed by multiple nodes
	 */
	public static ThreeDimensionalCycle generateBranchedNodeCycle(Object[] nodes, Object[] startingNode) {
		int size = Math.max(nodes.length, startingNode.length);
		int minSize = Math.min(nodes.length, startingNode.length);
		Object[][] Objects = new Object[size][];
		Object[] tempObjectArray;
		for(int i = 0; i < minSize; i++) {
			tempObjectArray = new Object[2];
			tempObjectArray[0] = startingNode[i];			
			tempObjectArray[1] = nodes[i];
			Objects[i] = tempObjectArray;
		}
		if(nodes.length < startingNode.length) {
			for(int i = minSize; i < startingNode.length; i++) {
				tempObjectArray = new Object[2];
				tempObjectArray[0] = startingNode[i];
				tempObjectArray[1] = null;
				Objects[i] = tempObjectArray;
			}
		} else {
			for(int i = minSize; i < nodes.length; i++) {
				tempObjectArray = new Object[2];
				tempObjectArray[0] = null;			
				tempObjectArray[1] = nodes[i];
				Objects[i] = tempObjectArray;
			}
		}
		return new ThreeDimensionalCycle(Objects, 0);
	}

	/**        Generates a split node where multiple nodes are followed by the end node
	 * @param  nodes as the starting nodes
	 * @param  endNode the node where all the starting nodes end up at 	 
	 * @return A cycle where multiple nodes are followed by a single node
	 */
	public static ThreeDimensionalCycle generateMeetAtNodeCycle(Object[] nodes, Object[] endingNode) {
		int size = Math.max(nodes.length, endingNode.length);
		int minSize = Math.min(nodes.length, endingNode.length);
		Object[][] Objects = new Object[size][];
		Object[] tempObjectArray;
		for(int i = 0; i < minSize; i++) {
			tempObjectArray = new Object[2];
			tempObjectArray[1] = endingNode[i];			
			tempObjectArray[0] = nodes[i];
			Objects[i] = tempObjectArray;
		}
		if(nodes.length < endingNode.length) {
			for(int i = minSize; i < endingNode.length; i++) {
				tempObjectArray = new Object[2];
				tempObjectArray[0] = endingNode[i];
				tempObjectArray[1] = null;
				Objects[i] = tempObjectArray;
			}
		} else {
			for(int i = minSize; i < nodes.length; i++) {
				tempObjectArray = new Object[2];
				tempObjectArray[1] = null;			
				tempObjectArray[0] = nodes[i];
				Objects[i] = tempObjectArray;
			}
		}
		return new ThreeDimensionalCycle(Objects, 0);
	}

}