package function;

import logic.OrderedPair;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for power functions
 */
public class Power extends UnivariateFunction {

	// y=a*b^(x)
	double a;
	double b;
	
	// For when the base is a function
	boolean flag = false;
	UnivariateFunction powerFunction;
	
	/** Creates a power function with the form y=a*b^(x)
	 * @param a as the power function coefficient
	 * @param b as the power function base
	 */
	public Power(double a, double b) {
	 this.a = a;
	 this.b = b;
	}
	
	/** Creates a power function with the form y=a*b^(x)
	 * @param a as the coefficient
	 * @param b as a function to take the base of the power function
	 */
	public Power(double a, UnivariateFunction b) {
	 this.a = a;
	 this.powerFunction = b;
	}
	
	/**       Creates a power function with two ordered pairs
	 * @param op1 One of the ordered pairs for the power function to include
	 * @param op2 One of the ordered pairs for the power function to include
	 */
	public Power(OrderedPair op1, OrderedPair op2) {
		double t0 = (double)op1.getX();
		double t1 = (double)op2.getX();
		double f0 = (double)op1.getY();
		double f1 = (double)op2.getY();
		double c = f0;
		if(t0 == 0 || t1 == 0) {
			if(t1 == 0) {
				Double t = t0;
				t0 = t1;
				t1 = t;
				Double f = f0;
				f0 = f1;
				f1 = f;
			}
		a = f0;
		b = Math.pow(f1/a, 1/t1);
		} else {
		a = Math.pow(Math.pow(f0-c, 1/t0)/Math.pow(f1-c, 1/t1), ((t0*t1)/(t0+t1)));
		b =Math.pow(f0-c, 1/t0)/Math.pow(a, 1/t0);
		}
	}
	
	/**        Takes input in and produces an output based on this power function
	 * @param  in Input value 
	 * @return The output of this power function
	 */
	public double fun(double in) {
		double out = (powerFunction!=null && !flag) ? fun(powerFunction, in) : a*(Math.pow(b, in));
		flag = false;
		return out;
	}
	
	/**        Takes the input in and produces an output based on the univariateFunction being the base of this power function
	 * @param  univariateFunction as the function to substitute as the base of this power function
	 * @param  in as the input value for this function
	 * @return an output based on the input, and the univariateFunction being the base of tis power function
	 */
	public double fun(UnivariateFunction univariateFunction, double in) {
		flag = true;
		b = univariateFunction.fun(in);
		return fun(b);
	}
	
	//TODO Derivate and integrate	
}