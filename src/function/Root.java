package function;

import function.UnivariateFunctions.OpType;
import logic.OrderedPair;
import polynomials.Polynomial;

/**
 * @author Alexander Johnston
 * Copyright 2019
 * A class for root functions
 */
public class Root extends UnivariateFunction {

	// a*pow(x, 1.0/root)+b
	final private double root;
	final private double a;
	final private double b;
	
	/**       Creates a root function with the form a*pow(x, 1.0/root)+b
	 * @param root The root to take
	 * @param a The multiplicand
	 * @param b The addend
	 */
	public Root(double root, double a, double b) {
		this.root = root;
		this.a = a;
		this.b = b;
	}
	
	/**       Creates a root function that has points specified by the ordered pairs
	 * @param root The root to take
	 * @param op0 An ordered pair that specifies a point of the root function
	 * @param op1 An ordered pair that specifies a point of the root function
	 */
	public Root(double root, OrderedPair op0, OrderedPair op1) {
		this.root = root;
		double t0 = (double)op0.getX();
		double t1 = (double)op1.getX();
		double f0 = (double)op0.getY();
		double f1 = (double)op1.getY();
		this.a = (f1-f0)/(Math.pow(t1, 1.0/root)-Math.pow(t0, 1.0/root));
		this.b = f0-this.a*Math.pow(t0, 1.0/root);
	}
	
	/**        Gets the root of a number
	 * @param  in The number to get the root from
	 * @return The root of in
	 */
	public double fun(double in) {
		return a*Math.pow(in, 1.0/root)+b;
	}
	
	/**        Gets the inverse root of a number
	 * @param  in The number to get the inverse root from
	 * @return The inverse root of in
	 */
	public double iFun(double in) {
		return Math.pow((in-b)/a, root);
	}
	
	/**
	 * @return The ordered pair of the x-intercept
	 */
	public OrderedPair xIntercept(){
		return new OrderedPair(iFun(0), 0);
	}

	/**
	 * @return The derivative of this root function
	 */
	public Root derivate() {
		return new Root((1.0-root)/root, a/root, 0.0);
	}
	
	/**
	 * @return The integral of this function with initial value 0
	 */
	public UnivariateFunctions integrate() {
		double[] coef = new double[2];
		coef[0] = b;
		coef[1] = 0;
		UnivariateFunction[] uf = {new Root((1.0+root)/root, a*root/(1.0+root), 0), new Polynomial(coef)};
		OpType[] ot = {OpType.ADD};
		UnivariateFunctions ufs = null;
		try {
			ufs = new UnivariateFunctions(uf, ot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ufs;
	}
	
	/**
	 * @return The integral of this function
	 * @param  op An ordered pair of the integral
	 */
	public UnivariateFunctions RootPolynomial(OrderedPair op) {
		double x = (double)op.getX();
		double y = (double)op.getY();
		UnivariateFunctions rp = integrate();
		double xo = y-rp.fun(x);
		double[] coef = new double[2];
		coef[0] = b;
		coef[1] = xo;
		UnivariateFunction[] uf = {new Root((1.0+root)/root, a*root/(1.0+root), 0), new Polynomial(coef)};
		OpType[] ot = {OpType.ADD};
		UnivariateFunctions ufs = null;
		try {
			ufs = new UnivariateFunctions(uf, ot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ufs;
	}
	
}