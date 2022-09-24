package arrays;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	public static double mag(double[] doubleArray) {
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double mag = MaxMagnitudeThread.findMaxMagnitudeButterfly(doubleArray, threadRunner);
		threadRunner.shutdown();
		return mag;
	}
	
	/**        Gets the max in an array of doubles
	 * @param  doubleArray the array of doubles
	 * @return The max 
	 */
	public static double max(double[] doubleArray) {
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double max = MaxThread.findMaxButterfly(doubleArray, threadRunner);
		threadRunner.shutdown();
		return max;
	}
	
	/**        Adds 2 arrays.
	 * @param  arrayAddend The array to be added to arrayAddend2.
	 * @param  arrayAddend2 The array to be added to arrayAddend.
	 * @return An array representing arrayAddend+arrayAddend2.
	 */
	static public double[] add(double[] arrayAddend, double[] arrayAddend2) {

		if(arrayAddend == null || arrayAddend.length == 0) {
			return arrayAddend2;
		}
		if(arrayAddend2 == null || arrayAddend2.length == 0) {
			return arrayAddend;
		}
		
		// Where the sum of both arrays will be stored
		double[] arraySum;

		// Figures out which array is shorter and which is longer 
		int shortarrayLength;
		if(arrayAddend.length > arrayAddend2.length) {
			arraySum = new double[arrayAddend.length];
			shortarrayLength = arrayAddend2.length;
		} else {
			arraySum = new double[arrayAddend2.length];
			shortarrayLength = arrayAddend.length;
		}

		// Adds the arrays
		for(int j = 0; j < shortarrayLength; j++) {
			arraySum[j] = (arrayAddend[j]+arrayAddend2[j]);
		}

		// Appends the rest of the longer array to the end of the combined array
		for(int i = shortarrayLength; i < arraySum.length; i++) {
			if(arrayAddend.length > arrayAddend2.length) {
				arraySum[i] = arrayAddend[i];
			} else {
				arraySum[i] = arrayAddend2[i];
			}
		}
		return arraySum;
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
	
	/**        Scales a array's amplitude
	 * @param  array The array to be scaled
	 * @param  multiplier The amount to multiply the array by
	 * @return An array of the scaled array
	 */
	static public double[] scale(double[] array, double multiplier) {
			
		// Where the scaled array will be stored
		double[] scaledArray = new double[array.length];
		
		// Scale the array
		for(int i = 0; i < array.length; i++) {
				scaledArray[i] = array[i]*multiplier;
			}
		return scaledArray;
	}			

	/**        Adds a number to a array
	 * @param  arrayAddend The array to be added to 
	 * @param  addend The number to add the array by
	 * @return An array of the array+addend
	 */
	static public double[] addTo(double[] arrayAddend, double addend) {
		
		// Where the resulting sum will be stored
		double[] arraySum = new double[arrayAddend.length];
		
		// Add the addend to the array
		for(int i = 0; i < arrayAddend.length; i++) {
			arraySum[i] = arrayAddend[i]+addend;
		}
		return arraySum;
	}
	
	/**        Reverses an array.
	 * @param  array The array to be reversed.
	 * @return An The reversed array.
	 */
	static public double[] reverse(double[] array) {
		
		// Stores the reversed array
		double[] reversedArray = new double[array.length];
		
		// Reverses the array
		for(int i = 0; i < array.length; i++) {
			reversedArray[i] = array[array.length-i-1];
		}
		return reversedArray;
	}
	
	/**        Normalizes an array to max amplitude.
	 * @param  array as the array to normalize.
	 * @param  bitsPerSample as the bit rate.
	 * @return The normalized array.
	 */
	static public double[] normalize(double[] array, double bitsPerSample) {
		
		// Max amplitude of given bit rate
		double  maxAmplitude = StrictMath.pow(2.0,  bitsPerSample-1)/2.0;
		double[] out =  Array.scale(array, maxAmplitude/Array.max(array));
		return out;
	}
	
}