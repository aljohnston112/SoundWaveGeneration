package polynomials;

import algorithms.ComplexNumber;
import logic.OrderedPair;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for quadratic functions
 */
public class Quadratic extends Polynomial {

	// ax^2+bx+c
	double a;
	double b;
	double c;
	
	/**        Creates a polynomial with the form ax^2+bx+c
	 * @param  a ax^2
	 * @param  b bx
	 * @param  c c
	 * @throws Exception If a is 0 and therefore this function would not be a quadratic, an exception is thrown
	 */
	public Quadratic(double a, double b, double c) throws Exception {
		if(a == 0) {
			throw notAQuadraticException();
		}
		this.a = a;
		this.b = b;
		this.c = c;
		double[] coef = new double[3];
		coef[0] = this.a;
		coef[1] = this.b;
		coef[2] = this.c;
		this.coef = coef;
	}
	
	/**        Creates a polynomial with roots r1 and r2
	 * @param  r The roots of the polynomial
	 * @param  a The ax^2 part of ax^2+bx+c
	 * @throws Exception If r doesn't contain two roots and therefore this function would not be a quadratic, an exception is thrown
	 */
	public Quadratic(double[] r, double a) throws Exception {
		if(r.length != 2) {
			throw wrongNumberOfRootsException();
		}
		double[] p1 = new double[2];
		double[] p2 = new double[2];
		p1[0] = 1.0;
		p1[1] = -r[0];
		p2[0] = 1.0;
		p2[1] = -r[1];
		double[] p3 = Polynomial.pe(p1, p2);
		a = a*p3[0];
		b = a*p3[1];
		c = a*p3[2];
		double[] coef = new double[3];
		coef[0] = this.a;
		coef[1] = this.b;
		coef[2] = this.c;
		this.coef = coef;
	}

	/**       Creates a quadratic that has a vertex specified by the ordered pair
	 * @param op The ordered pair that specifies the vertex of the quadratic
	 * @param  a The ax^2 part of ax^2+bx+c
	 */
	public Quadratic(OrderedPair op, double a) {
		double[] p1 = new double[2];
		p1[0] = 1.0;
		p1[1] = -(double)op.getX();
		double[] p3 = Polynomial.pe(p1, p1);
		this.a = a*p3[0];
		this.b = a*p3[1];
		this.c = a*p3[2]+(double)op.getY();
		double[] coef = new double[3];
		coef[0] = this.a;
		coef[1] = this.b;
		coef[2] = this.c;
		this.coef = coef;
	}
	
	/**        Takes input in and produces an output based on this quadratic
	 * @param  in Input value 
	 * @return The output of this quadratic
	 */
	public double fun(double in){
		return ((a*Math.pow(in, 2.0))+(b*in)+c);
	}
	
	/**        Gets the roots of this quadratic
	 * @return The roots of this quadratic
	 */
	public ComplexNumber[] getRoots() {
		double d = StrictMath.pow(b, 2.0)-(4.0*a*c);
		ComplexNumber[] r = new ComplexNumber[2];
		if(d == 0) {
			r[0] = new ComplexNumber(-b/(2.0*a), 0);
			r[1] = new ComplexNumber(-b/(2.0*a), 0);
		} else {
			if(d > 0) {
				r[0] = new ComplexNumber((-b+StrictMath.sqrt(d))/(2.0*a), 0);
				r[1] = new ComplexNumber((-b-StrictMath.sqrt(d))/(2.0*a), 0);
			} else {
				r[0] = ComplexNumber.divide(
						ComplexNumber.add(
								ComplexNumber.squareRoot(d), -b),(2.0*a));
				r[1] = ComplexNumber.divide(
						ComplexNumber.subtract(-b,
								ComplexNumber.squareRoot(d)),(2.0*a));
			}
		}
		return r;
	}
	
	/**
	 * @return The line of symmetry as an x value representing a horizontal line
	 */
	public double sym() {
		return -b/(2.0*a);
	}
	
	/**
	 * @return The vertex of this polynomial
	 */
	public OrderedPair vertex() {
		return new OrderedPair((-b/(2.0*a)),(c-(Math.pow(b, 2.0)/(4.0*a))));
	}
	
	/**
	 * @return The derivative of this quadratic
	 * @throws Exception If the derivative is a constant (which should never happen)
	 */
	public Linear derivate() throws Exception {
		return new Linear(2.0*a, b);
	}
	
	/**
	 * @return The integral of this function with initial value 0
	 * @throws Exception if a cubic function does not exist (which shouldn't happen)
	 */
	public Cubic integrate() throws Exception {
		return new Cubic(a/3.0, b/2.0, c, 0);
	}
	
	/**
	 * @return The integral of this function
	 * @param  op An ordered pair of the integral
	 * @throws Exception if a cubic function does not exist (which shouldn't happen)
	 */
	public Cubic integrate(OrderedPair op) throws Exception {
		double x = (double)op.getX();
		double y = (double)op.getY();
		double xo = y-(a*Math.pow(x, 3.0)/3.0+b*Math.pow(x, 2.0)/2.0+ c*x);
		return new Cubic(a/3.0, b/2.0, c, xo);
	}
	
	private Exception notAQuadraticException() {
		return new Exception("a = 0 creates a function that is not a polynomial");
	}
	
}