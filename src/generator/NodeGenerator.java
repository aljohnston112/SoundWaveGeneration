package generator;

import dynamics.Dynamics;
import graph.ThreeDimensionalCycle;
import graph.ThreeDimensionalGraphGenerator;
import notes.Scale;
import rhythm.Tempo;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for generating random nodes for directed graphs
 */
public class NodeGenerator {

	// TODO Create working node generator
	
	/**        Gets a three dimensional cycle to start off music generation
	 * @param  scale as the scale that contains the notes to choose from
	 * @param  tempo as the tempo that contains the times to choose from
	 * @param  dynamics as the dynamics that contains the amplitudes to choose from
	 * @param  samplesPerSecond as the sample rate
	 * @param  loops as the maximum number of loops that a cycle can loop
	 * @return a three dimensional cycle to start off music generation
	 */
	public static ThreeDimensionalCycle getFirstNode(Scale scale, Tempo tempo, int bottomFrequencyIndex, int topFrequencyIndex, Dynamics dynamics, float samplesPerSecond, int loops) {

		int choice = 3;//(int)Math.round(Math.random()*3);
		Object[] nodes = null;
		Object[] singleNode = null;
		if(choice != 0) {
			nodes = new Object[2];
			nodes[0] = NoteGenerator.getNote(scale, bottomFrequencyIndex, topFrequencyIndex, tempo, dynamics, samplesPerSecond);
			nodes[1] = NoteGenerator.getNote(scale, bottomFrequencyIndex, topFrequencyIndex, tempo, dynamics, samplesPerSecond);	
		}
		if(choice == 2 || choice == 3) {
			singleNode = new Object[1];
			singleNode[0] = NoteGenerator.getNote(scale, bottomFrequencyIndex, topFrequencyIndex, tempo, dynamics, samplesPerSecond);
		}

		switch(choice) {
		case 0:
			return ThreeDimensionalGraphGenerator.generateSingleNodeLoopCycle(NoteGenerator.getNote(scale, bottomFrequencyIndex, topFrequencyIndex, tempo, dynamics, samplesPerSecond), loops, false, true);	
		case 1:
			return ThreeDimensionalGraphGenerator.generateSingleNodeLoopCycle(ThreeDimensionalGraphGenerator.generateLinearCycle(nodes), loops, false, true);
		case 2:
			return ThreeDimensionalGraphGenerator.generateSingleNodeLoopCycle(ThreeDimensionalGraphGenerator.generateBranchedNodeCycle(nodes, singleNode), loops, false, true);
		case 3:
			return ThreeDimensionalGraphGenerator.generateSingleNodeLoopCycle(ThreeDimensionalGraphGenerator.generateMeetAtNodeCycle(nodes, singleNode), loops, false, true);
		default:
			return ThreeDimensionalGraphGenerator.generateSingleNodeLoopCycle(NoteGenerator.getNote(scale, bottomFrequencyIndex, topFrequencyIndex, tempo, dynamics, samplesPerSecond), loops, false, true);	
		}
	}

	public static ThreeDimensionalCycle appendNode(ThreeDimensionalCycle cycle, Scale scale, int bottomFrequencyIndex, int topFrequencyIndex, Tempo tempo, Dynamics dynamics, float samplesPerSecond, int loops) {
		int choice = 0;//(int)Math.round(Math.random()*3);
		Object[] nodes = null;
		switch(choice) {
		case 0:
			ThreeDimensionalCycle[][] outputCycles = new ThreeDimensionalCycle[cycle.nodes[cycle.nodes.length-1].length][];
			for(int i = 0; i < cycle.nodes[cycle.nodes.length-1].length; i++) {
				nodes = new Object[2];
				nodes[0] = cycle.nodes[cycle.nodes.length-1][i];
				nodes[1] = ThreeDimensionalGraphGenerator.generateSingleNodeLoopCycle(NoteGenerator.getNote(scale, bottomFrequencyIndex, topFrequencyIndex, tempo, dynamics, samplesPerSecond), loops, false, true);
				outputCycles[i] = new ThreeDimensionalCycle[1];
				outputCycles[i][0] = ThreeDimensionalGraphGenerator.generateLinearCycle(nodes);
			}
			return new ThreeDimensionalCycle(outputCycles, loops);
		case 1:
			ThreeDimensionalCycle[][] outputCycles2 = new ThreeDimensionalCycle[cycle.nodes[cycle.nodes.length-1].length][];
			for(int i = 0; i < cycle.nodes[cycle.nodes.length-1].length; i++) {
				nodes = new Object[2];
				nodes[0] = cycle.nodes[cycle.nodes.length-1][i];
				nodes[1] = NoteGenerator.getNote(scale, bottomFrequencyIndex, topFrequencyIndex,  tempo, dynamics, samplesPerSecond);
				outputCycles2[i] = new ThreeDimensionalCycle[1];
				outputCycles2[i][0] = ThreeDimensionalGraphGenerator.generateSingleNodeLoopCycle(ThreeDimensionalGraphGenerator.generateLinearCycle(nodes), loops, false, true);
			}
			return new ThreeDimensionalCycle(outputCycles2, loops);
		case 2:
			
		case 3:
		
		default:
			ThreeDimensionalCycle[][] outputCyclesDefault = new ThreeDimensionalCycle[cycle.nodes[cycle.nodes.length-1].length][];
			for(int i = 0; i < cycle.nodes[cycle.nodes.length-1].length; i++) {
				nodes = new Object[2];
				nodes[0] = cycle.nodes[cycle.nodes.length-1][i];
				nodes[1] = ThreeDimensionalGraphGenerator.generateSingleNodeLoopCycle(NoteGenerator.getNote(scale, bottomFrequencyIndex, topFrequencyIndex,  tempo, dynamics, samplesPerSecond), loops, false, true);
				outputCyclesDefault[i] = new ThreeDimensionalCycle[1];
				outputCyclesDefault[i][0] = ThreeDimensionalGraphGenerator.generateLinearCycle(nodes);
			}
			return new ThreeDimensionalCycle(outputCyclesDefault, loops);
		}
	}
}