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
 *         A class for square waves.
 */
public class SquareWave extends Wave {

	private final double hertz;
	private final boolean updateRadians;
	private double radians;

	/**       Creates a square wave.
	 * @param amplitude as the amplitude of the square wave from -amplitude to amplitude.
	 * @param hertz as the frequency of the square wave in hertz.
	 * @param radians as the phase of the square wave.
	 * @param updateRadians Whether or not to update the radians after generating a wave.
	 *        This allows the phase of the next generated wave to match up with ending phase of the previous generation.
	 */
	public SquareWave(double amplitude, double hertz, double radians, boolean updateRadians) {
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
		double rads = radians;
		while(rads < 0) {
			rads+=2.0*StrictMath.PI;
		}
		while(rads > 2.0*StrictMath.PI) {
			rads-=2.0*StrictMath.PI;
		}
		double extendedTime = rads/hertz/(2.0*StrictMath.PI);

		double hertz = this.hertz;
		SineWave harmonicSineWave;
		int harmonicNumber = 1;
		double harmonicAmplitude;
		double[] wave = new double[(int) Math.round((samplesPerSecond*(seconds+extendedTime)))];
		double[] waveO = new double[(int) Math.round((samplesPerSecond*(seconds)))];
		// Generates each harmonic for the square wave in its own thread
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double tempRads = 0;
		do{
			harmonicAmplitude = (this.amplitude)/((2.0*harmonicNumber)-1.0);
			harmonicSineWave = new SineWave(harmonicAmplitude, hertz, tempRads, updateRadians);
			futureSineWaves.add(threadRunner.submit(new SineWaveThread(harmonicSineWave, seconds+extendedTime, samplesPerSecond)));
			harmonicNumber++;
			hertz = (((harmonicNumber*2)-1)*this.hertz);
		} while((hertz < (MAXIMUM_FREQUENCY)));
		// Adds the harmonic waves butterfly style
		wave = AddThread.addArraysButterfly(futureSineWaves, threadRunner);
		threadRunner.shutdown();
		if(updateRadians) {
			// Saves the phase for the next wave generation
			radians = 2.0*StrictMath.PI*hertz*seconds;	
			if(futureSineWaves.size() % 2 != 0) {
				radians += StrictMath.PI;
			}
		}
		int startTime = (int)Math.round(samplesPerSecond*extendedTime);
		for(int i = 0; i < waveO.length; i++) {
			waveO[i] = wave[i+startTime];
		}
		if(seconds > 1.0/this.hertz) {
			return Array.scale(waveO, amplitude/Array.mag(waveO));
		} else {
			double[] t = getWaveNS(1.0/this.hertz, samplesPerSecond);
			return Array.scale(waveO, amplitude/Array.mag(t));
		}
	}

	/**        Gets a SquareWave that is not scaled to amplitude.
	 * @param  seconds as the time of the SquareWave.
	 * @param  samplesPerSecond as the sample rate.
	 * @return A SquareWave that has not been scaled to amplitude.
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
		double rads = radians;
		while(rads < 0) {
			rads+=2.0*StrictMath.PI;
		}
		while(rads > 2.0*StrictMath.PI) {
			rads-=2.0*StrictMath.PI;
		}
		double extendedTime = rads/hertz/(2.0*StrictMath.PI);

		double hertz = this.hertz;
		SineWave harmonicSineWave;
		int harmonicNumber = 1;
		double harmonicAmplitude;
		double[] wave = new double[(int) Math.round((samplesPerSecond*(seconds+extendedTime)))];
		double[] waveO = new double[(int) Math.round((samplesPerSecond*(seconds)))];
		// Generates each harmonic for the square wave in its own thread
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double tempRads = 0;
		do{
			harmonicAmplitude = (this.amplitude)/((2.0*harmonicNumber)-1.0);
			harmonicSineWave = new SineWave(harmonicAmplitude, hertz, tempRads, updateRadians);
			futureSineWaves.add(threadRunner.submit(new SineWaveThread(harmonicSineWave, seconds+extendedTime, samplesPerSecond)));
			harmonicNumber++;
			hertz = (((harmonicNumber*2)-1)*this.hertz);
		} while((hertz < (MAXIMUM_FREQUENCY)));
		// Adds the harmonic waves butterfly style
		wave = AddThread.addArraysButterfly(futureSineWaves, threadRunner);
		threadRunner.shutdown();
		int st = (int)Math.floor(samplesPerSecond*extendedTime);
		for(int i = 0; i < waveO.length; i++) {
			waveO[i] = wave[i+st];
		}
		return waveO;
	}

}