package waves;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import arrays.AddThread;
import arrays.Array;

/**
 * @author Alexander Johnston
 * @since  2019
 *         A class for triangle waves.
 */
public class TriangleWave extends Wave {

	private final double hertz;
	private final boolean updateRadians;
	private double radians;


	/**       Creates a triangle wave.
	 * @param amplitude as the amplitude of the triangle wave from -amplitude to amplitude.
	 * @param hertz as the frequency of the triangle wave in Hz.
	 * @param radians as the phase of the triangle wave.
	 * @param updateRadians Whether or not to update the radians after generating a wave.
	 *        This allows the phase of the next generated wave to match up with ending phase of the previous generation.
	 */
	public TriangleWave(double amplitude, double hertz, double radians, boolean updateRadians) {
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
		
		final double MAXIMUM_FREQUENCY = samplesPerSecond/2.0;
		// Amplitude of the harmonics
		double harmonicAmplitude;
		// The harmonic number
		int harmonic = 1;
		double hertz = this.hertz;
		double radians = this.radians;
		SineWave harmonicSineWave;
		double et = 0;
		if(hertz!=0) {
			et = (radians/(2.0*Math.PI)/hertz);
		}
		double[] wave = new double[(int) Math.round((samplesPerSecond*(seconds+et)))];
		double[] waveO = new double[(int) Math.round((samplesPerSecond*(seconds)))];

		// Generates each harmonic for the triangle wave in its own thread
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double r2 = 0;
		do{
			harmonicAmplitude = (this.amplitude)/Math.pow(((2.0*harmonic)-1.0), 2.0);
			harmonicSineWave = new SineWave(harmonicAmplitude, hertz, r2, false);
			futureSineWaves.add(threadRunner.submit(new SineWaveThread(harmonicSineWave, 
					seconds+et, samplesPerSecond)));
			harmonic++;
			hertz = (((harmonic*2)-1)*this.hertz);
			r2 += StrictMath.PI;
		} while((hertz < (MAXIMUM_FREQUENCY)));
		wave = AddThread.addArraysButterfly(futureSineWaves, threadRunner);
		threadRunner.shutdown();
		// Fix the phase
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
	
	/**        Gets a TriangleWave that is not scaled to amplitude.
	 * @param  seconds as the time of the TriangleWave.
	 * @param  samplesPerSecond as the sample rate.
	 * @return A TriangleWave that has not been scaled to amplitude.
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
		// Amplitude of the harmonics
		double harmonicAmplitude;
		// The harmonic number
		int harmonic = 1;
		double hertz = this.hertz;
		double radians = this.radians;
		SineWave harmonicSineWave;
		double et = 0;
		if(hertz!=0) {
			et = (radians/(2.0*Math.PI)/hertz);
		}
		double[] wave = new double[(int) Math.round((samplesPerSecond*(seconds+et)))];
		double[] waveO = new double[(int) Math.round((samplesPerSecond*(seconds)))];

		// Generates each harmonic for the triangle wave in its own thread
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double r2 = 0;
		do{
			harmonicAmplitude = (this.amplitude)/Math.pow(((2.0*harmonic)-1.0), 2.0);
			harmonicSineWave = new SineWave(harmonicAmplitude, hertz, r2, false);
			futureSineWaves.add(threadRunner.submit(new SineWaveThread(harmonicSineWave, 
					seconds+et, samplesPerSecond)));
			harmonic++;
			hertz = (((harmonic*2)-1)*this.hertz);
			r2 += StrictMath.PI;
		} while((hertz < (MAXIMUM_FREQUENCY)));
		wave = AddThread.addArraysButterfly(futureSineWaves, threadRunner);
		threadRunner.shutdown();
		// Fix the phase
		
		int st = (int)Math.floor(samplesPerSecond*et);
		for(int i = 0; i < waveO.length; i++) {
			waveO[i] = wave[i+st];
		}
		return waveO;
	}

}