package polynomials;

import algorithms.ComplexNumber;
import algorithms.Sum;
import function.UnivariateFunction;
import logic.OrderedPair;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for polynomials
 */
public class Polynomial extends UnivariateFunction {
	
	// Coefficients of the polynomial
	// Ex: (1, 0, -2, 0) is (x^3 -2x) 
	double[] coef; 

	/**
	 *  Creates a polynomial as the constant function y=1
	 */
	public Polynomial() {
		double[] coef = {1.0};
		this.coef = coef;
	}
	
	/**       Creates a polynomial with coefficients Ex: (1, 0, -2, 0) is (x^3 -2x) 
	 * @param coef The coefficients of the polynomial
	 */
	public Polynomial(double[] coef) {
		this.coef = new double[coef.length];
		for(int i = 0; i < coef.length; i++) {
			this.coef[i] = coef[i];
		}
	}
	
	/**       Creates a polynomial function that has vertices specified by the ordered pairs
	 * @param op An ordered pair array that specifies the vertices of the polynomial function
	 * @throws Exception If group of multiplicands is less than 0 (this should never happen)
	 */
	public Polynomial(OrderedPair[] op) throws Exception {
		double[] t = new double[op.length];
		double[] f = new double[op.length];
		for(int i = 0; i < op.length; i++) {
			t[i] = (double)op[i].getX();
			f[i] = (double)op[i].getY();
		}
		double g = (f[0]-f[1]);
		double gtemp0 = 0;
		double gtemp1 = 0;
		for(int i = 0; i < op.length+1; i++) {
			gtemp0 += (1.0/(op.length-i+1))*Math.pow(t[0], op.length-i+1)
					*Sum.mul(t, i)*Math.pow(-1, i);
			gtemp1 += (1.0/(op.length-i+1))*Math.pow(t[1], op.length-i+1)
					*Sum.mul(t, i)*Math.pow(-1, i);
		}
		g = g/(gtemp0-gtemp1);
		coef = new double[op.length+2];
		for(int i = 0; i < op.length+1; i++) {
			coef[i] = (g/(op.length-i+1))*Sum.mul(t, i)*Math.pow(-1, i);
		}
		coef[coef.length-1] = f[0] - fun(t[0]);
	}
	
	public double fun(double in) {
		double y = 0;
		for(int i = 0; i < coef.length; i++) {
			y += coef[i]*Math.pow(in, coef.length-1-i);
		}
		return y;
	}
	
	/**
	 * @throws Exception if a polynomial does not exist (which shouldn't happen)
	 * @return The derivative of this polynomial
	 */
	public Polynomial derivate() throws Exception {
		double[] coef = new double[this.coef.length-1];
		for(int i = 0; i < coef.length; i++) {
			coef[i] = this.coef[i]*(coef.length-1-i);
		}
		return new Polynomial(coef);
	}
	
	/**
	 * @throws Exception if a polynomial does not exist (which shouldn't happen)
	 * @return The integral of this function with initial value 0
	 * 
	 */
	public Polynomial integrate() throws Exception {
		double[] coef = new double[this.coef.length+1];
		for(int i = 0; i < coef.length-1; i++) {
			coef[i] = this.coef[i]/(coef.length-i);
		}
		coef[coef.length-1] = 0.0;
		return new Polynomial(coef);
	}
	
	/**
	 * @return The integral of this function
	 * @param  op An ordered pair of the integral
	 * @throws Exception if a polynomial does not exist (which shouldn't happen)
	 */
	public Polynomial integrate(OrderedPair op) throws Exception {
		double x = (double)op.getX();
		double y = (double)op.getY();
		double[] coef = new double[this.coef.length+1];
		for(int i = 0; i < coef.length-1; i++) {
			coef[i] = this.coef[i]/(coef.length-i);
		}
		coef[coef.length-1] = 0.0;
		Polynomial p = new Polynomial(coef);
		double xo = y-p.fun(x);
		coef[coef.length-1] = xo;
		return new Polynomial(coef);
	}

	/**        Gets the roots of a second order polynomial
	 * @param  a Coefficient of x^2
	 * @param  b Coefficient of x
	 * @param  c Constant
	 * @return The roots of the polynomial given by the coefficients
	 */
	public static ComplexNumber[] QFC(double a, double b, double c) {
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

	/**        Expands two polynomials by multiplying the coefficients together
	 *         First coefficient is highest order and each subsequent coefficient is one order lower until the last, which is a constant
	 *         Ex: (1, 0, -2, 0) is (x^3 -2x) 
	 * @param  p1 The polynomial to be multiplied by p2
	 * @param  p2 The polynomial to be multiplied by p1
	 * @return The coefficients resulting from the multiplication of p1 and p2
	 */
	public static ComplexNumber[] pe(ComplexNumber[] p1, ComplexNumber[] p2){
		ComplexNumber[] pe;
		pe = new ComplexNumber[p1.length+p2.length-1];
		for(int i = 0; i < pe.length; i++) {
			pe[i] = new ComplexNumber(0, 0);
		}
		for(int i = 0; i < p1.length; i++) {
			for(int j = 0; j < p2.length; j++) {
				pe[i+j] = ComplexNumber.add(pe[i+j],
						ComplexNumber.multiply(p1[i],p2[j])); 
			}
		}
		return pe;
	}

	/**        
	 * @param  cn A complex number to substitute into the polynomial
	 * @param  p An array of numbers representing a polynomial
	 *            Ex: (1, 0, -2, 0) is (x^3 -2x) 
	 * @return The solution to the substitution of cn in polynomial p
	 */
	public static ComplexNumber substitute(ComplexNumber cn, ComplexNumber[] p) {
		ComplexNumber add = new ComplexNumber(0);
		for(int i = 0; i < p.length; i++) {
			add = ComplexNumber.add(add, ComplexNumber.multiply(p[p.length-1-i], ComplexNumber.power(cn, i)));
		}
		return add;
	}

	/**        Expands two polynomials by multiplying the coefficients together
	 *         First coefficient is highest order and each subsequent coefficient is one order lower until the last, which is a constant
	 *         Ex: (1, 0, -2, 0) is (x^3 -2x) 
	 * @param  p1 The polynomial to be multiplied by p2
	 * @param  p2 The polynomial to be multiplied by p1
	 * @return The coefficients resulting from the multiplication of p1 and p2
	 */
	public static double[] pe(double[] p1, double[] p2){
		double[] pe = new double[p1.length*p2.length-1];
		for(int i = 0; i < p1.length*p2.length-1; i++) {
			pe[i] = 0;
		}
		for(int i = 0; i < p1.length; i++) {
			for(int j = 0; j < p2.length; j++) {
				pe[i+j] = (pe[i+j]+(p1[i]*p2[j])); 
			}
		}
		return pe;
	}

	protected Exception wrongNumberOfRootsException() {
		return new Exception("r needs to contain 2 roots to be a quadratic");
	}

}