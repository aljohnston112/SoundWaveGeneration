package arrays;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         For adding arrays in multiple threads thread
 */
public class AddThread implements Callable<double[]> {

	double[] array1;
	double[] array2;
	double[] sum;

	/**       For adding arrays
	 * @param array1 as one of the arrays to add to the other
	 * @param array2 as one of the arrays to add to the other
	 */
	AddThread(double[] array1, double[] array2){
		this.array1 = array1;
		this.array2 = array2;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public double[] call() throws Exception {
		return Array.add(array1, array2);
	}

	/**        For adding sine arrays together as they are generated
	 * @param  futureArrays as the sine arrays being generated in threads
	 * @param  threadRunner as the ExecutorService to run the array addition threads
	 * @return The addition of all the generated sine arrays together represented as amplitude over time
	 */
	public static double[] addArraysButterfly(ArrayList<Future<double[]>> futureArrays, ExecutorService threadRunner) {
		double[] array1;
		double[] array2;
		if(futureArrays.size() == 0) {
			return new double[0];
		}
		// Single array case
		if(futureArrays.size() == 1) {
			try {
				return futureArrays.get(futureArrays.size()-1).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		// Starts the threads to add pairs of arrays
		ArrayList<Future<double[]>> futureSumArrays = new ArrayList<Future<double[]>>();
		if(futureArrays.size() % 2 == 0) {
			// For an even amount of arrays to add
			for(int i = 0; i < futureArrays.size(); i+=2) {
				try {
					array1 = futureArrays.get(i).get();
					array2 = futureArrays.get(i+1).get();
					futureSumArrays.add(threadRunner.submit(new AddThread(array1, array2)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} else {
			// For an odd amount of arrays to add
			for(int i = 0; i < futureArrays.size()-1; i+=2) {
				try {
					array1 = futureArrays.get(i).get();
					array2 = futureArrays.get(i+1).get();
					futureSumArrays.add(threadRunner.submit(new AddThread(array1, array2)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			futureSumArrays.add(futureArrays.get(futureArrays.size()-1));
		}
		
		// Recursive call to add the arrays being added together in threads
		return addArraysButterfly(futureSumArrays, threadRunner);
	}
	
}