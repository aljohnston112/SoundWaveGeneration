package matrices;

import algorithms.ComplexNumber;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making complex matrices
 */
public class ComplexMatrix extends Matrix{

	ComplexNumber[][] m;
	
	/**       Creates an empty complex matrix
	 */
	public ComplexMatrix() {
	}

	/**       Creates an empty complex matrix
	 * @param rows The number of rows to be created
	 * @param cols The number of columns to be created
	 */
	public ComplexMatrix(int rows, int cols){
		m = new ComplexNumber[rows][cols];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				m[i][j] =  new ComplexNumber();
			}
		}
		this.rows = rows;
		this.cols = cols;
	}

	/**       Creates a complex matrix 
	 * @param m The matrix
	 */
	public ComplexMatrix(ComplexNumber[][] m){
		this.rows = m.length;
		this.cols = m[0].length;
		this.m = new ComplexNumber[rows][cols];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				this.m[i][j] =  m[i][j];
			}
		}
	}
	
	/**       Creates a complex matrix with one row
	 * @param m The matrix
	 */
	public ComplexMatrix(ComplexNumber[] m){
		this.rows = m.length;
		this.cols = 1;
		this.m = new ComplexNumber[rows][cols];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				this.m[i][j] =  m[i];
			}
		}
	}

	/**       Sets a value in this matrix
	 *        WARNING Will throw an exception if [row][col] is not an index in this matrix
	 * @param row The row to be set
	 * @param col The column to be set
	 * @param a The value to set the specified index to
	 */
	public void set(int row, int col, ComplexNumber a){
		m[row][col] = a;
	}

	/**       Sets a value in this matrix
	 *        WARNING Will throw an exception if [row][col] is not an index in this matrix
	 * @param row The row to be set
	 * @param col The column to be set
	 * @param a The value to set the specified index to
	 */
	public void set(int row, int col, double a){
		m[row][col] = new ComplexNumber(a, 0);
	}

	/**        Gets a value from this matrix from the specified index
	 * @param  row The row to get the value from
	 * @param  col The column to get the value from
	 * @return The value from the specified index
	 */
	public ComplexNumber get(int row, int col){
		return m[row][col];
	}
	
	/**       
	 * @return  Returns the complex array backing this matrix 
	 */
	public ComplexNumber[][] get(){
		return m;
	}
	
	/**        Gets a row from this matrix
	 * @param  row The row to get from this matrix
	 *         WARNING This method will throw an exception if row is not in the matrix
	 * @return The array backing the row 
	 */
	public ComplexNumber[] getRow(int row){
		ComplexNumber[] cn = new ComplexNumber[m[row].length];
		for(int i = 0; i < m[row].length; i++) {
			cn[i] = m[row][i];
		}
		return cn;
	}

	/**        Gets a matrix from this matrix from the specified indices
	 * @param  row The starting row to get the matrix from
	 * @param  col The starting column to get the matrix from
	 * @param  row2 The ending row to get the matrix from
	 * @param  col2 The ending column to get the matrix from
	 * @return The matrix from the specified indices
	 */
	public ComplexMatrix get(int row, int col, int row2, int col2){
		ComplexMatrix a = new ComplexMatrix(row2-row+1, col2-col+1);
		for(int i = 0; i < a.rows; i++) {
			for(int j = 0; j < a.cols; j++) {
				a.set(i, j , m[row+i][col+j]);
			}
		}
		return a;
	}

	/**        Switches two rows in this matrix
	 *         WARNING will throw an exception if r1 or r2 are not in matrix
	 * @param  r1 The row to switch with row r2
	 * @param  r2 The row to switch with row r1
	 */
	public void switchRows(int r1, int r2){
		ComplexNumber cm;
		for(int i = 0; i < this.cols; i++) {
			cm = get(r1, i);
			set(r1, i, get(r2, i));
			set(r2, i, cm);
		}
	}
	
	/**        Replaces a row in this matrix by the addition of it to a multiple of another row
	 *         WARNING will throw an exception if r1 and r2 are not in matrix
	 * @param  r1 The row to replace with r1+(d*r2)
	 * @param  r2 The row to be added to r1
	 * @param  m The multiples of r2 to be added to r1
	 */
	public void addRows(int r1, int r2, double m){
		for(int i = 0; i < this.cols; i++) {
			set(r1, i, ComplexNumber.add(ComplexNumber.multiply(get(r2, i), new ComplexNumber(m)), get(r1, i)));
		}
	}
	
	/**        Replaces a row in this matrix by the addition of it to a multiple of another row
	 *         WARNING will throw an exception if r1 and r2 are not in matrix
	 * @param  r1 The row to replace with r1+(d*r2)
	 * @param  r2 The row to be added to r1
	 * @param  m The multiples of r2 to be added to r1
	 */
	public void addRows(int r1, int r2, ComplexNumber cm){
		for(int i = 0; i < this.cols; i++) {
			set(r1, i, ComplexNumber.add(ComplexNumber.multiply(get(r2, i), cm), get(r1, i)));
		}
	}
	
	/**        Multiplies a row in this matrix by a number
	 *         WARNING will throw an exception if row is not in matrix
	 * @param  r The row to multiply by d
	 * @param  d The number to multiply r by
	 */
	public void mulRow(int r, double d){
		for(int i = 0; i < this.cols; i++) {
			set(r, i, ComplexNumber.multiply(get(r, i), new ComplexNumber(d)));
		}
	}
	
	/**        Multiplies a row in this matrix by a complex number
	 *         WARNING will throw an exception if row is not in matrix
	 * @param  r The row to multiply by cm
	 * @param  cm The complex number to multiply r by
	 */
	public void mulRow(int r, ComplexNumber cm){
		for(int i = 0; i < this.cols; i++) {
			set(r, i, ComplexNumber.multiply(get(r, i), cm));
		}
	}
	
	
	/**       Deletes a row
	 *        WARNING Throws an exception if r is not a row in this matrix
	 * @param r The row to delete
	 */
	public void delRow(int r) {
		ComplexNumber[][] cn = new ComplexNumber[rows-1][cols];
		int k = 0;
		for(int i = 0; i < cn.length; i++) {
			if(i == r) {
				i++;
			}
			for(int j = 0; j < cn[0].length; j++) {
				cn[k][j] = m[i][j];
			}
			k++;
		}
		m = cn;
		this.rows = this.rows-1;
	}
	
	/**       Deletes a column
	 *        WARNING Throws an exception if c is not a column in this matrix
	 * @param c The column to delete
	 */
	public void delCol(int c) {
		ComplexNumber[][] cn = new ComplexNumber[rows][cols-1];
		int k = 0;
		for(int i = 0; i < cn.length; i++) {
			
			for(int j = 0; j < cn[0].length; j++) {
				if(j == c) {
					j++;
				}
				cn[i][k] = m[i][j];
				k++;
			}
			k = 0;
		}
		m = cn;
		this.cols = this.cols-1;
	}
	
	/**        Returns an entry wise sum of two matrices
	 *         WARNING will return null if a and b do not have the same number of rows and columns
	 * @param  a The matrix to be added to b
	 * @param  b The matrix to be added to a
	 * @return The entry wise sum of a and b
	 */
	public static ComplexMatrix add(ComplexMatrix a, ComplexMatrix b){
		ComplexMatrix c = null;
		if(a.cols == b.cols && a.rows == b.rows) {
			c = new ComplexMatrix(a.rows, a.cols);
			for(int i = 0; i < a.rows; i++) {
				for(int j = 0; j < a.cols; j++) {
					c.set(i,j, ComplexNumber.add(a.get(i,j), b.get(i,j)));
				}
			}
		}
		return c;
	}

	/**        Returns an direct sum of two matrices
	 * @param  a The matrix to be added to b
	 * @param  b The matrix to be added to a
	 * @return The direct sum of a and b
	 */
	public static ComplexMatrix addDirect(ComplexMatrix a, ComplexMatrix b){
		ComplexMatrix c = new ComplexMatrix(a.rows+b.rows, a.cols+b.cols);
		for(int l = 0; l < a.rows; l++) {
			for(int k = 0; k < a.cols; k++) {
				c.set(l,k, a.get(l,k));
			}
			for(int j = 0; j < b.cols; j++) {
				c.set(l, j+a.cols, new ComplexNumber(0, 0));
			}
		}
		for(int l = 0; l < b.rows; l++) {
			for(int m = 0; m < a.cols; m++) {
				c.set(l+a.rows, m, new ComplexNumber(0, 0));
			}
			for(int j = 0; j < b.cols; j++) {
				c.set(l+a.rows, j+a.cols, b.get(l,j));
			}
		}
		return c;
	}

	/**        Gets an identity matrix 
	 * @param  row The rows in the matrix
	 * @param  col The columns in the matrix
	 * @return The identity matrix
	 */
	public static ComplexMatrix getIM(int row, int col){
		ComplexNumber[][] m = new ComplexNumber[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				m[i][j] =  new ComplexNumber();
			}
		}
		for(int i = 0; i < row; i++) {
			m[i][i] =  new ComplexNumber(1, 0);
		}
		return new ComplexMatrix(m);
	}	

	/**        Returns the multiplication of a matrix by a scalar
	 * @param  a The matrix to be multiplied by the scalar
	 * @param  s The scalar to multiply by the matrix
	 * @return The multiplication of a by b
	 */
	public static ComplexMatrix mulS(ComplexMatrix a, ComplexNumber s){
		ComplexMatrix c = new ComplexMatrix(2, 2);
		for(int i = 0; i < a.rows; i++) {
			for(int j = 0; j < a.cols; j++) {
				c.set(i,j, ComplexNumber.multiply(a.get(i,j), s));
			}
		}
		return c;
	}

	/**        Returns the transposition of a matrix
	 * @param  a The matrix to be transposed
	 * @return The transposition of a
	 */
	public static ComplexMatrix transpose(ComplexMatrix a){
		ComplexMatrix c = new ComplexMatrix(a.cols, a.rows);
		for(int i = 0; i < a.rows; i++) {
			for(int j = 0; j < a.cols; j++) {
				c.set(j,i, a.get(i,j));
			}
		}
		return c;
	}

	/**        Returns the multiplication of 2 matrices
	 *         WARNING will return null if the number of columns in a doesn't equal the number of rows in b
	 * @param  a The matrix to be multiplied by b
	 * @param  b The matrix to be multiplied by a
	 * @return The multiplication of a by b
	 */
	public static ComplexMatrix prod(ComplexMatrix a, ComplexMatrix b){
		ComplexMatrix c = new ComplexMatrix(a.rows, b.cols);
		if(a.cols == b.rows) {
			for(int i = 0; i < a.rows; i++) {
				for(int j = 0; j < b.cols; j++) {
					for(int m = 0; m < a.cols; m++) {
						c.set(i, j, ComplexNumber.add(c.get(i, j), (ComplexNumber.multiply(a.get(i,m), b.get(m, j)))));
					}
				}
			}
		}
		return c;
	}

	/**        Returns the entry wise multiplication of 2 matrices
	 *         WARNING will return null if the number of columns and rows in a doesn't equal the number of columns and rows in b
	 * @param  a The matrix to be multiplied by b
	 * @param  b The matrix to be multiplied by a
	 * @return The multiplication of a by b
	 */
	public static ComplexMatrix mul(ComplexMatrix a, ComplexMatrix b){
		ComplexMatrix c = null;
		if(a.cols == b.cols && a.rows == b.rows) {
			c = new ComplexMatrix(a.rows, b.cols);
			for(int i = 0; i < a.rows; i++) {
				for(int j = 0; j < a.cols; j++) {
					c.set(i, j, (ComplexNumber.multiply(a.get(i,j), b.get(i, j))));
				}
			}
		}
		return c;
	}

	/**        Returns the Frobenius inner product of 2 matrices
	 *         WARNING will return null if the number of columns and rows in a doesn't equal the number of columns and rows in b
	 * @param  a The matrix to be multiplied by b
	 * @param  b The matrix to be multiplied by a
	 * @return The Frobenius inner product of a and b
	 */
	public static ComplexNumber frobInProd(ComplexMatrix a, ComplexMatrix b){
		ComplexNumber c = null;
		if(a.cols == b.cols && a.rows == b.rows) {
			c = new ComplexNumber();
			for(int i = 0; i < a.rows; i++) {
				for(int j = 0; j < a.cols; j++) {
					c  = ComplexNumber.add(c, ComplexNumber.multiply(ComplexNumber.getConjugate(a.get(i,j)), b.get(i, j)));
				}
			}
		}
		return c;
	}

	/**        Returns the Kronecker product of 2 matrices
	 * @param  a The matrix to be multiplied by b
	 * @param  b The matrix to be multiplied by a
	 * @return The Kronecker product of a by b
	 */
	public static ComplexMatrix kronProd(ComplexMatrix a, ComplexMatrix b){
		ComplexMatrix c = new ComplexMatrix(a.rows*b.rows, a.cols*b.cols);
		for(int q = 0; q < a.cols + b.cols; q++) {
			for(int p = 0; p < b.rows+a.rows; p++) {
				c.set(p, q, ComplexNumber.multiply(a.get((int)Math.floor(p/a.rows) ,(int)Math.floor(q/a.cols)), b.get((p%b.rows), (q%b.cols))));
			}
		}
		return c;
	}
	
	/**        Returns the Kronecker sum of 2 matrices
	 *         WARNING a and b must be square matrices to be added and null will be returned if they aren't
	 * @param  a The matrix to be added to b
	 * @param  b The matrix to be added to a
	 * @return The Kronecker sum of a and b
	 */
	public static ComplexMatrix kronSum(ComplexMatrix a, ComplexMatrix b){
		if(a.rows == a.cols && b.rows == b.cols) {
		return add(kronProd(a, getIM(b.rows, b.cols)),kronProd(getIM(a.rows, a.cols), b));
		} else {
			return null;
		}
	}
	
}