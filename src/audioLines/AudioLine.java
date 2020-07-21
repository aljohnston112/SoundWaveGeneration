package audioLines;

import java.nio.ByteBuffer;
import java.util.Objects;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

/**
 * @author Alexander Johnston
 * @since  Copyright 2019
 *         A class for audio lines to extend.
 */
abstract public class AudioLine {

	// The audioFormat
	AudioFormat audioFormat;

	// The audio line
	protected SourceDataLine sourceDataLine;
	
	/** Prints the supported source data lines to the console.
	 * 
	 */
	public static void printSupportedSourceDataLines() {
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for(Mixer.Info mixerInfo : mixerInfos) {
			System.out.print(mixerInfo.toString());
			System.out.print("\n");
			 Line.Info[] lineInfos = AudioSystem.getMixer(mixerInfo).getSourceLineInfo();
			for(Line.Info lineInfo : lineInfos) {
				if(lineInfo instanceof DataLine.Info) {
					AudioFormat[] audioFormats = ((DataLine.Info) lineInfo).getFormats();
					for(AudioFormat audioFormat : audioFormats) {
					System.out.print(audioFormat.toString());
					System.out.print("\n");
					}
				}	
			}
		}
	}

	/**        Plays a 16-bit wave on this channel.
	 * @param  wave The 16-bit wave to be played on this channel.
	 * @throws NullPointerException if wave is null.
	 */
	protected void play(short[] wave) {
		Objects.requireNonNull(wave);
		// Invariants secured
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(2*wave.length);
		for(int i = 0; i < wave.length; i++) {
			byteBuffer.putShort((i*2), wave[i]);
		}
		byte[] output = new byte[byteBuffer.capacity()];
		byteBuffer.get(output);
		sourceDataLine.start();
		sourceDataLine.write(output, 0, output.length);
		sourceDataLine.drain();
	}

	/**        Plays a 8-bit wave on this channel.
	 * @param  wave The 8-bit wave to be played on this channel.
	 * @throws NullPointerException if wave is null.
	 */
	protected void play(byte[] wave) {
		Objects.requireNonNull(wave);
		// Invariants secured
		
		sourceDataLine.start();
		sourceDataLine.write(wave, 0, wave.length);
		sourceDataLine.drain();
	}

}