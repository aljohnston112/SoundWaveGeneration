package waves;

import java.util.concurrent.Callable;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         For making a sine wave samples in a thread
 */
public class SineWaveThread implements Callable<double[]> {

	final SineWave sineWave;
	final double seconds;
	final float samplesPerSecond;
	double[] wave;
	
	/**       For generating a sine wave sample
	 * @param sineWave as the sine wave object used to generate the sample
	 * @param seconds as the length of the sample
	 * @param samplesPerSecond as the sample rate
	 */
	public SineWaveThread(SineWave sineWave, double seconds, float samplesPerSecond) {
		this.sineWave = sineWave;
		this.seconds = seconds;
		this.samplesPerSecond = samplesPerSecond;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public double[] call() throws Exception {
		return sineWave.getWave(seconds, samplesPerSecond);	
	}
	
}