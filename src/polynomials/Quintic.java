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
        A class for quintic functions
 */
public class Quintic extends Polynomial {

	// ax^5+bx^4+cx^3+dx^2+ex+f
		double a;
		double b;
		double c;
		double d;
		double e;	
		double f;
		
		/**       Creates a quintic with the equation ax^5+bx^4+cx^3+dx^2+ex+f
		 * @param a
		 * @param b
		 * @param c
		 * @param d
		 * @param e
		 * @param f
		 * @throws Exception If a=0 and the resulting function would not be a quintic
		 */
		public Quintic(double a, double b, double c, double d, double e, double f) throws Exception {
				if(a == 0) {
					throw notAQuinticException();
				}
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.e = e;
			this.f = f;
		}	
		
		private Exception notAQuinticException() {
			return new Exception("a = 0 creates a function that is not a quintic");
		}
		
		/**       Creates a quintic function that has vertices specified by the ordered pairs
		 * @param op0 An ordered pair that specifies a vertex of the quintic function
		 * @param op1 An ordered pair that specifies a vertex of the quintic function
		 * @param op2 An ordered pair that specifies a vertex of the quintic function
		 * @param op3 An ordered pair that specifies a vertex of the quintic function		 * 
		 * @param t The time of the third vertex that has an unknown y value. To match y-value of op0, 
		 *        (x-value of op1 - x-value) of op0 should equal (t - x-value of op1)
		 */
		public Quintic(OrderedPair op0, OrderedPair op1, OrderedPair op2, OrderedPair op3) {
			double t0 = (double)op0.getX();
			double t1 = (double)op1.getX();
			double t2 = (double)op2.getX();
			double t3 = (double)op3.getX();
			double f0 = (double)op0.getY();
			double f1 = (double)op1.getY();
			double f2 = (double)op2.getY();
			double f3 = (double)op3.getY();

			// Coefficients for Gaussian elimination
			double[] t = {t0,t1,t2, t3};
			double p = 0;
			double q = 0;
			double r = 0;
			double s = 0;

			try {
				p = Sum.mul(t, 1);
				q = Sum.mul(t, 2);
				r = Sum.mul(t, 3);
				s = Sum.mul(t, 4);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			double gc0 = ((Math.pow(t0, 5.0)/5.0)-(p*Math.pow(t0, 4.0)/4.0));
			double hc0 = ((q*Math.pow(t0, 3.0)/3.0)-(r*Math.pow(t0, 2.0)/2.0));
			double ic0 = t0;
			double fc0 = 1.0;
			double cc0 = f0-(s*t0);

			double gc1 = ((Math.pow(t1, 5.0)/5.0)-(p*Math.pow(t1, 4.0)/4.0));
			double hc1 = ((q*Math.pow(t1, 3.0)/3.0)-(r*Math.pow(t1, 2.0)/2.0));
			double ic1 = t1;
			double fc1 = 1.0;
			double cc1 = f1-(s*t1);
			
			double gc2 = ((Math.pow(t2, 5.0)/5.0)-(p*Math.pow(t2, 4.0)/4.0));
			double hc2 = ((q*Math.pow(t2, 3.0)/3.0)-(r*Math.pow(t2, 2.0)/2.0));
			double ic2 = t2;
			double fc2 = 1.0;
			double cc2 = f2-(s*t2);
			
			double gc3 = ((Math.pow(t3, 5.0)/5.0)-(p*Math.pow(t3, 4.0)/4.0));
			double hc3 = ((q*Math.pow(t3, 3.0)/3.0)-(r*Math.pow(t3, 2.0)/2.0));
			double ic3 = t3;
			double fc3 = 1.0;
			double cc3 = f3-(s*t3);
			
			ComplexNumber[][] cm1 = {{new ComplexNumber(gc1), new ComplexNumber(hc1), new ComplexNumber(ic1), new ComplexNumber(fc1)},
					{new ComplexNumber(gc2), new ComplexNumber(hc2), new ComplexNumber(ic2), new ComplexNumber(fc2)}, 
				{new ComplexNumber(gc3), new ComplexNumber(hc3), new ComplexNumber(ic3), new ComplexNumber(fc3)},
				{new ComplexNumber(gc0), new ComplexNumber(hc0), new ComplexNumber(ic0), new ComplexNumber(fc0)}};
			ComplexNumber[][] cm2 = {{new ComplexNumber(cc1)},{new ComplexNumber(cc2)},{new ComplexNumber(cc3)},{new ComplexNumber(cc0)}};
			ComplexAugmentedMatrix cam = new ComplexAugmentedMatrix(cm1, cm2);
			ComplexMatrix ge = GaussianElimination.eliminate(cam);
			
			double g = ge.get(0, 0).getReal();
			double h = ge.get(1, 0).getReal();
			double i = ge.get(2, 0).getReal();
			double f = ge.get(3, 0).getReal();
					
			this.a = g/5.0;
			this.b = -(g*p)/4.0;
			this.c = (q*h)/3.0;
			this.d = -(h*r)/2.0;
			this.e = i+s;
			this.f = f;
			double[] coef = new double[6];
			coef[0] = this.a;
			coef[1] = this.b;
			coef[2] = this.c;
			coef[3] = this.d;
			coef[4] = this.e;
			coef[5] = this.f;
			this.coef = coef;
		}
		
		/**        Takes input in and produces an output based on this cubic function
		 * @param  in Input value 
		 * @return The output of this cubic function
		 */
		public double fun(double in){
			return ((a*Math.pow(in, 5.0))+(b*Math.pow(in, 4.0))+(c*Math.pow(in, 3.0))+(d*Math.pow(in, 2.0))+(e*in)+f);
		}
		
		/**
		 * @return The derivative of this quartic
		 * @throws Exception If the derivative is not a cubic (which should never happen)
		 */
		public Quartic derivate() throws Exception {
			return new Quartic(5.0*a, 4.0*b, 3.0*c, 2.0*d, e);
		}
		
		/**
		 * @return The integral of this function with initial value 0
		 */
		public Polynomial integrate() {
			double[] coef = {a/6.0, b/5.0, c/4.0, d/3.0, e/2,0, f, 0.0};
			return new Polynomial(coef);
		}
		
		/**
		 * @return The integral of this function
		 * @param  op An ordered pair of the integral
		 */
		public Polynomial integrate(OrderedPair op) {
			double x = (double)op.getX();
			double y = (double)op.getY();
			double xo = y-((a*Math.pow(x, 6.0))+(b*Math.pow(x, 5.0))+(c*Math.pow(x, 4.0))+(d*Math.pow(x, 3.0))+(e*Math.pow(x, 2.0))+(f*x));
			double[] coef = {a/6.0, b/5.0, c/4.0, d/3.0, e/2.0, f, xo};
			return new Polynomial(coef);
		}
}
