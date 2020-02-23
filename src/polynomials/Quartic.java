package polynomials;

import algorithms.ComplexNumber;
import algorithms.GaussianElimination;
import algorithms.Sum;
import logic.OrderedPair;
import matrices.ComplexAugmentedMatrix;
import matrices.ComplexMatrix;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for quartic functions
 */
public class Quartic extends Polynomial {
	
	// ax^4+bx^3+cx^2+dx+e
	double a;
	double b;
	double c;
	double d;
	double e;
	
	/**       Creates a quartic with the equation ax^4+bx^3+cx^2+dx+e      
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @throws Exception If a=0 and the resulting function would not be a quartic
	 */
	public Quartic(double a, double b, double c, double d, double e) throws Exception {
			if(a == 0) {
				throw notAQuarticException();
			}
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	}	
	
	private Exception notAQuarticException() {
		return new Exception("a = 0 creates a function that is not a quartic");
	}
	
	/**       Creates a quartic function that has vertices specified by the ordered pairs
	 * @param op0 An ordered pair that specifies a vertex of the quartic function
	 * @param op1 An ordered pair that specifies a vertex of the quartic function
	 * @param t The time of the third vertex that has an unknown y value. To match y-value of op0, 
	 *        (x-value of op1 - x-value) of op0 should equal (t - x-value of op1)
	 */
	public Quartic(OrderedPair op0, OrderedPair op1, OrderedPair op2) {
		double t0 = (double)op0.getX();
		double t1 = (double)op1.getX();
		double t2 = (double)op2.getX();
		double f0 = (double)op0.getY();
		double f1 = (double)op1.getY();
		double f2 = (double)op2.getY();

		// Coefficients for Gaussian elimination
		double[] t = {t0,t1,t2};
		double p = 0;
		double q = 0;
		double r = 0;

		try {
			p = Sum.mul(t, 1);
			q = Sum.mul(t, 2);
			r = Sum.mul(t, 3);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		double gc0 = ((Math.pow(t0, 4.0)/4.0)-(p*Math.pow(t0, 3.0)/3.0)+(q*Math.pow(t0, 2.0)/2.0)-(r*t0));
		double hc0 = t0;
		double ec0 = 1.0;
		double cc0 = f0;
		
		double gc1 = ((StrictMath.pow(t1, 4.0)/4.0)-(p*StrictMath.pow(t1, 3.0)/3.0)+(q*StrictMath.pow(t1, 2.0)/2.0)-(r*t1));
		double hc1 = t1;
		double ec1 = 1.0;
		double cc1 = f1;
		
		double gc2 = ((Math.pow(t2, 4.0)/4.0)-(p*Math.pow(t2, 3.0)/3.0)+(q*Math.pow(t2, 2.0)/2.0)-(r*t2));
		double hc2 = t2;
		double ec2 = 1.0;
		double cc2 = f2;
		
		ComplexNumber[][] cm1 = {{new ComplexNumber(gc1), new ComplexNumber(hc1), new ComplexNumber(ec1)},
				{new ComplexNumber(gc2), new ComplexNumber(hc2), new ComplexNumber(ec2)}, 
				{new ComplexNumber(gc0), new ComplexNumber(hc0), new ComplexNumber(ec0)}};
		ComplexNumber[][] cm2 = {{new ComplexNumber(cc1)},{new ComplexNumber(cc2)},{new ComplexNumber(cc0)}};
		ComplexAugmentedMatrix cam = new ComplexAugmentedMatrix(cm1, cm2);
		ComplexMatrix ge = GaussianElimination.eliminate(cam);
		
		double g = ge.get(0, 0).getReal();
		double h = ge.get(1, 0).getReal();
		double e = ge.get(2, 0).getReal();
				
		this.a = g/4.0;
		this.b = -(g*p)/3.0;
		this.c = (q*g)/2.0;
		this.d = h-(g*r);
		this.e = e;
		double[] coef = new double[5];
		coef[0] = this.a;
		coef[1] = this.b;
		coef[2] = this.c;
		coef[3] = this.d;
		coef[4] = this.e;
		this.coef = coef;
	}
	
	/**        Takes input in and produces an output based on this cubic function
	 * @param  in Input value 
	 * @return The output of this cubic function
	 */
	public double fun(double in){
		return ((a*Math.pow(in, 4.0))+(b*Math.pow(in, 3.0))+(c*Math.pow(in, 2.0))+(d*in)+e);
	}
	
	/**
	 * @return The derivative of this quartic
	 * @throws Exception If the derivative is not a cubic (which should never happen)
	 */
	public Cubic derivate() throws Exception {
		return new Cubic(4.0*a, 3.0*b, 2*c, d);
	}
	
	/**
	 * @return The integral of this function with initial value 0
	 */
	public Polynomial integrate() {
		double[] coef = {a/5.0, b/4.0, c/3.0, d/2.0, e, 0.0};
		return new Polynomial(coef);
	}
	
	/**
	 * @return The integral of this function
	 * @param  op An ordered pair of the integral
	 */
	public Polynomial integrate(OrderedPair op) {
		double x = (double)op.getX();
		double y = (double)op.getY();
		double xo = y-((a*Math.pow(x, 5.0))+(b*Math.pow(x, 4.0))+(c*Math.pow(x, 3.0))+(d*Math.pow(x, 2.0))+(e*x));
		double[] coef = {a/5.0, b/4.0, c/3.0, d/2.0, e, xo};
		return new Polynomial(coef);
	}
	
}