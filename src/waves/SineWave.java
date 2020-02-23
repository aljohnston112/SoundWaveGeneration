package waves;

import java.math.BigDecimal;

/**
 * @author Alexander Johnston
 * Copyright 2019
 * A class for sine waves
 */
public class SineWave extends Wave {

	/** Creates a sine wave
	 * @param amplitude as the amplitude of the sin wave, which goes from -amplitude to amplitude
	 * @param hertz as the frequency of the sine wave in hertz
	 * @param radians as the phase of the sine wave in radians
	 */
	public SineWave(double amplitude, double hertz, double radians) {
		this.amplitude = amplitude;
		this.hertz = hertz;
		this.radians = radians;
	}

	/* (non-Javadoc)
	 * @see waves.Wave#clone()
	 */
	public Object clone() {
		return new SineWave(amplitude, hertz, radians);
	}
	
	/* (non-Javadoc)
	 * @see wave.Wave#getSample(double, float)
	 */
	public double[] getWave(double seconds, float samplesPerSecond) {

		// Where the wave will be stored
		double[] wave = new double[(int) Math.round((samplesPerSecond*seconds))];

		// Generates the wave
		for(int i = 0; i < wave.length; i++) {
			wave[i] = amplitude*StrictMath.sin((2.0*(StrictMath.PI)*(hertz)*(double)(i)/(samplesPerSecond))+radians);
		}

		// Saves the phase for the next wave generation
		radians += 2.0*Math.PI*hertz*seconds;

		/*
		BigDecimal radiansBD = new BigDecimal(radians);
		radiansBD = radiansBD.add(new BigDecimal("2.0").multiply(BigDecimalMath.pi(new MathContext(1074, RoundingMode.DOWN)).multiply(new BigDecimal(hertz)).multiply(new BigDecimal(seconds))));
		radians = radiansBD.doubleValue();
		*/
		
		return wave;
	}

	/* (non-Javadoc)
	 * @see wave.Wave#getSample(double, float)
	 */
	public int[] getWaveInt(double seconds, float samplesPerSecond) {

		// Where the wave will be stored
		int[] wave = new int[(int) Math.round((samplesPerSecond*seconds))];

		// Generates the wave
		for(int i = 0; i < wave.length; i++) {
			wave[i] = (int) (amplitude*StrictMath.sin((2.0*(StrictMath.PI)*(hertz)*(double)(i)/(samplesPerSecond))+radians));
		}

		// Saves the phase for the next wave generation
		radians += 2.0*Math.PI*hertz*seconds;

		/*
		BigDecimal radiansBD = new BigDecimal(radians);
		radiansBD = radiansBD.add(new BigDecimal("2.0").multiply(BigDecimalMath.pi(new MathContext(1074, RoundingMode.DOWN)).multiply(new BigDecimal(hertz)).multiply(new BigDecimal(seconds))));
		radians = radiansBD.doubleValue();
		*/
		
		return wave;
	}

	
	/**        Gets an absolute valued sine wave
	 * @param seconds
	 * @param samplesPerSecond
	 * @return
	 */
	public double[] getWaveEdge(double seconds, float samplesPerSecond) {

		// Where the wave will be stored
		double[] wave = new double[(int)(Math.round(samplesPerSecond*seconds))];

		// Generates the wave
		for(int i = 0; i < wave.length; i++) {
			wave[i] = Math.abs(amplitude*StrictMath.sin((2.0*(StrictMath.PI)*(hertz)*(double)i/(samplesPerSecond))+radians));
		}

		// Saves the phase for the next wave generation
		BigDecimal radiansBD = new BigDecimal(radians);
		radiansBD = radiansBD.add(new BigDecimal(2.0).multiply(new BigDecimal(StrictMath.PI).multiply(new BigDecimal(hertz)).multiply(new BigDecimal(seconds))));
		radians = radiansBD.doubleValue();
		return wave;
	}

}