package waves;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for square waves
 */
public class SquareWave extends Wave {

	/**       Creates a square wave
	 * @param amplitude as the amplitude of the square wave from -amplitude to amplitude
	 * @param hertz as the frequency of the square wave in hertz
	 * @param radians as the phase of the square wave
	 */
	public SquareWave(double amplitude, double hertz, double radians) {
		this.amplitude = amplitude;
		this.hertz = hertz;
		this.radians = radians;
	}

	/* (non-Javadoc)
	 * @see waves.Wave#getWave(double, float)
	 */
	public double[] getWave(double seconds, float samplesPerSecond) {

		final double MAXIMUM_FREQUENCY = samplesPerSecond/2.0;
		double hertz = this.hertz;
		double radians = this.radians;
		SineWave harmonicSineWave;
		int harmonicNumber = 1;
		double harmonicAmplitude;
		double[] wave = new double[(int) Math.round((samplesPerSecond*seconds))];

		// Generates each harmonic for the square wave in its own thread
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		do{
			harmonicAmplitude = (this.amplitude)/((2.0*harmonicNumber)-1.0);
			harmonicSineWave = new SineWave(harmonicAmplitude, hertz, radians);
			futureSineWaves.add(threadRunner.submit(new SineWaveThread(harmonicSineWave, seconds, samplesPerSecond)));
			harmonicNumber++;
			hertz = (((harmonicNumber*2)-1)*this.hertz);

		} while((hertz < (MAXIMUM_FREQUENCY)));
		// Adds the harmonic waves butterfly style
		wave = WaveAddThread.addWavesButterfly(futureSineWaves, threadRunner);
		threadRunner.shutdown();
		if(seconds > 1.0/this.hertz) {
			return Wave.scaleWave(wave, amplitude/Wave.getMaxAmp(wave));
		}
		return wave;
	}

	@Override
	public Object clone() {
		return new SquareWave(amplitude, hertz, radians);
	}

}