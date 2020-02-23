package test;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import arrayLists.ArrayLists;
import arrays.Array;
import audioLines.AudioLine;
import audioLines.MonoAudioLine;
import audioLines.StereoAudioLine;
import dynamics.Dynamics;
import dynamics.Dynamics.Dynamic;
import envelopes.LinearAmplitudeEnvelope;
import generator.AmplitudeGenerator;
import generator.FrequencyGenerator;
import generator.IntervalGenerator;
import generator.LinearAmplitudeEnvelopeGenerator;
import generator.NodeGenerator;
import generator.NoteGenerator;
import generator.SequenceGenerator;
import generator.TempoGenerator;
import generator.TimeGenerator;
import generator.TimeSignatureGenerator;
import graph.ChanseyCycle;
import graph.Cycle;
import graph.Graph;
import graph.GraphGenerator;
import graph.ThreeDimensionalCycle;
import graph.ThreeDimensionalGraph;
import graph.ThreeDimensionalGraphGenerator;
import logic.OrderedPair;
import notes.EqualTemperament;
import notes.HarmonicMinorScale;
import notes.Interval;
import notes.MajorScale;
import notes.MelodicMinorScale;
import notes.MinorScale;
import notes.Note;
import notes.NoteSequence;
import notes.ThreeDimensionalNoteSequence;
import notes.TwelveToneEqualTemperament;
import rhythm.AdditiveTimeSignature;
import rhythm.MixedTimeSignature;
import rhythm.Tempo;
import rhythm.TimeSignature;
import waves.NoiseWave;
import waves.SawWave;
import waves.SineWave;
import waves.SquareWave;
import waves.TriangleWave;
import waves.Wave;

/**        
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for class testing without plotting
 */
public class Main {

	static final int BITS_PER_SAMPLE = 16;

	static final double  MAXIMUM_AMPLITUDE = (StrictMath.pow(2.0, BITS_PER_SAMPLE-1))/2.0;

	static final float SAMPLES_PER_SECOND = 44100;

	static final double MAXIMUM_FREQUENCY = (SAMPLES_PER_SECOND/2.0)-1.0;

	static final double MIDDLE_A = 440.0;

	public static void main(String[] args) {

		// TODO edit and retest algorithms, envelopes, function, matrices, and polynomials packages
		
		//testWave();
		//testSineWave();
		//testNoiseWave();
		//testSawWave();
		//testSquareWave();
		//testTriangleWave();
		//testTimeSignature();
		//testTempo();
		//testMixedTimeSignature();
		//testAdditiveTimeSignature();
		//testTwelveToneEqualTemperament();
		//testThreeDimensionalNoteSequence();
		//testNoteSequence();
		//testNote();
		//testInterval();
		//testScales();
		//testEqualTemperament();
		//testOrderedPair();
		//testTimeGenerator();
		//testTempoGenerator();
		//testSequenceGenerator();
		//testTimeSignatureGenerator();
		//testNoteGenerator();
		//testNodeGenerator();
		//testLinearAmplitudeEnvelopeGenerator();
		//testIntervalGenerator();
		//testFrequencyGenerator();
		//testAmplitudeGenerator();
		//testLinearAmplitudeEnvelope();
		//testDynamics();
		testThreeDimensionalGraphGenerator();
		testThreeDimensionalGraph();
		testThreeDimensionalCycle();
		testGraphGenerator();
		testGraph();
		testCycle();
		testChanseyCycle();
		//testStereoAudioLine();
		//testMonoAudioLine();
		//testAudioLine();
		//testArray();
		//testArrayLists();

	}

	/**Tests the Wave class 
	 * Passed
	 */
	private static void testWave() {

		// Create a SineWave
		double amplitude = 1.0;
		double hertz = 1.0;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Generate waves
		double seconds = 1.0;
		float samplesPerSecond = 16;
		double[] wave = sineWave.getWave(seconds, samplesPerSecond);
		double[] waveEdge = sineWave.getWaveEdge(seconds, samplesPerSecond);

		// Test getter methods
		double getAmplitude = sineWave.getAmplitude();
		double getHertz = sineWave.getFrequency();
		double getRadians = sineWave.getPhase();

		// Generate an amplitude array
		double[] amplitudeArray = new double[wave.length];
		for(int i = 0; i < amplitudeArray.length; i++) {
			amplitudeArray[i] = (double)i;
		}

		// Add waves
		double[] waveAmplitudeEnvelope = sineWave.getWave(amplitudeArray, samplesPerSecond);
		double[] addedWaves = Wave.addWaves(wave, waveEdge);

		// Concatenate waves
		double[] concatenatedWaves = Wave.concatWave(wave, waveEdge);

		// Delay a wave
		double[] delayedWave = Wave.delay(wave, seconds, samplesPerSecond);

		// Scale a wave
		double multiplier = 2.0;
		double[] scaledWave = Wave.scaleWave(wave, multiplier);

		// Add to a wave
		double addend = 1.0;
		double[] amplitudeShiftedWave = Wave.addToWave(wave, addend);

		// Find max amplitude
		double maxAmplitude = Wave.getMaxAmp(wave);

		// Normalize wave 
		double[] normalizedWave = Wave.normalize(wave, BITS_PER_SAMPLE); 

		// Reverse wave
		double[] reversedWave = Wave.reverse(wave);

		System.out.print("Wave breakpoint\n");
	}

	/**Tests SineWave 
	 * Passed
	 */
	private static void testSineWave() {

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = 1000;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Get waves
		double seconds = 1.0;
		float samplesPerSecond = SAMPLES_PER_SECOND;
		@SuppressWarnings("unused")
		double[] wave = sineWave.getWave(seconds, samplesPerSecond);
		//double[] waveEdge = sineWave.getWaveEdge(seconds, samplesPerSecond);

		// For testing audio channels and waves
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(wave);
		System.out.print("SineWave breakpoint\n");

	}

	/** Tests NoiseWave
	 *  Passed
	 */
	private static void testNoiseWave() {
		// Create a NoiseWave
		double amplitude = MAXIMUM_AMPLITUDE;
		NoiseWave noiseWave = new NoiseWave(amplitude);

		// Get waves
		double seconds = 1.0;
		float samplesPerSecond = SAMPLES_PER_SECOND;
		@SuppressWarnings("unused")
		double[] wave = noiseWave.getWave(seconds, samplesPerSecond);

		// For testing audio channels and waves
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(wave);
		System.out.print("NoiseWave breakpoint\n");		
	}

	/** Tests the SawWave
	 *  Passed
	 */
	private static void testSawWave() {
		// Create a SawWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = 200;
		double radians = 0.0;
		SawWave sawWave = new SawWave(amplitude, hertz, radians);

		// Get waves
		double seconds = 1.0;
		float samplesPerSecond = SAMPLES_PER_SECOND;
		@SuppressWarnings("unused")
		double[] wave = sawWave.getWave(seconds, samplesPerSecond);

		// For testing audio channels and waves
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(wave);
		System.out.print("SawWave breakpoint\n");
	}
	
