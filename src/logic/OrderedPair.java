package logic;

/**
 * @author    Alexander Johnston
 *            Copyright 2019
 *            A class for ordered pairs
 */
public class OrderedPair {

	// x
	Object x;
	
	/**
	 * @return The x value of this ordered pair
	 */
	public Object getX() {
		return x;
	}

	/**
	 * @return The y value of this ordered pair
	 */
	public Object getY() {
		return y;
	}

	// y
	Object y;
	
	/**       Creates an ordered pair
	 * @param x First of the pair
	 * @param y Second of the pair
	 */
	public OrderedPair(Object x, Object y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object op) {
		return this.x.equals(((OrderedPair) op).x) && this.y.equals(((OrderedPair) op).y);
	}

}
