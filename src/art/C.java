package art;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import arrays.AddThread;
import audioLines.MonoAudioLine;
import files.Wav;
import waves.SineWave;
import waves.SineWaveThread;

public class C {

	static final int SAMPLES_PER_SECOND = 48000;	

	static final int BITS_PER_SAMPLE = 16;

	static final double MAXIMUM_AMPLITUDE = (StrictMath.pow(2.0, BITS_PER_SAMPLE-1))/2.0;

	static final double MIDDLE_A = 55;

	static final double NUM_NOTES = 12;

	static final double NOTES_DIVISION = (1.0/NUM_NOTES);

	static final double C_POS = -9;

	static final double MIDDLE_C = MIDDLE_A*Math.pow(Math.pow(2.0, NOTES_DIVISION), C_POS);

	public static void main(String[] args) {
		//try1();
		try2();
	}

	private static void try2() {
		//create sine wave with middleC
		SineWave mcsw = new SineWave(MAXIMUM_AMPLITUDE, MIDDLE_C, 0, true);

		// get seconds
		int octaves = 21;
		double bottomPos = C_POS - (12*(octaves));
		double bottomHz = MIDDLE_A*Math.pow(Math.pow(2.0, NOTES_DIVISION), bottomPos);
		double seconds = 1.0/bottomHz/1024.0;
		System.out.println(seconds);

		// get Cs
		List<Double> topCs = new ArrayList<>();
		double tempCPosition = C_POS;
		double tempC = MIDDLE_A*Math.pow(Math.pow(2.0, NOTES_DIVISION), tempCPosition);
		System.out.print("Harmonic Cs: ");
		while(tempC < 20000) {
			System.out.println(tempC);
			System.out.print("             ");
			topCs.add(tempC);
			tempCPosition+=12;
			tempC = MIDDLE_A*Math.pow(Math.pow(2.0, NOTES_DIVISION), tempCPosition);
		}

		// get amplitudes
		List<Double> harmonicsHz = new ArrayList<>();
		for(double i = MIDDLE_C; i < 20000; i+=MIDDLE_C) {
			harmonicsHz.add(i);
		}
		List<Integer> roundedtopCs = new ArrayList<>();
		List<Integer> roundedHarmonicsHz = new ArrayList<>();
		for(Double d : topCs) {
			roundedtopCs.add((int)Math.round(d));
		}
		for(Double d : harmonicsHz) {
			roundedHarmonicsHz.add((int)Math.round(d));
		}
		List<Double> amplitudes = new ArrayList<>();
		int n = 0;
		for(int i = 0; i < roundedHarmonicsHz.size(); i++) {
			n+=1;
			if(i != 0) {
			if(roundedtopCs.contains(roundedHarmonicsHz.get(i))) {
				System.out.println(1.0/(n*(n/1.1)));
				System.out.print("            ");
				amplitudes.add(1.0/(n*(n/1.1)));
			}
			} else {
				System.out.println(1.0);
				System.out.print("            ");
				amplitudes.add(1.0);
			}
		}
		double ampSum = 0;
		for(double d : amplitudes) {
			ampSum += d;
		}

		// make wave
		//double[] mcw = mcsw.getWave(seconds, SAMPLES_PER_SECOND);

		ExecutorService threadRunner = Executors.newCachedThreadPool();
		List<Future<double[]>> wavesOut = new ArrayList<>();
		for(int i = 0; i < topCs.size(); i++) {
			wavesOut.add(threadRunner.submit(new SineWaveThread(
					new SineWave(amplitudes.get(i)*MAXIMUM_AMPLITUDE/ampSum, topCs.get(i), 0, false), seconds, SAMPLES_PER_SECOND)));
		}		
		double[] wave = AddThread.addArraysButterfly(wavesOut, threadRunner);
		threadRunner.shutdown();
		
		/*
		MonoAudioLine monoAudioLine = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		monoAudioLine.play(mcw);
		monoAudioLine.close();
		 */

		// save wave
		File out = new File("C:\\Users\\aljoh\\Documents\\Sounds\\C\\C1.wav");
		int channels = 1;
		Wav.writeWav(out, SAMPLES_PER_SECOND, BITS_PER_SAMPLE, channels, wave); 

	}

	private static void try1() {
		double middleA = 440;
		double numNotes = 12;
		double division = (1.0/numNotes);
		double middleC = middleA*Math.pow(Math.pow(2.0, division), C_POS);
		System.out.print("Middle C: ");
		System.out.println(middleC);
		List<Double> topCs = new ArrayList<>();
		List<Double> bottomCs = new ArrayList<>();
		double tempCPosition = C_POS;
		double tempC = middleA*Math.pow(Math.pow(2.0, division), tempCPosition);
		System.out.print("Harmonic Cs: ");
		while(tempC < 20000) {
			System.out.println(tempC);
			System.out.print("             ");
			topCs.add(tempC);
			tempCPosition+=12;
			tempC = middleA*Math.pow(Math.pow(2.0, division), tempCPosition);
		}
		tempCPosition = C_POS-12;
		while(bottomCs.size() < (topCs.size()*3)) {
			tempC = middleA*Math.pow(Math.pow(2.0, division), tempCPosition);
			System.out.println(tempC);
			System.out.print("             ");
			bottomCs.add(tempC);
			tempCPosition-=12;
		}
		System.out.println();
		//algorithms.EqualLoudnessContour.getGainAWeight();
		List<Double> harmonicsHz = new ArrayList<>();
		for(double i = middleC; i < 20000; i+=middleC) {
			harmonicsHz.add(i);
		}
		List<Integer> roundedtopCs = new ArrayList<>();
		List<Integer> roundedHarmonicsHz = new ArrayList<>();
		for(Double d : topCs) {
			roundedtopCs.add((int)Math.round(d));
		}
		for(Double d : harmonicsHz) {
			roundedHarmonicsHz.add((int)Math.round(d));
		}

		System.out.print("Amplitudes: ");
		List<Double> amplitudes = new ArrayList<>();
		int n = 0;
		for(Integer hz :roundedHarmonicsHz) {
			n+=1;
			if(roundedtopCs.contains(hz)) {
				System.out.println(1.0/n);
				System.out.print("            ");
				amplitudes.add(1.0/n);
			}
		}
		double seconds = 1.0/bottomCs.get(bottomCs.size()-7);
		System.out.print("Seconds: ");
		System.out.println(seconds);
		float samplesPerSecond = 48000;
		List<Future<double[]>> ampArrays = new ArrayList<>();
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		for(int i = bottomCs.size()-8, j = 0, k = 2; i >-1; i--, j++) {
			ampArrays.add(threadRunner.submit(new SineWaveThread(
					new SineWave(1.0/k, bottomCs.get(i), 0, false), seconds, samplesPerSecond)));
		}
		List<Future<double[]>> wavesOut = new ArrayList<>();
		for(int i = 0; i < topCs.size(); i++) {
			wavesOut.add(threadRunner.submit(new SineWaveThread(
					new SineWave(amplitudes.get(i)*MAXIMUM_AMPLITUDE, topCs.get(i), 0, false), seconds, samplesPerSecond)));
		}
		double[] wave = AddThread.addArraysButterfly(wavesOut, threadRunner);
		threadRunner.shutdown();
		MonoAudioLine monoAudioLine = new MonoAudioLine(samplesPerSecond, BITS_PER_SAMPLE);
		monoAudioLine.play(wave);

		//getWave(double[] amplitudeArray, float samplesPerSecond)		
	}

}
