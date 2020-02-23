package waves;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for waves to extend and methods to perform basic operations on waves
 */
public abstract class Wave {

	// The types of waves this class can generate
	public enum WaveType{ 
		SINE, TRIANGLE, SAW, SQUARE
	}

	// Amplitude of the wave
	protected double amplitude;

	// Frequency of the wave in Hz
	protected double hertz;

	// Phase of the wave in radians
	protected double radians;

	/**
	 * @return The amplitude of this wave
	 */
	public double getAmplitude() {
		return amplitude;
	}
	
	/**
	 * @param amplitude the amplitude
	 */
	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}

	/**
	 * @return The frequency of this wave
	 */
	public double getFrequency() {
		return hertz;
	}
	
	/**
	 * @param hertz the frequency
	 */
	public void setFrequency(double hertz) {
		this.hertz = hertz;
	}

	/**
	 * @return The phase of this wave
	 */
	public double getPhase() {
		return radians;
	}
	
	/**
	 * @param radians the phase
	 */
	public void setPhase(double radians) {
		this.radians = radians;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	abstract public Object clone();
	
	/**        Generates a wave
	 * @param  seconds as the length of the wave in seconds
	 * @param  samplesPerSecond as the sample rate
	 * @return An array representing this wave as amplitude over time
	 */
	abstract public double[] getWave(double seconds, float samplesPerSecond);

	/**        Gets a sample of this wave with the amplitude changing over time as specified by the amplitude envelope
	 * @param  amplitudeArray as the amplitude array representing amplitude per sample over time
	 * @param  samplesPerSecond as the sample rate
	 * @return An array representing this wave, with an amplitude envelope, as amplitude over time
	 */
	public double[] getWave(double[] amplitudeArray, float samplesPerSecond) {
		double[] wave = getWave(amplitudeArray.length/samplesPerSecond, samplesPerSecond);
		for(int i = 0; i < amplitudeArray.length; i++) {
			wave[i] = wave[i]*amplitudeArray[i];
		}
		return wave;
	}

	/**        Adds 2 waves 
	 * @param  waveAddend The wave to be added to waveAddend2
	 * @param  waveAddend2 The wave to be added to waveAddend
	 * @return An array with the waveform representing waveAddend+waveAddend2
	 */
	static public double[] addWaves(double[] waveAddend, double[] waveAddend2) {

		if(waveAddend == null) {
			return waveAddend2;
		}
		if(waveAddend2 == null) {
			return waveAddend;
		}
		
		// Where the sum of both waves will be stored
		double[] waveSum;

		// Figures out which wave is shorter and which is longer 
		int shortWaveLength;
		if(waveAddend.length > waveAddend2.length) {
			waveSum = new double[waveAddend.length];
			shortWaveLength = waveAddend2.length;
		} else {
			waveSum = new double[waveAddend2.length];
			shortWaveLength = waveAddend.length;
		}

		// Adds the waves
		for(int j = 0; j < shortWaveLength; j++) {
			waveSum[j] = (waveAddend[j]+waveAddend2[j]);
		}

		// Appends the rest of the longer wave to the end of the combined wave
		for(int i = shortWaveLength; i < waveSum.length; i++) {
			if(waveAddend.length > waveAddend2.length) {
				waveSum[i] = waveAddend[i];
			} else {
				waveSum[i] = waveAddend2[i];
			}
		}
		return waveSum;
	}

	/**        Concatenates two waves
	 * @param  waveLead The wave to be concatenated to
	 * @param  waveEnd The wave to concatenate
	 * @return An array of the concatenated waves
	 */
	static public double[] concatWave(double[] waveLead, double[] waveEnd) {

		if(waveLead == null) {
			return waveEnd;
		}
		if(waveEnd == null) {
			return waveLead;
		}
		
		// Where the concatenated wave will be stored
		double[] waveConcatenated = new double[waveLead.length + waveEnd.length];
		
		// Store the leading wave first
		for(int i = 0; i < waveLead.length; i++) {
			waveConcatenated[i] = waveLead[i];
		}
		
		// Concatenated the trailing wave 
		for(int i = waveLead.length; i < waveConcatenated.length; i++) {
			waveConcatenated[i] = waveEnd[i-waveLead.length];
		}
		return waveConcatenated;
	}

	/**        Delays a wave
	 * @param  wave The wave to be delayed
	 * @param  seconds The time to delay the wave by in seconds. The actual time may be up to one sample shorter.
	 * @param  samplesPerSecond The sample rate
	 * @return An array of the delayed wave
	 */
	static public double[] delay(double[] wave, double seconds, float samplesPerSecond) {

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

	/**        Scales a wave's amplitude
	 * @param  wave The wave to be scaled
	 * @param  multiplier The amount to multiply the wave by
	 * @return An array of the scaled wave
	 */
	static public double[] scaleWave(double[] wave, double multiplier) {
			
		// Where the scaled wave will be stored
		double[] scaledWave = new double[wave.length];
		
		// Scale the wave
		for(int i = 0; i < wave.length; i++) {
				scaledWave[i] = wave[i]*multiplier;
			}
		return scaledWave;
	}			

	/**        Adds a number to a wave
	 * @param  waveAddend The wave to be added to 
	 * @param  addend The number to add the wave by
	 * @return An array of the wave+addend
	 */
	static public double[] addToWave(double[] waveAddend, double addend) {
		
		// Where the resulting sum will be stored
		double[] waveSum = new double[waveAddend.length];
		
		// Add the addend to the wave
		for(int i = 0; i < waveAddend.length; i++) {
			waveSum[i] = waveAddend[i]+addend;
		}
		return waveSum;
	}

	/**        Finds the max amplitude of a wave
	 * @param  wave The wave to find the max amplitude of
	 * @return The max amplitude
	 */
	static public double getMaxAmp(double[] wave) {
		
		// Stores the max amplitude
		double maxAmplitude = 0;
		
		// Stores the absolute values of the wave 
		double amplitude;
		
		// Finds the max amplitude
		for(int i = 0; i < wave.length; i++){
			amplitude = Math.abs(wave[i]);
			if(amplitude > maxAmplitude){
				maxAmplitude = amplitude;
			}
		}
		return maxAmplitude;
	}

	/**        Normalizes a wave to max amplitude 
	 * @param  wave as the wave to normalize
	 * @param  bitsPerSample as the bit rate 
	 * @return The normalized wave
	 */
	static public double[] normalize(double[] wave, double bitsPerSample) {

		// Max amplitude of given bit rate
		double  maxAmplitude = StrictMath.pow(2.0,  bitsPerSample-1)/2.0;
		return scaleWave(wave, maxAmplitude/getMaxAmp(wave));
	}

	/**        Reverses a wave
	 * @param  wave The wave to be reversed
	 * @return An array representing the reversed wave as the amplitude over time
	 */
	static public double[] reverse(double[] wave) {
		
		// Stores the reversed wave
		double[] reversedWave = new double[wave.length];
		
		// Reverses the wave
		for(int i = 0; i < wave.length; i++) {
			reversedWave[i] = wave[wave.length-i-1];
		}
		return reversedWave;
	}

}