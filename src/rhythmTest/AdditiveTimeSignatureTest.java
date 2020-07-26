package rhythmTest;

import rhythm.AdditiveTimeSignature;
import rhythm.Tempo;

public class AdditiveTimeSignatureTest {
	
	public static void main(String[] args) {
		//testConstructor();
		testGetTime();
	}

	private static void testGetTime() {
		//getTime(Tempo tempo, int bars, int beats)
		//AdditiveTimeSignature(double beatUnit, int[] beatsPerBar)
				System.out.println("Creating a TimeSignature with...");
				System.out.println("Beat unit of 3...");
				System.out.println("and 6 beats per bar followed by 2 beats per bar");
				double beatUnit = 3;
				int[] beatsPerBar = {6, 2};
				AdditiveTimeSignature additiveTimeSignature = new AdditiveTimeSignature(beatUnit, beatsPerBar);
	}

	private static void testConstructor() {
		//AdditiveTimeSignature(double beatUnit, int[] beatsPerBar)
		System.out.println("Creating a TimeSignature with...");
		System.out.println("Beat unit of 3...");
		System.out.println("and 6 beats per bar followed by 2 beats per bar");
		double beatUnit = 3;
		int[] beatsPerBar = {6, 2};
		AdditiveTimeSignature additiveTimeSignature = new AdditiveTimeSignature(beatUnit, beatsPerBar);
		System.out.println(additiveTimeSignature);
	}
	
}
