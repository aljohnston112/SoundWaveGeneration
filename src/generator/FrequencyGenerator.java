package generator;

import graph.ChanseyCycle;
import notes.Scale;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for picking random notes from a scale
 */
public class FrequencyGenerator extends ChanseyCycle {

	/**       Creates an FrequencyGenerator that picks frequencies from a Scale with each frequency having the same probability of occurring
	 * @param scale as the scale to get the frequencies from
	 * @param firstNoteIndex as the first note index in the Scale's note array to start choosing from
	 * @param lastNoteIndex as the last note index in the Scale's note array to stop choosing from 
	 */
	public FrequencyGenerator(Scale scale, int firstNoteIndex, int lastNoteIndex) {
		// TODO ask user to set top and bottom notes
		// 320-5120 hz
		Object[] choices = new Object[lastNoteIndex-firstNoteIndex+1];
		for(int i = 0; i < choices.length; i++) {
			choices[i] = scale.notes.get(0+firstNoteIndex+i).getFrequency();
		}
		this.choices = choices.clone();
	}


	/**        Gets a random frequency from this frequency generator
	 * @return a frequency from this frequency generator
	 */
	public double getNextHertz() {
		return (double) fun().get(0);
	}

	/**        Returns a frequency that represents the given interval from the last returned frequency
	 * @param  intervalNumber as the interval number from the last returned frequency
	 * @return a frequency that represents the given interval from the last returned frequency
	 */
	public double getIntervalNote(int intervalNumber) {
		return (double) choices[lastReturnedIndex+intervalNumber-1];
	}

	/**        Picks a random frequency from a Scale with each frequency having the same probability of occurring
	 * @param  scale as the scale to get the frequencies from
	 * @param  firstNoteIndex as the first note index in the Scale's note array to start choosing from
	 * @param  lastNoteIndex as the last note index in the Scale's note array to stop choosing from 
	 * @return a random frequency from the Scale
	 */
	public static double getHertz(Scale scale, int firstNoteIndex, int lastNoteIndex) {
		// TODO ask user to set lowest and highest frequency
		// 320-5120 hz
		int choice = (int)(Math.round(Math.random()*(lastNoteIndex-firstNoteIndex)+firstNoteIndex));
		return scale.notes.get(choice).getFrequency();
	}

}