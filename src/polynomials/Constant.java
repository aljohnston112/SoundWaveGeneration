package polynomials;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for constant functions
 */
public class Constant extends Polynomial{

	// y = c
	double c;
	
	/**
	 * @param c The constant 
	 */
	public Constant(double c){
		this.c = c;
		double[] coef = new double[1];
		coef[0] = this.c;
		this.coef = coef;
	}
	
	/**
	 * @param  in The input value
	 * @return The constant value
	 */
	public double fun(double in) {
		return c;
	}
	
	/**
	 * @return y=0
	 */
	public Constant derivate() {
		return new Constant(0);
	}
	
	/**
	 * @return The integral of this function with initial value 0
	 * @throws Exception if a linear function does not exist (if this constant is 0)
	 */
	public Linear integrate() throws Exception {
		return new Linear(c, 0);
	}
	
	/**
	 * @return The integral of this function
	 * @param  xo The initial value
	 * @throws Exception if a linear function does not exist (if this constant is 0)
	 */
	public Linear integrate(double xo) throws Exception {
		return new Linear(c, xo);
	}
	
}