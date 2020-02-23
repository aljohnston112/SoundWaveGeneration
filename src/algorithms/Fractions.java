package algorithms;

import polynomials.Polynomial;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for fractions
 */
public class Fractions {

	/**        Performs a partial fraction decomposition 
	 *         Gets the numerator above each root
	 * @param  num as the numerator above the all the roots
	 * @param  roots as the roots in the denominator
	 * @return An array with the numerator above each root 
	 */
	public static ComplexNumber[] partialFracionDecomposition(ComplexNumber[] num, ComplexNumber[] roots){
		ComplexNumber[] nums = new ComplexNumber[roots.length];
		if(roots.length == 1) {
			return num;
		} else if(roots.length == 2) {
			ComplexNumber[] f1 = {new ComplexNumber(1), ComplexNumber.multiply(roots[1], -1)}; 
			nums[0] = ComplexNumber.divide(Polynomial.substitute(roots[0], num), Polynomial.substitute(roots[0], f1));
			ComplexNumber[] f2 = {new ComplexNumber(1), ComplexNumber.multiply(roots[0], -1)};
			nums[1] = ComplexNumber.divide(Polynomial.substitute(roots[1], num), Polynomial.substitute(roots[1], f2));
		} else {
			for(int i = 0; i < roots.length; i++){		
				ComplexNumber[] poly = {new ComplexNumber(1)};
				for(int j = 1; j < roots.length; j++){
					ComplexNumber[] f1 = {new ComplexNumber(1), ComplexNumber.multiply(roots[(j+i)%roots.length], -1)}; 
					poly = Polynomial.pe(poly, f1);
				}
				nums[i] = ComplexNumber.divide(Polynomial.substitute(roots[i], num), Polynomial.substitute(roots[i], poly));
			}
		}
		return nums;
	}

}