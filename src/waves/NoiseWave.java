package waves;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Alexander Johnston 
 * @since  2019 
 *         A class for making noise waves.
 */
public class NoiseWave extends Wave {

	/**       Instantiates a NoiseWave.
	 * @param amplitude The amplitude of the NoiseWave.
	 */
	public NoiseWave(double amplitude){
		super(amplitude);
	}

	/* (non-Javadoc)
	 * @see waves.Wave#getWave(double, float)
	 */
	@Override
	public double[] getWave(double seconds, float samplesPerSecond) {
		if(seconds < 0) {
			throw new IllegalArgumentException("seconds passed to getWave() must be at least 0");
		}
		if(samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to getWave() must be at least 1");
		}
		// Invariants secured

		double[] wave = new double[(int) Math.round((samplesPerSecond*seconds))];
		for(int i = 0; i < wave.length; i++) {
			wave[i] = amplitude*((ThreadLocalRandom.current().nextDouble()*2.0)-1);
		}
		return wave;
	}

}