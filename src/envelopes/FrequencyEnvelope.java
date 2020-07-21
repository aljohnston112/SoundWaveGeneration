package envelopes;

import arrays.Array;
import envelopes.AmplitudeOscillator.WaveType;
import waves.SawWave;
import waves.SineWave;
import waves.SquareWave;
import waves.TriangleWave;
import waves.Wave;

/**
 * @author Alexander Johnston 
 * @since  2019 
 *         A class for making frequency envelopes.
 */
public class FrequencyEnvelope extends Envelope{

	// The frequency at the beginning of the attack
	double startFrequency;

	//The frequency at the end of the attack and at the beginning of the decay
	double secondFrequency;

	// The frequency at the end of the decay and at the beginning of the release
	double thirdFrequency;

	// The frequency at the end of the release
	double endFrequency;

	/**       Constructs a frequency envelope. 
	 * @param attack in seconds as the amount of time it takes to get from 0 to the amplitude 
	 * @param decay in seconds as the amount of time it takes to get from the amplitude to the sustain
	 * @param release in seconds as the amount of time it takes to get from the sustain to 0
	 * @param startFrequency as the frequency at the beginning of the attack
	 * @param secondFrequency as the frequency at the end of the attack and at the beginning of the decay
	 * @param thirdFrequency as the frequency at the end of the decay and at the beginning of the release
	 * @param endFrequency as the frequency at the end of the release
	 */
	public FrequencyEnvelope(double attack, double decay, double release, double startFrequency, double secondFrequency, double thirdFrequency, double endFrequency, double samplesPerSecond) {

		this.attack = attack;
		this.release = release;
		this.decay = decay;

		// Fills the attack ArrayList
		double numberOfDataPointsNeeded = samplesPerSecond*attack;
		this.secondFrequency = secondFrequency;	
		this.startFrequency = startFrequency;	
		double slope = (this.secondFrequency-startFrequency)/numberOfDataPointsNeeded;
		for(int i = 0; i < numberOfDataPointsNeeded; i++) {
			this.attackAmplitudes[i] = (startFrequency + (i*slope));
		}

		// Fills the decay ArrayList
		numberOfDataPointsNeeded = samplesPerSecond*decay;
		this.thirdFrequency = thirdFrequency;
		slope = (this.secondFrequency - this.thirdFrequency)/numberOfDataPointsNeeded;
		for(int i = 0; i < numberOfDataPointsNeeded; i++) {
			this.decayAmplitudes[i] = ((this.secondFrequency)-(i*slope));
		}

		// Fills the release ArrayList
		this.endFrequency = endFrequency;
		numberOfDataPointsNeeded = samplesPerSecond*release;
		slope = ((this.thirdFrequency-this.endFrequency)/numberOfDataPointsNeeded);
		for(int i = 0; i < numberOfDataPointsNeeded; i++) {
			this.releaseAmplitudes[i] = ((this.thirdFrequency-(i*slope)));
		}
	}

	/**
	 * @return An array of frequency values that correspond to the envelope parameters
	 */
	public double[] getEnvelope() {

		// Holds the amplitude values of the envelope
		double[] envelope = new double[0];

		// Loops are added to envelope
		for(int i = 0; i < attackLoops; i++){
			envelope = Array.concat(envelope, attackAmplitudes);
		}
		for(int i = 0; i < attackDecayLoops; i++){
			envelope = Array.concat(envelope, attackAmplitudes);
			envelope = Array.concat(envelope, decayAmplitudes);
		}
		for(int i = 0; i < attackReleaseLoops; i++){
			envelope = Array.concat(envelope, attackAmplitudes);
			envelope = Array.concat(envelope, releaseAmplitudes);
		}
		for(int i = 0; i < loops; i++){
			envelope = Array.concat(envelope, attackAmplitudes);
			envelope = Array.concat(envelope, decayAmplitudes);
			envelope = Array.concat(envelope, releaseAmplitudes);
		}
		for(int i = 0; i < decayLoops; i++){
			envelope = Array.concat(envelope, decayAmplitudes);
		}
		for(int i = 0; i < decayReleaseLoops; i++){
			envelope = Array.concat(envelope, decayAmplitudes);
			envelope = Array.concat(envelope, releaseAmplitudes);
		}
		for(int i = 0; i < releaseLoops; i++){
			envelope = Array.concat(envelope, releaseAmplitudes);
		}
		return envelope;
	}

