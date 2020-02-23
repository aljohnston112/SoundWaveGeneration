package algorithms;

import function.UnivariateFunction;

public class Intergral {

	/**        Integrates a function
	 * @param  f as the functions output array
	 * @param  dt as the time between data points in the output
	 * @return the integral of the function 
	 */
	public static double[] RiemannIntergrate(double[] f, double dt) {
		double[] i = new double[f.length-1];
		for(int j = 1; j < f.length; j++) {
			i[j-1] = (dt*(f[j-1]+f[j])/2.0);
		}
		return i;
	}
	
	/**        Gets the integral sum of a function
	 * @param  f as the function output
	 * @param  dt as the time between data points in the output
	 * @return the integral sum of the output
	 */
	public static double RiemannIntergrateSum(double[] f, double dt) {
		double i = 0;
		for(int j = 1; j < f.length; j++) {
			i += (dt*(f[j-1]+f[j])/2.0);
		}
		return i;
	}
	
	/**        Integrates a function
	 * @param  uf as the function 
	 * @param  start as the start x value
	 * @param  end as the end x value
	 * @param  dt as the time between x values
	 * @return the integral of a function
	 */
	public static double[] RiemannIntergrate(UnivariateFunction uf, double start, double end, double dt) {
		double[] i = new double[(int) (Math.floor((end-start)*dt)-1)];
		for(int j = 0; j < i.length; j++) {
			i[j] = (dt*(uf.fun(start+(dt*j))+(uf.fun(start+(dt*j))/2.0)));
		}
		return i;
	}
	
	/**        Gets the integral sum of a function
	 * @param  uf as the function
	 * @param  start as the start x value
	 * @param  end as the end x value
	 * @param  dt as the time between x values
	 * @return the integral sum of the function
	 */
	public static double RiemannIntergrateSum(UnivariateFunction uf, double start, double end, double dt) {
		double i = 0;
		for(int j = 0; j < (int) (Math.floor((end-start)*dt)-1); j++) {
			i += (dt*(uf.fun(start+(dt*j))+(uf.fun(start+(dt*j))/2.0)));
		}
		return i;
	}
	
}