package envelopes;

import java.util.Objects;

import arrays.Array;
import waves.SawWave;
import waves.SineWave;
import waves.SquareWave;
import waves.TriangleWave;
import waves.Wave;
import waves.Wave.WaveType;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making frequency envelopes
*/
public class LinearFrequencyEnvelope extends Envelope {

	// The frequency at the beginning of the attack
	double firstFrequency;

	//The frequency at the end of the attack and at the beginning of the decay
	double secondFrequency;

	// The frequency at the end of the decay and at the beginning of the release
	double thirdFrequency;

	// The frequency at the end of the release
	double lastFrequency;

	/**       Constructs a frequency envelope with 
	 * @param attack as the amount of time it takes to get from 0 to the amplitude, in seconds 
	 * @param decay as the amount of time it takes to get from the amplitude to the sustain, in seconds 
	 * @param release as the amount of time it takes to get from the sustain to 0
	 * @param firstFrequency as the frequency at the beginning of the attack
	 * @param secondFrequency as the frequency at the beginning of the decay
	 * @param thirdFrequency as the frequency at the beginning of the release
	 * @param lastFrequency as the frequency at the end of the release
	 */
	public LinearFrequencyEnvelope(double attack, double decay, double release, 
			double firstFrequency, double secondFrequency, double thirdFrequency, double lastFrequency, double samplesPerSecond) {
		if(attack < 0) {
			throw new IllegalArgumentException("attack passed to LinearFrequencyEnvelope() must be at least 0");
		}
		if(decay < 0) {
			throw new IllegalArgumentException("decay passed to LinearFrequencyEnvelope() must be at least 0");
		}
		if(release < 0) {
			throw new IllegalArgumentException("release passed to LinearFrequencyEnvelope() must be at least 0");	
		}
		if(samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to "
					+ "LinearAmplitudeEnvelope() must be greater than 0");	
		}
		// Invariants secured
		this.attack = attack;
		this.release = release;
		this.decay = decay;

		// Fills the attack array
		double numberOfDataPointsNeeded = samplesPerSecond*attack;
		this.secondFrequency = secondFrequency;	
		this.firstFrequency = firstFrequency;	
		double slope = (this.secondFrequency-this.firstFrequency)/numberOfDataPointsNeeded;
		this.attackAmplitudes = new double[(int) Math.round(numberOfDataPointsNeeded)];
		for(int i = 0; i < numberOfDataPointsNeeded; i++) {
			this.attackAmplitudes[i] = firstFrequency + (i*slope);
		}

		// Fills the decay array
		numberOfDataPointsNeeded = samplesPerSecond*decay;
		this.thirdFrequency = thirdFrequency;
		slope = (this.secondFrequency - this.thirdFrequency)/numberOfDataPointsNeeded;
		this.decayAmplitudes = new double[(int) Math.round(numberOfDataPointsNeeded)];
		for(int i = 0; i < numberOfDataPointsNeeded; i++) {
			this.decayAmplitudes[i] = (this.secondFrequency)-(i*slope);
		}

