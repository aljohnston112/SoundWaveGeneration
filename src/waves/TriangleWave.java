package waves;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Alexander Johnston
 * Copyright 2019
 * A class for triangle waves
 */
public class TriangleWave extends Wave{

	/** Creates a triangle wave
	 * @param amplitude as the amplitude of the triangle wave from -amplitude to amplitude
	 * @param hertz as the frequency of the triangle wave in Hz
	 * @param radians as the phase of the triangle wave
	 */
	public TriangleWave(double amplitude, double hertz, double radians) {
		this.amplitude = amplitude;
		this.hertz = hertz;
		this.radians = radians;
	}

	/* (non-Javadoc)
	 * @see waves.Wave#getWave(double, float)
	 */
	@Override
	public double[] getWave(double seconds, float samplesPerSecond) {

		final double MAXIMUM_FREQUENCY = samplesPerSecond/2.0;
		// Amplitude of the harmonics
		double harmonicAmplitude;
		// The harmonic number
		int harmonic = 1;
		double hertz = this.hertz;
		double radians = this.radians;
		SineWave harmonicSineWave;
		double extendedTime = 0;
		if(hertz!=0) {
			extendedTime = (radians/(2.0*Math.PI)/hertz);
		}
		double[] wave = new double[(int) Math.round((samplesPerSecond*(seconds+extendedTime)))];
		double[] phaseShiftedWave = new double[(int) Math.round((samplesPerSecond*(seconds)))];

		// Generates each harmonic for the triangle wave in its own thread
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		do{
			harmonicAmplitude = (this.amplitude)/Math.pow(((2.0*harmonic)-1.0), 2.0);
			harmonicSineWave = new SineWave(harmonicAmplitude, hertz, 0);
			futureSineWaves.add(threadRunner.submit(new SineWaveThread(harmonicSineWave, seconds+extendedTime, samplesPerSecond)));
			harmonic++;
			hertz = (((harmonic*2)-1)*this.hertz);
			radians += StrictMath.PI;
		} while((hertz < (MAXIMUM_FREQUENCY)));
		wave = WaveAddThread.addWavesButterfly(futureSineWaves, threadRunner);
		threadRunner.shutdown();
		// Fix the phase
		if(this.radians == 0 || hertz == 0) {
			if(seconds > 1.0/this.hertz) {
				return Wave.scaleWave(wave, amplitude/Wave.getMaxAmp(wave));
			}
			return wave;
		} else {
			for(int i = 0; i < phaseShiftedWave.length; i++) {
				phaseShiftedWave[i] = wave[(int)Math.floor(i+(extendedTime*samplesPerSecond))];
			}
			if(seconds > 1.0/this.hertz) {
				return Wave.scaleWave(phaseShiftedWave, amplitude/Wave.getMaxAmp(phaseShiftedWave));
			}
			return phaseShiftedWave;
		}
	}

	/* (non-Javadoc)
	 * @see waves.Wave#clone()
	 */
	@Override
	public Object clone() {
		return new TriangleWave(amplitude, hertz, radians);
	}
	
}