package notes;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for intervals
*/
public class Interval {

	public Note[] intervalNotes;
	
	int intervalNumber;
	
	/**       Makes an interval
	 * @param root as the root Note
	 * @param top as the top Note
	 * @param intervalNumber as the interval number
	 */
	public Interval(Note root, Note top, int intervalNumber) {
		intervalNotes = new Note[2];
		intervalNotes[0] = root;
		intervalNotes[1] = top;
		this.intervalNumber = intervalNumber;
	}

	
	/**       Creates a musical interval
	 * @param scale as the scale that contains the notes
	 * @param noteIndex as the index of the root note
	 * @param intervalNumber as the interval between notes
	 */
	public Interval(Scale scale, int noteIndex, int intervalNumber){
		intervalNotes = new Note[2];
		intervalNotes[0] = scale.notes.get(noteIndex);
		intervalNotes[1] = scale.notes.get(noteIndex+intervalNumber-1);
		this.intervalNumber = intervalNumber;
	}
	
	/**       Creates a musical interval 
	 * @param temperament as the temperament that contains the notes
	 * @param noteIndex as the index of the root note
	 * @param intervalNumber as the interval between notes
	 */
	Interval(Temperament temperament, int noteIndex, int intervalNumber){
		intervalNotes = new Note[2];
		intervalNotes[0] = temperament.notes.get(noteIndex);
		intervalNotes[1] = temperament.notes.get(noteIndex+intervalNumber-1);
		this.intervalNumber = intervalNumber;
	}
	
}