package audioLines;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;

import arrays.Array;

/**
 * @author Alexander Johnston
 * @since  Copyright 2019
 *         A class for creating stereo audio lines.
 *         Sample order goes LRLR.
 */
public class StereoAudioLine extends AudioLine {

	// The number of channels in the audio line
	final static int CHANNELS = 2;

	/**       Creates a stereo audio line.
	 * @param samplesPerSecond Sample rate.
	 * @param bitsPerSample Bit rate.
	 */
	public StereoAudioLine(float samplesPerSecond, int bitsPerSample) {

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

	/**       Plays a wave on this line.
	 * @param wave The wave to be played on this line.
	 */
	private void play(double[] wave) {
		if(audioFormat.getSampleSizeInBits() == 16) {
			play(Array.doubleToShort(wave));
		} else if(audioFormat.getSampleSizeInBits() == 8) {
			play(Array.doubleToByte(wave));
		}
	}

	/**       Plays a stereo wave on this channel given the left and right channels.
	 * @param leftWave is the left wave to be played on this line.
	 * @param rightWave is the right wave to be played on this line.
	 */
	public void play(double[] leftWave, double[] rightWave) {
		double[] shortWave;
		double[] zeroWave;
		double[] leftRightWave;
		if(leftWave.length < rightWave.length) {
			zeroWave = new double[rightWave.length-leftWave.length];
			shortWave = Array.concat(leftWave, zeroWave);
			leftRightWave = stereoizeWaves(shortWave, rightWave);
		} else if(leftWave.length > rightWave.length) {
			zeroWave = new double[leftWave.length-rightWave.length];
			shortWave = Array.concat(rightWave, zeroWave);
			leftRightWave = stereoizeWaves(leftWave, shortWave);
		} else {
			leftRightWave = stereoizeWaves(leftWave, rightWave);
		}
		play(leftRightWave);
	}

	/**        Combines two waves to stereo.
	 * @param  leftWave Left wave.
	 * @param  rightWave Right wave.
	 * @return A stereo wave suitable for play back.
	 */
	private double[] stereoizeWaves(double[] leftWave, double[] rightWave) {
		double[] leftRightWave = new double[leftWave.length+rightWave.length];
		for(int i = 0; i < leftRightWave.length/2; i++) {
			leftRightWave[2*i] = leftWave[i];
			leftRightWave[2*i+1] = rightWave[i];
		}
		return leftRightWave;
	}

}