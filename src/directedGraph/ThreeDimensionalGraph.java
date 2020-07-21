package directedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import arrayLists.ArrayLists;

public class ThreeDimensionalGraph {

	// The directed graph cycles that make up this directed graph
	ThreeDimensionalCycle[] directedGraphCycles;

	/**       Creates a directed graph
	 * @param directedGraphCycle as the directed graph cycle that makes up this directed graph
	 */
	public ThreeDimensionalGraph(ThreeDimensionalCycle directedGraphCycle){
		this.directedGraphCycles = new ThreeDimensionalCycle[1];
		this.directedGraphCycles[0] = directedGraphCycle;
	}

	/**
	 * @return The object array that represent a traversal over this directed graph
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

		tempNodeArray = directedGraphCycles;
		// Go through the columns of one row
		for(int k = 0; k < tempNodeArray.length; k++) {
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
		}
		return output;
	}
}