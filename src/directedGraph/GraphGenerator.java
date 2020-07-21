package directedGraph;

import java.util.ArrayList;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for generating directed graphs
 */
public class GraphGenerator {

	/**        Generates one directed graph cycle, where one Object loops
	 * @param  Object as the directed graph Object that will make up the generated directed graph cycle
	 * @param  loops as the maximum number of times the nodes should loop
	 * @param  skippable as whether or not the returned cycle can be null
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return one directed graph cycle, where one Object loops
	 */
	public static Cycle generateSingleNodeLoopCycle(Object Object, int loops, boolean skippable, boolean loopingObjectHomogenenuity) {
		int randomLoops;
		if(skippable) {
			randomLoops = (int)Math.round(Math.random()*(loops+1)-1);
		} else {
			randomLoops = (int)Math.round(Math.random()*(loops));
		}
		if(randomLoops == -1) {
			return null;
		}
		if(Object instanceof Cycle) {
			((Cycle) Object).loops = randomLoops;
			return (Cycle) Object;
		}
		return new Cycle(Object, randomLoops, loopingObjectHomogenenuity);
	}

	/**        Generates a chansey cycle where the chance of getting any specific Object is random
	 * @param  nodes as the nodes to choose from 	
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return A chansey cycle where the chance of getting any Object is random
	 */
	public static ChanseyCycle generateChanseyCycle(Object[] nodes, boolean loopingObjectHomogenenuity) {
		double randomProbability = 0.0;
		double totalProbability = 0;
		double[] probabilities = new double[nodes.length];
		boolean wrongProbability = true;
		for(int i = 0; i < nodes.length-1; i++) {
			while(i != nodes.length && wrongProbability) {
				randomProbability = Math.random();
				if((randomProbability + totalProbability) < 1.0) {
					wrongProbability = false;
				}
			}
			wrongProbability = true;
			probabilities[i] = randomProbability;
			totalProbability += randomProbability;
		}
		probabilities[probabilities.length-1] = 1.0-totalProbability;
		ChanseyCycle chanseyObject = null;
		try {
			chanseyObject = new ChanseyCycle(nodes, probabilities, 0);
		} catch (Exception e) {
			e.printStackTrace();
		};
		return chanseyObject;
	}

	/**        Generates a split Object where the chance of getting any specific Object is random
	 * @param  nodes as the nodes to choose from
	 * @param  startingObject as the Object that the cycle starts at before choosing a random Object 	 
	 * @return A cycle where the first Object is followed by a Object at random
	 */
	public static Cycle generateBranchedNodeCycle(Object[] nodes, Object startingObject) {
		ChanseyCycle chanseyCycle = generateChanseyCycle(nodes, true);
		Object[] output = {startingObject, chanseyCycle};
		return new Cycle(output, 0);
	}


	/**        Generates a split Object where the chance of of the starting Object is random and all nodes meet at the end Object
	 * @param  nodes as the nodes to choose from as the starting nodes
	 * @param  endObject the Object where all the nodes end up at 	 
	 * @return A cycle where the chance of getting any specific Object is random
	 */
	public static Cycle generateMeetAtNodeCycle(Object[] nodes, Object endObject) {
		ChanseyCycle chanseyCycle = generateChanseyCycle(nodes, true);
		Object[] output = {chanseyCycle, endObject};
		return new Cycle(output, 0);
	}

	/**        Generates one directed graph cycle, where each Object loops and is part of a linear cycle
	 * @param  Object as the directed graph nodes that will make up the generated directed graph cycle
	 * @param  loops as the maximum number of times the nodes should loop
	 * @param  skippablenodes as whether or not individual nodes can be skipped 	 
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return one directed graph cycle, where one Object loops
	 */
	public static Cycle generateLinearSingleNodeLoopCycle(Object[] nodes, int loops, boolean skippablenodes, boolean loopingObjectHomogenenuity) {
		ArrayList<Cycle> ObjectCycles = new ArrayList<Cycle>();
		Cycle tempCycle;
		if(skippablenodes) {
			for(int i = 0; i < nodes.length; i++) {
				tempCycle = generateSingleNodeLoopCycle(nodes[i], loops, skippablenodes, loopingObjectHomogenenuity);
				if(tempCycle != null) {
					ObjectCycles.add(tempCycle);
				}
			}
		} else {
			for(int i = 0; i < nodes.length; i++) {
				ObjectCycles.add(generateSingleNodeLoopCycle(nodes[i], loops, skippablenodes, loopingObjectHomogenenuity));
			}
		}
		return new Cycle(ObjectCycles.toArray(new Object[ObjectCycles.size()]), 0);
	}