	/** Tests the SquareWave
	 *  Passed
	 */
	private static void testSquareWave() {
		// Create a SquareWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = 200;
		double radians = 0.0;
		SquareWave squareWave = new SquareWave(amplitude, hertz, radians);

		// Get waves
		double seconds = 1.0;
		float samplesPerSecond = SAMPLES_PER_SECOND;
		@SuppressWarnings("unused")
		double[] wave = squareWave.getWave(seconds, samplesPerSecond);

		// For testing audio channels and waves
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(wave);
		System.out.print("SquareWave breakpoint\n");
	}
	
	/** Tests the TriangleWave
	 *  Passed
	 */
	private static void testTriangleWave() {
		// Create a TriangleWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = 200;
		double radians = 0.0;
		TriangleWave triangleWave = new TriangleWave(amplitude, hertz, radians);

		// Get waves
		double seconds = 1.0;
		float samplesPerSecond = SAMPLES_PER_SECOND;
		@SuppressWarnings("unused")
		double[] wave = triangleWave.getWave(seconds, samplesPerSecond);

		// For testing audio channels and waves
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(wave);
		System.out.print("TriangleWave breakpoint\n");
	}

	/**Test the timeSignature class
	 * Passed
	 */
	private static void testTimeSignature() {

		// Make a TimeSignature
		double beatUnit = 4;
		int beatsPerBar = 4;
		TimeSignature timeSignature = new TimeSignature(beatUnit, beatsPerBar);

		// Make a tempo
		double beatsPerMinute = 120;
		Tempo tempo = new Tempo(beatsPerMinute);

		// Get a time array from the AdditiveTimeSignature
		int bars = 2;
		int beats = 3;
		double[] times = timeSignature.getTime(tempo, bars, beats);

		System.out.print("tempo breakpoint\n");			
	}

	/**Tests Tempo
	 * Passed
	 */
	private static void testTempo() {

		// Make a tempo
		double beatsPerMinute = 120;
		Tempo tempo = new Tempo(beatsPerMinute);
		System.out.print("tempo breakpoint\n");			

	}

	/**Test the MixedTimeSignature class
	 * Passed
	 */
	private static void testMixedTimeSignature() {

		// Make a time signature array
		TimeSignature[] timeSignatures = new TimeSignature[2];

		// Make a TimeSignature
		double beatUnit = 4;
		int beatsPerBar = 4;
		TimeSignature timeSignature = new TimeSignature(beatUnit, beatsPerBar);

		// Make a second TimeSignature
		double beatUnit2 = 8;
		int beatsPerBar2 = 2;
		TimeSignature timeSignature2 = new TimeSignature(beatUnit2, beatsPerBar2);

		// Add the TimeSignatures to the array
		timeSignatures[0] = timeSignature;
		timeSignatures[1] = timeSignature2;

		// Make a MixedTimeSignature
		MixedTimeSignature mixedTimeSignature = new MixedTimeSignature(timeSignatures);

		// Make a tempo
		double beatsPerMinute = 120;
		Tempo tempo = new Tempo(beatsPerMinute);

		// Get a time array from the AdditiveTimeSignature
		int bars = 4;
		int beats = 5;
		double[] times = mixedTimeSignature.getTime(tempo, bars, beats);

		System.out.print("MixedTimeSignature breakpoint\n");					
	}

	/**Tests the AdditiveTimeSignature class
	 * Passed
	 */
	private static void testAdditiveTimeSignature() {

		// Make an AdditiveTimeSignature
		double beatUnit = 4;
		int[] beatsPerBar = {4, 3};
		AdditiveTimeSignature additiveTimeSignature = new AdditiveTimeSignature(beatUnit, beatsPerBar);	

		// Make a tempo
		double beatsPerMinute = 120;
		Tempo tempo = new Tempo(beatsPerMinute);

		// Get a time array from the AdditiveTimeSignature
		int bars = 2;
		int beats = 3;
		double[] times = additiveTimeSignature.getTime(tempo, bars, beats);

		System.out.print("AdditiveTimeSignature breakpoint\n");				
	}

