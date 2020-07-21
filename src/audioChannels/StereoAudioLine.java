package audioChannels;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;

import arrays.Array;
import waves.Wave;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for creating stereo audio lines
 *         Sample order goes LRLRLRLR
 */
public class StereoAudioLine extends AudioLine{

	// The number of channels in the audio line
	final static int CHANNELS = 2;

	/**       Creates a stereo audio line
	 * @param sr Sample rate
	 * @param br Bit rate
	 */
	public StereoAudioLine(float sr, int br) {

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
	private void play(double[] w) {
		if(af.getSampleSizeInBits() == 16) {
			play(Array.doubleToShort(w));
		}else if(af.getSampleSizeInBits() == 8) {
			play(Array.doubleToByte(w));
		}
	}

	/**       Plays a stereo wave on this channel given the left and right channels
	 * @param wl is the left wave to be played on this line
	 * @param wr is the right wave to be played on this line
	 */
	public void play(double[] wl, double[] wr) {
		double[] sw;
		double[] wz;
		double[] wlr;
		if(wl.length < wr.length) {
			wz = new double[wr.length-wl.length];
			sw = Array.concat(wl, wz);
			wlr = stereoizeWaves(sw, wr);
		} else if(wl.length > wr.length) {
			wz = new double[wl.length-wr.length];
			sw = Array.concat(wr, wz);
			wlr = stereoizeWaves(wl, sw);
		} else {
			wlr = stereoizeWaves(wl, wr);
		}
		play(wlr);
	}

	/**        Combines two wave to stereo
	 * @param  wl Left wave
	 * @param  wr Right wave
	 * @return A stereo wave suitable for play back
	 */
	private double[] stereoizeWaves(double[] wl, double[] wr) {
		double[] wlr = new double[wl.length+wr.length];
		for(int i = 0; i < wlr.length/2; i++) {
			wlr[2*i] = wl[i];
			wlr[2*i+1] = wr[i];
		}
		return wlr;
	}

}