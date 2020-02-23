package rhythm;

import java.util.ArrayList;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making mixed time signatures
 */
public class MixedTimeSignature {

	// Holds the time signatures that make up this mixed time signature
	ArrayList<TimeSignature> timeSignature = new ArrayList<TimeSignature>();

	/**       Creates a mixed time signature from an array of time signatures
	 * @param timeSignature The array of time signatures
	 */
	public MixedTimeSignature(TimeSignature[] timeSignature){
		for(int i = 0; i < timeSignature.length; i++) {
			this.timeSignature.add(timeSignature[i]);
		}
	}

	/**       creates a mixed time signature from and array list of time signatures
	 * @param timeSignature The array list of time signatures
	 */
	public MixedTimeSignature(ArrayList<TimeSignature> timeSignature){
		for(int i = 0; i < timeSignature.size(); i++) {
			this.timeSignature.add(timeSignature.get(i));
		}
	}

	/**        Gets an array of times in seconds that represents this mixed time signature
	 * @param  tempo The tempo
	 * @param  bars The number of bars to get
	 * @param  beats The number of beats after the last bar to get
	 * @return An array of time values in seconds that represents this time signature
	 */
	public double[] getTime(Tempo tempo, int bars, int beats){

		double[] beatUnitTime = new double[timeSignature.size()];
		int[] beatsPerBar = new int[timeSignature.size()];
		double[] time;
		for(int i = 0; i < timeSignature.size(); i++) {
			beatUnitTime[i] = tempo.getQuarter()/(timeSignature.get(i).beatUnit/4);
			beatsPerBar[i] = timeSignature.get(i).beatsPerBar;
		}
		int totalBeats = 0;
		for(int l = 0; l < bars; l++) {
			totalBeats +=  beatsPerBar[l%beatsPerBar.length];
		}
		time = new double[totalBeats+beats];
		for(int i = 0; i < time.length; i+=0) {
			for(int k = 0; k < beatsPerBar.length && i < time.length; k++) {
				for(int j = 0; j < beatsPerBar[k] && i < time.length; j++) {
					time[i] = beatUnitTime[k];	
					i++;
				}
				
			}
		}
		return time;
	}
}