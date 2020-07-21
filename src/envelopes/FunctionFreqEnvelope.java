package envelopes;

import arrays.Array;
import envelopes.AmplitudeOscillator.WaveType;
import function.HyperbolicSine;
import function.Power;
import function.Root;
import logic.OrderedPair;
import polynomials.Cubic;
import polynomials.Linear;
import polynomials.Polynomial;
import polynomials.Quadratic;
import polynomials.Quartic;
import polynomials.Quintic;
import waves.SawWave;
import waves.SineWave;
import waves.SquareWave;
import waves.TriangleWave;
import waves.Wave;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for creating frequency envelopes from functions
 */
public class FunctionFreqEnvelope {

	// The phase
	private double radians;
	
	final private boolean updateRadians;

	/**       Creates a function frequency envelope that starts at phase p.
	 * @param radians The phase to start this function frequency envelope at
	 */
	public FunctionFreqEnvelope(double radians, boolean updateRadians){
		this.radians = radians;
		this.updateRadians = updateRadians;
	}

	/**        Creates a linear frequency envelope that goes from f0 to ft.
	 * @param  f0 The starting frequency
	 * @param  ft The ending frequency
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return A linear frequency envelope that goes from f0 to ft
	 */
	public double[] getEnvelopeLinear(double f0, double ft, double t, float sr){
		Linear l = new Linear(new OrderedPair(0.0, f0), new OrderedPair(t, ft));
		return l.fun(0.0, t, 1.0/sr);
	}
	
