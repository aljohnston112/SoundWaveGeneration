package waves;

import java.util.concurrent.Callable;

public class SineWaveAmpEnvelopeThread implements Callable<double[]> {

	// TODO where is this used? 
	
	final SineWave sineWave;
	final double[] ampArray;
	final float samplesPerSecond;
	double[] wave;

	/**       For generating a sine wave sample
	 * @param sineWave as the sine wave object used to generate the sample
	 * @param seconds as the length of the sample
	 * @param samplesPerSecond as the sample rate
	 */
	SineWaveAmpEnvelopeThread(SineWave sineWave, double[] amplitudeArray, float samplesPerSecond) {
		this.sineWave = sineWave;
		this.ampArray = amplitudeArray;
		this.samplesPerSecond = samplesPerSecond;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public double[] call() throws Exception {
		return sineWave.getWave(ampArray, samplesPerSecond);	
	}

}
