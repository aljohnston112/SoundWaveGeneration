package envelopes;

import waves.Wave.WaveType;
import waves.SawWave;
import waves.SineWave;
import waves.SquareWave;
import waves.TriangleWave;
import waves.Wave;

/**
 * @author Alexander Johnston
 * @since  2019
 *         A class for creating frequency oscillators
 */
public class FrequencyOscillator {

	// The starting frequency of this oscillator
	double startingFrequency;

	// The starting frequency width of this oscillator
	// This will create a wave that starts with a wave that goes from sfw to -sfw
	double startingFrequencyWidth;

	// The ending frequency of this oscillator
	double endingFrequency;

	// The ending frequency width of this oscillator
	// This will create a wave that starts with a wave that goes from efw to -efw
	double endingFrequencyWidth;

	// The phase of the wave generated by this class
	double radians;

	/**       Constructs a frequency oscillator with
	 * @param startingFrequency as the starting oscillation rate of this oscillator
	 * @param endingFrequency as the ending oscillation rate of this oscillator
	 * @param startingFrequencyWidth as the starting frequency width of this oscillator
	 *        This will create a wave that starts with a wave that goes from sfw to -sfw
	 * @param endingFrequencyWidth as the ending frequency width of this oscillator
	 *        This will create a wave that starts with a wave that goes from efw to -efw
	 * @param radians as the phase of the wave generated by this class
	 */
	public FrequencyOscillator(double startingFrequency, double endingFrequency, double startingFrequencyWidth, double endingFrequencyWidth, double radians) {
		this.startingFrequency = startingFrequency;
		this.endingFrequency = endingFrequency;
		this.startingFrequencyWidth = startingFrequencyWidth/2;
		this.endingFrequencyWidth = endingFrequencyWidth/2;
		this.radians = radians;
	}

	/**        Generates an array of frequencies with
	 * @param  seconds as the length of the wave in seconds
	 * @param  waveType as the wave type of this oscillator
	 * @param  samplesPerSecond as the sample rate
	 * @return an array with the frequencies generated by this oscillator
	 */
	public double[] getOscillator(double seconds, WaveType waveType, float samplesPerSecond) {
		double frequencySlope = (startingFrequency-endingFrequency)/(samplesPerSecond*seconds);
		double frequencyWidthSlope = (startingFrequencyWidth-endingFrequencyWidth)/(samplesPerSecond*seconds);
		double radians = this.radians;
		boolean updateRadians = true;	
		double wave[] = new double[(int) Math.floor((seconds*samplesPerSecond))];
		double[] temp; 
		Wave waveObject = null;
		for(int i = 0; i < wave.length-1; i++) {
			switch(waveType) {
			case SINE: 
				waveObject = new SineWave((startingFrequencyWidth-(frequencyWidthSlope*i))
						, ((startingFrequency-(frequencySlope*(i)))), radians, updateRadians);
				break;
			case TRIANGLE: 
				waveObject = new TriangleWave((startingFrequencyWidth-(frequencyWidthSlope*i))
						, ((startingFrequency-(frequencySlope*(i)))), radians, updateRadians);
				break;
			case SAW: 
				waveObject = new SawWave((startingFrequencyWidth-(frequencyWidthSlope*i))
						, ((startingFrequency-(frequencySlope*(i)))), radians, updateRadians);
				break;
			case SQUARE: 
				waveObject = new SquareWave((startingFrequencyWidth-(frequencyWidthSlope*i))
						, ((startingFrequency-(frequencySlope*(i)))), radians, updateRadians);
				break;
			default:
				waveObject = new SineWave((startingFrequencyWidth-(frequencyWidthSlope*i))
						, ((startingFrequency-(frequencySlope*(i)))), radians, updateRadians);
				break;
			}
			temp = waveObject.getWave(1.0/samplesPerSecond, samplesPerSecond);
			wave[i] = temp[0];
			radians += ((2.0*Math.PI)/((samplesPerSecond/((startingFrequency-(frequencySlope*(i+1)))))));// - ((2.0*Math.PI)/((sr/((sf-(fs*(i+1))))))) -((2.0*Math.PI)/((sr/((sf-(fs*(i))))))); 
			//p += ((2.0*Math.PI*(((sf-(fs*(i+1))))))/sr);
			//p -= ((2.0*Math.PI)*((((sf-(fs*(i+1)))-(sr/((sf-(fs*(i))))))))/sr);
		}
		switch(waveType) {
		case SINE: 
			waveObject = new SineWave((startingFrequencyWidth-(frequencyWidthSlope*wave.length-1))
					, ((startingFrequency-(frequencySlope*(wave.length-1)))), radians, updateRadians);
			break;
		case TRIANGLE: 
			waveObject = new TriangleWave((startingFrequencyWidth-(frequencyWidthSlope*wave.length-1))
					, ((startingFrequency-(frequencySlope*(wave.length-1)))), radians, updateRadians);
			break;
		case SAW: 
			waveObject = new SawWave((startingFrequencyWidth-(frequencyWidthSlope*wave.length-1))
					, ((startingFrequency-(frequencySlope*(wave.length-1)))), radians, updateRadians);
			break;
		case SQUARE: 
			waveObject = new SquareWave((startingFrequencyWidth-(frequencyWidthSlope*wave.length-1))
					, ((startingFrequency-(frequencySlope*(wave.length-1)))), radians, updateRadians);
			break;
		default:
			waveObject = new SineWave((startingFrequencyWidth-(frequencyWidthSlope*wave.length-1))
					, ((startingFrequency-(frequencySlope*(wave.length-1)))), radians, updateRadians);
			break;
		}
		temp = waveObject.getWave(1.0/samplesPerSecond, samplesPerSecond);
		wave[wave.length-1] = temp[0];
		return wave;
	}
	
