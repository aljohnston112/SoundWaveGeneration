package envelopesTest;

import java.util.ArrayList;
import java.util.List;

import envelopes.AmplitudeOscillator;
import envelopes.LinearAmplitudeEnvelope;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import waves.SineWave;
import waves.Wave.WaveType;

public class LinearAmplitudeEnvelopeTest extends Application {

	// GUI objects
	Stage primaryStage;
	StackPane root;
	ScatterChart<Number,Number> scatterChart;
	NumberAxis xAxis;
	NumberAxis yAxis;

	// For generating the x-axis
	static double minX = 0;
	static double maxX;
	double deltaX;
	static List<double[]> data = new ArrayList<>();
	
	
	
	public static void main(String[] args) {
		//testLinearAmplitudeEnvelopeConstructor();
		testAddTremoloSwell();
		//testAddTremoloSwellToAttack();
		//testAddTremoloSwellToDecay();
		//testAddTremoloSwellToRelease();
		//testAddTremoloSwellToAttackAndDecay();
		//testAddTremoloSwellToAttackAndRelease();
		//testAddTremoloSwellToDecayAndRelease();
		//testAddTremolo();
		//testAddTremoloToAttack();
		//testAddTremoloToDecay();
		//testAddTremoloToRelease();
		//testAddTremoloToAttackAndDecay();
		//testAddTremoloToAttackAndRelease();
		//testAddTremoloToDecayAndRelease();
		//testAddAmplitudeOscillator();
		launch(args);
	}

	private static void testAddAmplitudeOscillator() {
		//addAmplitudeOscillator(AmplitudeOscillator amplitudeOscillator, WaveType waveType, float samplesPerSecond)

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 5.0; 
		double decay = 5.0; 
		double release = 5.0; 
		float samplesPerSecond = (float) Math.pow(2.0, 5);
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Constructing AmplitudeOscillator with...");
		System.out.println("Starting frequency of 1...");
		System.out.println("Ending frequency of 0.1...");
		System.out.println("Starting amplitude width of 10...");
		System.out.println("Ending amplitude width of 20...");
		System.out.println("And phase of 0...");
		double startingFrequency = 1;
		double endingFrequency = 0.1;
		double startingAmplitudeWidth = 0.1;
		double endingAmplitudeWidth = 0.2;
		double radians = Math.PI / 2.0;
		AmplitudeOscillator amplitudeOscillator = new AmplitudeOscillator(startingFrequency, endingFrequency, startingAmplitudeWidth,
				endingAmplitudeWidth, radians);
		WaveType waveType = WaveType.SINE;
		linearAmplitudeEnvelope.addAmplitudeOscillator(amplitudeOscillator, waveType, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());		
	}

