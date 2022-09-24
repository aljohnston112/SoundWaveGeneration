package algorithms;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for performing complex number operations
 */
public class ComplexNumber {

	// The real portion of this complex number
	final private double real;

	// The imaginary portion of this complex number
	final private double imaginary;

	/**
	 * @return The real value of this imaginary number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * @return The imaginary value of this imaginary number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**       Creates a complex number with zero as the value
	 */
	public ComplexNumber() {
		this.real = 0;
		this.imaginary = 0;
	}
	
	/**       Creates a complex number with zero imaginary
	 * @param real The real part of the imaginary number
	 */
	public ComplexNumber(double real) {
		this.real = real;
		this.imaginary = 0;
	}
	
	/**       Creates a complex number
	 * @param real The real part of the imaginary number
	 * @param imaginary The imaginary part of the imaginary number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * @return The magnitude of this complex number
	 */
	public double getMagnitude() {
		return StrictMath.sqrt(StrictMath.pow(real, 2.0) + StrictMath.pow(imaginary, 2.0));
	}

	/**
	 * @return The angle of this complex number in radians
	 */
	public double getAngle() {
		return StrictMath.atan(imaginary/real);
	}
	
	public String toString() {
		return String.format("%f + %fi", getReal(), getImaginary());
	}

	/**
	 * @return The magnitude of this complex number
	 */
	public static double getMagnitude(ComplexNumber c) {
		return StrictMath.sqrt(StrictMath.pow(c.getReal(), 2.0) + StrictMath.pow(c.getImaginary(), 2.0));
	}

	/**
	 * @return The angle of this complex number in radians
	 */
	public static double getAngle(ComplexNumber c) {
		return StrictMath.atan(c.getImaginary()/c.getReal());
	}

	/**        Checks if two complex numbers are equal
	 * @param  c1 A complex number
	 * @param  c2 A complex number
	 * @return True if the complex numbers are equal, else false
	 */
	public static boolean isEqual(ComplexNumber c1, ComplexNumber c2) {
		return ((c1.real == c2.real)&&(c1.imaginary == c2.imaginary));
	}

	/**        Returns the conjugate of a complex number
	 * @param  c The complex number to get the conjugate of
	 * @return The conjugate of c
	 */
	public static ComplexNumber getConjugate(ComplexNumber c) {
		return new ComplexNumber(c.real, -c.imaginary);
	}

	/**        Adds complex numbers
	 * @param  c1 A complex number
	 * @param  c2 A complex number
	 * @return The addition of c1 to c2 (c1+c2)
	 */
	public static ComplexNumber add(ComplexNumber c1, ComplexNumber c2) {
		return new ComplexNumber((c1.real+c2.real), (c1.imaginary+c2.imaginary));
	}

	/**        Adds a complex number to a real number
	 * @param  c The complex number
	 * @param  r The real number
	 * @return The addition of c and r (c+r)
	 */
	public static ComplexNumber add(ComplexNumber c, double r) {
		return new ComplexNumber((c.real+r), (c.imaginary));
	}

	/**        Subtracts a complex number from another complex number
	 * @param  c1 The complex number to be subtracted from
	 * @param  c2 The complex number to be subtracted
	 * @return The subtraction of c2 from c1 (c1-c2)
	 */
	public static ComplexNumber subtract(ComplexNumber c1, ComplexNumber c2) {
		return new ComplexNumber((c1.real-c2.real), (c1.imaginary-c2.imaginary));
	}

	/**        Subtracts a real number from a complex number
	 * @param  c The complex number to be subtracted from
	 * @param  r The real number to be subtracted
	 * @return The subtraction of r from c (c-r)
	 */
	public static ComplexNumber subtract(ComplexNumber c, double r) {
		return new ComplexNumber((c.real-r), (c.imaginary));
	}

	/**        Subtracts a complex number from a real number
	 * @param  r The real to be subtracted from
	 * @param  c The complex number number to be subtracted
	 * @return The subtraction of c from r (r-c)
	 */
	public static ComplexNumber subtract(double r, ComplexNumber c) {
		return new ComplexNumber((r - c.real), (-c.imaginary));
	}

	/**        Multiplies complex numbers
	 * @param  c1 A complex number
	 * @param  c2 A complex number
	 * @return The multiplication of c1 and c2 (c1*c2)
	 */
	public static ComplexNumber multiply(ComplexNumber c1, ComplexNumber c2) {
		return new ComplexNumber(((c1.real*c2.real)-(c1.imaginary*c2.imaginary)), ((c1.real*c2.imaginary)+(c1.imaginary*c2.real)));
	}

	/**        Multiplies a complex number by a real number
	 * @param  c The complex number
	 * @param  r The real number
	 * @return The multiplication of c and r (c*r)
	 */
	public static ComplexNumber multiply(ComplexNumber c, double r) {
		return new ComplexNumber(((c.real*r)), ((c.imaginary*r)));
	}

	/**        Takes the reciprocal of a complex number
	 * @param  c The complex number to take the reciprocal of
	 * @return The reciprocal of c
	 */
	public static ComplexNumber recipricate(ComplexNumber c) {
		return new ComplexNumber(((c.real)/((c.real*c.real)+(c.imaginary*c.imaginary))), -((c.imaginary)/((c.real*c.real)+(c.imaginary*c.imaginary))));
	}

	/**        Divides complex numbers
	 * @param  c1 A complex number
	 * @param  c2 A complex number
	 * @return The division of c1 by c2 (c1/c2)
	 */
	public static ComplexNumber divide(ComplexNumber c1, ComplexNumber c2) {
		return multiply(recipricate(c2) , c1);
	}

	/**        Divides a real number from a complex number
	 * @param  c The complex number
	 * @param  r The real number
	 * @return The division of c by r (c/r)
	 */
	public static ComplexNumber divide(ComplexNumber c, double r) {
		return multiply(c, (1/r));
	}

	/**        Divides a complex number from a real number
	 * @param  r The real number
	 * @param  c The complex number
	 * @return The division of r by c (r/c)
	 */
	public static ComplexNumber divide(double r, ComplexNumber c) {
		return multiply(recipricate(c), r);
	}

	/**        Gets euler's number to an imaginary exponent without addition
	 * @param  x The power of the imaginary exponent
	 * @return euler's number to the ix (e^(ix))
	 */
	public static ComplexNumber eToTheiX(double x) {
		return new ComplexNumber(Math.cos(x), Math.sin(x));
	}

	/**        Calculates an imaginary number to an integer real power
	 * @param  c The complex number
	 * @param  n The exponent
	 * @return c to the n (c^n)
	 */
	public static ComplexNumber power(ComplexNumber c, int n) {
		ComplexNumber z = c;
		if(n == 0) {
			return new ComplexNumber(1);
		}
		for(int i = 0; i < n-1; i++) {
			z = ComplexNumber.multiply(c, z);
		}
		return z;
	}

	/**        Calculates the square root of a negative number
	 * @param  n The negative number
	 * @return The square root of the negative number or null if the number is equal to or above 0
	 */
	public static ComplexNumber squareRoot(double n) {
		if(n < 0) {
			return new ComplexNumber(0, StrictMath.sqrt(-n));
		} else {
			return null;
		}
	}

}