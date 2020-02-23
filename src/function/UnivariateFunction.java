package function;

import logic.OrderedPair;

/**
 * @author Alexander Johnston
 * Copyright 2019
 * A class for univariate functions to extend
 */
public abstract class UnivariateFunction {
	
	public abstract double fun(double in);	
	
	/**        Takes input in and produces an output based on this polynomial
	 * @param  in Input values 
	 * @return The output of this polynomial
	 */
	public double[] fun(double[] in){
		double[] y = new double[in.length];
		for(int i = 0; i < in.length; i++) {
			y[i] = fun(in[i]);
		}
		return y;
	}

	/**        Takes input x and produces an output based on this polynomial
	 * @param  minX The first x
	 * @param  maxX The max that x can be that may or may not be the last x
	 * @param  interval The interval between x values
	 * @return The output of this polynomial
	 */
	public double[] fun(double minX, double maxX, double interval) {
		double[] y = new double[ ((int)Math.floor((maxX-minX)/interval))+1];
		for(int i = 0; i < y.length; i++) {
			y[i] = fun((i*interval)+minX);
		}
		return y;
	}

	/**        Iterates this polynomial
	 * @param  init The initial value of the iteration
	 * @param  iterations The number of iterations to perform
	 * @return The results of the iteration
	 */
	public double[] iterate(double init, int iterations) {
		double[] y = new double[iterations];
		double yBuf = init;
		y[0] = init;
		for(int i = 1; i < iterations; i++) {
			y[i] = fun(yBuf);
			yBuf = y[i];
		}
		return y;
	}
	
	/**
	 * @return The ordered pair of the y-intercept
	 */
	public OrderedPair yIntercept(){
		return new OrderedPair(0.0, fun(0.0));
	}

	
	
}