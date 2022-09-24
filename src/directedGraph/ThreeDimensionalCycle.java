package directedGraph;

import java.util.ArrayList;
import java.util.Arrays;

import arrayLists.ArrayLists;
import arrays.Array;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for three dimensional directed graph cycles
 */
public class ThreeDimensionalCycle {

	public Object[][] nodes;

	protected int loops;

	boolean loopHomology = true;

	/**       Makes a three dimensional cycle with one channel
	 * @param nodes as the channel of nodes
	 * @param loops as the number of times to loop
	 */
	public ThreeDimensionalCycle(Object[] nodes, int loops){
		this.nodes = new Object[1][];
		this.nodes[0] = new Object[nodes.length];
		for(int j = 0; j < nodes.length; j++) {
			this.nodes[0][j] = nodes[j];
		}
		this.loops = loops;
	}

	/**       Makes a three dimensional cycle 
	 * @param nodes as the three dimensional node array where each row in a channel of nodes
	 * @param loops as the number of loops to loop
	 */
	public ThreeDimensionalCycle(Object[][] nodes, int loops){
		int columns = nodes[0].length; //Array.getNumberOfColumns(nodes);
		this.nodes = new Object[nodes.length][];
		for(int i = 0; i < nodes.length; i++) {
			this.nodes[i] = new Object[columns];
			for(int j = 0; j < nodes[i].length; j++) {
				this.nodes[i][j] = nodes[i][j];
			}
		}
		this.loops = loops;
	}

	/**       Make a single node ThreeDimensionalCycle
	 * @param node as the single nodes 
	 * @param loops as the maximum number of loops
	 * @param loopHomology as whether or not the number of loops should always be loops
	 */
	public ThreeDimensionalCycle(Object node, int loops, boolean loopHomology){
		this.nodes = new Object[1][1];
		this.nodes[0][0] = node;
		this.loops = loops;
		this.loopHomology = loopHomology;
	}
	
	/**       Make a single channel ThreeDimensionalCycle
	 * @param nodes as the single channel of nodes 
	 * @param loops as the maximum number of loops
	 * @param loopHomology as whether or not the number of loops should always be loops
	 */
	public ThreeDimensionalCycle(Object[] nodes, int loops, boolean loopHomology){
		this.nodes = new Object[1][];
		this.nodes[0] = new Object[nodes.length];
		for(int j = 0; j < nodes.length; j++) {
			this.nodes[0][j] = nodes[j];
		}
		this.loops = loops;
		this.loopHomology = loopHomology;
	}

	/**       Makes a multi channel ThreeDimensionalCycle
	 * @param nodes as the 3D node array to make up this cycle
	 * @param loops as the maximum number of loops
	 * @param loopHomology as whether or not the loops will always equal loops
	 */
	public ThreeDimensionalCycle(Object[][] nodes, int loops, boolean loopHomology){
		int columns = nodes[0].length; //Array.getNumberOfColumns(nodes);
		this.nodes = new Object[nodes.length][];
		for(int i = 0; i < nodes.length; i++) {
			this.nodes[i] = new Object[columns];
			for(int j = 0; j < nodes[i].length; j++) {
				this.nodes[i][j] = nodes[i][j];
			}
		}
		this.loops = loops;
		this.loopHomology = loopHomology;
	}

	/**
	 * @return a 3D arraylist with the output of this 3D directed graph cycle
	 */
	public ArrayList<ArrayList<Object>> fun() {
		
		//TODO possibly add threads to calculate the functions of the cycles
		// Also, Object would need a fun() method for the threads to return the nodes in order
		ArrayList<ArrayList<Object>> output = new ArrayList<ArrayList<Object>>();
		Object[] tempNodeArray;
		ArrayList<ArrayList<Object>> tempOutput = new ArrayList<ArrayList<Object>>();
		int nullColumnsToAdd;
		Object[] nullArray;
		int nullRowsToAdd;
		int oldOutputRows;
		int loops;
		if(loopHomology) {
			loops = this.loops;
		} else {
			loops = (int)Math.round(Math.random()*this.loops);
		}
		for(int j = 0; j < loops+1; j++) {

			// Go through the rows
			for(int i = 0; i < nodes.length; i++) {
				tempNodeArray = nodes[i];

				// Go through the columns of one row
				for(int k = 0; k < tempNodeArray.length; k++) {

					if(tempNodeArray[k] instanceof ThreeDimensionalCycle) {
						tempOutput.clear();
						tempOutput = ((ThreeDimensionalCycle)tempNodeArray[k]).fun();

						if(tempOutput.size() >= output.size()) {
							nullColumnsToAdd = ArrayLists.getNumberOfColumns(output);
							nullRowsToAdd = tempOutput.size() - output.size();
							oldOutputRows = output.size();

							// For adding extra columns to all the rows in the arraylist
							for(int l = 0; l < output.size(); l++) {
								output.get(l).addAll(tempOutput.get(l));
							}
							nullArray = new Object[nullColumnsToAdd];
							// For adding null rows before the data columns
							for(int l = 0; l < nullRowsToAdd; l++) {
								output.add(new ArrayList<Object>(Arrays.asList(nullArray)));
							}
							// For appending the data to the null rows
							for(int l = oldOutputRows; l < output.size() ; l++) {
								output.get(l).addAll(tempOutput.get(l));
							}
						} else {
							nullColumnsToAdd = ArrayLists.getNumberOfColumns(tempOutput);
							nullRowsToAdd =  output.size()- tempOutput.size();

							// For adding extra columns from the rows in the temp arraylist
							for(int l = 0; l < tempOutput.size(); l++) {
								output.get(l).addAll(tempOutput.get(l));
							}
							nullArray = new Object[nullColumnsToAdd];

							// For adding null columns to the rest of the output arrayList
							for(int l = tempOutput.size(); l < output.size(); l++) {
								output.get(l).addAll(new ArrayList<Object>(Arrays.asList(nullArray)));
							}	
						}
					} else {
						if(output.size() == 0 || k==0) {
							ArrayList<Object> singleNodeArray = new ArrayList<Object>();
							singleNodeArray.add(tempNodeArray[k]);
							output.add(singleNodeArray);
						} else {
							output.get(i).add(tempNodeArray[k]);

							// For adding a null to the rest of the rows to match column width
							for(int l = i; l < output.size(); l++) {
								if(l != i) {
									output.get(l+i).add(null);
								}
							}
						}
					}
				}
			}
		}
		return output;
	}

}