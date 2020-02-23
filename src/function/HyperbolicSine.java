package function;

import logic.OrderedPair;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for hyperbolic sines
 */
public class HyperbolicSine extends UnivariateFunction {

	// y=(a*(e^x-e^-x)/2)+c
	double a;
	double c;

	/**        Creates a hyperbolic sine with the form y=(a*(e^x-e^-x)/2)+c 
	 * @param  a
	 * @param  c
	 * @throws Exception if a=0 and the returned function would not be a hyperbolic sine
	 */
	public HyperbolicSine(double a, double c) throws Exception {
		if(a == 0) {
			throw notAHyperbolicSineException();
		}
		this.a = a;
		this.c = c;
	}
	
	/**           Creates a hyperbolic sine with 2 ordered pairs
	 * @param op0 An ordered pair for the hyperbolic sine to contain
	 * @param op1 An ordered pair for the hyperbolic sine to contain
	 */
	public HyperbolicSine(OrderedPair op0, OrderedPair op1) {
		double t0 = (double)op0.getX();
		double t1 = (double)op1.getX();
		double f0 = (double)op0.getY();
		double f1 = (double)op1.getY();
		this.a = ((2.0*f0)-(2.0*f1))/((Math.pow(Math.E, t0))-(Math.pow(Math.E, -t0))-(Math.pow(Math.E, t1))+(Math.pow(Math.E, -t1)));
		this.c = f0-(a*(((Math.pow(Math.E, t0))-(Math.pow(Math.E, -t0)))/2.0));
	}
	
	/**
	 * @return an exception that indicates a HyperbolicSine could not be made
	 */
	private Exception notAHyperbolicSineException() {
		return new Exception("a = 0 creates a function that is not a hyperbolic sine");
	}
	
	/**        Takes input in and produces an output based on this hyperbolic sine
	 * @param  in Input value 
	 * @return The output of this power function
	 */
	public double fun(double in) {
		return (a*(Math.pow(Math.E, in)-Math.pow(Math.E, -in))/2.0)+c;
	}

}