	/**Tests the TwelveToneEqualTemperament class
	 * Passed
	 */
	private static void testTwelveToneEqualTemperament() {
		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);
		System.out.print("Twelve Tone Equal Temperament breakpoint\n");
	}

	/**Test the ThreeDimensionalNoteSequence class
	 * Passed
	 */
	private static void testThreeDimensionalNoteSequence() {
		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = MIDDLE_A;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Create a linear amplitude envelope
		double envelopeAmplitude = 1.0; 
		double sustain = 0.5; 
		double attack = 1; 
		double decay = 1; 
		double release = 1; 
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude/64, sustain, attack, decay, release, SAMPLES_PER_SECOND);

		ArrayList<ArrayList<Object>> notes = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> notes1 = new ArrayList<Object>();
		ArrayList<Object> notes2 = new ArrayList<Object>();

		// Create a note
		Note note = new Note(hertz, linearAmplitudeEnvelope, radians);
		notes1.add(note);
		note = new Note(hertz/2.0, linearAmplitudeEnvelope, radians);
		notes2.add(note);
		notes.add(notes1);
		notes.add(notes2);
		ThreeDimensionalNoteSequence threeDimensionalNoteSequence = new ThreeDimensionalNoteSequence(notes);
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double[] wave = threeDimensionalNoteSequence.getWave(threadRunner, sineWave, SAMPLES_PER_SECOND);
		threadRunner.shutdown();
		// Make audio channel
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(wave);
		System.out.print("Note breakpoint\n");		

	}

	/**Tests the note sequence class
	 * Passed
	 */
	private static void testNoteSequence() {

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = MIDDLE_A;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Create a linear amplitude envelope
		double envelopeAmplitude = 1.0; 
		double sustain = 0.5; 
		double attack = 0.1; 
		double decay = 0.1; 
		double release = 0.1; 
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(envelopeAmplitude, sustain, attack, decay, release, SAMPLES_PER_SECOND);

		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);

		// Create a scale
		char tonicLetter = 'C';
		MajorScale majorScale = new MajorScale(twelveToneEqualTemperament, tonicLetter);

		// Fill a note array
		ArrayList<Note> notes = new ArrayList<Note>();
		Note note;
		Note getNoteD = null;
		for(int i = 0; i < 7; i++) {
			note = majorScale.notes.get(majorScale.middleAIndex-12+i);
			note.setAmplitude(amplitude);
			note.setLinearAmplitudeEnvelope(linearAmplitudeEnvelope);
			notes.add(note);
			if(i == 1) {
				getNoteD = note;
			}
		}

		// Make a note sequence
		NoteSequence noteSequence = new NoteSequence(notes);
		int[] getNoteIndex = noteSequence.getNote(getNoteD);
		Note e = noteSequence.getNote(2);

		// Add a note
		note = majorScale.notes.get(majorScale.middleAIndex-12+7);
		note.setAmplitude(amplitude);
		note.setLinearAmplitudeEnvelope(linearAmplitudeEnvelope);
		noteSequence.addNote(note);

		// Remove a note
		note = majorScale.notes.get(majorScale.middleAIndex-12+4);
		note.setAmplitude(amplitude);
		note.setLinearAmplitudeEnvelope(linearAmplitudeEnvelope);
		noteSequence.removeNote(note);

		noteSequence.removeNote(5);

		double seconds18 = noteSequence.getTime();

		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double[] wave = noteSequence.getSample(threadRunner, sineWave, SAMPLES_PER_SECOND);
		threadRunner.shutdown();

		// Make audio channel
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(wave);
		System.out.print("Note breakpoint\n");
	}

	/**Tests the Note class
	 * Passed
	 */
	private static void testNote() {

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = 333.3;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Create a linear amplitude envelope
		double envelopeAmplitude = 1.0; 
		double sustain = 0.5; 
		double attack = 1.0; 
		double decay = 1.0; 
		double release = 1.0; 
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(envelopeAmplitude, sustain, attack, decay, release, SAMPLES_PER_SECOND);

		// Create a note
		String name = "Middle A";
		Note note = new Note(name, hertz, linearAmplitudeEnvelope, radians);
		double[] wave = note.getWave(sineWave, SAMPLES_PER_SECOND);

		// Make audio channel
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(wave);
		System.out.print("Note breakpoint\n");
	}

	/**Tests the Interval class
	 * Passed
	 */
	private static void testInterval() {

		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);

		// Create a scale
		char tonicLetter = 'C';
		MajorScale majorScale = new MajorScale(twelveToneEqualTemperament, tonicLetter);

		// Create an interval
		int noteIndex = majorScale.middleAIndex;
		int intervalNumber = 5;
		Interval Interval = new Interval(majorScale, noteIndex, intervalNumber);

		System.out.print("Interval breakpoint\n");
	}

	/**TestsThe scale classes
	 * Passed
	 */
	private static void testScales() {

		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);

		// Create a scale
		char tonicLetter = 'A';
		HarmonicMinorScale harmonicMinorScale = new HarmonicMinorScale(twelveToneEqualTemperament, tonicLetter);
		MajorScale majorScale = new MajorScale(twelveToneEqualTemperament, tonicLetter);
		MelodicMinorScale melodicMinorScale = new MelodicMinorScale(twelveToneEqualTemperament, tonicLetter);
		MinorScale minorScale = new MinorScale(twelveToneEqualTemperament, tonicLetter);

		System.out.print("Scales breakpoint\n");

	}

	/**Tests the EqualTemperament class
	 * Passed
	 */
	private static void testEqualTemperament() {

		// Create a new EqualTemperament class
		int notesPerOctave = 24;
		class TwentyFourToneEqualTemperament extends EqualTemperament {

			public TwentyFourToneEqualTemperament(double middleA, int notesPerOctave, float samplesPerSecond) {
				super(middleA, notesPerOctave, samplesPerSecond);
			}

			@Override
			protected void addNames() {
				String[] names  = {"A", "A'", "A#", "A#'", "B", "B'", "C", "C'", "C#","C#'", "D" ,"D'", "D#", "D#'", "E", "E'", "F", "F'", "F#", "F#'", "G", "G'", "G#", "G#'"};
				for(int j = 0; j < notes.size(); j++) {
					notes.get(j).setName(names[j%12]);
				}
			}

		}
		TwentyFourToneEqualTemperament twentyFourToneEqualTemperament = new TwentyFourToneEqualTemperament(MIDDLE_A, notesPerOctave, SAMPLES_PER_SECOND);

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = 333.3;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Create a linear amplitude envelope
		double envelopeAmplitude = 1.0; 
		double sustain = 0.5; 
		double attack = 1; 
		double decay = 1; 
		double release = 1; 
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(envelopeAmplitude, sustain, attack, decay, release, SAMPLES_PER_SECOND);

		// Fill a Note array
		Note[] notes = new Note[24];
		Note note;
		for(int i = 0; i < notes.length; i++) {
			note = twentyFourToneEqualTemperament.notes.get(twentyFourToneEqualTemperament.middleAIndex+i);
			note.setAmplitude(amplitude);
			note.setLinearAmplitudeEnvelope(linearAmplitudeEnvelope);
			notes[i] = note;
		}

		// Make a NoteSequence
		NoteSequence noteSequence = new NoteSequence(notes);

		// Get the wave from the NoteSequence
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double[] wave = noteSequence.getSample(threadRunner, sineWave, SAMPLES_PER_SECOND);
		threadRunner.shutdown();

		// Make audio channel
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(wave);

		System.out.print("EqualTemperament breakpoint\n");
	}


	/**Tests the OrderedPair class
	 * Passed
	 */
	private static void testOrderedPair() {

		// Create OrderedPairs
		OrderedPair orderPair = new OrderedPair("A", 1);
		OrderedPair orderedPair2 = new OrderedPair("A", 1);

		// Test OrderPairs for equality
		boolean equalsTrue = orderPair.equals(orderedPair2);

		// Create an OrderedPair
		OrderedPair orderedPair3 = new OrderedPair("A", 2);

		// Test OrderPairs for equality
		boolean equalsFalse = orderPair.equals(orderedPair3);
		System.out.print(true);

	}

	/**Tests the TimeSignatureGenerator
	 * Passed
	 */
	private static void testTimeSignatureGenerator() {

		// Make a tempo
		double beatsPerMinute = 120;
		Tempo tempo = new Tempo(beatsPerMinute);

		// Make an array of TimeSignatures
		TimeSignature[] timeSignatures = new TimeSignature[10];
		for(int i = 0; i < timeSignatures.length; i++) {
			timeSignatures[i] = TimeSignatureGenerator.getTimeSignature(tempo);
		}

		TimeSignatureGenerator timeSignatureGenerator = new TimeSignatureGenerator();
		TimeSignature[] timeSignatures2 = new TimeSignature[10];
		double[] indices = new double[10];
		for(int i = 0; i < timeSignatures2.length; i++) {
			timeSignatures2[i] = timeSignatureGenerator.getNextTimeSignature();
			indices[i] = timeSignatureGenerator.getLastBeatUnitChoiceIndex();
		}

		System.out.print("TimeSignatureGenerator breakpoint\n");	
	}

	/**Tests the time generator
	 * Passed
	 */
	private static void testTimeGenerator() {

		// Generate a Tempo
		Tempo tempo = TempoGenerator.getTempo();

		// Fill an array with generated times from the Tempo
		double[] ten = new double[10];
		for(int i = 0; i < ten.length; i++) {
			ten[i] = TimeGenerator.getTime(tempo);
		}

		// Create a TimeGenerator with an array of times
		Object[] times = {1.0, 2.0};
		TimeGenerator timeGenerator = new TimeGenerator(times);

		// Create a TimeGenerator with an array of times and probabilities
		double[] probabilities = {0.9, 0.1};
		TimeGenerator timeGenerator2 = new TimeGenerator(times, probabilities);

		double[] tenTimes = new double[20];
		double[] tenTimes2 = new double[20];
		for(int i = 0; i < tenTimes.length; i++) {
			tenTimes[i] = timeGenerator.getNextTime();
			tenTimes2[i] = timeGenerator2.getNextTime();
		}
		System.out.print("TimeGenerator breakpoint\n");	

	}

	/**Tests the TempoGenerator
	 * Passed
	 */
	private static void testTempoGenerator() {

		TempoGenerator tempoGenerator = new TempoGenerator();

		// Fill an array with generated times from the Tempo
		Tempo[] ten = new Tempo[10];
		for(int i = 0; i < ten.length; i++) {
			ten[i] = tempoGenerator.getNextTempo();
		}
		System.out.print("TempoGenerator breakpoint\n");	
	}

	private static void testSequenceGenerator() {
		Dynamics amplitudes = new Dynamics(MAXIMUM_AMPLITUDE);

		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);

		// Create a scale
		char tonicLetter = 'A';
		MajorScale majorScale = new MajorScale(twelveToneEqualTemperament, tonicLetter);

		// 320-5120 hz
		int bottomFrequencyIndex = majorScale.subHertzIndex+(7*5)+1;
		int topFrequencyIndex = majorScale.subHertzIndex+(6*7);
		Object[] frequencies = new Object[topFrequencyIndex-bottomFrequencyIndex+1];
		for(int i = 0; i < frequencies.length; i++) {
			frequencies[i] = majorScale.notes.get(0+bottomFrequencyIndex+i).getFrequency();
		}
		boolean fullNote = false;
		SequenceGenerator sequenceGenerator = new SequenceGenerator(majorScale, bottomFrequencyIndex, topFrequencyIndex, amplitudes, frequencies);
		ThreeDimensionalNoteSequence threeDimensionalNoteSequence = sequenceGenerator.getNextNoteSequence(fullNote, SAMPLES_PER_SECOND);

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = MIDDLE_A;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);
		ExecutorService threadRunner = Executors.newCachedThreadPool();;
		double[] data = threeDimensionalNoteSequence.getWave(threadRunner, sineWave, SAMPLES_PER_SECOND);
		threadRunner.shutdown();

		// Make audio channel
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);

		// Play audio
		mac.play(Wave.normalize(data, BITS_PER_SAMPLE));
		System.out.print("SequenceGenerator breakpoint\n");
	}

	/**Tests the note generator 
	 * Passed
	 */
	private static void testNoteGenerator() {
		Tempo tempo = TempoGenerator.getTempo();
		Dynamics dynamics = new Dynamics(MAXIMUM_AMPLITUDE);

		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);

		// Create a scale
		char tonicLetter = 'A';
		MajorScale majorScale = new MajorScale(twelveToneEqualTemperament, tonicLetter);
		Note[] ten = new Note[10];


		// Create a FrequencyGenerator with an array of frequencies
		int bottomFrequencyIndex = majorScale.subHertzIndex+(7*4)+1;
		int TopFrequencyIndex = majorScale.subHertzIndex+(6*7);
		for(int i = 0; i < ten.length; i++) {
			ten[i] = NoteGenerator.getNote(majorScale, bottomFrequencyIndex, TopFrequencyIndex, tempo, dynamics, SAMPLES_PER_SECOND);	
		}

		FrequencyGenerator frequencyGenerator = new FrequencyGenerator(majorScale, bottomFrequencyIndex, TopFrequencyIndex);
		double[] times = {1.0/12.0, 1.0/8.0, 1.0/6.0, 1.0/4.0, 1.0/3.0, 1.0/2.0, 2.0/3.0, 1.0};

		// Create a AmplitudeGenerator with an array of times
		AmplitudeGenerator amplitudeGenerator = new AmplitudeGenerator(dynamics);
		AmplitudeGenerator amplitudeGenerator2 = new AmplitudeGenerator(dynamics);	

		NoteGenerator noteGenerator = new NoteGenerator(frequencyGenerator, amplitudeGenerator, amplitudeGenerator2, times);
		Note[] tenNotes = new Note[10];
		double[] noteTimes = new double[10];
		double[] noteRestTimes = new double[10];

		for(int i = 0; i < ten.length; i++) {
			tenNotes[i] = noteGenerator.getNextPartialNote(SAMPLES_PER_SECOND);
			noteTimes[i] = tenNotes[i].getDuration();
			noteRestTimes[i] = noteGenerator.getLastRestTime();
		}

		System.out.print("NoteGenerator breakpoint\n");	
	}

	/**TODO Test working NodeGenerator
	 * 
	 */
	private static void testNodeGenerator() {

		// Create a random tempo
		Tempo tempo = TempoGenerator.getTempo();

		// Create a random amplitude
		Dynamics dynamics =new Dynamics(MAXIMUM_AMPLITUDE);

		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);

		// Create a scale
		char tonicLetter = 'A';
		MajorScale majorScale = new MajorScale(twelveToneEqualTemperament, tonicLetter);

		// Create a random node
		int loops = 4;
		int bottomFrequencyIndex = majorScale.subHertzIndex+(7*4)+1;
		int topFrequencyIndex = majorScale.subHertzIndex+(6*7);
		ThreeDimensionalCycle threeDimensionalCycle = NodeGenerator.getFirstNode(majorScale, tempo, bottomFrequencyIndex, topFrequencyIndex, dynamics, SAMPLES_PER_SECOND, loops);
		ThreeDimensionalGraph threeDimensionalGraph = new ThreeDimensionalGraph(threeDimensionalCycle);

		// Get the node output
		ArrayList<ArrayList<Object>> output = threeDimensionalGraph.fun();

		// Create a ThreeDimensionalNoteSequence
		ThreeDimensionalNoteSequence threeDimensionalNoteSequence = new ThreeDimensionalNoteSequence(output);

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = MIDDLE_A;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Get ThreeDimensionalNoteSequence wave
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double[] wave = threeDimensionalNoteSequence.getWave(threadRunner, sineWave, SAMPLES_PER_SECOND);
		threadRunner.shutdown();

		// Make audio channel
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);

		// Play audio
		mac.play(Wave.normalize(wave, BITS_PER_SAMPLE));
		System.out.print("Node breakpoint\n");		

	}

	/**Tests the LinearAmplitudeEnvelopeGenerator
	 * Passed
	 */
	private static void testLinearAmplitudeEnvelopeGenerator() {
		Tempo tempo = TempoGenerator.getTempo();
		Dynamics dynamics = new Dynamics(MAXIMUM_AMPLITUDE);
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = LinearAmplitudeEnvelopeGenerator.generateLinearAmplitudeEnvelope(tempo, dynamics, SAMPLES_PER_SECOND);		
		LinearAmplitudeEnvelope[] linearAmplitudeEnvelope2 = new LinearAmplitudeEnvelope[10];
		double[] staticTimes = new double[10];
		boolean found = false;
		while(!found) {
			for(int i = 0; i < linearAmplitudeEnvelope2.length; i++) {		
				linearAmplitudeEnvelope2[i] = LinearAmplitudeEnvelopeGenerator.generateLinearAmplitudeADREnvelope(tempo.noteTimes, dynamics, SAMPLES_PER_SECOND);
				staticTimes[i] = linearAmplitudeEnvelope2[i].getTime();
				if(staticTimes[i] == tempo.getWhole()) {
					found = true;
				}
			}
		}

		// Create an AmplitudeEnvelopeGenrator with times and amplitudes
		double[] times = {1.0/12.0, 1.0/8.0, 1.0/6.0, 1.0/4.0, 1.0/3.0, 1.0/2.0, 2.0/3.0, 1.0};
		Object[] amplitudes = {1.0, 2.0};
		Object[] amplitudes2 = {3.0, 4.0};

		// Create a AmplitudeGenerator with an array of times
		AmplitudeGenerator amplitudeGenerator = new AmplitudeGenerator(dynamics);
		AmplitudeGenerator amplitudeGenerator2 = new AmplitudeGenerator(dynamics);	
		LinearAmplitudeEnvelopeGenerator linearAmplitudeEnvelopeGenerator = new LinearAmplitudeEnvelopeGenerator(times, amplitudeGenerator, amplitudeGenerator2);

		double[] amplitudeProbabilities = {1.0, 2.0};
		LinearAmplitudeEnvelopeGenerator linearAmplitudeEnvelopeGenerator2 = null;	
		try {
			linearAmplitudeEnvelopeGenerator2 = new LinearAmplitudeEnvelopeGenerator(times, amplitudeGenerator, amplitudeGenerator2, amplitudeProbabilities, amplitudeProbabilities);
		} catch (Exception e) {
			e.printStackTrace();
		}

		found = false;
		LinearAmplitudeEnvelope[] linearAmplitudeEnvelope3 = new LinearAmplitudeEnvelope[10];
		LinearAmplitudeEnvelope[] linearAmplitudeEnvelope4 = new LinearAmplitudeEnvelope[10];
		double[] nonStaticTimes = new double[10];
		double[] nonStaticTimes2 = new double[10];
		while(!found) {
			for(int i = 0; i < linearAmplitudeEnvelope3.length; i++) {		
				linearAmplitudeEnvelope3[i] = linearAmplitudeEnvelopeGenerator.getLinearAmplitudeEnvelope(SAMPLES_PER_SECOND);
				nonStaticTimes[i] = linearAmplitudeEnvelope3[i].getTime();
				if(nonStaticTimes[i] == times[times.length-1]) {
					found = true;
				}
			}
		}

		found = false;
		while(!found) {
			for(int i = 0; i < linearAmplitudeEnvelope4.length; i++) {		
				linearAmplitudeEnvelope4[i] = linearAmplitudeEnvelopeGenerator2.getLinearAmplitudeEnvelope(SAMPLES_PER_SECOND);
				nonStaticTimes2[i] = linearAmplitudeEnvelope4[i].getTime();
				if(nonStaticTimes2[i] == times[times.length-1]) {
					found = true;
				}
			}
		}

		System.out.print("LinearAmplitudeEnvelopeGenerator breakpoint\n");	
	}

	/**Tests the IntervalGenerator class
	 * Passed
	 */
	private static void testIntervalGenerator() {

		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);

		// Create a scale
		char tonicLetter = 'C';
		MajorScale majorScale = new MajorScale(twelveToneEqualTemperament, tonicLetter);

		// Make a tempo
		double beatsPerMinute = 120;
		Tempo tempo = new Tempo(beatsPerMinute);

		// Make dynamics
		Dynamics dynamics = new Dynamics(MAXIMUM_AMPLITUDE);



		// Create a FrequencyGenerator with an array of frequencies
		int bottomFrequencyIndex = majorScale.subHertzIndex+(7*4)+1;
		int topFrequencyIndex = majorScale.subHertzIndex+(6*7);
		// Generate an array of intervals
		Interval[] ten = new Interval[10];
		for(int i = 0; i < ten.length; i++) {
			ten[i] = IntervalGenerator.getInterval(majorScale, tempo, bottomFrequencyIndex, topFrequencyIndex, dynamics, SAMPLES_PER_SECOND);
		}
		FrequencyGenerator frequencyGenerator = new FrequencyGenerator(majorScale, bottomFrequencyIndex, topFrequencyIndex);
		double[] times = {1.0/12.0, 1.0/8.0, 1.0/6.0, 1.0/4.0, 1.0/3.0, 1.0/2.0, 2.0/3.0, 1.0};
		boolean sameLinearAmplitudeEnvelope = false;

		// Create a AmplitudeGenerator with an array of times
		AmplitudeGenerator amplitudeGenerator = new AmplitudeGenerator(dynamics);
		AmplitudeGenerator amplitudeGenerator2 = new AmplitudeGenerator(dynamics);	

		NoteGenerator noteGenerator = new NoteGenerator(frequencyGenerator, amplitudeGenerator, amplitudeGenerator2, times);
		IntervalGenerator intervalGenerator = new IntervalGenerator(noteGenerator, times);
		Interval[] tenIntrervals = new Interval[10];
		for(int i = 0; i < tenIntrervals.length; i++) {
			tenIntrervals[i] = intervalGenerator.getNextInterval(sameLinearAmplitudeEnvelope, SAMPLES_PER_SECOND);
		}
		System.out.print("IntervalGenerator breakpoint\n");					
	}

	/**Tests the frequency generator
	 * Passed
	 */
	private static void testFrequencyGenerator() {

		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);

		// Create a scale
		char tonicLetter = 'C';
		MajorScale majorScale = new MajorScale(twelveToneEqualTemperament, tonicLetter);
		double[] ten = new double[10];


		// Create a FrequencyGenerator with an array of frequencies
		int bottomFrequencyIndex = majorScale.subHertzIndex+(7*4)+1;
		int topFrequencyIndex = majorScale.subHertzIndex+(6*7);
		for(int i = 0; i < ten.length; i++) {
			ten[i] = FrequencyGenerator.getHertz(majorScale, bottomFrequencyIndex, topFrequencyIndex);
		}
		FrequencyGenerator frequencyGenerator = new FrequencyGenerator(majorScale, bottomFrequencyIndex, topFrequencyIndex);

		double[] tenFrequencies = new double[20];
		double[] tenFrequenciesIndices = new double[20];
		for(int i = 0; i < tenFrequencies.length; i++) {
			tenFrequencies[i] = frequencyGenerator.getNextHertz();
			tenFrequenciesIndices[i] = frequencyGenerator.getLastReturnedIndex();
		}

		System.out.print("FrequencyGenerator breakpoint\n");			
	}

	/**Tests the amplitude generator
	 * Passed
	 */
	private static void testAmplitudeGenerator() {

		// Makes a Dynamics
		Dynamics dynamics = new Dynamics(MAXIMUM_AMPLITUDE);

		// Fill an array with amplitudes from the Dynamics
		double[] ten = new double[10];
		for(int i = 0; i < ten.length; i++) {
			ten[i] = AmplitudeGenerator.getAmplitude(dynamics);
		}

		// Create a AmplitudeGenerator with an array of times
		Object[] amplitudes = {1.0, 2.0};
		AmplitudeGenerator amplitudeGenerator = new AmplitudeGenerator(dynamics);

		// Fills an array with random amplitudes
		double[] tenAmplitudes = new double[20];
		for(int i = 0; i < tenAmplitudes.length; i++) {
			tenAmplitudes[i] = (double) amplitudeGenerator.getNextAmplitude();
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.print("AmplitudeGenerator breakpoint\n");		
	}

	/**Test the LinearAmplitudeEnvelope and Envelope classes
	 * Some of the test is in the MainPlotter class
	 * Passed
	 */
	private static void testLinearAmplitudeEnvelope() {

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = 444.4;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Create a linear amplitude envelope
		double[] data;
		double envelopeAmplitude = 1.0; 
		double sustain = 0.5; 
		double attack = 1; 
		double decay = 1; 
		double release = 1; 
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(envelopeAmplitude, sustain, attack, decay, release, SAMPLES_PER_SECOND);

		linearAmplitudeEnvelope.setAttackLoops(0);
		linearAmplitudeEnvelope.setDecayLoops(0);
		linearAmplitudeEnvelope.setReleaseLoops(1);
		linearAmplitudeEnvelope.setAttackDecayLoops(0);
		linearAmplitudeEnvelope.setAttackReleaseLoops(0);
		linearAmplitudeEnvelope.setDecayReleaseLoops(2);


		// Get the linear amplitude envelope time
		double getTime4 = linearAmplitudeEnvelope.getTime();
		data = sineWave.getWave(linearAmplitudeEnvelope.getEnvelope(), SAMPLES_PER_SECOND);		

		// Make audio channel
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);
		mac.play(data);
		System.out.print("LinearAmplitudeEnvelope breakpoint\n");

	}

	/**Tests the dynamics class
	 * Passed
	 */
	private static void testDynamics() {

		// Make dynamics
		Dynamics dynamics = new Dynamics(MAXIMUM_AMPLITUDE);

		// Get amplitudes
		double fff = dynamics.getAmplitude(Dynamic.fff);
		double ff = dynamics.getAmplitude(Dynamic.ff);
		double f = dynamics.getAmplitude(Dynamic.f);
		double mf = dynamics.getAmplitude(Dynamic.mf);
		double mp = dynamics.getAmplitude(Dynamic.mp);
		double p = dynamics.getAmplitude(Dynamic.p);
		double pp = dynamics.getAmplitude(Dynamic.pp);
		double ppp = dynamics.getAmplitude(Dynamic.ppp);

		// Get a crescendo or decrescendo
		double attack = .1;
		double decay = .1;
		double release = .1;
		Dynamic firstDynamic = Dynamic.ff;
		Dynamic secondDynamic = Dynamic.pp;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = dynamics.getDynamicChangeLinearAmplitudeEnvelope(attack, decay, release, SAMPLES_PER_SECOND, firstDynamic, secondDynamic);

		System.out.print("dynamics breakpoint\n");		
	}

	/**Tests the ThreeDimensionalGenerator class
	 * Passed
	 */
	private static void testThreeDimensionalGraphGenerator() {
		Object[] node = {0.0};
		int loops = 2;
		boolean skippable = false;
		boolean loopingNodeHomogenenuity = true;
		ThreeDimensionalCycle threeDimensionalCycle = ThreeDimensionalGraphGenerator.generateSingleNodeLoopCycle(0.0, loops, skippable, loopingNodeHomogenenuity);		
		ThreeDimensionalGraph ThreeDimensionalGraph = new ThreeDimensionalGraph(threeDimensionalCycle);
		ArrayList<ArrayList<Object>> output = ThreeDimensionalGraph.fun();

		Object[] node2 = {2.0, 1.0, 3.0};
		ThreeDimensionalCycle threeDimensionalCycle2 =  ThreeDimensionalGraphGenerator.generateLinearCycle(node2);
		ThreeDimensionalGraph ThreeDimensionalGraph2 = new ThreeDimensionalGraph(threeDimensionalCycle2);
		ArrayList<ArrayList<Object>> output2 = ThreeDimensionalGraph2.fun();

		ThreeDimensionalCycle threeDimensionalCycle3 =  ThreeDimensionalGraphGenerator.generateBranchedNodeCycle(node2, node);
		ThreeDimensionalGraph ThreeDimensionalGraph3 = new ThreeDimensionalGraph(threeDimensionalCycle3);
		ArrayList<ArrayList<Object>> output3 = ThreeDimensionalGraph3.fun();

		ThreeDimensionalCycle threeDimensionalCycle4 =  ThreeDimensionalGraphGenerator.generateMeetAtNodeCycle(node2, node);
		ThreeDimensionalGraph ThreeDimensionalGraph4 = new ThreeDimensionalGraph(threeDimensionalCycle4);
		ArrayList<ArrayList<Object>> output4 = ThreeDimensionalGraph4.fun();

		System.out.print("three dimensional generator breakpoint\n");				

	}

	/**Tests the ThreeDimensionalGraph class
	 * Passed
	 */
	private static void testThreeDimensionalGraph() {

		// Make a 3D Object array
		Object[][] Objects = new Object[2][];
		Object[] ObjectArray1 = new Object[2];
		Object[] ObjectArray2 = new Object[1];

		// Make Objects
		Double one = 1.0;
		Double two = 2.0;
		Object Object1 = one;
		Object Object2 = two;

		// Add Objects to the arrays
		ObjectArray1[0] = Object1;
		ObjectArray1[1] = Object2;
		ObjectArray2[0] = Object1;

		// Add arrays to the 3D array
		Objects[0] = ObjectArray1;
		Objects[1] = ObjectArray2;

		// Make the 3D cycle
		int loops = 0;
		ThreeDimensionalCycle threeDimensionalCycle = new ThreeDimensionalCycle(Objects, loops);

		// Make a 3D Object array
		Object[][] Objects2 = new Object[1][];
		Object[] ObjectArray3 = new Object[3];

		// Add Objects to the arrays
		ObjectArray3[1] = Object1;
		ObjectArray3[2] = Object2;

		// Add arrays to the 3D array
		Objects2[0] = ObjectArray3;

		// Make the 3D cycle
		ThreeDimensionalCycle threeDimensionalCycle2 = new ThreeDimensionalCycle(Objects2, loops);

		// Make a 3D Object array
		Object[][] Objects4 = new Object[1][];
		Object[] ObjectArray4 = new Object[2];

		// Add Objects to the arrays
		ObjectArray4[0] = threeDimensionalCycle;
		ObjectArray4[1] = threeDimensionalCycle2;

		// Add arrays to the 3D array
		Objects4[0] = ObjectArray4;

		// Make the 3D cycle
		ThreeDimensionalCycle threeDimensionalCycle3 = new ThreeDimensionalCycle(Objects4, loops);

		ThreeDimensionalGraph ThreeDimensionalGraph = new ThreeDimensionalGraph(threeDimensionalCycle3);
		ArrayList<ArrayList<Object>> output = ThreeDimensionalGraph.fun();
		System.out.print("three dimensional cycle breakpoint\n");				
	}

	/**Tests the ThreeDimensionalCycle class
	 * Passed 
	 */
	private static void testThreeDimensionalCycle() {

		// Make a 3D Object array
		Object[][] Objects = new Object[2][];
		Object[] ObjectArray1 = new Object[2];
		Object[] ObjectArray2 = new Object[1];

		// Make Objects
		Double one = 1.0;
		Double two = 2.0;
		Object Object1 = one;
		Object Object2 = two;

		// Add Objects to the arrays
		ObjectArray1[0] = Object1;
		ObjectArray1[1] = Object2;
		ObjectArray2[0] = Object1;

		// Add arrays to the 3D array
		Objects[0] = ObjectArray1;
		Objects[1] = ObjectArray2;

		// Make the 3D cycle
		int loops = 0;
		ThreeDimensionalCycle threeDimensionalCycle = new ThreeDimensionalCycle(Objects, loops);

		ArrayList<ArrayList<Object>> output = threeDimensionalCycle.fun();

		// Make a 3D Object array
		Object[][] Objects2 = new Object[1][];
		Object[] ObjectArray3 = new Object[3];

		// Add Objects to the arrays
		ObjectArray3[1] = Object1;
		ObjectArray3[2] = Object2;

		// Add arrays to the 3D array
		Objects2[0] = ObjectArray3;

		// Make the 3D cycle
		ThreeDimensionalCycle threeDimensionalCycle2 = new ThreeDimensionalCycle(Objects2, loops);

		ArrayList<ArrayList<Object>> output2 = threeDimensionalCycle2.fun();

		// Make a 3D Object array
		Object[][] Objects4 = new Object[1][];
		Object[] ObjectArray4 = new Object[2];

		// Add Objects to the arrays
		ObjectArray4[0] = threeDimensionalCycle;
		ObjectArray4[1] = threeDimensionalCycle2;

		// Add arrays to the 3D array
		Objects4[0] = ObjectArray4;

		// Make the 3D cycle
		ThreeDimensionalCycle threeDimensionalCycle3 = new ThreeDimensionalCycle(Objects4, loops);

		ArrayList<ArrayList<Object>> output3 = threeDimensionalCycle3.fun();

		System.out.print("three dimensional cycle breakpoint\n");		

	}

	/**Tests the GraphGenerator class
	 * Passed
	 */
	private static void testGraphGenerator() {

		// Make a node
		Object directedGraphNode = 1.0;	

		// Make a second directed graph node
		Object directedGraphNode2 = 2.0;

		// Make a third directed graph node
		Object directedGraphNode3 = 3.0;

		// Make a fourth directed graph node
		Object directedGraphNode4 = 4.0;

		// Make a node array
		Object[] nodes = {directedGraphNode, directedGraphNode2, directedGraphNode3};

		// Generate cycles
		int loops = 10;
		boolean skippable = true;
		Cycle cycle;
		int groupSize = 2;
		boolean loopingNodeHomogenenuity = false;
		//cycle = GraphGenerator.generateChanseyCycle(nodes, loopingNodeHomogenenuity);
		//cycle = GraphGenerator.generateBranchedNodeCycle(nodes, directedGraphNode);
		//cycle = GraphGenerator.generateMeetAtNodeCycle(nodes, directedGraphNode);
		//cycle = GraphGenerator.generateMeetAtSingleNodeLoopCycle(nodes, directedGraphNode4, loops, skippable, loopingNodeHomogenenuity);
		// Make a directed graph cycle from an array of directed graph nodes
		Double[][] testAnswer = new Double[10][];

		for(int j = 0; j < 10; j++) {
			//cycle = GraphGenerator.generateSingleNodeLoopCycle(directedGraphNode, loops, skippable, loopingNodeHomogenenuity);
			//cycle = GraphGenerator.generateLinearSingleNodeLoopCycle(nodes, loops, skippable, loopingNodeHomogenenuity);
			//cycle = GraphGenerator.generateBranchedSingleNodeLoopCycle(nodes, directedGraphNode4, loops, skippable, loopingNodeHomogenenuity);
			//cycle = GraphGenerator.generateLinearLoopCycle(nodes, loops, loopingNodeHomogenenuity);
			//cycle = GraphGenerator.generateBranchedNodeLinearLoopCycle(nodes, loops, skippable, groupSize, loopingNodeHomogenenuity);
			//cycle = GraphGenerator.generateLoopedBranchedNodeLinearLoopCycle(nodes, directedGraphNode4, loops, skippable, groupSize, loopingNodeHomogenenuity);
			//cycle = GraphGenerator.generateMeetAtNodeLinearLoopCycle(nodes, loops, skippable, groupSize, loopingNodeHomogenenuity);
			cycle = GraphGenerator.generateLoopedMeetAtNodeLinearLoopCycle(nodes, directedGraphNode4, loops, skippable, groupSize, loopingNodeHomogenenuity);

			// Make a directed graph
			if(cycle != null) {
				Graph directedGraph = new Graph(cycle);

				// Get the Graph output
				ArrayList<Object> objectOutput = directedGraph.fun();
				Double[] output = new Double[objectOutput.size()];
				for(int i = 0; i < objectOutput.size(); i++) {
					output[i] = (Double) objectOutput.get(i);
				}
				testAnswer[j] = output;
			}
		}
		System.out.print("directed graph generator node breakpoint\n");		
	}

	/**Tests the Graph class
	 * Passed
	 */
	private static void testGraph() {

		// Create a node 
		Object directedGraphNode = 0.0;

		// Create a cycle with one node
		int loops = 12;
		Cycle directedGraphCycle = new Cycle(directedGraphNode, loops);
		Graph graph = new Graph(directedGraphCycle);

		// Get the graph output
		ArrayList<Object> output = graph.fun();

		System.out.print("directed graph breakpoint\n");		
	}

	/**Tests the Cycle class
	 * Passed
	 */
	private static void testCycle() {

		// Create a node 
		Object directedGraphNode = 0.0;

		// Create a cycle with one node
		int loops = 12;
		Cycle cycle = new Cycle(directedGraphNode, loops);	

		// Create a node array
		Object[] directedGraphNodes = new Object[2];
		directedGraphNodes[0] = 1.0;
		directedGraphNodes[1] = 2.0;

		// Creates a cycle with multiple nodes
		Cycle cycle2 = new Cycle(directedGraphNodes, loops);

		// Creates a nonhomogeneous cycle with one node 
		boolean loopHomology = false;
		Cycle cycle3 = new Cycle(directedGraphNode, loops, loopHomology);

		// Creates a nonhomogeneous cycle with multiple nodes
		Cycle cycle4 = new Cycle(directedGraphNodes, loops, loopHomology);

		// Gets the cycle output
		ArrayList<Object> homogeneousCycleWithOneNode = cycle.fun();
		ArrayList<Object> homogeneousCycleWithMultipleNode = cycle2.fun();
		ArrayList<Object> nonhomogeneousCycleWithOneNode = cycle3.fun();
		ArrayList<Object> nonhomogeneousCycleWithMultipleNode = cycle4.fun();
		System.out.print("Cycle breakpoint\n");
	}

	/**Tests the chanseyCycle class
	 * Passed
	 */
	private static void testChanseyCycle() {

		// Creates a node array
		Object[] nodeChoices = new Object[3];
		nodeChoices[0] = 0.0;
		nodeChoices[1] = 1.0;
		nodeChoices[2] = 2.0;

		// Creates a ChanseyCycle
		int loops = 12;
		ChanseyCycle chanseyCycle = new ChanseyCycle(nodeChoices, loops);

		// Create probabilities
		double[] probabilities = new double[nodeChoices.length];
		probabilities[0] = .12;
		probabilities[1] = .38;
		probabilities[2] = .50;

		// Create a ChanseyCycle with probabilities
		ChanseyCycle chanseyCycle2 = null;
		try {
			chanseyCycle2 = new ChanseyCycle(nodeChoices, probabilities, loops);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Creates a non homogeneous ChanseyCycle
		boolean loopingNodeHomology = false;
		ChanseyCycle chanseyCycle3 = new ChanseyCycle(nodeChoices, loops, loopingNodeHomology);

		// Creates a non homogeneous ChanseyCycle with probabilities
		ChanseyCycle chanseyCycle4 = null;
		try {
			chanseyCycle4 = new ChanseyCycle(nodeChoices, probabilities, loops, loopingNodeHomology);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get the cycle output
		ArrayList<Object> equalProbability = chanseyCycle.fun();
		ArrayList<Object> unequalProbability = chanseyCycle2.fun();
		ArrayList<Object> equalProbabilityNonHomogeneous = chanseyCycle3.fun();
		ArrayList<Object> unequalProbabilityNonHomogeneous = chanseyCycle4.fun();
		System.out.print("ChanseyCycle breakpoint\n");
	}

	/**Tests the StereoAudioLine class
	 * Passed
	 */
	private static void testStereoAudioLine() {

		// Make an stereo audio channel
		float samplesPerSecond = SAMPLES_PER_SECOND;
		int bitsPerSample = BITS_PER_SAMPLE;
		StereoAudioLine stereoAudioLine = new StereoAudioLine(samplesPerSecond, bitsPerSample);

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = 444;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Generate wave
		double seconds = 1.0;
		double[] wave = sineWave.getWave(seconds, samplesPerSecond);

		// Create a SineWave
		double hertz2 = 888;
		SineWave sineWave2 = new SineWave(amplitude, hertz2, radians);

		// Generate wave
		double[] wave2 = sineWave2.getWave(seconds, samplesPerSecond);

		// Play two waves where one is on each side
		stereoAudioLine.play(wave, wave2);

		System.out.print("Stereo AudioLine breakpoint\n");
	}

	/**Test MonoAudioLine
	 * Passed
	 */
	private static void testMonoAudioLine() {

		// Make an mono audio channel
		float samplesPerSecond = SAMPLES_PER_SECOND;
		int bitsPerSample = BITS_PER_SAMPLE;
		MonoAudioLine monoAudioLine = new MonoAudioLine(samplesPerSecond, bitsPerSample);

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = 444;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);

		// Generate waves
		double seconds = 1.0;
		double[] wave = sineWave.getWave(seconds, samplesPerSecond);

		// Play a wave
		monoAudioLine.play(wave);

		System.out.print("MonoAudioLine breakpoint\n");
	}

	/**Tests the AudioLine class
	 * Passed
	 */
	private static void testAudioLine() {

		// Print the supported audioFormats
		AudioLine.printSupportedSourceDataLines();
		System.out.print("AudioLine breakpoint\n");
	}

	/**Tests the Array class
	 * Passed
	 */
	public static void testArray(){

		// Make an array
		double[] doubleArray = {0, -1, 2.435, -3.657, 47777, -5777777777777777777777.0, 1, 2, 3, 4};

		// double[] to short[]
		short[] shortArray = Array.doubleToShort(doubleArray);

		// double[] to byte[]
		byte[] byteArray = Array.doubleToByte(doubleArray);

		// Create a thread
		ExecutorService threadRunner = Executors.newCachedThreadPool();

		// Find max magnitude
		double maxMagnitude = Array.getMaxMag(doubleArray, threadRunner);

		// Find max amplitude
		double maxAmplitude = Array.getMax(doubleArray, threadRunner);

		// Shutdown a thread
		threadRunner.shutdown();

		// Make an array
		double[] doubleArray2 = {10, -11, 12.435, -13.657, 147777, -15777777777777777777777.0, 11, 12, 13, 14};

		// Concatenate two arrays
		double[] arrayConcat = Array.concat(doubleArray, doubleArray2);

		// Make a 2D array
		Object[][] array2D = new Object[2][];

		// Make array rows
		Object[] array = new Object[2];
		Object[] array2 = new Object[3];
		array[0] = 0.0;
		array[1] = 0.0;
		array2[0] = 0.0;
		array2[1] = 0.0;
		array2[2] = 0.0;

		// Add array rows
		array2D[0] = array;
		array2D[1] = array2;

		// Get the number of columns in an array
		int cols3 = Array.getNumberOfColumns(array2D);
		System.out.print("Array breakpoint\n");

	}

	/**Tests the ArrayLists class
	 * Passed
	 */
	public static void testArrayLists(){

		// Create a 2d ArrayList
		ArrayList<ArrayList<Object>> arrayList2D = new ArrayList<ArrayList<Object>>();

		// Create ArrayList rows
		ArrayList<Object> arrayList = new ArrayList<Object>();
		ArrayList<Object> arrayList2 = new ArrayList<Object>();
		arrayList.add(0.0);
		arrayList.add(0.0);
		arrayList2.add(0.0);
		arrayList2.add(0.0);
		arrayList2.add(0.0);

		// Add the ArrayList rows
		arrayList2D.add(arrayList);
		arrayList2D.add(arrayList2);

		// Get the number of columns in a 2d array
		int cols3 = ArrayLists.getNumberOfColumns(arrayList2D);
		System.out.print("ArrayLists breakpoint\n");

	}
}