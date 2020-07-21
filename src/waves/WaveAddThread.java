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

	/**        Adds double arrays together in a butterfly method
	 * @param  futureSineWaves as the waves to be added together
	 * @param  threadRunner as the executor of the thread to execute the additions
	 * @return an array of doubles representing the addition of all the double arrays in futureSineWaves
	 */
	public static double[] addWavesButterfly(ArrayList<double[]> futureSineWaves, ExecutorService threadRunner) {
		double[] wave1;
		double[] wave2;
		if(futureSineWaves.size() == 1) {
			return futureSineWaves.get(futureSineWaves.size()-1);
		}
		ArrayList<Future<double[]>> futureSumWaves = new ArrayList<Future<double[]>>();
		if(futureSineWaves.size() % 2 == 0) {
			for(int i = 0; i < futureSineWaves.size(); i+=2) {
				wave1 = futureSineWaves.get(i);
				wave2 = futureSineWaves.get(i+1);
				futureSumWaves.add(threadRunner.submit(new WaveAddThread(wave1, wave2)));
			}
		} else {
			for(int i = 0; i < futureSineWaves.size()-1; i+=2) {
				wave1 = futureSineWaves.get(i);
				wave2 = futureSineWaves.get(i+1);
				futureSumWaves.add(threadRunner.submit(new WaveAddThread(wave1, wave2)));
			}
			futureSumWaves.add(threadRunner.submit(new WaveAddThread(futureSineWaves.get(futureSineWaves.size()-1), null)));
		}
		ArrayList<double[]> futureSumWavesArrayList = new ArrayList<double[]>();
		for(int i = 0; i < futureSumWaves.size(); i++) {
			try {
				futureSumWavesArrayList.add(futureSumWaves.get(i).get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		if(futureSumWavesArrayList.size() != 0) {
			return addWavesButterfly(futureSumWavesArrayList, threadRunner);
		} else {
			return new double[0];
		}
	}	
}