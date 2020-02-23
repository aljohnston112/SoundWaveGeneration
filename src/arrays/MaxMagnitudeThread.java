package arrays;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for threads to find the max magnitude
*/
public class MaxMagnitudeThread implements Callable<Double> {

	double max;

	
	/**       Makes a thread that can find the max magnitude
	 * @param number1 as a number
	 * @param number2 as a number
	 */       
	MaxMagnitudeThread(double number1, double number2){
	this.max = Math.max(Math.abs(number1), Math.abs(number2));
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Double call() throws Exception {
		return max;
	}
	
	/**        Finds the max in an array in a butterfly method
	 * @param  values as the doubles to check for max magnitude
	 * @param  threadRunner as the executor of the thread to execute the magnitude comparisons
	 * @return the max magnitude
	 */
	public static double findMaxMagnitudeButterfly(double[] values, ExecutorService threadRunner) {
		/*
		if(values.length < 1) {
			return Double.NaN;
		}
		*/
		if(values.length == 1) {
			return values[0];
		}
		double number1;
		double number2;
		ArrayList<Future<Double>> futureMaxMagnitude = new ArrayList<Future<Double>>();
		if(values.length % 2 == 0) {
			for(int i = 0; i < values.length; i+=2) {
				number1 = values[i];
				number2 = values[i+1];
				futureMaxMagnitude.add(threadRunner.submit(new MaxMagnitudeThread(number1, number2)));
			}
		} else {
			for(int i = 0; i < values.length-1; i+=2) {
				number1 = values[i];
				number2 = values[i+1];
				futureMaxMagnitude.add(threadRunner.submit(new MaxMagnitudeThread(number1, number2)));
			}
			futureMaxMagnitude.add(threadRunner.submit(new MaxMagnitudeThread(values[values.length-1], 0.0)));
		}
		double futureSumWavesArrayList[] = new double[futureMaxMagnitude.size()];
		for(int i = 0; i < futureMaxMagnitude.size(); i++) {
			try {
				futureSumWavesArrayList[i] = (futureMaxMagnitude.get(i).get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return findMaxMagnitudeButterfly(futureSumWavesArrayList, threadRunner);
	}	
}