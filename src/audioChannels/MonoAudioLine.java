package audioChannels;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import arrays.Array;

/**
 * @author Alexander Johnston
 * Copyright 2019
 * A class for creating mono audio lines
 */
public class MonoAudioLine extends AudioLine {

	// The number of channels in the audio line
	final static int CHANNELS = 1;

	/**       Creates a mono audio line
	 * @param sr Sample rate
	 * @param br Bit rate
	 */
	public MonoAudioLine(float sr, int br) {

		// Format for the audio line
		af = new AudioFormat(sr, br, CHANNELS, true, true);		

		// Direct audio stream initiation
		try {
			sdl = AudioSystem.getSourceDataLine(af);
			sdl.open();
		} catch (LineUnavailableException e) {
			System.out.println(String.format("Unable to make a %d bit rate audio line with a sample rate of %f", br, sr));

		}
	}

	/**       Plays a wave on this line
	 * @param w is the wave to be played on this line
	 */
	public void play(double[] w) {
		if(af.getSampleSizeInBits() == 16) {
			play(Array.doubleToShort(w));
		}else if(af.getSampleSizeInBits() == 8) {
			play(Array.doubleToByte(w));
		}
	}

}