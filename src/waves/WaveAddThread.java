package waves;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         For adding waves in a thread
 */
public class WaveAddThread implements Callable<double[]> {

	double[] wave1;
	double[] wave2;
	double[] sum;

	/**       For adding waves
	 * @param wave1 as one of the waves to add to the other
	 * @param wave2 as one of the waves to add to the other
	 */
	WaveAddThread(double[] wave1, double[] wave2){
		this.wave1 = wave1;
		this.wave2 = wave2;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public double[] call() throws Exception {
		return Wave.addWaves(wave1, wave2);
	}

	/**        For adding sine waves together as they are generated
	 * @param  futureSineWaves as the sine waves being generated in threads
	 * @param  threadRunner as the ExecutorService to run the wave addition threads
	 * @return The addition of all the generated sine waves together represented as amplitude over time
	 */
	public static double[] addWavesButterfly(ArrayList<Future<double[]>> futureSineWaves, ExecutorService threadRunner) {
		double[] wave1;
		double[] wave2;
		if(futureSineWaves.size() == 0) {
			return null;
		}
		// Single wave case
		if(futureSineWaves.size() == 1) {
			try {
				return futureSineWaves.get(futureSineWaves.size()-1).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		// Starts the threads to add pairs of waves
		ArrayList<Future<double[]>> futureSumWaves = new ArrayList<Future<double[]>>();
		if(futureSineWaves.size() % 2 == 0) {
			// For an even amount of waves to add
			for(int i = 0; i < futureSineWaves.size(); i+=2) {
				try {
					wave1 = futureSineWaves.get(i).get();
					wave2 = futureSineWaves.get(i+1).get();
					futureSumWaves.add(threadRunner.submit(new WaveAddThread(wave1, wave2)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} else {
			// For an odd amount of waves to add
			for(int i = 0; i < futureSineWaves.size()-1; i+=2) {
				try {
					wave1 = futureSineWaves.get(i).get();
					wave2 = futureSineWaves.get(i+1).get();
					futureSumWaves.add(threadRunner.submit(new WaveAddThread(wave1, wave2)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				futureSumWaves.add(futureSineWaves.get(futureSineWaves.size()-1));
			}
		}
		
		// Recursive call to add the waves being added together in threads
		return addWavesButterfly(futureSumWaves, threadRunner);
	}
	
}