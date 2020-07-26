package files;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import arrays.Array;

public class Wav {

	void writeWav(File out, float samplesPerSecond, int bitsPerSample, int channels, double[] data) {
		ByteBuffer bb = null;
		if(bitsPerSample == 16) {
			short[] dataS = Array.doubleToShort(data);
			bb = ByteBuffer.allocate(dataS.length*2);
			for(short s : dataS) {
				bb.putShort(s);
			}
		} else if(bitsPerSample == 8) {
			byte[] dataB = Array.doubleToByte(data);
			bb = ByteBuffer.wrap(dataB);
		}
		AudioFormat audioFormat = new AudioFormat(samplesPerSecond, bitsPerSample, channels, true, true);	
		AudioInputStream in = new AudioInputStream(new ByteArrayInputStream(bb.array()), audioFormat, bb.array().length);
		try {
			AudioSystem.write(in, AudioFileFormat.Type.WAVE, out);
		} catch (IOException e) {
			System.out.print("Problem writing to File out passed to writeWav()");
			e.printStackTrace();
		}
	}

}
