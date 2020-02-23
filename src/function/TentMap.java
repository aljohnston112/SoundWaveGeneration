package function;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for creating a tent maps
 */
public class TentMap {

	// Parameter value. Works best between 0 and 2
	double u;
	
	/**         Creates a tent map with paramter value r
	 * @param r Parameter value. Works best between 0 and 4
	 */
	public TentMap(double u){
		this.u = u;
	}
	
	/**        Takes in a value and calculates the next iteration of this tent map
	 * @param  in The input value. Works best between 0 and 1
	 * @return The current output and the next input of this tent map
	 */
	public double fun(double in) {
		return u*Math.min(in, 1-in);
	}
	
	/**        Takes in a value and calculates the next iteration of this tent map
	 * @param  in The input values. Works best between 0 and 1
	 * @return The output of this tent map
	 */
	public double[] fun(double[] in) {
		double[] out = new double[in.length];
		for(int i = 0; i < in.length; i++) {
			out[i] = u*Math.min(in[i], 1-in[i]);
		}
		return out;
	}
	
	/**        Creates an array representing the graph of this logistic function
	 * @param  x0 The initial input. Works best between 0 and 1
	 * @param  iterations The number of iterations to perform 
	 * @return An array representing the graph of this logistic function
	 */
	public double[] iterate(double x0, int iterations) {
		double[] x = new double[iterations];
		double xn = x0;
		x[0] = xn;
		for(int i = 1; i < x.length; i++) {
			x[i] = fun(xn);
			xn = x[i];
		}
		return x;
	}
	
}