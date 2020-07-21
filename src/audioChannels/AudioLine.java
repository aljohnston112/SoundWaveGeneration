package audioChannels;

import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for audio channels to extend
 */
abstract public class AudioLine {

	// The audioFromat
	AudioFormat af;

	// The audio line
	protected SourceDataLine sdl;

	/**        Plays a 16-bit wave on this channel
	 * @param  s is the 16-bit wave to be played on this channel
	 * @throws InterruptedException 
	 */
	protected void play(short[] s) {
		ByteBuffer bb = ByteBuffer.allocate(2*s.length);
		for(int i = 0; i < s.length; i++) {
			bb.putShort((i*2), s[i]);
		}
		byte[] b = new byte[bb.capacity()];
		bb.get(b);
		sdl.start();
		sdl.write(b, 0, b.length);
		sdl.drain();
	}

	/** Plays a 8-bit wave on this channel
	 * @param wave is the 8-bit wave to be played on this channel
	 */
	protected void play(byte[] wave) {
		sdl.start();
		sdl.write(wave, 0, wave.length);
		sdl.drain();
	}


}