	/**        Creates a wave sample of a linear shaped frequency envelope that goes from f0 to ft.
	 * @param  f0 The first frequency of this envelope
	 * @param  ft The last frequency of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return A wave sample of a linear shaped frequency envelope that goes from f0 to ft
	 */
	public double[] getSampleLinear(double f0, double ft, double t, double a, WaveType wt, float sr){
		double[] fa = getEnvelopeLinear(f0, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	
	/**        Creates a quadratic shaped frequency envelope that goes from f0 to ft.
	 * @param  f0 The first frequency of this envelope
	 * @param  ft The last frequency of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  sr The sample rate
	 * @return An array of frequencies that represent the parameters of this frequency envelope
	 */
	public double[] getEnvelopeQuadratic1(double f0, double ft, double t, float sr){
		double dif = ft-f0;
		double m = -1;
		double sum = f0;
		if(dif < 0) {
			dif*=-1;
			m*=-1;
			sum = 0;
		}
		double maxAmpX = (Math.sqrt(dif));
		Quadratic q = new Quadratic(new OrderedPair(maxAmpX, Math.pow(maxAmpX, 2.0)), m);
		double[] data = q.fun(0, maxAmpX, (maxAmpX)/(sr*t));
		for(int i = 0; i < data.length; i++) {
			data[i] += sum;
		}
		return data;
	}

	/**        Creates a wave sample of a quadratic shaped frequency envelope that goes from f0 to ft.
	 * @param  f0 The first frequency of this envelope
	 * @param  ft The last frequency of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return A wave sample of a quadratic shaped frequency envelope that goes from f0 to ft
	 */
	public double[] getSampleQuadratic1(double f0, double ft, double t, double a, WaveType wt, float sr){
		double[] fa = getEnvelopeQuadratic1(f0, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}
	
	/**        Creates a quadratic shaped frequency envelope that goes from f0 to ft.
	 * @param  f0 The first frequency of this envelope
	 * @param  ft The last frequency of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  sr The sample rate
	 * @return An array of frequencies that represent the parameters of this frequency envelope
	 */
	public double[] getEnvelopeQuadratic2(double f0, double ft, double t, float sr){
		double dif = ft-f0;
		double m = -1.0;
		double sum = f0;
		if(dif < 0) {
			dif*=-1;
			m*=-1.0;
			sum = 0.00;
		}
		double maxAmpX = (Math.sqrt(dif));
		Quadratic q = new Quadratic(new OrderedPair(maxAmpX, Math.pow(maxAmpX, 2.0)), m);
		double[] data = q.fun(maxAmpX, 2.0*maxAmpX, (maxAmpX)/(sr*t));
		for(int i = 0; i < data.length; i++) {
			data[i] += sum;
		}
		return data;
	}

	/**        Creates a wave sample of a quadratic shaped frequency envelope that goes from f0 to ft.
	 * @param  f0 The first frequency of this envelope
	 * @param  ft The last frequency of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return A wave sample of a quadratic shaped frequency envelope that goes from f0 to ft
	 */
	public double[] getSampleQuadratic2(double f0, double ft, double t, double a, WaveType wt, float sr){
		double[] fa = getEnvelopeQuadratic2(f0, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Creates a quadratic shaped frequency envelope that goes from f0 to ft and then to f0.
	 * @param  f0 The first and last frequency of this envelope
	 * @param  ft The middle frequency of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  sr The sample rate
	 * @return An array of frequencies that represent the parameters of this frequency envelope
	 */
	public double[] getEnvelopeQuadratic3(double f0, double ft, double t, float sr){
		double dif = ft-f0;
		double m = -1;
		double sum = f0;
		if(dif < 0) {
			dif*=-1;
			m*=-1;
			sum = 0;
		}
		double maxAmpX = (Math.sqrt(dif));
		Quadratic q = new Quadratic(new OrderedPair(maxAmpX, Math.pow(maxAmpX, 2.0)), m);
		double[] data = q.fun(0, maxAmpX*2, (maxAmpX*2)/(sr*t));
		for(int i = 0; i < data.length; i++) {
			data[i] += sum;
		}
		return data;
	}

	/**        Creates a wave sample of a quadratic shaped frequency envelope that goes from f0 to ft and then to f0.
	 * @param  f0 The first and last frequency of this envelope
	 * @param  ft The middle frequency of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return A wave sample of a quadratic shaped frequency envelope that goes from f0 to ft and back down to f0
	 */
	public double[] getSampleQuadratic3(double f0, double ft, double t, double a, WaveType wt, float sr){
		double[] fa = getEnvelopeQuadratic3(f0, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Creates a cubic shaped frequency envelope that goes from f0 to ft.
	 * @param  f0 The first frequency of this envelope
	 * @param  ft The last frequency of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  sr The sample rate
	 * @return An array of frequencies that represent the parameters of this frequency envelope
	 */
	public double[] getEnvelopeCubic1(double f0, double ft, double t, float sr) {
		Cubic c = new Cubic(new OrderedPair(0.0, f0), new OrderedPair(t, ft));
		return c.fun(0.0, t, t/(sr*t));
	}

	/**        Creates a wave sample of a cubic shaped frequency envelope that goes from f0 to ft.
	 * @param  f0 The first frequency of this envelope
	 * @param  ft The last frequency of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return A wave sample of a cubic shaped frequency envelope that goes from f0 to ft
	 */
	public double[] getSampleCubic1(double f0, double ft, double t, double a, WaveType wt, float sr) {
		double[] fa = getEnvelopeCubic1(f0, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Creates a cubic shaped frequency envelope the goes from 
	 *         ft to the inflection point at f0 and then to an inflection point at ft.
	 * @param  f0 The frequency of an inflection point
	 * @param  ft The frequency of an inflection point
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a cubic shaped frequency envelope the goes from 
	 *         ft to the inflection point at f0 and then to an inflection point at ft
	 */
	public double[] getEnvelopeCubic2(double f0, double ft, double t, float sr) {
		double t0 = (t/3.0);
		Cubic q = new Cubic(new OrderedPair(0.0, f0), new OrderedPair(t-t0, ft));
		double[] data = q.fun(-t0, t-t0, t/(sr*t));
		return data;
	}

	/**       Creates a wave sample of a cubic shaped frequency envelope that goes from
	 *        ft to the inflection point at f0 and then to an inflection point at ft.
	 * @param  f0 The frequency of an inflection point
	 * @param  ft The frequency of an inflection point
	 * @param  t The time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return A wave sample of a cubic shaped frequency envelope that goes from 
	 *         ft to the inflection point at f0 and then to an inflection point at ft
	 */
	public double[] getSampleCubic2(double f0, double ft, double t, double a, WaveType wt, float sr) {
		double[] fa = getEnvelopeCubic2(f0, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Creates a cubic shaped frequency envelope the goes from 
	 *         the inflection point at f0 to the inflection point at ft and then to a point at f0.
	 * @param  f0 The frequency of an inflection point
	 * @param  ft The frequency of an inflection point
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a cubic shaped frequency envelope the goes from 
	 *         the inflection point at f0 to the inflection point at ft and back to a point at f0
	 */
	public double[] getEnvelopeCubic3(double f0, double ft, double t, float sr) {
		double t0 = (t/3.0);
		Cubic q = new Cubic(new OrderedPair(0.0, f0), new OrderedPair(t-t0, ft));
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}

	/**        Creates a wave from a cubic shaped frequency envelope the goes from 
	 *         the inflection point at f0 to the inflection point at ft and then to a point at f0.
	 * @param  f0 The frequency of an inflection point
	 * @param  ft The frequency of an inflection point
	 * @param  t The time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return The wave of a cubic shaped frequency envelope the goes from 
	 *         the inflection point at f0 to the inflection point at ft and back to a point at f0
	 */
	public double[] getSampleCubic3(double f0, double ft, double t, double a, WaveType wt, float sr) {
		double[] fa = getEnvelopeCubic3(f0, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Creates a cubic shaped frequency envelope the goes from 
	 *         a point at ft to the inflection point at f0 to the inflection point at ft and then to a point at f0.
	 * @param  f0 The frequency of an inflection point
	 * @param  ft The frequency of an inflection point
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a cubic shaped frequency envelope the goes from 
	 *         a point at ft to the inflection point at f0 to the inflection point at ft and then to a point at f0
	 */
	public double[] getEnvelopeCubic4(double f0, double ft, double t, float sr) {
		double t0 = (t/4.0);
		Cubic q = new Cubic(new OrderedPair(0.0, f0), new OrderedPair(t-t0-t0, ft));
		double[] data = q.fun(-t0, t-t0, t/(sr*t));
		return data;
	}

	/**        Creates a wave from a cubic shaped frequency envelope the goes from 
	 *         a point at ft to the inflection point at f0 to the inflection point at ft and then to a point at f0.
	 * @param  f0 The frequency of an inflection point
	 * @param  ft The frequency of an inflection point
	 * @param  t The time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return The wave of a cubic shaped frequency envelope the goes from 
	 *         a point at ft to the inflection point at f0 to the inflection point at ft and then to a point at f0
	 */
	public double[] getSampleCubic4(double f0, double ft, double t, double a, WaveType wt, float sr) {
		double[] fa = getEnvelopeCubic4(f0, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Creates a quartic shaped frequency envelope that goes from 
	 *         the inflection point at f0 to the inflection point at midF to the inflection point at ft.
	 * @param  f0 The starting frequency
	 * @param  midF The frequency at t/2
	 * @param  ft The ending frequency
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a quartic shaped frequency envelope that goes from 
	 *         the inflection point at f0 to the inflection point at midF to the inflection point at ft
	 */
	public double[] getEnvelopeQuartic1(double f0, double midF, double ft, double t, float sr) {
		Quartic c = new Quartic(new OrderedPair(0.0, f0), new OrderedPair(t/2.0, midF), new OrderedPair(t, ft));
		return c.fun(0, t, t/(sr*t));
	}

	/**        Creates a wave from a quartic shaped frequency envelope that goes from 
	 *         the inflection point at f0 to the inflection point at midF to the inflection point at ft.
	 * @param  f0 The starting frequency
	 * @param  midF The frequency at t/2
	 * @param  ft The ending frequency
	 * @param  t The time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return A wave from a quartic shaped frequency envelope that goes from 
	 *         the inflection point at f0 to the inflection point at midF to the inflection point at ft
	 */
	public double[] getSampleQuartic1(double f0, double midF, double ft, double t, double a, WaveType wt, float sr) {
		double[] fa = getEnvelopeQuartic1(f0, midF, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Creates a quartic shaped frequency envelope that goes from 
	 *         the inflection point at f0 to the inflection point at midF to the inflection point at ft.
	 * @param  f0 The starting frequency
	 * @param  midF The frequency at t/3
	 * @param  midF2 The frequency at 2*t/3
	 * @param  ft The ending frequency
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a quartic shaped frequency envelope that goes from 
	 *         the inflection point at f0 to the inflection point at midF to the inflection point at ft
	 */
	public double[] getEnvelopeQuintic1(double f0, double midF, double midF2, double ft, double t, float sr) {
		Quintic c = new Quintic(new OrderedPair(0.0, f0), new OrderedPair(t/3.0, midF), new OrderedPair(2.0*t/3.0, midF), new OrderedPair(t, ft));
		return c.fun(0, t, t/(sr*t));
	}

	/**        Creates a wave from a quartic shaped frequency envelope that goes from 
	 *         the inflection point at f0 to the inflection point at midF to the inflection point at ft.
	 * @param  f0 The starting frequency
	 * @param  midF The frequency at t/3
	 * @param  midF The frequency at 2*t/3
	 * @param  ft The ending frequency
	 * @param  t The time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return A wave from a quartic shaped frequency envelope that goes from 
	 *         the inflection point at f0 to the inflection point at midF to the inflection point at ft
	 */
	public double[] getSampleQuintic1(double f0, double midF, double midF2, double ft, double t, double a, WaveType wt, float sr) {
		double[] fa = getEnvelopeQuintic1(f0, midF, midF2, ft, t, sr);
		return getSample(fa, t, a, wt, sr);
	}
	
	// TODO
	public double[] getEnvelopePolynomial(OrderedPair[] vertices, double t, float sr) throws Exception {
		Polynomial q = new Polynomial(vertices);
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}

	// TODO
	public double[] getSamplePolynomial(OrderedPair[] vertices, double t, double a, WaveType wt, float sr) throws Exception {
		double[] fa = getEnvelopePolynomial(vertices, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Creates a root function shaped frequency envelope that goes from 
	 *         f0 to ft.
	 * @param  f0 The starting frequency
	 * @param  ft The ending frequency
	 * @param  r The root number Ex: 2 means y = x^(1/2) 
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a root function shaped frequency envelope that goes from 
	 *         f0 to ft
	 */
	public double[] getEnvelopeRoot(double f0, double ft, double r, double t, float sr){
		Root q = new Root(r, new OrderedPair(0.0, f0),new OrderedPair(t, ft));
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}

	/**        Creates a wave from a root function shaped frequency envelope that goes from 
	 *         f0 to ft
	 * @param  f0 The starting frequency
	 * @param  midF The frequency at t/2
	 * @param  ft The ending frequency
	 * @param  t The time for this envelope to take
	 * @param  a The amplitude of the wave representing the parameters of this frequency envelope
	 * @param  wt The wave type of the wave to be generated
	 * @param  sr The sample rate
	 * @return A wave from a root function shaped frequency envelope that goes from 
	 *         f0 to ft
	 */
	public double[] getSampleRoot(double f0, double ft, double r, double t, double a, WaveType wt, float sr){
		double[] fa = getEnvelopeRoot(f0, ft, r, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Creates an array of frequencies represented as a power function.
	 * @param  f0 The starting frequency of the envelope
	 * @param  f The ending frequency of the envelope
	 * @param  t The time for the envelope to take
	 * @param  sr The sample rate
	 * @return n Array of frequencies represented as a power function
	 */
	public double[] getEnvelopePow(double f0, double f, double t, float sr){
		Power q = new Power(new OrderedPair(0.0, f0),new OrderedPair(t, f));
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}

	/**        Creates a wave that changes frequency according to a power function.
	 * @param  f0 The starting frequency of the envelope
	 * @param  f The ending frequency of the envelope
	 * @param  t The time for the envelope to take
	 * @param  a The amplitude of the wave
	 * @param  wt The type of wave to play
	 * @param  sr The sample rate
	 * @return A wave that changes frequency according to a power function
	 */
	public double[] getSamplePow(double f0, double f, double t, double a, WaveType wt, float sr){
		double[] fa = getEnvelopePow(f0, f, t, sr);
		return getSample(fa, t, a, wt, sr);
	}
	
	/**        Creates an array of frequencies represented as a hyperbolic sine function.
	 * @param  f0 The starting frequency of the envelope
	 * @param  f The ending frequency of the envelope
	 * @param  t The time for the envelope to take
	 * @param  sr The sample rate
	 * @return n Array of frequencies represented as a hyperbolic sine function
	 */
	public double[] getEnvelopeHypeSine(double f0, double f, double t, float sr){
		HyperbolicSine q = new HyperbolicSine(new OrderedPair(0.0, f0),new OrderedPair(t, f));
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}

	/**        Creates a wave that changes frequency according to a hyperbolic sine function.
	 * @param  f0 The starting frequency of the envelope
	 * @param  f The ending frequency of the envelope
	 * @param  t The time for the envelope to take
	 * @param  a The amplitude of the wave
	 * @param  wt The type of wave to play
	 * @param  sr The sample rate
	 * @return A wave that changes frequency according to a hyperbolic sine function
	 */
	public double[] getSampleHypeSine(double f0, double f, double t, double a, WaveType wt, float sr){
		double[] fa = getEnvelopeHypeSine(f0, f, t, sr);
		return getSample(fa, t, a, wt, sr);
	}

	/**        Gets a wave sample given an array of frequencies.
	 * @param  frequencyArray The array of frequencies
	 * @param  seconds The amount of time to go through the array of frequencies
	 * @param  amplitude The amplitude of the wave
	 * @param  waveType The wave type
	 * @param  samplesPerSecond The sample rate
	 * @return A wave sample whose frequency changes according to the array
	 */
	private double[] getSample(double[] frequencyArray, double seconds, double amplitude
			, WaveType waveType, float samplesPerSecond) {
		double[] wave = new double[frequencyArray.length];
		double[] temp; 
		Wave tempWave;
		double startRadians = this.radians;
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

			radians += ((2.0*Math.PI)/((samplesPerSecond/(frequencyArray[i+1])))); 
			temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
			wave[i] = temp[0]; 
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
		radians += ((2.0*Math.PI)/((samplesPerSecond/(frequencyArray[frequencyArray.length-1])))); 
		temp = tempWave.getWave(1.0/samplesPerSecond, samplesPerSecond); 
		wave[frequencyArray.length-1] = temp[0]; 
		if(!this.updateRadians) {
			this.radians = startRadians;
		}
		return Array.scale(wave, amplitude/Array.mag(wave));
	}

}