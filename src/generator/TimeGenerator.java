package generator;

import graph.ChanseyCycle;
import rhythm.Tempo;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for creating random tempos and generating a random time that belong to that Tempo
*/
public class TimeGenerator extends ChanseyCycle {
	
	/**       Creates an TimeGenerator that picks a time from a time array based on the probabilities
	 * @param times as the objects that holds the time values
	 * @param probabilities as the probabilities of picking each time from the time array
	 */
	public TimeGenerator(Object[] times, double[] probabilities) {
		this.probabilities = probabilities.clone();
		this.choices = times.clone();
	}
	
	/**       Creates an TimeGenerator that picks a time from a time array with equal probability
	 * @param times as the objects that holds the time values
	 */
	public TimeGenerator(Object[] times) {
		this.choices = times.clone();
	}
	
	/**        Gets a random time from the time array
	 * @return a time that represents a time from the time array
	 */
	public double getNextTime() {
		return (double) fun().get(0);
	}
	
	/**
	 * @param  tempo as the tempo to get the note duration from
	 * @return a time that represents the duration of a tempo note
	 */
	public static double getTime(Tempo tempo) {
		int choices = tempo.noteTimes.length-8;
		int choice = (int)(Math.round(Math.random()*choices));
		return tempo.noteTimes[choice];
	}
	
}