package algorithms;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class that provides transfer functions
        for frequency to amplitude
        for equal loudness normalized at 1
        from 10hz to 20khz
 */
public class EqualLoudnessContour {

	/**        A-weighted transfer function
	 * @param  f The frequency whose loudness is to be normalized
	 * @return The normalized amplitude of frequency
	 */
	static public double getGainAWeight(double f) {
		ComplexNumber fn = new ComplexNumber(0, f*2.0*StrictMath.PI);
		// Normalization coefficient
		double k= 7397050000.0;
		double num = ComplexNumber.power(fn, 4).getMagnitude();
		double den = ComplexNumber.multiply(
				ComplexNumber.multiply(
						ComplexNumber.multiply(
								ComplexNumber.power(
										ComplexNumber.add(fn, 129.4), 2), 
								ComplexNumber.add(fn, 676.7)), 
						ComplexNumber.add(fn, 4636.0)), 
				ComplexNumber.power(
						ComplexNumber.add(fn, 76655.0), 2)).getMagnitude();
		return  1.0- k*num/den;
	}

	/**        B-weighted transfer function
	 * @param  f The frequency whose loudness is to be normalized
	 * @return The normalized amplitude of frequency
	 */
	static public double getGainBWeight(double f) {
		ComplexNumber fn = new ComplexNumber(0, f*2.0*StrictMath.PI);
		// Normalization coefficient
		double k= 5991850000.0;
		double num = ComplexNumber.power(fn, 3).getMagnitude();
		double den = ComplexNumber.multiply(
				ComplexNumber.multiply(
						ComplexNumber.power(
								ComplexNumber.add(fn, 129.4), 2), 
						ComplexNumber.add(fn, 995.9)),
				ComplexNumber.power(
						ComplexNumber.add(fn, 76655.0), 2)).getMagnitude();
		return  1.0- k*num/den;
	}

	/**        C-weighted transfer function
	 * @param  f The frequency whose loudness is to be normalized
	 * @return The normalized amplitude of frequency
	 */
	static public double getGainCWeight(double f) {
		ComplexNumber fn = new ComplexNumber(0, f*2.0*StrictMath.PI);
		// Normalization coefficient
		double k= 5917970000.0;
		double num = ComplexNumber.power(fn, 2).getMagnitude();
		double den = ComplexNumber.multiply(
				ComplexNumber.power(
						ComplexNumber.add(fn, 129.4), 2),
				ComplexNumber.power(
						ComplexNumber.add(fn, 76655.0), 2)).getMagnitude();
		return 1.0 - k*num/den;
	}

	/**        D-weighted transfer function
	 * @param  f The frequency whose loudness is to be normalized
	 * @return The normalized amplitude of frequency
	 */
	static public double getGainDWeight(double f) {
		ComplexNumber fn = new ComplexNumber(0, f*2.0*StrictMath.PI);
		// Normalization coefficient
		double k = 91104.32;
		double num = ComplexNumber.add(
				ComplexNumber.add(
						ComplexNumber.multiply(fn,(
								ComplexNumber.power(fn, 2))), 
						ComplexNumber.multiply(fn, 6532)), 40975000).getMagnitude();
		double den = ComplexNumber.multiply(
				ComplexNumber.multiply(
						ComplexNumber.add(fn, 1776.3), 
						ComplexNumber.add(fn,7288.5)), (
								ComplexNumber.add(
										ComplexNumber.power(fn, 2),
										ComplexNumber.add(
												ComplexNumber.multiply(fn, 21514), 388360000)))).getMagnitude();
		return  1.0 - k*num/den;
	}

}
