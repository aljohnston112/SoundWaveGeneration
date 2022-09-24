package function;

/**
 * @author Alexander Johnston
 * Copyright 2019
 * A class for combining univariate functions
 */
public class UnivariateFunctions extends UnivariateFunction{


	/** The types of elementary operations that this class can perform
	 *  ADD is addition, SUB is subtraction, MUL is multiplication, DIV is division
	 */
	public enum OpType{ 
		ADD, SUB, MUL, DIV
	}

	// The functions to combine
	UnivariateFunction[] uf;

	// Order of operations
	OpType[] ooo;

	public UnivariateFunctions(UnivariateFunction[] uf, OpType[] ot) throws Exception {
		if(ot.length != uf.length-1) {
			throw FunctionOperationException();
		}
		this.uf = new UnivariateFunction[uf.length];
		ooo = new OpType[ot.length];
		for(int i = 0; i < uf.length-1; i++) {
			this.uf[i] = uf[i];
			ooo[i] = ot[i];
		}
		this.uf[uf.length-1] = uf[uf.length-1];
	}

	/**        Takes input in and produces an output of the functions contained in this object
	 *         based on the order of operations of this object
	 * @param  in Input values 
	 * @return The output of the multiple univariate functions
	 */
	public double fun(double in) {
		double o = 0;
		for(int i = 0; i < uf.length; i++) {
			if(i == 0) {
				o = uf[i].fun(in);
			} else {
				switch (ooo[i - 1]) {
					case ADD -> {
						o += uf[i].fun(in);
						break;
					}
					case SUB -> {
						o -= uf[i].fun(in);
						break;
					}
					case MUL -> {
						o *= uf[i].fun(in);
						break;
					}
					case DIV -> {
						o /= uf[i].fun(in);
						break;
					}
				}
			}
		}
		return o;
	}

	private Exception FunctionOperationException() {
		return new Exception("The number of functions does not equal the number of operations plus one");
	}

}