	/**        Generates one directed graph cycle, where the chance of getting each single looped Object is random
	 * @param  Object as the directed graph nodes that will make up the generated directed graph cycle and the first Object is the starting Object
	 * @param  startingObject as the Object that the cycle starts at before choosing a random Object 	 
	 * @param  loops as the maximum number of times the nodes should loop
	 * @param  skippablenodes as whether or not individual nodes can be left out of the branch
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return one directed graph cycle, where one Object loops
	 */
	public static Cycle generateBranchedSingleNodeLoopCycle(Object[] nodes, Object startingObject, int loops, boolean skippablenodes, boolean loopingObjectHomogenenuity) {
		ArrayList<Object> loopednodes = new ArrayList<Object>();
		Object temp;
		for(int i = 0; i < nodes.length; i++) {
			temp = generateSingleNodeLoopCycle(nodes[i], loops, skippablenodes, loopingObjectHomogenenuity);
			if(temp != null) {
				loopednodes.add(temp);
			}
		}
		return generateBranchedNodeCycle(loopednodes.toArray(new Object[loopednodes.size()]), generateSingleNodeLoopCycle(startingObject, loops, false, loopingObjectHomogenenuity));
	}

	/**        Generates a split Object where the chance of of the starting Object is random and all nodes meet at the end Object. All nodes loop by themselves.
	 * @param  nodes as the nodes to choose from as the starting nodes
	 * @param  endObject the Object where all the nodes end up at
	 * @param  loops as the maximum number of times the nodes should loop
	 * @param  skippablenodes as whether or not individual nodes can be skipped
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return A cycle where the chance of getting any specific Object is random
	 */
	public static Cycle generateMeetAtSingleNodeLoopCycle(Object[] nodes, Object endObject, int loops, boolean skippablenodes, boolean loopingObjectHomogenenuity) {
		ArrayList<Object> loopednodes = new ArrayList<Object>();
		Object temp;
		for(int i = 0; i < nodes.length; i++) {
			temp = generateSingleNodeLoopCycle(nodes[i], loops, skippablenodes, loopingObjectHomogenenuity);
			if(temp != null) {
				loopednodes.add(temp);
			}
		}
		ChanseyCycle chanseyCycle = generateChanseyCycle(loopednodes.toArray(new Object[loopednodes.size()]), loopingObjectHomogenenuity);
		loopednodes.add(0, chanseyCycle);
		return generateMeetAtNodeCycle(loopednodes.toArray(new Object[loopednodes.size()]), generateSingleNodeLoopCycle(endObject, loops, false, loopingObjectHomogenenuity));

	}

	/**        Generates a linear cycle where the nodes follow one after the other and then loop
	 * @param  nodes as the array of nodes in the order that they will appear in the linear loop cycle 	 
	 * @param  loops as the maximum number of times the linear cycle should loop 	 
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return A linear cycle where the nodes follow one after the other and then loop
	 */
	public static Cycle generateLinearLoopCycle(Object[] nodes, int loops, boolean loopingObjectHomogenenuity) {
		Object linearCycle = new Cycle(nodes, 0);
		return generateSingleNodeLoopCycle(linearCycle, loops, false, loopingObjectHomogenenuity);
	}

	/**        Generates subgroups of nodes with the same starting Object
	 * @param  nodes as the nodes to get subgroups from
	 * @param  groupSize as the number of nodes in a group 	 
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return Every group of nodes with the same starting Object and the specified group size
	 */
	private static ArrayList<Object[]> generateSubGroupsForBranch(Object[] nodes, int groupSize){
		ArrayList<Object[]> ObjectGroups = new ArrayList<Object[]>();
		for(int j = 0; j < nodes.length-1; j++) {
			Object[] groups = new Object[nodes.length-1-(groupSize-2)];
			groups[0] = nodes[0];
			for(int offset = 1; offset < nodes.length-1-(groupSize-2); offset++) {
				groups[offset] = nodes[offset+j];
			}
			ObjectGroups.add(groups);
		}
		return ObjectGroups;
	}

	/**        Generates a split Object where the chance of getting any specific Object is random and some of the nodes are linear loops with the same start Object
	 * @param  nodes as the nodes to choose from except the first Object which is the start Object
	 * @param  loops as the maximum number of times the linear loops can loop
	 * @param  skippable as whether or not linear loops can be skipped
	 * @param  groupSize as the number of nodes in the linear loops 	 
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return A cycle where the first Object is followed by a Object at random, where some nodes are linear loops and others are linear graph ends
	 */
	public static Cycle generateBranchedNodeLinearLoopCycle(Object[] nodes, int loops, boolean skippable, int groupSize, boolean loopingObjectHomogenenuity) {
		ArrayList<Object[]> subGroupsArrayLists = generateSubGroupsForBranch(nodes, groupSize);
		ArrayList<Object> loopednodes = new ArrayList<Object>();
		ArrayList<Object> unloopednodes = new ArrayList<Object>();
		Cycle temp;
		for(int i = 0; i < subGroupsArrayLists.size(); i++) {
			temp = generateSingleNodeLoopCycle(new Cycle(subGroupsArrayLists.get(i), 0), loops, skippable, loopingObjectHomogenenuity);
			if(temp != null) {
				if(temp.loops > 0) {
					loopednodes.add(temp);
				} else {
					unloopednodes.add(temp);
				}
			}
		}
		if(unloopednodes.size() != 0) {
			Object unLoopedChansey = new ChanseyCycle(unloopednodes.toArray(new Object[unloopednodes.size()]), 0);
			loopednodes.add(unLoopedChansey);
			return new Cycle(loopednodes.toArray(new Object[loopednodes.size()]), 0);
		}
		if(loopednodes.size()!= 0) {
			return new ChanseyCycle(loopednodes.toArray(new Object[loopednodes.size()]), 0);
		}
		return new ChanseyCycle(unloopednodes.toArray(new Object[unloopednodes.size()]), 0);
	}

