package waves;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import arrays.Array;
import arrays.AddThread;

/**
 * @author Alexander Johnston 
 * @since  Copyright 2019 
 *         A class for making saw waves
 */
public class SawWave extends Wave {

	private final double hertz;
	private final boolean updateRadians;
	private double radians;

	/**       Creates a saw wave.
	 * @param amplitude The amplitude of the saw wave from -amplitude to amplitude
	 * @param hertz The frequency of the saw wave in Hz
	 * @param radians The phase of the saw wave
	 * @param updateRadians Whether or not to update the radians after generating a wave.
	 *        This allows the phase of the next generated wave to match up with ending phase of the previous genration.
	 */
	public SawWave(double amplitude, double hertz, double radians, boolean updateRadians) {
		super(amplitude);
		this.hertz = hertz;
		this.radians = radians;
		this.updateRadians = updateRadians;
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

	@Override
	public double[] getWave(double seconds, float samplesPerSecond) {
		if(seconds < 0) {
			throw new IllegalArgumentException("seconds passed to getWave() must be at least 0");
		}
		if(samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to getWave() must be at least 1");
		}
		// Invariants secured

		final double MAXIMUM_FREQUENCY = samplesPerSecond/2.0;
		// For phase shifting since phase doesn't shift smoothly in the fouriour series
		double r = radians;
		while(r < 0) {
			r+=2.0*StrictMath.PI;
		}
		while(r > 2.0*StrictMath.PI) {
			r-=2.0*StrictMath.PI;
		}
		double et = r/hertz/(2.0*StrictMath.PI);
		// For keeping track of harmonic amplitudes
		double harmonicAmplitude;
		// For keeping track of the harmonic number
		int harmonic = 1;
		double[] wave = new double[(int) Math.round((samplesPerSecond*(seconds+et)))];
		double[] waveO = new double[(int) Math.round((samplesPerSecond*(seconds)))];
		double hertz = this.hertz;
		SineWave harmonicSineWave;
		// Generates each harmonic for the saw wave in its own thread
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double r2 = 0;
		do{
			harmonicAmplitude = (this.amplitude)/Math.pow(harmonic, 1.0);
			harmonicSineWave = new SineWave(harmonicAmplitude, hertz, 
					r2, this.updateRadians);
			futureSineWaves.add(threadRunner.submit(new SineWaveThread(harmonicSineWave, seconds+et, samplesPerSecond)));
			harmonic++;
			hertz = (harmonic*this.hertz);
			r2 += StrictMath.PI;
		} while((hertz < (MAXIMUM_FREQUENCY)));
		wave = AddThread.addArraysButterfly(futureSineWaves, threadRunner);
		threadRunner.shutdown();
		if(updateRadians) {
			// Saves the phase for the next wave generation
			radians = 2.0*StrictMath.PI*hertz*seconds;	
			if(futureSineWaves.size() % 2 != 0) {
				radians += StrictMath.PI;
			}
		}
		int st = (int)Math.round(samplesPerSecond*et);
		for(int i = 0; i < waveO.length; i++) {
			waveO[i] = wave[i+st];
		}
		if(seconds > 1.0/this.hertz) {
			if(Array.mag(waveO) != 0) {
			return Array.scale(waveO, amplitude/Array.mag(waveO));
			} else {
				return waveO;
			}
		} else {
			double[] t = getWaveNS(1.0/this.hertz, samplesPerSecond);
			if(Array.mag(waveO) != 0) {
			return Array.scale(waveO, amplitude/Array.mag(t));
			} else {
				return waveO;
			}
		}
	}

	/**        Gets a SawWave that is not scaled to amplitude.
	 *         Radians will not be updated and subsequent calls to this method will return the same phase.
	 * @param  seconds as the time of the SawWave.
	 * @param  samplesPerSecond as the sample rate.
	 * @return A SawWave that has not been scaled to amplitude.
	 */
	private double[] getWaveNS(double seconds, float samplesPerSecond) {
		if(seconds < 0) {
			throw new IllegalArgumentException("seconds passed to getWave() must be at least 0");
		}
		if(samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to getWave() must be at least 1");
		}
		// Invariants secured

		final double MAXIMUM_FREQUENCY = samplesPerSecond/2.0;
		// For phase shifting since phase doesn't shift smoothly in the fouriour series
		double r = radians;
		while(r < 0) {
			r+=2.0*StrictMath.PI;
		}
		while(r > 2.0*StrictMath.PI) {
			r-=2.0*StrictMath.PI;
		}
		double et = r/hertz/(2.0*StrictMath.PI);
		double[] wave = new double[(int) Math.round((samplesPerSecond*(seconds+et)))];
		double[] waveO = new double[(int) Math.round((samplesPerSecond*(seconds)))];
		// For keeping track of harmonic amplitudes
		double harmonicAmplitude;
		// For keeping track of the harmonic number
		int harmonic = 1;
		double hertz = this.hertz;
		SineWave harmonicSineWave;
		// Generates each harmonic for the saw wave in its own thread
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		do{
			harmonicAmplitude = (this.amplitude)/Math.pow(harmonic, 1.0);
			harmonicSineWave = new SineWave(harmonicAmplitude, hertz, this.radians, this.updateRadians);
			futureSineWaves.add(threadRunner.submit(new SineWaveThread(harmonicSineWave, seconds+et, samplesPerSecond)));
			harmonic++;
			hertz = (harmonic*this.hertz);
			radians += StrictMath.PI;
		} while((hertz < (MAXIMUM_FREQUENCY)));
		wave = AddThread.addArraysButterfly(futureSineWaves, threadRunner);
		threadRunner.shutdown();
		int st = (int)Math.floor(samplesPerSecond*et);
		for(int i = 0; i < waveO.length; i++) {
			waveO[i] = wave[i+st];
		}
		return waveO;
	}

}
