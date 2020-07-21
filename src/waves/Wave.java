package waves;

import java.util.Objects;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for {@link Wave}s to extend and static methods to perform basic operations on arrays.
 */
public abstract class Wave {

	// The amplitude of this NoiseWave.
	final double amplitude;

	Wave(double amplitude){
		this.amplitude = amplitude;
	}
	
	public double getAmplitude() {
		return amplitude;
	}

	/**        Generates a wave.
	 * @param  seconds as the length of the wave in seconds.
	 * @param  samplesPerSecond as the sample rate.
	 * @return An array representing this wave as amplitude over time.
	 * @throws IllegalArgumentException if seconds is less than 0 or samplesPerSecond is less than 1.
	 */
	abstract public double[] getWave(double seconds, float samplesPerSecond);

	/**        Gets a sample of this wave with the amplitude changing over time as specified by amplitudeArray.
	 * @param  amplitudeArray as the amplitude array representing amplitude per sample over time.
	 * @param  samplesPerSecond as the sample rate.
	 * @return An array representing this wave, with an amplitude envelope, as amplitude over time.
	 * @throws NullPointerException if amplitudeArray is null.
	 * @throws IllegalArgumentException if samplesPerSecond is less than 1.
	 */
	public double[] getWave(double[] amplitudeArray, float samplesPerSecond) {
		Objects.requireNonNull(amplitudeArray);
		if(samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to getWave() must be at least 1");
		}
		// Invariants secured

		double[] wave = getWave(amplitudeArray.length/samplesPerSecond, samplesPerSecond);
		for(int i = 0; i < wave.length; i++) {
			wave[i] *= amplitudeArray[i];
		}
		return wave;
	}

	/**        Delays a wave.
	 * @param  wave The wave to be delayed.
	 * @param  seconds The time to delay the wave by in seconds. 
	 *         The actual time may be up to one sample shorter.
	 * @param  samplesPerSecond The sample rate.
	 * @return An array of the delayed wave as amplitude over time.
	 * @throws NullPointerException if wave is null.
	 * @throws IllegalArgumentException if seconds is less than 0 
	 *         or samplesPerSecond is less than 1.
	 */
	static public double[] delay(double[] wave, double seconds, float samplesPerSecond) {
		Objects.requireNonNull(wave);
		if(seconds < 0) {
			throw new IllegalArgumentException("seconds passed to delay() must be at least 0");
		}
		if(samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to delay() must be at least 1");
		}
		// Invariants secured
		// Number of samples to delay by
		int samplesForDelay =  (int)Math.floor(seconds*samplesPerSecond);

		// Where the delayed wave will be stored
		double[] delayedWave = new double[(wave.length+samplesForDelay)];

		// Fill the delay time with 0s
		for(int i = 0; i < samplesForDelay; i++) {
			delayedWave[i] = 0;
		}

		// Append the wave
		for(int i = samplesForDelay; i < delayedWave.length; i++) {
			delayedWave[i] = wave[i-samplesForDelay];
		}
		return delayedWave;
	}

}