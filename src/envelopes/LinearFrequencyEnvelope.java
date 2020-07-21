package envelopes;

import waves.SineWave;
import waves.Wave;
import waves.Wave.WaveType;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making frequency envelopes
*/
public class LinearFrequencyEnvelope extends Envelope{

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
	public LinearFrequencyEnvelope(double attack, double decay, double release, double firstFrequency, double secondFrequency, double thirdFrequency, double lastFrequency, double samplesPerSecond) {

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
	public double[] getLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope(LinearAmplitudeEnvelope linearAmplitudeEnvelope, double amplitude, WaveType waveType, float samplesPerSecond){
		double[] amplitudeArray = linearAmplitudeEnvelope.getEnvelope();
		double[] frequencyArray = this.getEnvelope();
		double[] wave = new double[amplitudeArray.length];
		double[] temp; 
		double phase = 0; 
		Wave tempWave;
		switch(waveType) {
		case SINE: 
			tempWave = new SineWave(amplitudeArray[0]*amplitude, frequencyArray[0], phase); 
			break;
			/*
		case TRIANGLE:
			tempWave = new TriangleWave(amplitudeArray[i]*amplitude, frequencyArray[i], phase); 
			break;
		case SAW:
			tempWave = new SawWave(amplitudeArray[i]*amplitude, frequencyArray[i], phase); 
			break;
		case SQUARE:
			tempWave = new SquareWave(amplitudeArray[i]*amplitude, frequencyArray[i], phase); 
			break;
			*/
		default:
			tempWave = new SineWave(amplitudeArray[0]*amplitude, frequencyArray[0], phase); 
			break;
		}
		for(int i = 1; i < frequencyArray.length; i++) {
			tempWave.setAmplitude(amplitudeArray[i]);
			tempWave.setFrequency(frequencyArray[i]);
			temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
			wave[i] = temp[0]; 
		}
		 return Wave.scaleWave(wave, amplitude/Wave.getMaxAmp(wave));
	}
	
	/**        Creates the linearFrequencyEnvelope wave
	 * @param  amplitude as the amplitude of the wave
	 * @param  time as the time
	 * @param  waveType as the WaveType of this oscillator
	 * @param  samplesPerSecond as the sample rate
	 * @return an array of sound representing this FrequencyEnvelope
	 */
	public double[] getWave(double amplitude, double time, WaveType waveType, float samplesPerSecond){
		double[] frequencyArray = this.getEnvelope();
		double[] wave = new double[(int) Math.round((time*samplesPerSecond))];
		double[] temp; 
		double phase = 0; 
		Wave tempWave;
		
		switch(waveType) {
		case SINE: 
			tempWave = new SineWave(amplitude, frequencyArray[0], phase);
			
			break;
			/*
		case TRIANGLE:
			tempWave = new TriangleWave(amplitude, frequencyArray[i], phase); 
			break;
		case SAW:
			tempWave = new SawWave(amplitude, frequencyArray[i], phase); 
			break;
		case SQUARE:
			tempWave = new SquareWave(amplitude, frequencyArray[i], phase); 
			break;
			*/
		default:
			tempWave = new SineWave(amplitude, frequencyArray[0], phase); 
			break;
		}
		temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
		wave[0] = temp[0]; 
		for(int i = 1; i < frequencyArray.length; i++) {
			temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
			wave[i] = temp[0]; 
			tempWave.setFrequency(frequencyArray[i]);
		}
		 return Wave.scaleWave(wave, amplitude/Wave.getMaxAmp(wave));
	}
}