package waves;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making noise waves
*/
public class NoiseWave extends Wave {
	
	// The amplitude of the noise
	double amplitude;
	
	/**       Creates a noise wave
	 * @param amplitude The amplitude of the noise wave
	 */
	public NoiseWave(double amplitude){
		this.amplitude = amplitude;
	}

	/* (non-Javadoc)
	 * @see waves.Wave#getWave(double, float)
	 */
	@Override
	public double[] getWave(double seconds, float samplesPerSecond) {
		double[] wave = new double[(int) (samplesPerSecond*seconds)];
		for(int i = 0; i < wave.length; i++) {
			wave[i] = amplitude*((Math.random()*2.0)-1);
		}
		return wave;
	}

	/* (non-Javadoc)
	 * @see waves.Wave#clone()
	 */
	@Override
	public Object clone() {
		return new NoiseWave(amplitude);
	}
	
}