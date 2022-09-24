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

	private static final double RA = ((Math.pow(12194, 2)*Math.pow(1000, 4))/
			((Math.pow(1000, 2) + Math.pow(20.6, 2))*
					(Math.pow((Math.pow(1000, 2)+Math.pow(107.7, 2))*
							(Math.pow(1000, 2)+Math.pow(737.9, 2))*
							(Math.pow(1000, 2)+Math.pow(12194, 2)), 0.5))));
	
	private static final double SUB_A = ((Math.pow(12194, 2)*Math.pow(20000, 4))/
			((Math.pow(20000, 2) + Math.pow(20.6, 2))*
					(Math.pow((Math.pow(20000, 2)+Math.pow(107.7, 2))*
							(Math.pow(20000, 2)+Math.pow(737.9, 2))*
							(Math.pow(20000, 2)+Math.pow(12194, 2)), 0.5))));
	
	private static final double DIV_A = 5.16;
	
	/**        A-weighted transfer function
	 * @param  f The frequency whose loudness is to be normalized
	 * @return The normalized amplitude of frequency
	 */
	static public double getGainAWeight(double f) {
		// Normalization coefficient
		return (f > 1000) ? (20*Math.log(((Math.pow(12194, 2)*Math.pow(f, 4))/
				((Math.pow(f, 2) + Math.pow(20.6, 2))*
						(Math.pow((Math.pow(f, 2)+Math.pow(107.7, 2))*
								(Math.pow(f, 2)+Math.pow(737.9, 2))*
								(Math.pow(f, 2)+Math.pow(12194, 2)), 0.5)))))-(20*Math.log(RA))-SUB_A)/DIV_A :
									RA;
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
