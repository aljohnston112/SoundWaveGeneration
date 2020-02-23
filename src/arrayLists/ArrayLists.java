package arrayLists;

import java.util.ArrayList;

public class ArrayLists {

	/**        Gets the number of columns from the array list
	 * @param  arrayList as the array list
	 * @return the number of columns in the array list
	 */
	public static int getNumberOfColumns(ArrayList<ArrayList<Object>> arrayList) {
		int columns = 0;
		for(int i = 0; i < arrayList.size(); i++) {
			columns = Math.max(columns, arrayList.get(i).size());
		}
		return columns;
	}
	
}
