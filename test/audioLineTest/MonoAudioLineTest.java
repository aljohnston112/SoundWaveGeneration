package audioLineTest;

import audioLines.MonoAudioLine;

public class MonoAudioLineTest {

	public static void main(String[] args) {
		testConstructor();
		
	}

	private static void testConstructor() {
		//MonoAudioLine(float samplesPerSecond, int bitsPerSample)		
		float samplesPerSecond = 48000;
		int bitsPerSample = 16;
		MonoAudioLine monoAudioLine = new MonoAudioLine(samplesPerSecond, bitsPerSample);
		System.out.println(monoAudioLine);
	}

}
