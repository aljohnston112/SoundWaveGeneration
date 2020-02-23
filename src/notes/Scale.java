package notes;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for musical scales
 */
public class Scale extends Temperament {

	// The tonic
	char tonicLetter;

	/**       Adds a note and name to the scale
	 * @param i The index of the notes and names of the 12 tone equal temperament to add
	 * @param ttet The 12 tone equal temperament
	 */
	protected void addNoteAndName(int i, TwelveToneEqualTemperament twelveToneEqualTemperament) {
		notes.add(new Note(twelveToneEqualTemperament.notes.get(i).getName(), twelveToneEqualTemperament.notes.get(i).getFrequency()));
	}

	/**       Adds a note and name to the scale
	 * @param j The index of the note and name arrays to add the note and names to
	 * @param i The index of the notes and names of the 12 tone equal temperament to add
	 * @param twelveToneEqualTemperament The 12 tone equal temperament
	 */
	protected void addNoteAndName(int j, int i, TwelveToneEqualTemperament twelveToneEqualTemperament) {
		notes.set(j, new Note(twelveToneEqualTemperament.notes.get(i).getName(), twelveToneEqualTemperament.notes.get(i).getFrequency()));
	}

}
