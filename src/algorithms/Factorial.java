package algorithms;

import java.math.BigDecimal;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for performing factorial operations
 */
public class Factorial {

	/**        Finds the factorial of a number
	 *         Works up to 5! using byteValue()
	 *         Works up to 12! using intValue()
	 *         Works up to 20! using intValue()
	 *         Works up to 34! using floatValue(), after which it becomes infinity
	 *         Works up to 170! using doubleValue(), after which it becomes infinity
	 * @param  n The number to perform the factorial operation on 
	 * @return The factorial of n (n!)
	 */
	public static BigDecimal factorial(int n) {
		BigDecimal a = BigDecimal.ONE;
		BigDecimal m = new BigDecimal(String.valueOf(n));
		while(m.doubleValue() > 1) {
			a = a.multiply(m);
			m = m.subtract(BigDecimal.ONE);
		}
		return a;
	}
}
