package notes;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class GetNoteSequenceDurationThread implements Callable<Double> {

	ArrayList<ArrayList<Note>> notes;
	int i;
	double t =0;

	/**       Makes a thread that gets the duration of a note sequence
	 * @param notes as the 3d note ArrayList
	 * @param i as the row to get the duration of
	 */
	GetNoteSequenceDurationThread(ArrayList<ArrayList<Note>> notes, int i){
		this.notes = notes;
		this.i = i;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Double call() throws Exception {
		for(int j = 0; j < notes.get(i).size(); j++) {
			t+=notes.get(i).get(j).getDuration();
		}		
		return t;
	}
}