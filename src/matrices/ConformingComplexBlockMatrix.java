package matrices;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making conforming complex block matrices
 */
public class ConformingComplexBlockMatrix {

	private int rows;

	private int cols;

	private int rowpart;

	private int colpart;

	ComplexMatrix m[][];

	/**       Creates an empty complex block matrix
	 * @param rows The number of rows to be created in each matrix
	 * @param cols The number of columns to be created in each matrix
	 * @param rowpart The number of matrix block in a row
	 * @param colpart The number of matrix blocks in a column
	 */
	public ConformingComplexBlockMatrix(int rows, int cols, int rowpart, int colpart){
		m = new ComplexMatrix[rowpart][colpart];
		for(int i = 0; i < rowpart; i++) {
			for(int j = 0; j < colpart; j++) {
				m[i][j] =  new ComplexMatrix(rows, cols);
			}
		}
		this.rows = rows;
		this.cols = cols;
		this.rowpart = rowpart;
		this.colpart = colpart;
	}

	/**       Creates a complex block matrix only if the rows and columns in m are divisible by rowpart and colpart respectively
	 *        WARNING if rows and columns in m are not divisible by rowpart and colpart respectively the returned matrix will be empty
	 * @param rowpart The number of matrix blocks in a row
	 * @param colpart The number of matrix columns in a column
	 * @param m The block matrix
	 */
	public ConformingComplexBlockMatrix(int rowpart, int colpart, ComplexMatrix m){
		this.m = new ComplexMatrix[rowpart][colpart];
		if(m.getRows()%rowpart == 0 && m.getCols()%colpart == 0) {
		this.rows = m.getRows()/rowpart;
		this.cols = m.getCols()/colpart;
		for(int i = 0; i < rowpart; i++) {
			for(int j = 0; j < colpart; j++) {
				this.m[i][j] = m.get(i*this.rows, j*this.cols, (i+1)*this.rows-1, (j+1)*this.cols-1);
			}
		}
		this.rowpart = rowpart;
		this.colpart = colpart;
		}
	}
	
	/**       Creates a complex block matrix 
	 *        WARNING will throw exceptions or only copy a subset of the matrices if rowpart and colpart are not accurate representations of the number of rows and columns in m respectively
	 * @param rowpart The number of block matrices in a row
	 * @param colpart The number of block matrices in a column
	 * @param m The block matrix array
	 */
	public ConformingComplexBlockMatrix(int rowpart, int colpart, ComplexMatrix[][] m){
		this.m = new ComplexMatrix[rowpart][colpart];
		this.rows = m.length;
		this.cols = m[0].length;
		for(int i = 0; i < rowpart; i++) {
			for(int j = 0; j < colpart; j++) {
				set(i,j, m[i][j]);
			}
		}
		this.rowpart = rowpart;
		this.colpart = colpart;
	}

	/**       Sets a matrix into this block matrix only if the number of rows and columns in the matrix matches 
	 *        the number of rows and columns contained in the matrices in this block matrix
	 *        WARNING Will throw an exception if [rowpart][colpart] is not an index in this block matrix
	 * @param rowpart The row partition to be set
	 * @param colpart The column partition to be set
	 * @param a The value to set the specified index to
	 */
	public void set(int rowpart, int colpart, ComplexMatrix a){
		if(a.getRows() == this.rows && a.getCols() == this.cols) {
		m[rowpart][colpart] = a;
		}
	}

	/**        Gets a matrix from this block matrix from the specified index
	 * @param  rowpart The row to get the value from
	 * @param  colpart The column to get the value from
	 * @return The value from the specified index
	 */
	public ComplexMatrix get(int rowpart, int colpart){
		return m[rowpart][colpart];
	}

	/**        Returns the multiplication of 2 block matrices
	 *         WARNING will return null if the number of columns in a doesn't equal the number of rows in b
	 * @param  a The block matrix to be multiplied by b
	 * @param  b The block matrix to be multiplied by a
	 * @return The multiplication of a by b
	 */
	public static ConformingComplexBlockMatrix mul(ConformingComplexBlockMatrix a, ConformingComplexBlockMatrix b){
		ConformingComplexBlockMatrix c = new ConformingComplexBlockMatrix(a.rows, b.cols, a.rowpart, b.colpart);
		if(a.colpart == b.rowpart) {
			for(int i = 0; i < a.rowpart; i++) {
				for(int j = 0; j < b.colpart; j++) {
					for(int m = 0; m < a.colpart; m++) {
						c.set(i, j, ComplexMatrix.add(c.get(i, j), (ComplexMatrix.prod(a.get(i,m), b.get(m, j)))));
					}
				}
			}
		}
		return c;
	}
}
