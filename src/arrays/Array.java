package arrays;

import java.util.concurrent.ExecutorService;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for array methods
*/
public class Array {

	/**        Converts a double array to a short array
	 * @param  doubleArray The double array to convert
	 * @return A short array version of the double array
	 */
	public static short[] doubleToShort(double[] doubleArray) {
		short[] shortArray = new short[doubleArray.length];
		for (int i = 0; i < doubleArray.length; i++) {
			shortArray[i] = (short) Math.round(doubleArray[i]);
		}
		return shortArray;
	}

	/**        Converts a double array to a byte array
	 * @param  doubleArray The double array to convert
	 * @return A byte array version of the double array
	 */
	public static byte[] doubleToByte(double[] doubleArray) {
		byte[] byteWave = new byte[doubleArray.length];
		for (int i = 0; i < doubleArray.length; i++) {
			byteWave[i] = (byte) Math.round(doubleArray[i]);
		}
		return byteWave;
	}
	
	/**        Gets the max magnitude in an array of doubles
	 * @param  doubleArray the array of doubles
	 * @return The max magnitude 
	 */
	public static double getMaxMag(double[] doubleArray, ExecutorService threadRunner) {
		return MaxMagnitudeThread.findMaxMagnitudeButterfly(doubleArray, threadRunner);
	}
	
	/**        Gets the max in an array of doubles
	 * @param  doubleArray the array of doubles
	 * @return The max 
	 */
	public static double getMax(double[] doubleArray, ExecutorService threadRunner) {
		return MaxThread.findMaxButterfly(doubleArray, threadRunner);
	}
	
	/**        Concatenates two arrays
	 * @param  arrayLead The wave to be concatenated to
	 * @param  arrayEnd The wave to concatenate
	 * @return An array of the concatenated waves
	 */
	static public double[] concat(double[] arrayLead, double[] arrayEnd) {

		// Where the concatenated array will be stored
		double[] arrayConcatenated = new double[arrayLead.length + arrayEnd.length];
		
		// Store the leading array first
		for(int i = 0; i < arrayLead.length; i++) {
			arrayConcatenated[i] = arrayLead[i];
		}
		
		// Concatenated the trailing array 
		for(int i = arrayLead.length; i < arrayConcatenated.length; i++) {
			arrayConcatenated[i] = arrayEnd[i-arrayLead.length];
		}
		return arrayConcatenated;
	}
	
	/**        Gets the number of columns from the array list
	 * @param  arrayList as the array list
	 * @return the number of columns in the array list
	 */
	public static int getNumberOfColumns(Object[][] array) {
		int columns = 0;
		for(int i = 0; i < array.length; i++) {
			columns = Math.max(columns, array[i].length);
		}
		return columns;
	}
	
}