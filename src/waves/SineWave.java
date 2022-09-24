package waves;


/**
 * @author Alexander Johnston
 * @since  2019
 *         A class for sine waves
 */
public class SineWave extends Wave {

	private final double hertz;
	private final boolean updateRadians;
	private double radians;

	/**       Creates a sine wave.
	 * @param amplitude The amplitude of the sine wave, which goes from -amplitude to amplitude.
	 * @param hertz The frequency of the sine wave in hertz.
	 * @param radians The phase of the sine wave in radians.
	 * @param updateRadians Whether or not to update the radians after generating a wave.
	 *        This allows the phase of the next generated wave to match up with ending phase of the previous generation.
	 */
	public SineWave(double amplitude, double hertz, double radians, boolean updateRadians) {
		super(amplitude);
		this.hertz = hertz;
		this.radians = radians;
		this.updateRadians = updateRadians;
	}
	
	//TODO Test
	/**       Copies a SineWave.
	 * @param sineWave as the SineWave to copy.
	 */
	public SineWave(SineWave sineWave) {
		super(sineWave.getAmplitude());
		this.hertz = sineWave.getHertz();
		this.radians = sineWave.getRadians();
		this.updateRadians = sineWave.updateRadians;
	}

	public double getHertz() {
		return hertz;
	}

	public boolean isUpdateRadians() {
		return updateRadians;
	}
	
	public double getRadians() {
		return radians;
	}

	/* (non-Javadoc)
	 * @see wave.Wave#getSample(double, float)
	 */
	public double[] getWave(double seconds, float samplesPerSecond) {
		if(seconds < 0) {
			throw new IllegalArgumentException("seconds passed to getWave() must be at least 0");
		}
		if(samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to getWave() must be at least 1");
		}
		// Invariants secured

		// Where the wave will be stored
		double[] wave = new double[(int) Math.round((samplesPerSecond*seconds))];

		// Generates the wave
		for(int i = 0; i < wave.length; i++) {
			wave[i] = amplitude*StrictMath.sin((2.0*(StrictMath.PI)*(hertz)*(double)(i)/(samplesPerSecond))+radians);
		}

		if(updateRadians) {
			// Saves the phase for the next wave generation
			radians = 2.0*StrictMath.PI*hertz*seconds;
		}
		return wave;
	}

	/* (non-Javadoc)
	 * @see wave.Wave#getSample(double, float)
	 */
	public int[] getWaveInt(double seconds, float samplesPerSecond) {
		if(seconds < 0) {
			throw new IllegalArgumentException("seconds passed to getWaveInt() must be at least 0");
		}
		if(samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to getWaveInt() must be at least 1");
		}
		// Invariants secured

		// Where the wave will be stored
		int[] wave = new int[(int) Math.round((samplesPerSecond*seconds))];

		// Generates the wave
		for(int i = 0; i < wave.length; i++) {
			wave[i] = (int) (amplitude*StrictMath.sin((2.0*(StrictMath.PI)*(hertz)*(double)(i)/(samplesPerSecond))+radians));
		}

		if(updateRadians) {
			// Saves the phase for the next wave generation
			radians = 2.0*StrictMath.PI*hertz*seconds;
		}
		return wave;
	}


	/**        Gets an absolute valued sine wave.
	 * @param  seconds as the length of the wave in seconds.
	 * @param  samplesPerSecond as the sample rate.
	 * @return An array representing the absolute valued wave as amplitude over time.
	 */
	public double[] getWaveAbs(double seconds, float samplesPerSecond) {

		// Where the wave will be stored
		double[] wave = new double[(int)(Math.round(samplesPerSecond*seconds))];

		// Generates the wave
		for(int i = 0; i < wave.length; i++) {
			wave[i] = Math.abs(amplitude*StrictMath.sin((2.0*(StrictMath.PI)*(hertz)*(double)i/(samplesPerSecond))+radians));
		}

		if(updateRadians) {
			// Saves the phase for the next wave generation
			radians = 2.0*StrictMath.PI*hertz*seconds;
		}
		return wave;
	}

}