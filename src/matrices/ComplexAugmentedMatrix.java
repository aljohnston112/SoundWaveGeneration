package matrices;

import algorithms.ComplexNumber;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making complex augmented matrices
 */
public class ComplexAugmentedMatrix extends ComplexMatrix{

	int col2;

	/**       Creates an empty ComplexAugmentedMatrix
	 * @param rows as the number of rows
	 * @param cols as the number of columns
	 * @param col2 as the number of columns in the augmented part of the matrix
	 */
	public ComplexAugmentedMatrix(int rows, int cols, int col2) {
		super(rows, cols);
		this.col2 = col2;
	}
	
	/**       Creates a deep copy of a ComplexAugmentedMatrix
	 * @param cam The ComplexAugmentedMatrix to create a deep copy of
	 */
	public ComplexAugmentedMatrix(ComplexAugmentedMatrix cam) {
		this.rows = cam.rows;
		this.cols = cam.cols;
		this.col2 = cam.col2;
		ComplexNumber[][] cn = new ComplexNumber[rows][cols];
		for(int j = 0; j < cols; j++) {
			for(int i = 0; i < rows; i++) {
					cn[i][j] = cam.get(i,j);
				}
			}
		this.m = cn;
	}

	/**       Creates a ComplexAugmentedMatrix from two 2-d arrays
	 * @param cn1 A complex number array representing the left hand side of the equation
	 * @param cn2 A complex number array representing the right hand side of the equation
	 */
	public ComplexAugmentedMatrix(ComplexNumber[][] cn1, ComplexNumber[][] cn2) {
		ComplexNumber[][] cn = null;
		if(cn1.length == cn2.length) {
			this.rows = cn1.length;
			this.cols = cn1[0].length+cn2[0].length;
			this.col2 = cn1[0].length+1;
			cn = new ComplexNumber[rows][cols];
			for(int j = 0; j < cols; j++) {
				for(int i = 0; i < rows; i++) {
					if(j >= cn1[0].length) {
						cn[i][j] = cn2[i][j-cn1[0].length];
					} else {
						cn[i][j] = cn1[i][j];
					}
				}
			}
		}
		m = cn;
	}
	
	/**       Creates a ComplexAugmentedMatrix from two complex matrices
	 * @param cm1 A complex matrix representing the left hand side of the equation
	 * @param cm2 A complex matrix representing the right hand side of the equation
	 */
	public ComplexAugmentedMatrix(ComplexMatrix cm1, ComplexMatrix cm2) {
		ComplexNumber[][] cn = null;
		if(cm1.getRows() == cm2.getRows()) {
			this.rows = cm1.getRows();
			this.cols = cm1.getCols()+cm2.getCols();
			this.col2 = cm1.getCols()+1;
			cn = new ComplexNumber[rows][cols];
			for(int j = 0; j < cols; j++) {
				for(int i = 0; i < rows; i++) {
					if(j >= cm1.getCols()) {
						cn[i][j] = cm2.get(i,j-cm1.getCols());
					} else {
						cn[i][j] = cm1.get(i,j);
					}
				}
			}
		}
		m = cn;
	}

}