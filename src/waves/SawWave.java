package waves;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making saw waves
 */
public class SawWave extends Wave{

	/** Creates a saw wave
	 * @param amplitude The amplitude of the saw wave from -amplitude to amplitude
	 * @param hertz The frequency of the saw wave in Hz
	 * @param radians The phase of the saw wave
	 */
	public SawWave(double amplitude, double hertz, double radians) {
		this.amplitude = amplitude;
		this.hertz = hertz;
		this.radians = radians;
	}

	@Override
	public double[] getWave(double seconds, float samplesPerSecond) {

		final double MAXIMUM_FREQUENCY = samplesPerSecond/2.0;
		// For keeping track of harmonic amplitudes
		double harmonicAmplitude;
		// For keeping track of the harmonic number
		int harmonic = 1;
		double[] wave = new double[(int) (samplesPerSecond*seconds)];
		double hertz = this.hertz;
		SineWave harmonicSineWave;
		// Generates each harmonic for the saw wave in its own thread
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		do{
			harmonicAmplitude = (this.amplitude)/Math.pow(harmonic, 1.0);
			harmonicSineWave = new SineWave(harmonicAmplitude, hertz, this.radians+(((harmonic%2)-1)*(-Math.PI)));
			futureSineWaves.add(threadRunner.submit(new SineWaveThread(harmonicSineWave, seconds, samplesPerSecond)));
			harmonic++;
			hertz = (harmonic*this.hertz);
			radians += StrictMath.PI;
		} while((hertz < (MAXIMUM_FREQUENCY)));
		wave = WaveAddThread.addWavesButterfly(futureSineWaves, threadRunner);
		threadRunner.shutdown();
		if(seconds > 1.0/this.hertz) {
			return Wave.scaleWave(wave, amplitude/Wave.getMaxAmp(wave));
		}
		return wave;
	}

	/* (non-Javadoc)
	 * @see waves.Wave#clone()
	 */
	@Override
	public Object clone() {
		return new SawWave(amplitude, hertz, radians);
	}

}
