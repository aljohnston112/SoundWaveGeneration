package algorithms;

import matrices.ComplexAugmentedMatrix;
import matrices.ComplexMatrix;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for solving linear equations
 */
public class GaussianElimination {

	/**        Solves a system of linear equations represented as a augmented matrix with the right hand side having one column 
	 *         and assuming the left hand side is a square matrix
	 * @param  cam The complex augmented matrix representing the system of linear equations
	 * @return A complex matrix that represents the solution to the system of linear equations
	 */
	public static ComplexMatrix eliminate(ComplexAugmentedMatrix cam) {
		ComplexMatrix cm = new ComplexMatrix(cam.getRows(), 1);
		ComplexAugmentedMatrix cam2 = new ComplexAugmentedMatrix(cam);
		ComplexNumber cn = ComplexNumber.multiply(cam2.get(0, 0), new ComplexNumber(-1));
		ComplexNumber scale;
		// Set all rows to 0 under the first row in column 0
		for(int i = 1; i < cam2.getRows(); i++) {
			scale = ComplexNumber.divide(cam2.get(i, 0), cn);
			cam2.addRows(i, 0, scale);
		}
		// Set all columns to 0 in the last row to the left of the last column 
		for(int i = 0; i < cam2.getCols()-2; i++) {
			cn = ComplexNumber.multiply(cam2.get(i, i), new ComplexNumber(-1));
			scale = ComplexNumber.divide(cam2.get(cam2.getRows()-1, i), cn);
			cam2.addRows(cam2.getRows()-1, i, scale);
		}
		// Change matrix to reduced echelon form
		if(cam2.getRows() > 3) {
			for(int i = 2; i < cam2.getRows()-3; i++) {
				for(int j = 0; j < cam2.getCols()-4+i; j++) {
					cn = ComplexNumber.multiply(cam2.get(i-1, j), new ComplexNumber(-1));
					scale = ComplexNumber.divide(cam2.get(i, j), cn);
					cam2.addRows(i, i-1, scale);
				}
			}
		}
		// Set all rows in the last column above the last row to 0
		cn = ComplexNumber.multiply(cam2.get(cam2.getRows()-1, cam2.getCols()-2), new ComplexNumber(-1));
		for(int i = 0; i < cam2.getRows()-1; i++) {
			scale = ComplexNumber.divide(cam2.get(i, cam2.getCols()-2), cn);
			cam2.addRows(i, cam2.getRows()-1, scale);
		}
		// Set all columns to 0 except in the identity positions
		if(cam2.getCols() > 3) {
			for(int i = cam2.getCols()-3; i > 0; i--) {
				for(int j = 0; j < cam2.getRows()-3+i; j++) {
					cn = ComplexNumber.multiply(cam2.get(j+1, i), new ComplexNumber(-1));
					scale = ComplexNumber.divide(cam2.get(j, i), cn);
					cam2.addRows(j, j+1, scale);
				}
			}
		}
		// Create an identity matrix on the left hand side of the arguments
		for(int i = 0; i < cam2.getRows(); i++) {
			scale = ComplexNumber.divide(new ComplexNumber(1), cam2.get(i, i));
			cam2.mulRow(i, scale);
		}
		for(int i = 0; i < cam2.getRows(); i++) {
			cm.set(i, 0, cam2.get(i, cam2.getCols()-1));
		}
		return cm;
	}

}