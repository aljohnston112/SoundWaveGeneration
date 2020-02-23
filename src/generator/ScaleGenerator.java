package generator;

import graph.ChanseyCycle;
import notes.HarmonicMinorScale;
import notes.MajorScale;
import notes.MelodicMinorScale;
import notes.MinorScale;
import notes.Scale;
import notes.TwelveToneEqualTemperament;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for generating a random scale
 */
public class ScaleGenerator {

	static final Object[] NAMES  = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};

	static final Object[] SCALE_CHOICE  = {0, 1, 2, 3};

	ChanseyCycle rootNoteChoice = new ChanseyCycle(NAMES, 0);

	ChanseyCycle scaleChoice = new ChanseyCycle(SCALE_CHOICE, 0);

	// Create a temperament
	TwelveToneEqualTemperament twelveToneEqualTemperament;

	/**       Creates a ScaleGenerator
	 * @param middleA as the frequency of middle a
	 * @param samplesPerSecond as the sample rate
	 */
	public ScaleGenerator(double middleA, float samplesPerSecond) {
		twelveToneEqualTemperament = new TwelveToneEqualTemperament(middleA, samplesPerSecond);
	}

	/**
	 * @return a random scale
	 */
	public Scale getNextScale() {

		// Create a scale
		char tonicLetter = (char) rootNoteChoice.fun().get(0);

		switch((int)scaleChoice.fun().get(0)) {
		case 0:
			return new HarmonicMinorScale(twelveToneEqualTemperament, tonicLetter);
		case 1:
			return new MajorScale(twelveToneEqualTemperament, tonicLetter);
		case 2:
			return new MelodicMinorScale(twelveToneEqualTemperament, tonicLetter);
		case 3:
			return new MinorScale(twelveToneEqualTemperament, tonicLetter);
		default:
			return new MajorScale(twelveToneEqualTemperament, tonicLetter);
		}
	}

}