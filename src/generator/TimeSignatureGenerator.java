package generator;

import graph.ChanseyCycle;
import rhythm.Tempo;
import rhythm.TimeSignature;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for generating time signatures
 */
public class TimeSignatureGenerator extends ChanseyCycle {

	static public final Object[] BEAT_UNIT_ARRAY = {16.0, 12.0, 8.0, 6.0, 4.0, 3.0, 2.0, 1.5, 1.0};
	
	static public final Object[] BEATS_PER_BAR_ARRAY = {1, 2, 3, 4};
	
	ChanseyCycle beatsPerBarChoice = new ChanseyCycle(BEATS_PER_BAR_ARRAY, 0);
	
	private int lastBeatUnitChoiceIndex;
	
	public TimeSignatureGenerator() {
		choices = BEAT_UNIT_ARRAY.clone();
	}
	
	/**
	 * @return a random TimeSignature
	 */
	public TimeSignature getNextTimeSignature() {
		TimeSignature timeSignature = new TimeSignature((double) fun().get(0), (int) beatsPerBarChoice.fun().get(0));
		lastBeatUnitChoiceIndex = lastReturnedIndex+7;
		return timeSignature;
	}

	/** 
	 * @return the last index chosen for the beat unit from the BEAU_UNIT_ARRAY
	 */
	public int getLastBeatUnitChoiceIndex() {
		return lastBeatUnitChoiceIndex;
	}
	
	/**
	 * @param  tempo as the tempo to get the note duration from
	 * @return a time that represents the duration of a tempo note
	 */
	public static TimeSignature getTimeSignature(Tempo tempo) {
		int beatUnitChoice = (int)(Math.round(Math.random()*(BEAT_UNIT_ARRAY.length-1)));
		int beatsPerBarChoice = (int)(Math.round(Math.random()*3)+1);
		return new TimeSignature(beatUnitChoice, beatsPerBarChoice);
	}
	
}