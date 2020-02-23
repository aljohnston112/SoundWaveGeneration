package function;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for sine functions
 */
public class Sine extends UnivariateFunction {

	// y=a*sin(x)
	double a;
	
	UnivariateFunction uf;
	
	boolean flag;
	
	/**        Creates a sine with the form y=a*sin(i)
	 * @param  a
	 */
	public Sine(double a) {
		this.a = a;
	}
	
	/**        Creates a sine with the form y=a*sin(uf.fun(x))
	 * @param  a
	 */
	public Sine(double a, UnivariateFunction uf) {
		this.a = a;
		this.uf = uf;
	}

	/**        Takes input in and produces an output based on this hyperbolic sine
	 * @param  in Input value 
	 * @return The output of this power function
	 */
	public double fun(double in) {
		double out = (uf!=null && flag) ? fun(uf, in) : a*StrictMath.sin(in);
		flag = true;
		return out;
	}

	public double fun(UnivariateFunction uf, double in) {
		flag = false;
		return fun(uf.fun(in));
	}

}