	/**        Generates a split Object where the chance of getting any specific Object is random and all of the nodes are linear loops
	 * @param  nodes as the nodes to choose from
	 * @param  startingObject as the Object that the cycle starts at before choosing a random Object
	 * @param  loops as the maximum number of times the linear loops can loop
	 * @param  skippable as whether or not linear loops can be skipped
	 * @param  groupSize as the number of nodes in the linear loops 	 
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return A cycle where the first Object is followed by a Object at random, where all nodes are linear loops
	 */
	public static Cycle generateLoopedBranchedNodeLinearLoopCycle(Object[] nodes, Object startingObject, int loops, boolean skippable, int groupSize, boolean loopingObjectHomogenenuity) {
		Object cycle = generateBranchedNodeCycle(nodes, startingObject);
		return generateSingleNodeLoopCycle(cycle, loops, skippable, loopingObjectHomogenenuity);
	}

	/**        Generates subgroups of nodes with the same ending Object
	 * @param  nodes as the nodes to get subgroups from
	 * @param  groupSize as the number of nodes in a group 	 
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return Every group of nodes with the same ending Object and the specified group size
	 */
	private static ArrayList<Object[]> generateSubGroupsForMeet(Object[] nodes, int groupSize){
		ArrayList<Object[]> ObjectGroups = new ArrayList<Object[]>();
		for(int j = 0; j < nodes.length-1; j++) {
			Object[] groups = new Object[nodes.length-1-(groupSize-2)];
			groups[groups.length-1] = nodes[nodes.length-1];
			for(int offset = 0; offset < nodes.length-2-(groupSize-2); offset++) {
				groups[offset] = nodes[offset+j];
			}
			ObjectGroups.add(groups);
		}
		return ObjectGroups;
	}

	/**        Generates a split Object where the chance of the starting Object is random and all nodes meet at the end Object which connects to some of the starting nodes as linear loops
	 * @param  nodes as the nodes to choose from as the starting nodes except the last Object which is the end Object
	 * @param  loops as the maximum number of times the linear loops can loop
	 * @param  skippable as whether or not linear loops can be skipped
	 * @param  groupSize as the number of nodes in the linear loops 	 
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return A cycle where the chance of getting any specific Object is random
	 */
	public static Cycle generateMeetAtNodeLinearLoopCycle(Object[] nodes, int loops, boolean skippable, int groupSize, boolean loopingObjectHomogenenuity) {
		ArrayList<Object[]> subGroupsArrayLists = generateSubGroupsForMeet(nodes, groupSize);
		ArrayList<Object> loopednodes = new ArrayList<Object>();
		ArrayList<Object> unloopednodes = new ArrayList<Object>();
		Cycle temp;
		for(int i = 0; i < subGroupsArrayLists.size(); i++) {
			temp = generateSingleNodeLoopCycle(new Cycle(subGroupsArrayLists.get(i), 0), loops, skippable, loopingObjectHomogenenuity);
			if(temp != null) {
				if(temp.loops > 0) {
					loopednodes.add(temp);
				} else {
					unloopednodes.add(temp);
				}
			}
		}
		if(unloopednodes.size() != 0) {
			Object unLoopedChansey = new ChanseyCycle(unloopednodes.toArray(new Object[unloopednodes.size()]), 0);
			loopednodes.add(unLoopedChansey);
			return new Cycle(loopednodes.toArray(new Object[loopednodes.size()]), 0);
		}
		if(loopednodes.size()!= 0) {
			return new ChanseyCycle(loopednodes.toArray(new Object[loopednodes.size()]), 0);
		}
		return new ChanseyCycle(unloopednodes.toArray(new Object[unloopednodes.size()]), 0);
	}

	/**        Generates a meeting Object where the chance of getting any specific Object is random and all of the nodes are linear loops
	 * @param  nodes as the nodes to choose from
	 * @param  endingObject as the Object that the cycle ends at before choosing another random Object
	 * @param  loops as the maximum number of times the linear loops can loop
	 * @param  skippable as whether or not linear loops can be skipped
	 * @param  groupSize as the number of nodes in the linear loops 	 
	 * @param  loopingObjectHomogenenuity as whether or not the nodes will loop the same amount of times every loop
	 * @return A cycle where the first Object is followed by a Object at random, where all nodes are linear loops
	 */
	public static Cycle generateLoopedMeetAtNodeLinearLoopCycle(Object[] nodes, Object endingObject, int loops, boolean skippable, int groupSize, boolean loopingObjectHomogenenuity) {
		Object cycle = generateMeetAtNodeCycle(nodes, endingObject);
		return generateSingleNodeLoopCycle(cycle, loops, skippable, loopingObjectHomogenenuity);
	}
	
}