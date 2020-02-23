package polynomials;

import logic.OrderedPair;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for linear functions
 */
public class Linear extends Polynomial{

	// ax+b
	double a;
	double b;
	
	/**       Creates a linear function ax+b
	 * @param a ax
	 * @param b b
	 * @throws Exception Throws an exception when a = 0 because y = b is not a linear function 
	 */
	public Linear(double a, double b) throws Exception {
		if(a == 0) {
			throw notALinearFunctionException();
		}
		this.a = a;
		this.b = b;
		double[] coef = new double[2];
		coef[0] = this.a;
		coef[1] = this.b;
		this.coef = coef;
	}

	/**       Creates a linear function with a point at op and a slope of a
	 * @param op The ordered pair of a point for this function to include
	 * @param a The slope
	 */
	public Linear(OrderedPair op, double a) {
		this.a = a;
		this.b = (double)op.getY()-(a*(double)op.getX());
		double[] coef = new double[2];
		coef[0] = this.a;
		coef[1] = this.b;
		this.coef = coef;
	}
	
	/**       Creates a linear function with a point at op1 and a point at op2
	 * @param op1 The ordered pair of a point for this function to include
	 * @param op2 The ordered pair of a point for this function to include
	 */
	public Linear(OrderedPair op1, OrderedPair op2) {
		this.a = ((double)op2.getY()-(double)op1.getY())/((double)op2.getX()-(double)op1.getX());
		this.b = (double)op1.getY()-(a*(double)op1.getX());
		double[] coef = new double[2];
		coef[0] = this.a;
		coef[1] = this.b;
		this.coef = coef;
		
	}
	
	/**
	 * @param  in The input value
	 * @return The output value of this function
	 */
	public double fun(double in) {
		return (a*in)+b;
	}
	
	/**
	 * @return The slope of this linear function
	 */
	public double slope() {
		return a;
	}
	
	/**
	 * @return The ordered pair of the x-intercept
	 */
	public OrderedPair xIntercept(){
		return new OrderedPair(-b/a, 0);
	}
	
	/**
	 * @return The root
	 */
	public double root(){
		return (-b/a);
	}
	
	/**
	 * @return The inverse linear function
	 * @throws Exception If the inverse is a constant function (which should never happen)
	 */
	public Linear inverse() throws Exception {
		return new Linear(1/a, -b/a);
	}
	
	/**
	 * @return The derivative of this linear function
	 */
	public Constant derivate() {
		return new Constant(a);
	}
	
	/**
	 * @return The integral of this function with initial value 0
	 * @throws Exception if a quadratic function does not exist (which shouldn't happen)
	 */
	public Quadratic integrate() throws Exception {
		return new Quadratic(a, b, 0);
	}
	
	/**
	 * @return The integral of this function
	 * @param  xo The initial value
	 * @throws Exception if a quadratic function does not exist (which shouldn't happen)
	 */
	public Quadratic integrate(double xo) throws Exception {
		return new Quadratic(a, b, xo);
	}
	
	private Exception notALinearFunctionException() {
		return new Exception("a = 0 creates a function that is not a linear function");
	}
	
}