		// Fills the release array
		this.lastFrequency = lastFrequency;
		numberOfDataPointsNeeded = samplesPerSecond*release;
		slope = ((this.thirdFrequency-this.lastFrequency)/numberOfDataPointsNeeded);
		this.releaseAmplitudes = new double[(int) Math.round(numberOfDataPointsNeeded)];
		for(int i = 0; i < numberOfDataPointsNeeded; i++) {
			this.releaseAmplitudes[i] = (this.thirdFrequency-(i*slope));
		}
	}

	/**        Adds this FrequencyEnvelope to an AmplitudeEnvelope
	 * @param  linearAmplitudeEnvelope as the AmplitudeEnvelope
	 * @param  amplitude as the amplitude of the wave
	 * @param  waveType as the WaveType of this oscillator
	 * @param  samplesPerSecond as the sample rate
	 * @return an array of sound representing this FrequencyEnvelope with AmplitudeEnvelope
	 */
	public double[] getLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope(
			LinearAmplitudeEnvelope linearAmplitudeEnvelope, double amplitude, WaveType waveType, float samplesPerSecond){
		Objects.requireNonNull(linearAmplitudeEnvelope);
		if (samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to getLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope() must be greater than 0");
		}
		if (waveType == null) {
			System.out.println("waveType passed to getLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope() is null. Defaulting to SINE.");
		}
		// Invariants secured
		double[] amplitudeArray = linearAmplitudeEnvelope.getEnvelope();
		double[] frequencyArray = this.getEnvelope();
		double[] wave = new double[amplitudeArray.length];
		double[] temp; 
		double radians = 0; 
		boolean phaseTracking = true;
		Wave tempWave;
		for(int i = 0; i < frequencyArray.length; i++) {
			switch(waveType) {
			case SINE: 
				tempWave = new SineWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, phaseTracking); 
				break;
			case TRIANGLE:
				tempWave = new TriangleWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, phaseTracking); 
				break;
			case SAW:
				tempWave = new SawWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, phaseTracking); 
				break;
			case SQUARE:
				tempWave = new SquareWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, phaseTracking); 
				break;
			default:
				tempWave = new SineWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, phaseTracking); 
				break;
			}
			temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
			wave[i] = temp[0]; 
			radians += ((2.0 * Math.PI) / ((samplesPerSecond / (frequencyArray[i]))));
		}
		 return Array.scale(wave, amplitude/Array.max(wave));
	}
	
	/**        Creates the linearFrequencyEnvelope wave
	 * @param  amplitude as the amplitude of the wave
	 * @param  seconds as the time
	 * @param  waveType as the WaveType of this oscillator
	 * @param  samplesPerSecond as the sample rate
	 * @return an array of sound representing this FrequencyEnvelope
	 */
	public double[] getWave(double amplitude, double seconds, WaveType waveType, float samplesPerSecond){
		if (samplesPerSecond < 1) {
			throw new IllegalArgumentException("samplesPerSecond passed to getLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope() must be greater than 0");
		}
		if (waveType == null) {
			System.out.println("waveType passed to getLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope() is null. Defaulting to SINE.");
		}
		if (seconds < 0) {
			throw new IllegalArgumentException("seconds passed to getLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope() must be at least 0");
		}
		// Invariants secured
		double[] frequencyArray = this.getEnvelope();
		double[] wave = new double[(int) Math.round((seconds*samplesPerSecond))];
		double[] temp; 
		double radians = 0; 
		Wave tempWave;
		boolean phaseTracking = true;
		for(int i = 0; i < frequencyArray.length; i++) {
			switch(waveType) {
			case SINE: 
				tempWave = new SineWave(amplitude, frequencyArray[i], radians, phaseTracking);
				break;
			case TRIANGLE:
				tempWave = new TriangleWave(amplitude, frequencyArray[i], radians, phaseTracking); 
				break;
			case SAW:
				tempWave = new SawWave(amplitude, frequencyArray[i], radians, phaseTracking); 
				break;
			case SQUARE:
				tempWave = new SquareWave(amplitude, frequencyArray[i], radians, phaseTracking); 
				break;
			default:
				tempWave = new SineWave(amplitude, frequencyArray[i], radians, phaseTracking); 
				break;
			}
			temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
			wave[i] = temp[0]; 
			radians += ((2.0 * Math.PI) / ((samplesPerSecond / (frequencyArray[i]))));
		}
		 return Array.scale(wave, amplitude/Array.max(wave));
	}
	
	/**       // TODO
	 * @param frequencyOscillator
	 * @param waveType
	 * @param samplesPerSecond
	 */
	public void addFrequencyOscillator(FrequencyOscillator frequencyOscillator, WaveType waveType, 
			float samplesPerSecond) {
		
	}
	
	
}