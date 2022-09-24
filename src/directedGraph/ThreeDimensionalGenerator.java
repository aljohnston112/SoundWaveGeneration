package directedGraph;

import notes.Note;

public class ThreeDimensionalGenerator {

	/**        Generates one directed graph cycle, where one node loops
	 * @param  node as the directed graph node that will make up the generated directed graph cycle
	 * @param  loops as the maximum number of times the nodes should loop
	 * @param  skippable as whether or not the returned cycle can be null
	 * @param  loopingNodeHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return one directed graph cycle, where one node loops
	 */
	public static ThreeDimensionalCycle generateSingleNodeLoopCycle(ThreeDimensionalNode node, int loops, boolean skippable, boolean loopingNodeHomogenenuity) {
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
	public static ThreeDimensionalCycle generateLinearCycle(ThreeDimensionalNode[] nodes) {
		return new ThreeDimensionalCycle(nodes, 0);
	}

	/**        Generates a split node
	 * @param  nodes as the nodes to follow the starting node
	 * @param  startingNode as the node that the cycle starts at before going to the next nodes	 
	 * @return A cycle where the first node is followed by multiple nodes
	 */
	public static ThreeDimensionalCycle generateBranchedNodeCycle(ThreeDimensionalNode[] nodes, ThreeDimensionalNode[] startingNode) {
		int size = Math.max(nodes.length, startingNode.length);
		int minSize = Math.min(nodes.length, startingNode.length);
		ThreeDimensionalNode[][] threeDimensionalNodes = new ThreeDimensionalNode[size][];
		ThreeDimensionalNode[] tempThreeDimensionalNodeArray;
		for(int i = 0; i < minSize; i++) {
			tempThreeDimensionalNodeArray = new ThreeDimensionalNode[2];
			tempThreeDimensionalNodeArray[0] = startingNode[i];			
			tempThreeDimensionalNodeArray[1] = nodes[i];
			threeDimensionalNodes[i] = tempThreeDimensionalNodeArray;
		}
		if(nodes.length < startingNode.length) {
			for(int i = minSize; i < startingNode.length; i++) {
				tempThreeDimensionalNodeArray = new ThreeDimensionalNode[2];
				tempThreeDimensionalNodeArray[0] = startingNode[i];
				Note note = new Note(1);
				note.setDuration(((Note)nodes[i-1].node).getDuration());
				ThreeDimensionalNode threeDimensionalNode = new ThreeDimensionalNode(note);
				tempThreeDimensionalNodeArray[1] = threeDimensionalNode;
				threeDimensionalNodes[i] = tempThreeDimensionalNodeArray;
			}
		} else {
			for(int i = minSize; i < nodes.length; i++) {
				tempThreeDimensionalNodeArray = new ThreeDimensionalNode[2];
				Note note = new Note(1);
				note.setDuration(((Note)startingNode[i-1].node).getDuration());
				ThreeDimensionalNode threeDimensionalNode = new ThreeDimensionalNode(note);
				tempThreeDimensionalNodeArray[0] = threeDimensionalNode;			
				tempThreeDimensionalNodeArray[1] = nodes[i];
				threeDimensionalNodes[i] = tempThreeDimensionalNodeArray;
			}
		}
		return new ThreeDimensionalCycle(threeDimensionalNodes, 0);
	}

	/**        Generates a split node where multiple nodes are followed by the end node
	 * @param  nodes as the starting nodes
	 * @param  endNode the node where all the starting nodes end up at 	 
	 * @return A cycle where multiple nodes are followed by a single node
	 */
	public static ThreeDimensionalCycle generateMeetAtNodeCycle(ThreeDimensionalNode[] nodes, ThreeDimensionalNode[] endingNode) {
		int size = Math.max(nodes.length, endingNode.length);
		int minSize = Math.min(nodes.length, endingNode.length);
		ThreeDimensionalNode[][] threeDimensionalNodes = new ThreeDimensionalNode[size][];
		ThreeDimensionalNode[] tempThreeDimensionalNodeArray;
		for(int i = 0; i < minSize; i++) {
			tempThreeDimensionalNodeArray = new ThreeDimensionalNode[2];
			tempThreeDimensionalNodeArray[1] = endingNode[i];			
			tempThreeDimensionalNodeArray[0] = nodes[i];
			threeDimensionalNodes[i] = tempThreeDimensionalNodeArray;
		}
		if(nodes.length < endingNode.length) {
			for(int i = minSize; i < endingNode.length; i++) {
				tempThreeDimensionalNodeArray = new ThreeDimensionalNode[2];
				tempThreeDimensionalNodeArray[0] = endingNode[i];
				Note note = new Note(1);
				note.setDuration(((Note)nodes[i-1].node).getDuration());
				ThreeDimensionalNode threeDimensionalNode = new ThreeDimensionalNode(note);
				tempThreeDimensionalNodeArray[1] = threeDimensionalNode;
				threeDimensionalNodes[i] = tempThreeDimensionalNodeArray;
			}
		} else {
			for(int i = minSize; i < nodes.length; i++) {
				tempThreeDimensionalNodeArray = new ThreeDimensionalNode[2];
				Note note = new Note(1);
				note.setDuration(((Note)endingNode[i-1].node).getDuration());
				ThreeDimensionalNode threeDimensionalNode = new ThreeDimensionalNode(note);
				tempThreeDimensionalNodeArray[1] = threeDimensionalNode;			
				tempThreeDimensionalNodeArray[0] = nodes[i];
				threeDimensionalNodes[i] = tempThreeDimensionalNodeArray;
			}
		}
		return new ThreeDimensionalCycle(threeDimensionalNodes, 0);
	}

}