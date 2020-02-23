package audioLines;

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
	 * @param samplesPerSecond Sample rate
	 * @param bitsPerSample Bit rate
	 */
	public MonoAudioLine(float samplesPerSecond, int bitsPerSample) {

		// Format for the audio line
		audioFormat = new AudioFormat(samplesPerSecond, bitsPerSample, CHANNELS, true, true);		

		// Direct audio stream initiation
		try {
			sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
			sourceDataLine.open();
		} catch (LineUnavailableException e) {
			System.out.println(String.format("Unable to make a %d bit rate audio line with a sample rate of %f", bitsPerSample, samplesPerSecond));

		}
	}

	/**       Plays a wave on this line
	 * @param wave is the wave to be played on this line
	 */
	public void play(double[] wave) {
		if(audioFormat.getSampleSizeInBits() == 16) {
			play(Array.doubleToShort(wave));
		}else if(audioFormat.getSampleSizeInBits() == 8) {
			play(Array.doubleToByte(wave));
		}
	}

}