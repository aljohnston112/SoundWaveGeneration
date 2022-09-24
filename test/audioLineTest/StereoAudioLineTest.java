package audioLineTest;

import audioLines.StereoAudioLine;

public class StereoAudioLineTest {

	public static void main(String[] args) {
		testConstructor();
	}

	private static void testConstructor() {
		//StereoAudioLine(float samplesPerSecond, int bitsPerSample)
		float samplesPerSecond = 48000;
		int bitsPerSample = 16;
		StereoAudioLine stereoAudioLine = new StereoAudioLine(samplesPerSecond, bitsPerSample);
		System.out.println(stereoAudioLine);
	}

}
