package rhythm;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for creating tempos and extracting time values of note divisions
 */
public class Tempo {

	// The time interval of the quarter note in seconds
	private double quarterNote;

	public double[] noteTimes = new double[16];

	/**
	 * @param beatsPerMinute The beats per minute of the tempo
	 */
	public Tempo(double beatsPerMinute){
		this.quarterNote = (60.0/beatsPerMinute);
		noteTimes[15] = this.getWhole();
		noteTimes[14] = this.getTwoThirds();
		noteTimes[13] = this.getHalf();
		noteTimes[12] = this.getThird();
		noteTimes[11] = this.getQuarter();
		noteTimes[10] = this.getSixth();
		noteTimes[9] = this.getEighth();
		noteTimes[8] = this.getTwelth();
		noteTimes[7] = this.getSixteenth();
		noteTimes[6] = this.getTwentyFourth();
		noteTimes[5] = this.getThirtySecond();
		noteTimes[4] = this.getFortyEighth();
		noteTimes[3] = this.getSixtyFourth();
		noteTimes[2] = this.getNinetySixth();
		noteTimes[1] = this.getOneTwentyEighth();
		noteTimes[0] = this.getOneNinetySecond();
	}

	/**
	 * @return The time interval of the whole note in seconds
	 */
	public double getWhole(){
		return quarterNote*4;
	}

	/**
	 * @return The time interval of the two-thirds note in seconds
	 */
	public double getTwoThirds(){
		return quarterNote*8.0/3.0;
	}

	/**
	 * @return The time interval of the half note in seconds
	 */
	public double getHalf(){
		return quarterNote*2.0;
	}

	/**
	 * @return The time interval of the third note in seconds
	 */
	public double getThird(){
		return quarterNote*4.0/3.0;
	}

	/**
	 * @return The time interval of the quarter note in seconds
	 */
	public double getQuarter(){
		return quarterNote;
	}

	/**
	 * @return The time interval of the sixth note in seconds
	 */
	public double getSixth(){
		return quarterNote*4.0/6.0;
	}

	/**
	 * @return The time interval of the eighth note in seconds
	 */
	public double getEighth(){
		return quarterNote/2.0;
	}

	/**
	 * @return The time interval of the twelfth note in seconds
	 */
	public double getTwelth(){
		return quarterNote*4.0/12.0;
	}

	/**
	 * @return The time interval of the sixteenth note in seconds
	 */
	public double getSixteenth(){
		return quarterNote/4.0;
	}

	/**
	 * @return The time interval of the twenty-fourth note in seconds
	 */
	public double getTwentyFourth(){
		return quarterNote*4.0/24.0;
	}

	/**
	 * @return The time interval of the thirty-second note in seconds
	 */
	public double getThirtySecond(){
		return quarterNote/8.0;
	}

	/**
	 * @return The time interval of the forty-eighth note in seconds
	 */
	public double getFortyEighth(){
		return quarterNote*4.0/48.0;
	}

	/**
	 * @return The time interval of the sixty-fourth note in seconds
	 */
	public double getSixtyFourth(){
		return quarterNote/16.0;
	}

	/**
	 * @return The time interval of the ninety-sixth note in seconds
	 */
	public double getNinetySixth(){
		return quarterNote*4.0/96.0;
	}

	/**
	 * @return The time interval of the one-hundred-twenty-eighth note in seconds
	 */
	public double getOneTwentyEighth(){
		return quarterNote/32.0;
	}

	/**
	 * @return The time interval of the one-hundred-ninety-second note in seconds
	 */
	public double getOneNinetySecond(){
		return quarterNote*4.0/192.0;
	}

}