	private static void testAddTremoloSwell() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope.setAttackDecayLoops(2);
		System.out.println("Adding sine wave swell to linear amplitude envelope...");
		// Add tremolo swell to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloSwell(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());		
	}

	private static void testAddTremoloToDecayAndRelease() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave to linear amplitude envelope decay and release...");

		// Add tremolo to release to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloToDecayAndRelease(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());		
	}

	private static void testAddTremoloToAttackAndRelease() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave to linear amplitude envelope attack and release...");

		// Add tremolo to release to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloToAttackAndRelease(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());		
	}

	private static void testAddTremoloToAttackAndDecay() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 0...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 0.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave to linear amplitude envelope attack and decay...");

		// Add tremolo to release to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloToAttackAndDecay(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());			
	}

	private static void testAddTremoloToRelease() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave to linear amplitude envelope release...");
		// Add tremolo to release to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloToRelease(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());	
	}

	private static void testAddTremoloToDecay() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave to linear amplitude envelope decay...");

		// Add tremolo to decay to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloToDecay(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());			
	}

	private static void testAddTremoloToAttack() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave to linear amplitude envelope attack...");

		// Add tremolo to attack to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloToAttack(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());	
	}

	private static void testAddTremoloSwellToDecayAndRelease() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 0...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 1.0; 
		double attack = 1.0; 
		double decay = 0.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave swell to linear amplitude envelope decay and release...");

		// Add tremolo swell to decay and release to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloSwellToDecayAndRelease(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());


	}

	private static void testAddTremoloSwellToAttackAndRelease() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave swell to linear amplitude envelope attack and release...");

		// Add tremolo swell to attack and release to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloSwellToAttackAndRelease(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());

	}

	private static void testAddTremoloSwellToAttackAndDecay() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 10...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 10.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 10...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 10.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave swell to linear amplitude envelope attack and decay...");

		// Add tremolo swell to attack and decay to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloSwellToAttackAndDecay(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());		
	}

	private static void testAddTremoloSwellToRelease() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave swell to linear amplitude envelope release...");

		// Add tremolo swell to release to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloSwellToRelease(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());

	}

	private static void testAddTremoloSwellToDecay() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 5...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 5.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 10...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 10.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave swell to linear amplitude envelope decay...");

		// Add tremolo swell to decay to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloSwellToDecay(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());


	}



	private static void testAddTremoloSwellToAttack() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 10...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 10.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave swell to linear amplitude envelope attack...");

		// Add tremolo swell to attack to linear amplitude envelope
		
		linearAmplitudeEnvelope.addTremoloSwellToAttack(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());			
	}

	private static void testAddTremolo() {
		System.out.println("Creating a sine wave with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Hertz of 2...");
		System.out.println("Radians of 0...");
		System.out.println("Update radians as false...");
		
		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		boolean updateRadians = false;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians, updateRadians);

		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		System.out.println("Adding sine wave to linear amplitude envelope...");

		// Add tremolo to linear amplitude envelope
		linearAmplitudeEnvelope.addTremolo(sineWave, samplesPerSecond);
		data.add(linearAmplitudeEnvelope.getEnvelope());
	}

	private static void testLinearAmplitudeEnvelopeConstructor() {
		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 1...");
		System.out.println("Decay of 2...");
		System.out.println("Release of 1...");
		
		// Create a linear amplitude envelope
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		
		data.add(linearAmplitudeEnvelope.getEnvelope());


	}

	/**For labeling the axis'
	 * 
	 */
	private void labelAxis() {
		primaryStage.setTitle("LinearAmpEnv Test");
		Text text = new Text("LinearAmpEnv methods");
		root.getChildren().add(text);
		xAxis.setLabel("Sample Numer");
		yAxis.setLabel("Amplitude");
		scatterChart.setTitle("LinearAmpEnv methods");			
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		root = new StackPane();
		primaryStage.setScene(new Scene(root, 720, 720));
		primaryStage.show();	
		double[] xAxis = generateXAxis(data);
		plotData(xAxis, data);

	}

	/**        Generates the values for the xAxis
	 * @param  data as multiple sets of y values
	 * @return an array of double with the x-values for the x-axis
	 */
	private double[] generateXAxis(List<double[]> data) {

		// Find the y value set with the most values
		int maxXIndex = 0;
		for(int i = 0; i < data.size(); i++) {
			while(data.get(i) == null) {
				i++;
				if( i >= data.size()) {
					break;
				}
			}
			if( i >= data.size()) {
				break;
			}
			if(maxX < data.get(i).length) {
				maxX = data.get(i).length;
				maxXIndex = i;
			}
		}

		// Generate the x-axis data 
		deltaX = maxX/data.get(maxXIndex).length;
		double[] xAxis = new double[(int) Math.round((maxX/deltaX))];
		for(int i = 0; i < xAxis.length; i++) {
			xAxis[i] = i*deltaX;
		}
		return xAxis;
	}

	/**       Loads data into a scatter plot
	 * @param x as the x-values
	 * @param y as multiple sets of y-values
	 */
	public void plotData(double[] x, List<double[]> y) {

		// For generating the y-axis
		double minY = 0;
		double maxY = 0;
		for(int j = 0; j < y.size(); j++) {
			while(y.get(j) == null) {
				j++;
				if( j >= y.size()) {
					break;
				}
			}
			if( j >= y.size()) {
				break;
			}
			for(int i = 0; i < y.get(j).length; i++) {
				if(y.get(j)[i] < minY) {
					minY = y.get(j)[i];
				}
				if(y.get(j)[i] > maxY) {
					maxY = y.get(j)[i];
				}
			}
		}
		// Generates the axis'
		double deltaY = maxY/100.0;
		xAxis = new NumberAxis(minX, maxX, deltaX);
		yAxis = new NumberAxis(minY, maxY, deltaY);
		// Renders the GUI
		scatterChart = new ScatterChart<>(xAxis, yAxis);
		scatterChart.setHorizontalGridLinesVisible(false);
		scatterChart.setVerticalGridLinesVisible(false);
		labelAxis();
		List<XYChart.Series<Number, Number>> series = new ArrayList<XYChart.Series<Number, Number>>();
		for(int j = 0; j < y.size(); j++) {
			while(y.get(j) == null) {
				j++;
				if( j >= y.size()) {
					break;
				}
			}
			if( j >= y.size()) {
				break;
			}
			series.add(new XYChart.Series<Number, Number>());
			for(int i = 0; i < y.get(j).length; i++) {
				series.get(j).getData().add(new XYChart.Data<Number, Number>(x[i],y.get(j)[i]));
			}
		}
		scatterChart.getData().addAll(series);
		root.getChildren().add(scatterChart);
	}
}

