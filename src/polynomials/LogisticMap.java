package polynomials;

import function.UnivariateFunction;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for creating a logistic maps
 */
public class LogisticMap extends UnivariateFunction {

	// Parameter value. Works best between 0 and 4
	double r;
	
	/**         Creates a logistic map with parameter value r
	 * @param r Parameter value. Works best between 0 and 4
	 */
	public LogisticMap(double r){
		this.r = r;
	}
	
	/**        Takes in a value and calculates the next iteration of this logistic map
	 * @param  in The input value. Works best between 0 and 1
	 * @return The current output and the next input of this logistic map
	 */
	public double fun(double in) {
		return ((r*in)*(1.0-in));
	}
	
}