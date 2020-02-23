package algorithms;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for finding arc lengths
*/
public class ArcLength {

	/**        Gets the arc length of an array representing the output values of a function
	 * @param  functionOutput The array representing the output values of a function
	 * @return The arc length of the function
	 */
	public static double get(double[] functionOutput) {
		double arcLength = 0;
		for(int i = 1; i < functionOutput.length; i++) {
			arcLength+= StrictMath.abs(functionOutput[i]-functionOutput[i-1]);
		}
		return arcLength;
	}
	
	/**        Gets the arc length of a Sine function
	 * @param  amplitude as the amplitude of the Sine
	 * @param  functionOutput as the output values of the Sine function
	 * @param  dt as the amount of time between two points in the function
	 * @return the arc length of a Sine function
	 */
	public static double getSine(double amplitude, double[] functionOutput, double dt) {
		double[] f2 = new double[functionOutput.length];
		for(int i = 0; i < functionOutput.length; i++) {
		f2[i] = Math.sqrt(1.0-amplitude*amplitude*functionOutput[i]*functionOutput[i]);
		}
		return Intergral.RiemannIntergrateSum(f2, dt);
	}
	
}