	/**        Generates a wave of frequencies with
	 * @param  linearAmplitudeEnvelope as the LinearAmplitudeEnvelope
	 * @param  amplitude as the amplitude of this oscillator
	 * @param  centerFrequency as the frequency this oscillator starts at
	 *         WARNING If (cf - sfw) or (cf - efw) is negative, or (cf + sfw) or (cf + efw) is above (sr/2.0) aliasing will occur
	 * @param  waveTypeOscillator as the WaveType of this oscillator
	 * @param  waveTypeWave as the WaveType of the sound save
	 * @param  samplesPerSecond as the sample rate
	 * @return an array with the wave generated WARNING will be null if a waveType has not been specified
	 */
	public double[] getSample(LinearAmplitudeEnvelope linearAmplitudeEnvelope, double amplitude, double centerFrequency, WaveType waveTypeOscillator, WaveType waveTypeWave, float samplesPerSecond) {

		double[] amplitudeArray = linearAmplitudeEnvelope.getEnvelope();
		double[] wave = new double[(int) (amplitudeArray.length)];
		double[] frequencyArray = getOscillator(linearAmplitudeEnvelope.getTime(), waveTypeOscillator, samplesPerSecond); 
		double[] temp; 
		double radians = this.radians; 
		boolean updateRadians = true;	
		Wave tempWave;
		for(int i = 0; i < frequencyArray.length-1; i++) {
			switch(waveTypeWave) {
			case SINE: 
				tempWave = new SineWave(amplitudeArray[i]*amplitude, centerFrequency+frequencyArray[i]
						, radians, updateRadians); 
				break;
			case TRIANGLE:
				tempWave = new TriangleWave(amplitudeArray[i]*amplitude, centerFrequency+frequencyArray[i]
						, radians, updateRadians); 
				break;
			case SAW:
				tempWave = new SawWave(amplitudeArray[i]*amplitude, centerFrequency+frequencyArray[i]
						, radians, updateRadians); 
				break;
			case SQUARE:
				tempWave = new SquareWave(amplitudeArray[i]*amplitude, centerFrequency+frequencyArray[i]
						, radians, updateRadians); 
				break;
			default:
				tempWave = new SineWave(amplitudeArray[i]*amplitude, centerFrequency+frequencyArray[i]
						, radians, updateRadians); 
				break;
			}
			temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
			wave[i] = temp[0]; 
			radians += ((2.0*Math.PI)/((samplesPerSecond/(centerFrequency+frequencyArray[i+1]))));// - (((2.0*Math.PI)/((sr/(cf+osc[i+1])))) - ((2.0*Math.PI)/((sr/(cf+osc[i]))))); 
		}
		switch(waveTypeWave) {
		case SINE: 
			tempWave = new SineWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, centerFrequency+frequencyArray[amplitudeArray.length-1], radians, updateRadians); 
			break;
		case TRIANGLE:
			tempWave = new TriangleWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, centerFrequency+frequencyArray[amplitudeArray.length-1], radians, updateRadians); 
			break;
		case SAW:
			tempWave = new SawWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, centerFrequency+frequencyArray[amplitudeArray.length-1], radians, updateRadians); 
			break;
		case SQUARE:
			tempWave = new SquareWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, centerFrequency+frequencyArray[amplitudeArray.length-1], radians, updateRadians); 
			break;
		default:
			tempWave = new SineWave(amplitudeArray[amplitudeArray.length-1]*amplitude
					, centerFrequency+frequencyArray[amplitudeArray.length-1], radians, updateRadians); 
			break;
		}
		temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
		wave[wave.length-1] = temp[0]; 
		return wave;
	}
	
}