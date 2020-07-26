package rhythm;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making additive time signatures
 */
public class AdditiveTimeSignature {

	// The length of one note
	// 4 is quarter, 3 is third, etc
	double beatUnit;

	// The number of beat units in a bar
	int[] beatsPerBar;

	/**       Creates a time signature
	 * @param beatUnit As the beat unit
	 * @param beatsPerBar As the array of beats per bar to add together
	 */
	public AdditiveTimeSignature(double beatUnit, int[] beatsPerBar){
		this.beatUnit = beatUnit;
		this.beatsPerBar = beatsPerBar;
	}

	/**        Gets an array of time values representing this time signature.
	 *         Every change in bar will be proceeded by a 0.
	 * @param  tempo As the tempo
	 * @param  bars As the number of bars
	 * @param  beats As the number of beats after the bars
	 * @return An array of time values that represent this time signature
	 */
	public double[] getTime(Tempo tempo, int bars, int beats){
		double beatUnitTime = tempo.getQuarter()/(beatUnit/4);
		int beatsPerBar = 0;
		for(int i = 0; i < this.beatsPerBar.length; i++) {
			beatsPerBar += this.beatsPerBar[i];
		}
		double[] time = new double[(bars*beatsPerBar) + (beats) + (int)(Math.round((bars*beatsPerBar)/beatUnit))];
		for(int i = 0; i < time.length; i++) {
			time[i] = beatUnitTime;
			if(i%beatUnit == 0) {
				i++;
				time[i] = 0;
			}
		}
		return time;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Additive Time Signature: ");
		sb.append("(");
		for(int bpb : beatsPerBar) {
		sb.append(bpb);
		sb.append(" + ");
		}
		sb.delete(sb.length()-3, sb.length());
		
		sb.append(")/");
		sb.append(beatUnit);
		return sb.toString();
	}
	
}
