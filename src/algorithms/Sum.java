package algorithms;

import java.util.ArrayList;

public class Sum {

	/**        Multiplies every number by the other in groups of the group and adds them together
	 *         Useful for solving polynomials
	 * @param  m The group of numbers to sum the multiplication of
	 * @param  group The number of numbers in m to multiply together in every term of the sum
	 * @return The sum of the multiplication of groups of m 
	 * @throws Exception If the group number is less than 1
	 */
	public static double mul(double[] m, int group) throws Exception{
		if(group < 0 || group > m.length) {
			throw wrongGroupNumberException();
		}
		if(group == 0) {
			return 1;
		}
		if(group == 1) {
			return sum(m);
		}
		double temp = 1;
		ArrayList<Double> mul = new ArrayList<Double>();
		for(int i = 0; i < m.length; i++) {
			for(int k = 0; (k) < m.length-i-1; k++) {
				for(int l = 0; l < (m.length-group+1-k-i); l++) {
					for(int j = 1; j < group; j++) {
						if(j == 1) {
							temp*=(m[i]*m[j+k+i]);
						} else {
							temp*=m[j+k+i+l];
						}
					}

					mul.add(temp);
					temp = 1;
				}
			}
		}
		return sum(mul);
	}

	/**
	 * @return an exception when the group number is less than 0 or greater than the group size
	 */
	private static Exception wrongGroupNumberException() {
		return new Exception("Need a group number greater than -1 and less than the group size");
	}

	/**        Sums up an array of doubles 
	 * @param  d An array of doubles
	 * @return The sum of the doubles
	 */
	public static double sum(double[] d) {
		double sum = 0;
		for(int i = 0; i < d.length; i++) {
			sum += d[i];
		}
		return sum;
	}

	/**
	 * @param  d The array list of doubles
	 * @return The sum of the doubles
	 */
	public static double sum(ArrayList<Double> d) {
		double sum = 0;
		for(int i = 0; i < d.size(); i++) {
			sum += d.get(i);
		}
		return sum;
	}

}