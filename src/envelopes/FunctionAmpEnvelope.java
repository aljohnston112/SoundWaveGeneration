package envelopes;

import function.HyperbolicSine;
import function.Power;
import function.Root;
import logic.OrderedPair;
import polynomials.Cubic;
import polynomials.Linear;
import polynomials.Polynomial;
import polynomials.Quadratic;
import polynomials.Quartic;

public class FunctionAmpEnvelope {

	/**        Creates a linear amplitude envelope that goes from a0 to at
	 * @param  a0 The starting amplitude
	 * @param  at The ending amplitude
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return A linear amplitude envelope that goes from a0 to at
	 */
	public double[] getEnvelopeLinear(double a0, double at, double t, float sr){
		Linear l = new Linear(new OrderedPair(0.0, a0), new OrderedPair(t, at));
		return l.fun(0.0, t, 1.0/sr);
	}
	
	/**        Creates a quadratic shaped amplitude envelope that goes from a0 to at
	 * @param  a0 The first amplitude of this envelope
	 * @param  at The last amplitude of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  sr The sample rate
	 * @return An array of amplitudes that represent the parameters of this amplitude envelope
	 */
	public double[] getEnvelopeQuadratic1(double a0, double at, double t, float sr){
		double dif = at-a0;
		double m = -1;
		double sum = a0;
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
	
	/**        Creates a quadratic shaped amplitude envelope that goes from a0 to at
	 * @param  a0 The first amplitude of this envelope
	 * @param  at The last amplitude of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  sr The sample rate
	 * @return An array of amplitudes that represent the parameters of this amplitude envelope
	 */
	public double[] getEnvelopeQuadratic2(double a0, double at, double t, float sr){
		double dif = at-a0;
		double m = -1.0;
		double sum = a0;
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

	/**        Creates a quadratic shaped amplitude envelope that goes from a0 to at and then to a0
	 * @param  a0 The first and last amplitude of this envelope
	 * @param  at The middle amplitude of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  sr The sample rate
	 * @return An array of amplitudes that represent the parameters of this amplitude envelope
	 */
	public double[] getEnvelopeQuadratic3(double a0, double at, double t, float sr){
		double dif = at-a0;
		double m = -1;
		double sum = a0;
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

	/**        Creates a cubic shaped amplitude envelope that goes from a0 to at
	 * @param  a0 The first amplitude of this envelope
	 * @param  at The last amplitude of this envelope
	 * @param  t The amount of time for this envelope to take
	 * @param  sr The sample rate
	 * @return An array of amplitudes that represent the parameters of this amplitude envelope
	 */
	public double[] getEnvelopeCubic1(double a0, double at, double t, float sr) {
		Cubic c = new Cubic(new OrderedPair(0.0, a0), new OrderedPair(t, at));
		return c.fun(0.0, t, t/(sr*t));
	}

	/**        Creates a cubic shaped amplitude envelope the goes from 
	 *         at to the inflection point at a0 and then to an inflection point at at
	 * @param  a0 The amplitude of an inflection point
	 * @param  at The amplitude of an inflection point
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a cubic shaped amplitude envelope the goes from 
	 *         at to the inflection point at a0 and then to an inflection point at at
	 */
	public double[] getEnvelopeCubic2(double a0, double at, double t, float sr) {
		double t0 = (t/3.0);
		Cubic q = new Cubic(new OrderedPair(0.0, a0), new OrderedPair(t-t0, at));
		double[] data = q.fun(-t0, t-t0, t/(sr*t));
		return data;
	}

	/**        Creates a cubic shaped amplitude envelope the goes from 
	 *         the inflection point at a0 to the inflection point at at and then to a point at a0
	 * @param  a0 The amplitude of an inflection point
	 * @param  at The amplitude of an inflection point
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a cubic shaped amplitude envelope the goes from 
	 *         the inflection point at a0 to the inflection point at at and back to a point at a0
	 */
	public double[] getEnvelopeCubic3(double a0, double at, double t, float sr) {
		double t0 = (t/3.0);
		Cubic q = new Cubic(new OrderedPair(0.0, a0), new OrderedPair(t-t0, at));
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}

	/**        Creates a cubic shaped amplitude envelope the goes from 
	 *         a point at at to the inflection point at a0 to the inflection point at at and then to a point at a0
	 * @param  a0 The amplitude of an inflection point
	 * @param  at The amplitude of an inflection point
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a cubic shaped amplitude envelope the goes from 
	 *         a point at at to the inflection point at a0 to the inflection point at at and then to a point at a0
	 */
	public double[] getEnvelopeCubic4(double a0, double at, double t, float sr) {
		double t0 = (t/4.0);
		Cubic q = new Cubic(new OrderedPair(0.0, a0), new OrderedPair(t-t0-t0, at));
		double[] data = q.fun(-t0, t-t0, t/(sr*t));
		return data;
	}

	/**        Creates a quartic shaped amplitude envelope that goes from 
	 *         the inflection point at a0 to the inflection point at midF to the inflection point at at
	 * @param  a0 The starting amplitude
	 * @param  midA The amplitude at t/2
	 * @param  at The ending amplitude
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a quartic shaped amplitude envelope that goes from 
	 *         the inflection point at a0 to the inflection point at midF to the inflection point at at
	 */
	public double[] getEnvelopeQuartic1(double a0, double midA, double at, double t, float sr) {
		Quartic c = new Quartic(new OrderedPair(0.0, a0), new OrderedPair(t/2.0, midA), new OrderedPair(t, at));
		return c.fun(0, t, t/(sr*t));
	}

	// TODO
	public double[] getEnvelopePolynomial(OrderedPair[] vertices, double t, float sr) throws Exception {
		Polynomial q = new Polynomial(vertices);
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}

	/**        Creates a root function shaped amplitude envelope that goes from 
	 *         a0 to at
	 * @param  a0 The starting amplitude
	 * @param  at The ending amplitude
	 * @param  r The root number Ex: 2 means y = x^(1/2) 
	 * @param  t The time for this envelope to take
	 * @param  sr The sample rate
	 * @return a root function shaped amplitude envelope that goes from 
	 *         a0 to at
	 */
	public double[] getEnvelopeRoot(double a0, double at, double r, double t, float sr){
		Root q = new Root(r, new OrderedPair(0.0, a0),new OrderedPair(t, at));
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}

	/**        Creates an array of amplitudes represented as a power function
	 * @param  a0 The starting amplitude of the envelope
	 * @param  f The ending amplitude of the envelope
	 * @param  t The time for the envelope to take
	 * @param  sr The sample rate
	 * @return n Array of amplitudes represented as a power function
	 */
	public double[] getEnvelopePow(double a0, double a, double t, float sr){
		Power q = new Power(new OrderedPair(0.0, a0),new OrderedPair(t, a));
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}
	
	/**        Creates an array of amplitudes represented as a hyperbolic sine function
	 * @param  a0 The starting amplitude of the envelope
	 * @param  f The ending amplitude of the envelope
	 * @param  t The time for the envelope to take
	 * @param  sr The sample rate
	 * @return n Array of amplitudes represented as a hyperbolic sine function
	 */
	public double[] getEnvelopeHypeSine(double a0, double a, double t, float sr){
		HyperbolicSine q = new HyperbolicSine(new OrderedPair(0.0, a0),new OrderedPair(t, a));
		double[] data = q.fun(0, t, t/(sr*t));
		return data;
	}
	
}