	/**        Adds this FrequencyEnvelope to a LinearAmplitudeEnvelope.
	 * @param  linearAmplitudeEnvelope as the LinearAmplitudeEnvelope
	 * @param  amplitude as the amplitude of the wave
	 * @param  waveType as the WaveType of this oscillator
	 * @param  samplesPerSecond as the sample rate
	 * @return an array of sound representing this FrequencyEnvelope with the LinearAmplitudeEnvelope
	 */
	public double[] getSample(LinearAmplitudeEnvelope linearAmplitudeEnvelope, double amplitude
			, WaveType waveType, float samplesPerSecond){
		double[] amplitudeArray = linearAmplitudeEnvelope.getEnvelope();
		double[] frequencyArray = this.getEnvelope();
		double[] wave = new double[amplitudeArray.length];
		double[] temp; 
		double radians = 0; 
		boolean updateRadians = true;
		Wave tempWave;

		for(int i = 0; i < frequencyArray.length-1; i++) {
			switch(waveType) {
			case SINE: 
				tempWave = new SineWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			case TRIANGLE:
				tempWave = new TriangleWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			case SAW:
				tempWave = new SawWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			case SQUARE:
				tempWave = new SquareWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			default:
				tempWave = new SineWave(amplitudeArray[i]*amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			}

			temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
			wave[i] = temp[0]; 
			radians += ((2.0*Math.PI)/((samplesPerSecond/((frequencyArray[i+1])))));
		}

		switch(waveType) {
		case SINE: 
			tempWave = new SineWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		case TRIANGLE:
			tempWave = new TriangleWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		case SAW:
			tempWave = new SawWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		case SQUARE:
			tempWave = new SquareWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		default:
			tempWave = new SineWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		}
		temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
		wave[frequencyArray.length-1] = temp[0]; 
		return Array.scale(wave, amplitude/Array.mag(wave));
	}

	/**        Adds this FrequencyEnvelope to an AmplitudeEnvelope.
	 * @param  frequencyEnvelope as the frequency envelope
	 * @param  amplitude as the amplitude of the wave
	 * @param  seconds as the time in seconds
	 * @param  waveType as the WaveType of this envelope
	 * @param  samplesPerSecond as the sample rate
	 * @return an array of sound representing this FrequencyEnvelope over time
	 */
	public double[] getSample(double[] frequencyEnvelope, double amplitude, double seconds
			, WaveType waveType, float samplesPerSecond){
		double[] frequencyArray = new double[(int) Math.ceil((seconds*samplesPerSecond))];
		double[] wave = new double[(int) Math.ceil((seconds*samplesPerSecond))];
		double[] temp; 
		double radians = 0; 
		boolean updateRadians = true;
		Wave tempWave;

		int slices = (frequencyArray.length/frequencyEnvelope.length);
		double slope;
		for(int i = 1; i < frequencyEnvelope.length; i++) {
			slope = (frequencyEnvelope[i]- frequencyEnvelope[i-1])/slices;
			for(int j = 1; j < slices; j++) {
				frequencyArray[(((i-1)*slices)+j)] = (frequencyEnvelope[i-1]+(slope*j));
			}
		}

		for(int i = 0; i < frequencyArray.length-1; i++) {
			switch(waveType) {
			case SINE: 
				tempWave = new SineWave(amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			case TRIANGLE:
				tempWave = new TriangleWave(amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			case SAW:
				tempWave = new SawWave(amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			case SQUARE:
				tempWave = new SquareWave(amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			default:
				tempWave = new SineWave(amplitude, frequencyArray[i], radians, updateRadians); 
				break;
			}

			temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
			wave[i] = temp[0]; 
			radians += ((2.0*Math.PI)/((samplesPerSecond/((frequencyArray[i+1])))));
		}

		switch(waveType) {
		case SINE: 
			tempWave = new SineWave(amplitude, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		case TRIANGLE:
			tempWave = new TriangleWave(amplitude, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		case SAW:
			tempWave = new SawWave(amplitude, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		case SQUARE:
			tempWave = new SquareWave(amplitude, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		default:
			tempWave = new SineWave(amplitude, frequencyArray[frequencyArray.length-1], radians, updateRadians); 
			break;
		}
		temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
		wave[frequencyArray.length-1] = temp[0]; 
		return Array.scale(wave, amplitude/Array.mag(wave));
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