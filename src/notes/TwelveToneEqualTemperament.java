package notes;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for creating a 12 tone equal temperament
 */
public class TwelveToneEqualTemperament extends EqualTemperament {

	/**       Creates a 12 tone equal temperament
	 * @param middleA is middle A
	 * @param samplesPerSecond the sample rate 
	 */
	public TwelveToneEqualTemperament(double middleA, float samplesPerSecond) {
		super(middleA, 12, samplesPerSecond);
	}

	/**       Adds note names for 12-TET (A, A#, B, C, C#, D, D#, E, F, F#, G, G#)
	 * 
	 */
	protected void addNames(){
		String[] names  = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
		for(int j = 0; j < notes.size(); j++) {
			notes.get(j).setName(names[j%12]);
		}
	}
}