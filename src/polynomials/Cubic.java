package polynomials;

import logic.OrderedPair;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for cubic functions
 */
public class Cubic extends Polynomial {

	// ax^3+bx^2+cx+d
	public double a;
	public double b;
	public double c;
	public double d;

	/**        Creates a cubic function with the form ax^3+bx^2+cx+d
	 * @param  a ax^3
	 * @param  b bx^2
	 * @param  c cx
	 * @param  d d
	 * @throws Exception If a is 0 and therefore this function would not be a cubic, an exception is thrown
	 */
	public Cubic(double a, double b, double c, double d) throws Exception {
		if(a == 0) {
			throw notACubicException();
		}
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		double[] coef = new double[4];
		coef[0] = this.a;
		coef[1] = this.b;
		coef[2] = this.c;
		coef[3] = this.d;
		this.coef = coef;
	}

	/**        Creates a cubic function with roots r1 and r2
	 * @param  r The roots of the cubic function
	 * @param  a The ax^3 part of ax^3+bx^2+cx+d
	 * @throws Exception If r doesn't contain three roots and 
	 *         therefore this function would not be a cubic function, an exception is thrown
	 */
	public Cubic(double[] r, double a) throws Exception {
		if(r.length != 3) {
			throw wrongNumberOfRootsException();
		}
		double[] p1 = new double[2];
		double[] p2 = new double[2];
		double[] p3 = new double[2];
		p1[0] = 1.0;
		p1[1] = -r[0];
		p2[0] = 1.0;
		p2[1] = -r[1];
		p3[0] = 1.0;
		p3[1] = -r[2];
		double[] p4 = Polynomial.pe(p1, p2);
		p4 = Polynomial.pe(p4, p3);
		a = a*p4[0];
		b = a*p4[1];
		c = a*p4[2];
		d = a*p4[3];
		double[] coef = new double[4];
		coef[0] = this.a;
		coef[1] = this.b;
		coef[2] = this.c;
		coef[3] = this.d;
		this.coef = coef;
	}

	/**       Creates a cubic function that has an inflection point specified by the ordered pair
	 * @param op The ordered pair that specifies the inflection point of the cubic function
	 * @param  a The ax^3 part of ax^3+bx^2+cx+d
	 */
	public Cubic(OrderedPair op, double a) {
		double[] p1 = new double[2];
		p1[0] = 1.0;
		p1[1] = -(double)op.getX();
		double[] p3 = Polynomial.pe(p1, p1);
		p3 = Polynomial.pe(p1, p3);
		this.a = a*p3[0];
		this.b = a*p3[1];
		this.c = a*p3[2]+(double)op.getY();
	}

	/**       Creates a cubic function that has vertices specified by the ordered pairs
	 * @param op0 An ordered pair that specifies a vertex of the cubic function
	 * @param op1 An ordered pair that specifies a vertex of the cubic function
	 */
	public Cubic(OrderedPair op0, OrderedPair op1) {
		double t0 = (double)op0.getX();
		double t1 = (double)op1.getX();
		double f0 = (double)op0.getY();
		double f1 = (double)op1.getY();
		double g = 0;
		g = (f1-f0)/(((Math.pow(t1, 3.0))/3.0)-(((t0+t1)*Math.pow(t1, 2.0))/2.0)+(t0*Math.pow(t1, 2.0))-
				(Math.pow(t0, 3.0))/3.0)+(((t0+t1)*Math.pow(t0, 2.0))/2.0)-(t1*Math.pow(t0, 2.0));
		this.a = g/3.0;
		this.b = -(g*(t0+t1))/2.0;
		this.c = t0*t1*g;
		this.d = ((-a*Math.pow(t0, 3.0))-(b*Math.pow(t0, 2.0))-(c*t0)+f0);
		double[] coef = new double[4];
		coef[0] = this.a;
		coef[1] = this.b;
		coef[2] = this.c;
		coef[3] = this.d;
		this.coef = coef;
	}

	/**        Takes input in and produces an output based on this cubic function
	 * @param  in Input value 
	 * @return The output of this cubic function
	 */
	public double fun(double in){
		return ((a*Math.pow(in, 3.0))+(b*Math.pow(in, 2.0))+(c*in)+d);
	}
	
	private Exception notACubicException() {
		return new Exception("a = 0 creates a function that is not a cubic");
	}

	// TODO root finder, midpoint and inflection points
	
	/**
	 * @return The derivative of this cubic
	 * @throws Exception If the derivative is not a quadratic (which should never happen)
	 */
	public Quadratic derivate() throws Exception {
		return new Quadratic(3.0*a, 2.0*b, c);
	}
	
	/**
	 * @return The integral of this function with initial value 0
	 * @throws Exception if a quartic function does not exist (which shouldn't happen)
	 */
	public Quartic integrate() throws Exception {
		return new Quartic(a/4.0, b/3.0, c/2.0, d, 0.0);
	}
	
	/**
	 * @return The integral of this function
	 * @param  op An ordered pair of the integral
	 * @throws Exception if a quartic function does not exist (which shouldn't happen)
	 */
	public Quartic integrate(OrderedPair op) throws Exception {
		double x = (double)op.getX();
		double y = (double)op.getY();
		double xo = y-((a*Math.pow(x, 4.0))+(b*Math.pow(x, 3.0))+(c*Math.pow(x, 2.0))+(d*x));
		return new Quartic(a/4.0, b/3.0, c/2.0, d, xo);
	}
	
}